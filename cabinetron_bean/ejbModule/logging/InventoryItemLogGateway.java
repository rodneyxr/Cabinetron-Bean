package logging;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.swing.DefaultListModel;

import redis.clients.jedis.Jedis;

//@Stateful(name="StateBeanJedis")
//@Stateless(name="StateBeanJedis")
@Singleton(name = "InventoryItemLogGateway")
public class InventoryItemLogGateway implements InventoryItemLogGatewayRemote {

	public static final String KEY_LOG = "inventory_item_log.";
	private Jedis jedis;

	private ArrayList<StateObserverRemote> observers;

	public InventoryItemLogGateway() {
		System.out.println("opening jedis connection...");
		// connect to redis server and read childCount value
		// if null then init to zero
		try {
			jedis = new Jedis("cloud.fulgentcorp.com");
			jedis.auth("8fgJk923j3gdK2023e9jvBj45otrJ298dh3gHjk4t10jmV94buh");
			jedis.select(42);// select redis db 42
			observers = new ArrayList<StateObserverRemote>();
			System.out.println("jedis connection succesful!");
		} catch (Exception e) {
			jedis = null;
		}
	}

	@PreDestroy
	public void closeRedis() {
		jedis.close();
	}

	private final String getKeyForItem(UUID item) {
		return KEY_LOG + item.toString();
	}

	@Lock(LockType.READ)
	public DefaultListModel<InventoryItemLogEntry> getLogListModel(UUID item) {
		// if (Main.DEBUG_MODE)
		// System.out.println("InventoryItemLogGateway->getLog(): " + getKeyForItem(item)); // DEBUG

		// instantiate log to return
		// InventoryItemLog logList = new InventoryItemLog();
		DefaultListModel<InventoryItemLogEntry> logList = new DefaultListModel<InventoryItemLogEntry>();

		// get key and retrieve the set of entries from the DB
		String key = getKeyForItem(item);
		long cardinality = jedis.zcard(key);
		Set<String> blobs = jedis.zrange(key, 0, cardinality);
		// System.out.println("Cardinality: " + cardinality + ", Size: " + blobs.size()); // DEBUG

		// add them to the log object
		for (String blob : blobs) {
			logList.addElement((InventoryItemLogEntry) Utils.deblobify(blob));// .addLogEntry((InventoryItemLogEntry) Utils.deblobify(blob));
		}

		return logList;
	}

	@Lock(LockType.WRITE)
	public void addLogEntry(UUID item, InventoryItemLogEntry entry) {
		// System.out.println("InventoryItemLogGateway->addLogEntry(): " + getKeyForItem(item)); // DEBUG
		// System.out.println(entry); // DEBUG

		String key = getKeyForItem(item);
		// get cardinality of set to append to the end of the ordered set
		long insertPos = jedis.zcard(key);
		if (insertPos > 0)
			insertPos += 1;

		// add entry to the end of the ordered set
		jedis.zadd(key, insertPos, Utils.blobify(entry));

		notifyObservers(item);
	}

	/**
	 * Notifies observers which item has been updated so that they can fetch the updated information
	 * 
	 * @param item
	 */
	private void notifyObservers(UUID item) {
		try {
			for (int i = 0; i < observers.size(); i++) {
				((StateObserverRemote) observers.get(i)).callback(item);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Lock(LockType.WRITE)
	public void registerObserver(StateObserverRemote o) {
		observers.add(o);
	}

	@Lock(LockType.WRITE)
	public void unregisterObserver(StateObserverRemote o) {
		observers.remove(o);
	}
}
