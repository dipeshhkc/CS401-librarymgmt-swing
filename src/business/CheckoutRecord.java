package business;

import java.io.Serializable;
import java.util.List;

final public class CheckoutRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CheckoutRecordEntry> checkoutRecordEntryList;
	private LibraryMember libmember;

	public CheckoutRecord(LibraryMember libmember, List<CheckoutRecordEntry> cRecordEntry) {
		this.libmember = libmember;
		this.checkoutRecordEntryList = cRecordEntry;
	}

	public List<CheckoutRecordEntry> getCheckoutRecordEntryList() {
		return checkoutRecordEntryList;
	}

	public void setCheckoutRecordEntryList(List<CheckoutRecordEntry> checkoutRecordEntryList) {
		this.checkoutRecordEntryList = checkoutRecordEntryList;
	}

	public LibraryMember getLibmember() {
		return libmember;
	}

	public void setLibmember(LibraryMember libmember) {
		this.libmember = libmember;
	}

}
