package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String bio;
	private Boolean isExpert;
	public String getBio() {
		return bio;
	}
	
	public Boolean getIsExpert() {
		return isExpert;
	}
	
	
	public Author(String f, String l, String t, Address a, String bio,Boolean isExpert) {
		super(f, l, t, a);
		this.bio = bio;
		this.isExpert=isExpert;
	}

	private static final long serialVersionUID = 7508481940058530471L;
}
