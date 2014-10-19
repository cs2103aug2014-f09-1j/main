/*
 * This is the Logic class.
 */
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;
import whatsupnext.structure.TaskComparators.TaskDefaultComparator;
import whatsupnext.storage.Storage;

public class Logic {
	
	protected static ArrayList<Task> list = new ArrayList<Task>();
	protected static ArrayList<String> output = new ArrayList<String>();
	
	private String MESSAGE_UPDATED = "A task is successfully updated.";
	private String MESSAGE_NOTFOUND = "No tasks are found.";
	//private String TASK_DISPLAY = "Task ID: %1$s\n\t%2$s\n\tStart Time: %3$s\n\tEnd Time: %4$s";
	
	protected static PriorityQueue<Integer> availableIDs;
	protected static int maxTasks = 1000000;
	
	
	public Logic() {
		Storage storage = Storage.getInstance();
		try {
			list = storage.readTasks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setupAvailableIDs();
	}

	protected void setupAvailableIDs() {
		availableIDs = new PriorityQueue<Integer>(maxTasks);
		
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

	public String execute(Task task) {
		String feedback;
		
		
		switch (task.getOpCode()) {
			case ADD:
				AddTask addTask = new AddTask();
				addTask.setOpcode(task.getOpCode());
				addTask.setAddType(task.getAddType());
				addTask.setTaskID(task.getTaskID());
				addTask.setDescription(task.getDescription());
				addTask.setStartTime(task.getStartTime());
				addTask.setEndTime(task.getEndTime());
				feedback = addTask.execute();
				break;
			case DELETE:
				DeleteTask deleteTask = new DeleteTask();
				deleteTask.setOpcode(task.getOpCode());
				deleteTask.setDeleteType(task.getDeleteType());
				deleteTask.setTaskID(task.getTaskID());
				deleteTask.setDescription(task.getDescription());
				deleteTask.setStartTime(task.getStartTime());
				deleteTask.setEndTime(task.getEndTime());
				feedback = deleteTask.execute();
				break;
			case UPDATE:
				feedback = updateTask(task);
				break;
			case VIEW:
				feedback = viewTask(task);
				break;
			default:
				feedback = "Unable to execute the command";
				break;
		}
		
		return feedback;
	}
	
	private String updateTask(Task task) {	
		switch (task.getUpdateType()) {
			case DESCRIPTION:
				updateInfo(task);
				break;
			case DEADLINE:
				updateDeadline(task);
				break;
			case TIMEFRAME:
				updateTimeFrame(task);
				break;
			default:
				break;
		}
		
		String feedbackUpdate;
		try {
			Storage.getInstance().inputTasks(list);
			feedbackUpdate = MESSAGE_UPDATED;
		} catch (IOException e) {
			feedbackUpdate = e.getMessage();
		}
		
		return feedbackUpdate;
	}	
	
	private String viewTask(Task task) {	
		switch (task.getViewType()) {
			case ALL:
				viewAll();
				break;
			case NEXT:
				viewNext(task);
				break;
			case DATE:
				viewDate(task);
				break;
			case TIMEFRAME:
				viewTimeFrame(task);
				break;
			default:
				break;
		}
		
		String feedbackView = formatArrayAsString(output);
		output.clear();
		
		return feedbackView;
	}
	
	private String formatArrayAsString(ArrayList<String> taskNumberedArray) {
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
	 * Three types of UPDATE functions.
	 */
	private void updateInfo(Task updateTask) {
		int index = getTaskIndexInArray(updateTask.getTaskID());
		String info = updateTask.getDescription();
		
		Task task = list.get(index);
		task.setDescription(info);
	}
	
	private void updateDeadline(Task updateTask) {
		int index = getTaskIndexInArray(updateTask.getTaskID());
		String endTime = updateTask.getEndTime();
		
		Task task = list.get(index);
		task.setEndTime(endTime);
	}
	
	private void updateTimeFrame(Task updateTask) {
		int index = getTaskIndexInArray(updateTask.getTaskID());
		String startTime = updateTask.getStartTime();
		String endTime = updateTask.getEndTime();
		
		Task task = list.get(index);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
	}
	
	/*
	 * Four types of VIEW functions.
	 */
	private void viewTimeFrame(Task viewTask) {
		String startTime = viewTask.getStartTime();
		String endTime = viewTask.getEndTime();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && !endsBeforeDeadline(task, startTime) && endsBeforeDeadline(task, endTime)) {
				String taskInfo = getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
	}

	private void viewDate(Task viewTask) {
		String endTime = viewTask.getEndTime();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && endsOnGivenDate(task, endTime)) {
				String taskInfo = getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
	}
	
	private void viewNext(Task viewTask) {
		Task currentTask;
		long nearestEndTime = 999999999999L;
		ArrayList<Task> sortedList = new ArrayList<Task>(list);
		Collections.sort(sortedList, new TaskDefaultComparator());
		Iterator<Task> taskIterator = sortedList.iterator();
		
		while (taskIterator.hasNext()) {
			currentTask = taskIterator.next();
			if (!currentTask.getEndTime().isEmpty() && !endsBeforeDeadline(currentTask, viewTask.getEndTime())) {
				if (output.isEmpty()) {
					nearestEndTime = Long.parseLong(currentTask.getEndTime());
					String taskInfo = getFormattedOutput(currentTask);;
					output.add(taskInfo);
				} else {
					if (Long.parseLong(currentTask.getEndTime()) < nearestEndTime) {
						output.clear();
						nearestEndTime = Long.parseLong(currentTask.getEndTime());
						String taskInfo = getFormattedOutput(currentTask); 
						output.add(taskInfo);
					} else if (Long.parseLong(currentTask.getEndTime()) == nearestEndTime) {
						String taskInfo = getFormattedOutput(currentTask);
						output.add(taskInfo);
					}
				}
			}				
		}
		
		if (output.isEmpty()) {
			output.add(MESSAGE_NOTFOUND);
		}
	}
		
	private void viewAll() {			
		Iterator<Task> taskIterator = list.iterator();
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			String taskInfo = getFormattedOutput(task);
			output.add(taskInfo);
		}
	}	
	
	/*
	 * Three types of SEARCH functions.
	 */
	private ArrayList<Task> searchByDescription(String task_Info) {
		return null;
	}
	
	private ArrayList<Task> searchByDeadline(String deadline) {
		ArrayList<Task> taskResults = new ArrayList<Task>();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (endsBeforeDeadline(task, deadline)) {
				taskResults.add(task);
			}
		}
		return taskResults;
	}

	private ArrayList<Task> searchByDate(String date) {
		ArrayList<Task> taskResults = new ArrayList<Task>();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (endsOnGivenDate(task, date)) {
				taskResults.add(task);
			}
		}
		return taskResults;

	}

	/*
	 * Return the index of a task in the list.
	 */
	protected static int getTaskIndexInArray(String id) {
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			if (task.getTaskID().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
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
	private String getFormattedOutput(Task task) {
		String task_Info = task.getTaskID() + ": " + task.getDescription();
		if (!task.getStartTime().isEmpty()) {
			task_Info = task_Info + "\n\tStart Time: " + getFormattedTime(task.getStartTime());
		}
		if (!task.getEndTime().isEmpty()) {
			task_Info = task_Info + "\n\tEnd Time: " + getFormattedTime(task.getEndTime());
		}
		return task_Info;
	}
	
	protected String getFormattedTime(String time) {
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
	
	private String getFormattedMonth(String month) {		
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
