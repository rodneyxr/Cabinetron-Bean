package logging;

import java.util.UUID;

import javax.ejb.Remote;
import javax.swing.DefaultListModel;

@Remote
public interface InventoryItemLogGatewayRemote {
	public DefaultListModel<InventoryItemLogEntry> getLogListModel(UUID item);

	public void addLogEntry(UUID item, InventoryItemLogEntry entry);

	public void registerObserver(StateObserverRemote o);

	public void unregisterObserver(StateObserverRemote o);
}
