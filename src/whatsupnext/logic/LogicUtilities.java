package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

public class LogicUtilities {
	
	protected static final ArrayList<Task> list = new ArrayList<Task>();
	protected static final ArrayList<String> output = new ArrayList<String>();
	protected static final int maxTasks = 1000000;
	protected static final PriorityQueue<Integer> availableIDs = new PriorityQueue<Integer>(maxTasks);
	
	
	/*
	 * Return the index of a task in the list.
	 */
	public static int getTaskIndexInArray(String id) {
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			if (task.getTaskID().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void setupAvailableIDs() {
		availableIDs.clear();
		
		// Populate the available ID list
		for (int i = 1; i < maxTasks; i++) {
			availableIDs.add(i);
		}
		
		// Remove the IDs that are already in use
		for (int i = 0; i < list.size(); i++) {
			int usedID = Integer.parseInt(list.get(i).getTaskID());
			availableIDs.remove(usedID);
		}
	}

	public static void readTasksIntoInternalList() {
		Storage storage = Storage.getInstance();
		try {
			Iterator<Task> readIterator = storage.readTasks().iterator();
			while (readIterator.hasNext()) {
				Task readTask = readIterator.next();
				list.add(readTask);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
