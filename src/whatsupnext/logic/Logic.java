/*
 * This is the Logic class.
 */
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;
import whatsupnext.storage.Storage;

public class Logic {
	
	private ArrayList<Task> list = new ArrayList<Task>();
	private ArrayList<String> output = new ArrayList<String>();
	
	private String MESSAGE_ADDED = "A task is successfully added.";
	private String MESSAGE_DELETED = "Tasks are successfully deleted.";
	private String MESSAGE_UPDATED = "A task is successfully updated.";
	private String MESSAGE_NOTFOUND = "No tasks are found.";
	private String TASK_DISPLAY = "Task ID: %1$s\n\t%2$s\n\tStart Time: %3$s\n\tEnd Time: %4$s";
	
	private Storage storage;
	private PriorityQueue<Integer> availableIDs;
	private int maxTasks = 1000000;
	
	
	public Logic() {
		storage = new Storage();
		try {
			list = storage.readTasks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setupAvailableIDs();
	}
	
	private void setupAvailableIDs() {
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
				feedback = addTask(task);
				break;
			case DELETE:
				feedback = deleteTask(task);
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
	
	private String addTask(Task task) {
		String taskID = Integer.toString(availableIDs.remove());
		task.setTaskID(taskID);
		list.add(task);
		
		String feedbackAdd;
		try {
			storage.inputTasks(list);
			feedbackAdd = MESSAGE_ADDED;
		} catch (IOException e) {
			feedbackAdd = e.getMessage();
		}
		
		return feedbackAdd;
	}
	
	private String deleteTask(Task task) {
		switch (task.getDeleteType()) {
			case ALL:
				deleteAll();
				break;
			case ID:
				deleteById(task);
				break;
			case DEADLINE:
				deleteByDeadline(task);
				break;
			case DATE:
				deleteByDate(task);
				break;
			case TIMEFRAME:
				deleteByTimeFrame(task);
				break;
			default:
				break;
		}
		
		String feedbackDelete;
		try {
			storage.inputTasks(list);
			feedbackDelete = MESSAGE_DELETED;
		} catch (IOException e) {
			feedbackDelete = e.getMessage();
		}
		
		return feedbackDelete;
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
				updateByTimeFrame(task);
				break;
			default:
				break;
		}
		
		String feedbackUpdate;
		try {
			storage.inputTasks(list);
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
			return "No saved tasks!";
		}
		
		String arrayAsString = taskNumberedArray.get(0);
		for (int i = 1; i < taskNumberedArray.size(); i++) {
			arrayAsString = arrayAsString.concat("\n" + taskNumberedArray.get(i));
		}
		return arrayAsString;
	}
	
	/*
	 * Four types of DELETE functions.
	 */
	private void deleteById(Task deleteTask) {
		int index = getTaskIndexInArray(deleteTask.getTaskID());		
		list.remove(index);
	}
	
	private void deleteByDate(Task deleteTask) {
		String endTime = deleteTask.getEndTime();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (endsOnGivenDate(task, endTime)) {
				taskIterator.remove();
			}
		}
	}
	
	private void deleteByDeadline(Task deleteTask) {
		String endTime = deleteTask.getEndTime();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (endsBeforeDeadline(task, endTime)) {
				taskIterator.remove();
			}
		}
	}
	
	private void deleteByTimeFrame(Task deleteTask) {
		String startTime = deleteTask.getStartTime();
		String endTime = deleteTask.getEndTime();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!endsBeforeDeadline(task, startTime) && endsBeforeDeadline(task, endTime)) {
				taskIterator.remove();
			}
		}
	}
	
	private void deleteAll() {
		list.clear();
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
	
	private void updateByTimeFrame(Task updateTask) {
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
			if (!endsBeforeDeadline(task, startTime) && endsBeforeDeadline(task, endTime)) {
				String taskInfo = String.format(TASK_DISPLAY, task.getTaskID(), task.getDescription(), task.getStartTime(), task.getEndTime());
				output.add(taskInfo);
			}
		}
	}

	private void viewDate(Task viewTask) {
		String endTime = viewTask.getEndTime();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (endsOnGivenDate(task, endTime)) {
				String taskInfo = String.format(TASK_DISPLAY, task.getTaskID(), task.getDescription(), task.getStartTime(), task.getEndTime());
				output.add(taskInfo);
			}
		}
	}
	
	private void viewNext(Task viewTask) {
		Task currentTask = new Task();
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			currentTask = taskIterator.next();
			if (!endsBeforeDeadline(currentTask, viewTask.getEndTime())) {
				String taskInfo = String.format(TASK_DISPLAY, currentTask.getTaskID(), currentTask.getDescription(), currentTask.getStartTime(), currentTask.getEndTime());
			    output.add(taskInfo);
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
			String taskInfo = String.format(TASK_DISPLAY, task.getTaskID(), task.getDescription(), task.getStartTime(), task.getEndTime());
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
	private int getTaskIndexInArray(String id) {
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
	private boolean endsBeforeDeadline(Task task, String deadline) {	
		if (task.getEndTime().isEmpty()) {
			return false;
		}
		
		long endTime = Long.parseLong(task.getEndTime());
		long deadlineTime = Long.parseLong(deadline);
		
		return endTime <= deadlineTime;
	}
	
	private boolean endsOnGivenDate(Task task, String date) {
		if (task.getEndTime().isEmpty()) {
			return false;
		}
		
		long endTime = Long.parseLong(task.getEndTime());
		long givenDate = Long.parseLong(date);
		
		long endTimeDay = endTime / 10000;
		long givenDateDay = givenDate / 10000;
		long endTimeHrMin = endTime % 10000;
		
		return (endTimeDay == givenDateDay) && (endTimeHrMin >= 0000) && (endTimeHrMin <= 2359);
	}
}
