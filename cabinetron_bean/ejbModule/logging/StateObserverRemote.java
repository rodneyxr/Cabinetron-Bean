package logging;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface StateObserverRemote extends Remote {
	public void callback(UUID item) throws RemoteException; 
}
