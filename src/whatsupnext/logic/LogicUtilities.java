package whatsupnext.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;
import whatsupnext.structure.TaskComparators.TaskDefaultComparator;

public class LogicUtilities {
	
	static final ArrayList<Task> list = new ArrayList<Task>();
	static final ArrayList<String> output = new ArrayList<String>();
	static final int maxTasks = 1000000;
	static final PriorityQueue<Integer> availableIDs = new PriorityQueue<Integer>(maxTasks);
	
	
	/*
	 * Return the index of a task in the list.
	 */
	static int getTaskIndexInArray(String id) {
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			if (task.getTaskID().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
	}
	
	static void setupAvailableIDs() {
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

	public static String formatArrayAsString(ArrayList<String> taskNumberedArray) {
		if (taskNumberedArray.isEmpty()) {
			return "No tasks to display!";
		}
		
		String arrayAsString = taskNumberedArray.get(0);
		for (int i = 1; i < taskNumberedArray.size(); i++) {
			arrayAsString = arrayAsString.concat("\n" + taskNumberedArray.get(i));
		}
		return arrayAsString;
	}
	
	/*
	 * This check function is to check whether the end time of task(i) is before a given time.
	 */
	public static boolean endsBeforeDeadline(Task task, String deadline) {	
		assert(!task.getEndTime().isEmpty());
		
		long endTime = Long.parseLong(task.getEndTime());
		long deadlineTime = Long.parseLong(deadline);
		
		return endTime <= deadlineTime;
	}
	
	public static boolean endsOnGivenDate(Task task, String date) {
		assert(!task.getEndTime().isEmpty());
		
		long endTime = Long.parseLong(task.getEndTime());
		long givenDate = Long.parseLong(date);
		
		long endTimeDay = endTime / 10000;
		long givenDateDay = givenDate / 10000;
		long endTimeHrMin = endTime % 10000;
		
		return (endTimeDay == givenDateDay) && (endTimeHrMin >= 0000) && (endTimeHrMin <= 2359);
	}
	
	/*
	 * Methods for getting a formatted task information and its formatted time.
	 */
	public static String getFormattedOutput(Task task) {
		String task_Info = task.getTaskID() + ": " + task.getDescription();
		if (!task.getStartTime().isEmpty()) {
			task_Info = task_Info + "\n\tStart Time: " + getFormattedTime(task.getStartTime());
		}
		if (!task.getEndTime().isEmpty()) {
			task_Info = task_Info + "\n\tEnd Time: " + getFormattedTime(task.getEndTime());
		}
		if (task.getDone()) {
			task_Info = task_Info + "\n\tDone.";
		} else {
			task_Info = task_Info + "\n\tNot done.";
		}
		return task_Info;
	}
	
	public static String getFormattedTime(String time) {
		String year = time.substring(0, 4);
		String month = time.substring(4, 6);
		String day = time.substring(6, 8);
		String temp = year + " " + getFormattedMonth(month) + " " + day;
		
		if (time.length()==12) {
			String hour = time.substring(8, 10);
			String minute = time.substring(10, 12);
			temp = temp + " " + hour + ":" + minute;
		}
		
		return temp;
	}
	
	private static String getFormattedMonth(String month) {		
		switch (month) {
			case "01":
				return "Jan";
			case "02":
				return "Feb";
			case "03":
				return "Mar";
			case "04": 
				return "Apr";
			case "05":
				return "May";
			case "06":
				return "Jun";
			case "07":
				return "Jul";
			case "08":
				return "Aug";
			case "09":
				return "Sep";
			case "10":
				return "Oct";
			case "11":
				return "Nov";
			case "12":
				return "Dec";
			}
		return null;
	}
	
	/*
	 * Sort the tasks in the list.
	 */
	public static void sortTasks(ArrayList<Task> tasks) {
		TaskDefaultComparator comparator = new TaskDefaultComparator();
		Collections.sort(tasks, comparator);
	}
	
	public static void clearList() {
		list.clear();
	}
}
