package session;

import java.util.HashMap;

import javax.ejb.Stateless;

import session.Session.Role;

@Stateless(name = "Authenticator")
public class Authenticator implements AuthenticatorRemote {
	private static final HashMap<String, String[]> USERS = new HashMap<>(); // email, username
	static {
		USERS.put("tomjones@email.com", new String[] { "password", "0", "Tom Jones", Role.ProdManager.toString() });
		USERS.put("suesmith@email.com", new String[] { "password", "1", "Sue Smith", Role.InvManager.toString() });
		USERS.put("ragnarnelson@email.com", new String[] { "password", "2", "Ragnar Nelson", Role.Admin.toString() });
	}

	public Authenticator() {
	}

	public Session authenticateUser(String email, String password) {
		email = email.toLowerCase();
		String[] userinfo = USERS.get(email);
		if (userinfo == null)
			return null;
		if (userinfo[0].equals(password)) {
			int id;
			try {
				id = Integer.valueOf(userinfo[1]);
			} catch (NumberFormatException nfe) {
				System.err.println("Error: Invalid user ID!");
				id = 0;
			}
			User user = new User(userinfo[2], email, id);
			return new Session(user, Role.valueOf(userinfo[3]));
		}
		return null;
	}

}
