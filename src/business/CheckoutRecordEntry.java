package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private BookCopy bCopy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private double fineAmount;
	private LocalDate bookReturnedDate;
	private String memberId;

	public CheckoutRecordEntry(BookCopy bookCopy, String libMemberId) {
		this.bCopy = bookCopy;
		this.checkoutDate = LocalDate.now();
		this.dueDate = LocalDate.now().plusDays(bookCopy.getBook().getMaxCheckoutLength());
		this.memberId = libMemberId;
	}

	public BookCopy getbCopy() {
		return bCopy;
	}

	public void setbCopy(BookCopy bCopy) {
		this.bCopy = bCopy;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public LocalDate getBookReturnedDate() {
		return bookReturnedDate;
	}

	public void setBookReturnedDate(LocalDate bookReturnedDate) {
		this.bookReturnedDate = bookReturnedDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
