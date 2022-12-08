package business;

import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public void addMember(LibraryMember mem) throws LibrarySystemException;

	public List<String> allBookIds();
	
	public BookCopy checkIfBookCopyAvailable(String libraryMemberId, String isbn);

}
