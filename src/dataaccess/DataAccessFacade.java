package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import business.LibrarySystemException;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, BOOKCOPIES, CHECKOUTRECORDENTRY, CHECKOUTRECORD;
	}
	// Windows user can use

	/*
	 * public static final String OUTPUT_DIR = System.getProperty("user.dir") +
	 * "\\src\\dataaccess\\storage";
	 */

	// For Mac Users path can use /
	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "/src/dataaccess/storage";

	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// implement: other save operations
	public void saveNewMember(LibraryMember member) throws LibrarySystemException {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		// Member with this ID already present.
		if (mems.containsKey(memberId)) {
			throw new LibrarySystemException("Member with this ID already Present.");
		}
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	public void saveBookCopies(BookCopy bc) {
		boolean ISBNFound = false;
		HashMap<String, HashMap<Integer, BookCopy>> bookAndBookCopyMap = readBookCopies();
		String isbn = bc.getBook().getIsbn();
		int bookcopy_uid = bc.getUid();
		for (HashMap.Entry<String, HashMap<Integer, BookCopy>> bookCopyMapSet : bookAndBookCopyMap.entrySet()) {
			if (bookCopyMapSet.getKey() == isbn) {
				HashMap<Integer, BookCopy> particularISBNCopySet = bookCopyMapSet.getValue();
				particularISBNCopySet.put(bookcopy_uid, bc);
				ISBNFound = true;
				break;
			} else {
				continue;
			}
		}

		if (!ISBNFound) {
			HashMap<Integer, BookCopy> newBookCopyMap = new HashMap<>();
			bookAndBookCopyMap.put(isbn, newBookCopyMap);
		}
		saveToStorage(StorageType.BOOKCOPIES, bookAndBookCopyMap);

	}

	@Override
	public void saveCheckoutRecordEntry(CheckoutRecordEntry cre) {
		HashMap<Integer, CheckoutRecordEntry> checkoutRecordEntryMap = readCheckoutRecordEntry();
		Integer uCheckoutId = cre.getUid();
		checkoutRecordEntryMap.put(uCheckoutId, cre);
		saveToStorage(StorageType.CHECKOUTRECORDENTRY, cre);
	}

	@Override
	public void saveCheckoutRecord(String memberId, List<CheckoutRecordEntry> creList) {
		HashMap<String, List<CheckoutRecordEntry>> checkoutRecordList = readCheckoutRecord();
		if (checkoutRecordList.containsKey(memberId)) {
			List<CheckoutRecordEntry> updateCheckoutList = checkoutRecordList.get(memberId);
			updateCheckoutList.addAll(creList);
			checkoutRecordList.put(memberId, updateCheckoutList);
		} else {
			checkoutRecordList.put(memberId, creList);
		}
		saveToStorage(StorageType.CHECKOUTRECORD, checkoutRecordList);
	}

	@Override
	public void updateBook(Book b) {
		HashMap<String, Book> bookListMap = readBooksMap();
		String isbn = b.getIsbn();
		if (bookListMap.containsKey(isbn)) {
			bookListMap.put(isbn, b);
		}
		saveToStorage(StorageType.BOOKS, bookListMap);
	}

	@Override
	public void updateBookCopy(int bookCopyUID, BookCopy bc) {
		HashMap<String, HashMap<Integer, BookCopy>> bookAndBookCopyMap = readBookCopies();
		HashMap<Integer, BookCopy> bookCopyMap = null;
		String isbn = bc.getBook().getIsbn();
		if (bookAndBookCopyMap.containsKey(isbn)) {
			bookCopyMap = bookAndBookCopyMap.get(isbn);
			bookCopyMap.put(bookCopyUID, bc);
			bookAndBookCopyMap.put(isbn, bookCopyMap);
		}
		saveToStorage(StorageType.BOOKCOPIES, bookAndBookCopyMap);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, HashMap<Integer, BookCopy>> readBookCopies() {
		return (HashMap<String, HashMap<Integer, BookCopy>>) readFromStorage(StorageType.BOOKCOPIES);
	}

	@SuppressWarnings("unchecked")
	private HashMap<Integer, CheckoutRecordEntry> readCheckoutRecordEntry() {
		// TODO Auto-generated method stub
		return (HashMap<Integer, CheckoutRecordEntry>) readFromStorage(StorageType.CHECKOUTRECORDENTRY);
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, List<CheckoutRecordEntry>> readCheckoutRecord() {
		return (HashMap<String, List<CheckoutRecordEntry>>) readFromStorage(StorageType.CHECKOUTRECORD);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

	@Override
	public HashMap<Integer, BookCopy> readBookCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
