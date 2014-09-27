/*
 * This is the Logic class.
 */
package Logic;

import Structure.Task;
import Structure.OPCODE;
import java.util.ArrayList;

public class Logic {
	
	public static ArrayList<Task> list = new ArrayList<Task>();
	public static ArrayList<String> output = new ArrayList<String>();
	
	public static String MESSAGE_ADDED = "A task is successfully added";
	public static String MESSAGE_DELETED = "A task is successfully deleted";
	public static String MESSAGE_UPDATED = "A task is successfully updated";
	
	static int numberOfTasks = 0;
	
	public static void execute(OPCODE command, Task task) {
		Task temp = (Task) task;
		
		switch (command) {
		case ADD:
			addTask(temp);
			break;
		case DELETE:
			deleteTask(temp);
			break;
		case UPDATE:
			updateTask(temp);
			break;
		case VIEW:
			viewTask();
			break;
		default:
			break;		
		}
	}
	
	public static String addTask(Task task) {
		int task_ID = numberOfTasks + 1;
		numberOfTasks++;
		
		list.add(task_ID, task);
		
		sort();
		return MESSAGE_ADDED;
	}
	
	public static String deleteTask(Task temp) {
		String deleteType = temp.getDeleteType();
		
		switch (deleteType) {
		case "ID":
			break;
		case "DEADLINE":
			break;
		case "DATE":
			break;
		case "TIMEFRAME":
			break;
		default:
			break;	
		}
		
		return MESSAGE_DELETED;
	}	
	
	public static String updateTask(Task temp) {
		String updateType = temp.getUpdateType();
		
		switch (updateType) {
		case "DESCRIPTION":
			updateByInfo(temp.getDescription());
			break;
		case "DATE":
			break;
		case "TIMEFRAME":
			break;
		}
		
		return MESSAGE_UPDATED;
	}	
	
	public static void viewTask() {
		
		getOutput();
	}
	
	
	
	
	
	
	
	
	
	
	// This function needs to be changed.
	public static void storeIntoList() {

		for (int i = 0; i < numberOfTasks; i++) {
			Task temp = new Task();
			list.add(i+1, temp);
			// TODO: Reading in every element as a Task.
		}
	}	
	
	public static void getOutput() {
		//TODO: Return results.
	}
	
	
	public static void deleteById(int id) {
		list.remove(id);
		numberOfTasks--;
	}
	
	public static void deleteByDate(int time) {
	}
	
	public static void deleteByDeadline(int time) {
	}
	
	public static void deleteByTimeFrame() {
	}
	
	
	
	public static void updateByInfo(String task_Info) {
		//TODO: 
	}
	
	public static void updateByDeadline(int time) {
	}
	
	public static void updateByTimeFrame() {
	}
	
	public static void viewNext(Task task) {
	}
		
	public static void viewAll() {		
	}	
	
	public static int searchByDescription(String task_Info) {
		int index = 0;
		Task temp = list.get(0);
		
		while (!temp.getDescription().equalsIgnoreCase(task_Info)) {
			index++;
			temp = list.get(index);
		}
		
		return index;
	}
	
	public static int searchByDeadline(String time) {
		int index = 0;
		
		return index;
	}
	
	public static void searchByDate() {
	}
	
	public static void sort() {
	}
}



