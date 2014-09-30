/*
 * This is the Logic class.
 */
package Logic;

import Structure.Task;

import java.util.ArrayList;

public class Logic {
	
	public static ArrayList<Task> list = new ArrayList<Task>();
	public static ArrayList<String> output = new ArrayList<String>();
	
	public static String MESSAGE_ADDED = "A task is successfully added";
	public static String MESSAGE_DELETED = "A task is successfully deleted";
	public static String MESSAGE_UPDATED = "A task is successfully updated";
	
	static int numberOfTasks = 0;
	
	public static String execute(Task task) {
		Task temp = (Task) task;
		String feedback;
		
		switch (task.getOpCode()) {
			case ADD:
				feedback = addTask(temp);
				break;
			case DELETE:
				feedback = deleteTask(temp);
				break;
			case UPDATE:
				feedback = updateTask(temp);
				break;
			case VIEW:
				feedback = viewTask(temp);
				break;
			default:
				feedback = "Unable to execute the command";
				break;
		}
		
		return feedback;
	}
	
	public static String addTask(Task task) {
		int task_ID = numberOfTasks + 1;
		numberOfTasks++;
		
		list.add(task_ID - 1, task);

		sort();
		return MESSAGE_ADDED;
	}
	
	public static String deleteTask(Task temp) {
		String deleteType = temp.getDeleteType();
		int id = 0;
		// id = temp.getTaskID;
				
		switch (deleteType) {
		case "ID":
			deleteById(id);
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
		int id = 0;
		String info = temp.getDescription();
		// id = temp.getTaskID;
		
		switch (updateType) {
		case "DESCRIPTION":
			updateInfo(id, info);
			break;
		case "DEADLINE":
			
			break;
		case "TIMEFRAME":
			break;
		}
		
		return MESSAGE_UPDATED;
	}	
	
	public static String viewTask(Task temp) {
		String viewType = temp.getViewType();
		
		switch (viewType) {
		case "ALL":
			viewAll();
			break;
		case "NEXT":
			break;
		default:
			break;
		}
		
		return formatArrayAsString(output);
	}
	
	private static String formatArrayAsString(ArrayList<String> taskNumberedArray) {
		String textsAsNumberedList = taskNumberedArray.get(0);
		for (int i = 1; i < taskNumberedArray.size(); i++) {
			textsAsNumberedList = textsAsNumberedList.concat("\n" + taskNumberedArray.get(i));
		}
		return textsAsNumberedList;
	}
	
	// This function needs to be changed.
	public static void storeIntoList() {

		for (int i = 0; i < numberOfTasks; i++) {
			Task temp = new Task();
			list.add(i+1, temp);
			// TODO: Reading in every element as a Task.
		}
	}	
	
	public static String getOutput(int id) {
		Task task = list.get(id);
		String temp = (id + 1) + ". " + task.getDescription();
		
		return temp;
	}
	
	
	public static void deleteById(int id) {
		list.remove(id);
		numberOfTasks--;
	}
	
	public static void deleteByDate(String time) {
		
	}
	
	public static void deleteByDeadline(String time) {
		int id = 0;	
		id = searchByDeadline(time);	
		
		list.remove(id);
		numberOfTasks--;
	}
	
	public static void deleteByTimeFrame() {
	}
	
	
	
	public static void updateInfo(int id, String info) {
		Task temp = list.get(id);
		temp.setDescription(info);
	}
	
	public static void updateDeadline(int id, String time) {
		Task temp = list.get(id);
		temp.setEndTime(time);
	}
	
	public static void updateByTimeFrame() {
	}
	
	public static void viewNext(Task task) {
	}
		
	public static void viewAll() {	
		
		for (int i = 0; i < numberOfTasks; i++) {
			String task_Info = getOutput(i);
			
			output.add(i, task_Info);
		}
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
		Task temp = list.get(0);
		
		while (!temp.getEndTime().equalsIgnoreCase(time)) {
			index++;
			temp = list.get(index);
		}
		
		return index;
	}
	
	public static void searchByDate() {
	}
	
	public static void sort() {
	}
}
