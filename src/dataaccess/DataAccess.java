package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutRecordEntry;
import business.LibraryMember;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();
	
	public HashMap<Integer, BookCopy> readBookCopy();

	public HashMap<String, User> readUserMap();

	public HashMap<String, LibraryMember> readMemberMap();

	public void saveNewMember(LibraryMember member);

	public HashMap<String, HashMap<Integer, BookCopy>> readBookCopies();

	public void saveCheckoutRecordEntry(CheckoutRecordEntry cre);

	public void saveCheckoutRecord(String memberId, List<CheckoutRecordEntry> cre);
	
	public void updateBookCopy(int bookCopyUID,BookCopy bc);

	public void updateBook(Book b);

//	public void checkIfBookAvailable();
}
