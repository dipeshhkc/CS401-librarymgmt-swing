package business;

import java.util.List;

import business.DTO.BookWithPastDueDateDTO;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public void addMember(LibraryMember mem) throws LibrarySystemException;

	public void addBook(Book b) throws LibrarySystemException;

	public List<String> allBookIds();

	public BookCopy checkoutBook(String libraryMemberId, String isbn) throws Exception;

	public Book addNewBookCopy(String isbn) throws Exception;

	public Book getBook(String isbn) throws Exception;

	public BookWithPastDueDateDTO getOverdueBooks(String isbnNumber) throws LibrarySystemException;

	public List<CheckoutRecordEntry> getCheckoutRecordByMemberId(String libraryMemberId) throws Exception;

}
