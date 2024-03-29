package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import business.LibrarySystemException;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();

	public HashMap<Integer, BookCopy> readBookCopy();

	public HashMap<String, User> readUserMap();

	public HashMap<String, LibraryMember> readMemberMap();
	
	public HashMap<String, HashMap<Integer, BookCopy>> readBookCopies();

	public HashMap<String, List<CheckoutRecordEntry>> readCheckoutRecord();
	
	public List<CheckoutRecordEntry> getCheckoutRecordByMemberId(String libraryMemberId);
	
	public void saveToCheckoutRecord(String memberId, CheckoutRecordEntry cre);
	
	public void saveCheckoutRecordEntry(CheckoutRecordEntry cre);

	public void saveCheckoutRecord(String memberId, List<CheckoutRecordEntry> cre);

	public void addBook(Book b) throws LibrarySystemException;
	
	public void updateBookCopy(int bookCopyUID, BookCopy bc);

	public void updateBook(Book b);

//	public void checkIfBookAvailable();
	public void saveNewMember(LibraryMember member) throws LibrarySystemException;
}