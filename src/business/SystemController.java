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

	public BookCopy checkIfBookCopyAvailable(String libraryMemberId, String isbn) {
		BookCopy availableBookCopy = null;
		boolean outOfStock = true;
		try {
			if (checkIfLoginIdExists(libraryMemberId)) {
				DataAccess da = new DataAccessFacade();
				HashMap<String, HashMap<Integer, BookCopy>> bookAndBookCopymap = da.readBookCopies();
				if (bookAndBookCopymap.containsKey(isbn)) {
					HashMap<Integer, BookCopy> bookCopyMap = bookAndBookCopymap.get(isbn);
					for (HashMap.Entry<Integer, BookCopy> bookCopyMapSet : bookCopyMap.entrySet()) {
						BookCopy bcopy = bookCopyMapSet.getValue();
						if (bcopy.isAvailable()) {
							availableBookCopy = bcopy;
							outOfStock = false;
							break;
						}
					}

					if (outOfStock) {
						throw new Exception("Book out of stock!!!");
					}

				} else {
					throw new Exception("Requested book with ISBN " + isbn + " is not available");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return availableBookCopy;
	}

	public void addNewBookCopy(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		Book b = bookMap.get(isbn);
		b.addCopy();
		da.updateBook(b);

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

				CheckoutRecordEntry cre = new CheckoutRecordEntry(updateBookCopyObj, libMember);
				da.saveCheckoutRecordEntry(cre);
				checkoutBookList.add(cre);
			}

		}

		// bulk update done on CheckoutRecord
		if (checkoutBookList.size() > 0) {
			da.saveCheckoutRecord(libMember.getMemberId(), checkoutBookList);
		}
	}

	public boolean checkIfLoginIdExists(String libraryMemberId) throws LoginException {
		boolean loginExists = true;
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(libraryMemberId)) {
			throw new LoginException("ID " + libraryMemberId + " not found");
		}
		return loginExists;
	}

	@Override
	public void addMember(LibraryMember mem) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(mem);
	}
	
	@Override
	public void addBook(Book b) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		da.addBook(b);
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
