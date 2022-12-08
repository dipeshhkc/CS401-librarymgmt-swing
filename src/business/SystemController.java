package business;

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
		BookCopy bCopy = book.getNextAvailableCopy();
		
		addToCheckoutRecordEntry(bCopy,libraryMemberId);
		return book.getNextAvailableCopy();
	}

	private void addToCheckoutRecordEntry(BookCopy bCopy, String libraryMemberId) {
		CheckoutRecordEntry cre = new CheckoutRecordEntry(bCopy, libraryMemberId);
		DataAccess da = new DataAccessFacade();
		da.saveCheckoutRecordEntry(cre);
	}

	public boolean checkIfLoginIdExists(String libraryMemberId) throws LoginException {
		boolean loginExists;
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> map = da.readMemberMap();
		loginExists = map.containsKey(libraryMemberId) ? true : false;
		System.out.println(loginExists);
		return loginExists;
	}

	public void addNewBookCopy(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
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
			int bookCopyUID = bc.getUid();
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
