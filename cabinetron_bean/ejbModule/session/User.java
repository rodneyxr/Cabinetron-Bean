package session;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 8049545194275668931L;

	// automatically assigned by persistent storage as an auto-incrementing primary key
	private int id;
	private String name;
	private String email;

	public User(String name, String email, int id) {
		this.name = name;
		this.email = email;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

}
