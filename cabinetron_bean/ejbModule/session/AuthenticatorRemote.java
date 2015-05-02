package session;

import javax.ejb.Remote;

@Remote
public interface AuthenticatorRemote {
	public Session authenticateUser(String email, String password);
}
