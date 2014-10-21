package whatsupnext.logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;

public class LogicUtilities {
	
	private static final ArrayList<Task> list = new ArrayList<Task>();
	private static final ArrayList<String> output = new ArrayList<String>();
	private static final int maxTasks = 1000000;
	private static final PriorityQueue<Integer> availableIDs = new PriorityQueue<Integer>(maxTasks);
	
	public static ArrayList<Task> getTaskList() {
		return list;
	}
	
	public static ArrayList<String> getOutputList() {
		return output;
	}

	public static int getMaxTasks() {
		return maxTasks;
	}
	
	public static PriorityQueue<Integer> getAvailableIDs() {
		return availableIDs;
	}
	
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
		for (int i = 1; i < LogicUtilities.getMaxTasks(); i++) {
			availableIDs.add(i);
		}
		
		// Remove the IDs that are already in use
		for (int i = 0; i < list.size(); i++) {
			int usedID = Integer.parseInt(list.get(i).getTaskID());
			availableIDs.remove(usedID);
		}
	}
}
