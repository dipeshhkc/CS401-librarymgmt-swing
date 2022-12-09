package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	public void login(String id, String password) throws LoginException {
		if (id.isBlank())
			throw new LoginException("ID is empty");
		if (password.isBlank())
			throw new LoginException("Password is empty");

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();

	}

	public BookCopy checkIfBookCopyAvailable(String libraryMemberId, String isbn) throws Exception {
		if (!checkIfLoginIdExists(libraryMemberId))
			throw new Exception("You're not a member of our library");

		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		if (!bookMap.containsKey(isbn))
			throw new Exception("Book does not exist!!!");

		Book book = bookMap.get(isbn);
		if (!book.isAvailable())
			throw new Exception("Requested book with ISBN " + isbn + " is not available");
		return addToCheckout(book.getNextAvailableCopy(), libraryMemberId);
	}

	private BookCopy addToCheckout(BookCopy bCopy, String libraryMemberId) throws Exception {
		CheckoutRecordEntry cre = new CheckoutRecordEntry(bCopy, libraryMemberId);
		DataAccess da = new DataAccessFacade();
		try {
			da.saveToCheckoutRecord(libraryMemberId, cre);
			HashMap<String, Book> bookMap = da.readBooksMap();
			Book book = bCopy.getBook();
			bCopy.changeAvailability();
			book.updateCopies(bCopy);
			bookMap.put(book.getIsbn(), book);
			da.updateBook(book);
		} catch (Exception e) {
			throw new Exception("Checkout Error");
		}
		return bCopy;
	}

	public List<CheckoutRecordEntry> getCheckoutRecordByMemberId(String libraryMemberId) {
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecordEntry> checkoutList = da.getCheckoutRecordByMemberId(libraryMemberId);
		return checkoutList;
	}

	public List<Object> getOverdueBooks(String isbnNumber) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, List<CheckoutRecordEntry>> checkoutRecordList = da.readCheckoutRecord();
		List<Object> overDueList = new ArrayList<Object>();
		LocalDate currentDate = LocalDate.now();
		checkoutRecordList.forEach((k, v) -> {
			v.forEach(e -> {
				if ((e.getbCopy().getBook().getIsbn().equals(isbnNumber)) && ((e.getDueDate()).isBefore(currentDate))) {
					Book b = e.getbCopy().getBook();
					String isbn = b.getIsbn();
					String title = b.getTitle();
					int copyNumber = e.getbCopy().getCopyNum();
					String memberId = e.getMemberId();
					LocalDate dueDate = e.getDueDate();

					Object obj = new Object[] { isbn, title, copyNumber, memberId, dueDate };
					overDueList.add(obj);
				}
			});
		});

		// to access
//		Object[] a = (Object[]) overDueList.get(0);
//		System.out.println(a[0]);
		return overDueList;
	}

	public boolean checkIfLoginIdExists(String libraryMemberId) throws LoginException {
		boolean loginExists;
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> map = da.readMemberMap();
		loginExists = map.containsKey(libraryMemberId) ? true : false;
		return loginExists;
	}

	public void addNewBookCopy(String isbn) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		if (!bookMap.containsKey(isbn))
			throw new Exception("No book found, couldn't add new copy");
		Book b = bookMap.get(isbn);
		b.addCopy();
		da.updateBook(b);
	}

	public int getBookCopiesCount(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		Book book = bookMap.get(isbn);
		return book.getCopies().length;
	}

	public List<BookCopy> addBookCopyForCheckout(BookCopy bc, List<BookCopy> bookCopyCheckoutList) {
		bookCopyCheckoutList.add(bc);
		return bookCopyCheckoutList;
	}

	public void confirmBookCheckout(List<BookCopy> bookCopyCheckoutList, LibraryMember libMember) {
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecordEntry> checkoutBookList = new ArrayList<>();
		HashMap<String, HashMap<Integer, BookCopy>> bookAndBookCopyMap = da.readBookCopies();
		for (BookCopy bc : bookCopyCheckoutList) {
			String isbn = bc.getBook().getIsbn();
			int bookCopyUID = bc.getCopyNum();
			if (bookAndBookCopyMap.containsKey(isbn)) {
				HashMap<Integer, BookCopy> bookCopyMap = bookAndBookCopyMap.get(isbn);
				BookCopy updateBookCopyObj = bookCopyMap.get(bookCopyUID);
				updateBookCopyObj.setAvailable(false);
				da.updateBookCopy(bookCopyUID, updateBookCopyObj);

				CheckoutRecordEntry cre = new CheckoutRecordEntry(updateBookCopyObj, libMember.getMemberId());
				da.saveCheckoutRecordEntry(cre);
				checkoutBookList.add(cre);
			}

		}

		// bulk update done on CheckoutRecord
		if (checkoutBookList.size() > 0) {
			da.saveCheckoutRecord(libMember.getMemberId(), checkoutBookList);
		}
	}

	@Override
	public void addMember(LibraryMember mem) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(mem);
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

}
