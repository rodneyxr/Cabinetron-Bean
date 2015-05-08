package logging;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * This class holds update information for the inventory item's update log
 * 
 * @author Rodney
 * 
 */
public class InventoryItemLogEntry implements Serializable {
	private static final long serialVersionUID = -8760454336462465334L;

	private Timestamp entryDate;
	private String entryDescription;

	public InventoryItemLogEntry(String entryDescription) {
		this(new Timestamp(new Date().getTime()), entryDescription);
		this.setEntryDescription(entryDescription);
	}

	public InventoryItemLogEntry(Timestamp entryDate, String entryDescription) {
		this.setEntryDate(entryDate);
		this.setEntryDescription(entryDescription);
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryDescription() {
		return entryDescription;
	}

	public void setEntryDescription(String entryDescription) {
		this.entryDescription = entryDescription;
	}

	@Override
	public String toString() {
		return entryDate.toString() + ": " + entryDescription;
	}

}
