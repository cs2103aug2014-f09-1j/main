/*
 * This is the Logic class.
 */
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import whatsupnext.structure.Task;
import whatsupnext.storage.Storage;

public class Logic {
	
	private ArrayList<Task> list = LogicUtilities.getTaskList();
	
	public Logic() {
		Storage storage = Storage.getInstance();
		try {
			list = storage.readTasks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogicUtilities.setupAvailableIDs();
	}
	
	public Logic(String fileName) {
		Storage storage = Storage.getInstance(fileName);
		try {
			list = storage.readTasks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogicUtilities.setupAvailableIDs();
	}
	
	public String executeTask(Task task) {
		Command userCommand;
		
		switch (task.getOpCode()) {
			case ADD:
				userCommand = new AddCommand(task);
				break;
			case DELETE:
				userCommand = new DeleteCommand(task);
				break;
			case UPDATE:
				userCommand = new UpdateCommand(task);
				break;
			case VIEW:
				userCommand = new ViewCommand(task);
				break;
			case DONE:
				userCommand = new DoneCommand(task);
				break;
			default:
				return "Unable to execute the command";
		}
		
		return userCommand.executeCommand();
	}
	
	protected static String formatArrayAsString(ArrayList<String> taskNumberedArray) {
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
	 * Three types of SEARCH functions.
	 */
	private ArrayList<Task> searchByDescription(String task_Info) {
		return null;
	}
	
	/*
	 * This check function is to check whether the end time of task(i) is before a given time.
	 */
	protected static boolean endsBeforeDeadline(Task task, String deadline) {	
		assert(!task.getEndTime().isEmpty());
		
		long endTime = Long.parseLong(task.getEndTime());
		long deadlineTime = Long.parseLong(deadline);
		
		return endTime <= deadlineTime;
	}
	
	protected static boolean endsOnGivenDate(Task task, String date) {
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
	protected static String getFormattedOutput(Task task) {
		String task_Info = task.getTaskID() + ": " + task.getDescription();
		if (!task.getStartTime().isEmpty()) {
			task_Info = task_Info + "\n\tStart Time: " + getFormattedTime(task.getStartTime());
		}
		if (!task.getEndTime().isEmpty()) {
			task_Info = task_Info + "\n\tEnd Time: " + getFormattedTime(task.getEndTime());
		}
		if (task.getDone()) {
			task_Info = task_Info + "\nDone.";
		} else {
			task_Info = task_Info + "\nNot done.";
		}
		return task_Info;
	}
	
	protected static String getFormattedTime(String time) {
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
}
