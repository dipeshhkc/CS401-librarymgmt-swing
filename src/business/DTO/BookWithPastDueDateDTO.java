package business.DTO;

import java.time.LocalDate;
import java.util.List;

public class BookWithPastDueDateDTO {
	
	private String ISBN;
	private String title;
	private List<BookWithPastDueDateInternalDTO> overDueLists;
	
	public BookWithPastDueDateDTO() {};
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<BookWithPastDueDateInternalDTO> getOverDueLists() {
		return overDueLists;
	}
	public void setOverDueLists(List<BookWithPastDueDateInternalDTO> overDueLists) {
		this.overDueLists = overDueLists;
	}
	public class BookWithPastDueDateInternalDTO {
		
		public BookWithPastDueDateInternalDTO() {};
		private int copyNumber;
		private String memberId;
		public int getCopyNumber() {
			return copyNumber;
		}
		public void setCopyNumber(int copyNumber) {
			this.copyNumber = copyNumber;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public LocalDate getDueDate() {
			return dueDate;
		}
		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}
		private LocalDate dueDate;
	}
	
	
	
}
