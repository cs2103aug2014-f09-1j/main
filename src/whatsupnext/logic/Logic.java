/*
 * This is the Logic class.
 */
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.structure.Task;
import whatsupnext.storage.Storage;

public class Logic {
	
	private ArrayList<Task> list = new ArrayList<Task>();
	private ArrayList<String> output = new ArrayList<String>();
	
	private String MESSAGE_ADDED = "A task is successfully added.";
	private String MESSAGE_DELETED = "Tasks are successfully deleted.";
	private String MESSAGE_UPDATED = "A task is successfully updated.";
	private String MESSAGE_NOTFOUND = "No tasks are found.";
	
	private int numberOfTasks = 0;
	
	private Storage storage;
	
	public Logic() {
		storage = new Storage();
		try {
			list = storage.readTasks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		numberOfTasks = list.size();	
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
		String taskID = "" + (numberOfTasks + 1);
		task.setTaskID(taskID);
		list.add(task);
		numberOfTasks++;
		try {
			storage.inputTasks(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return MESSAGE_ADDED;
	}
	
	private String deleteTask(Task temp) {
		switch (temp.getDeleteType()) {
		case ALL:
			deleteAll();
			break;
		case ID:
			deleteById(temp);
			break;
		case DEADLINE:
			deleteByDeadline(temp);
			break;
		case DATE:
			deleteByDate(temp);
			break;
		case TIMEFRAME:
			deleteByTimeFrame(temp);
			break;
		default:
			break;	
		}
		
		try {
			storage.inputTasks(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return MESSAGE_DELETED;
	}
	
	private String updateTask(Task temp) {	
		switch (temp.getUpdateType()) {
		case DESCRIPTION:
			updateInfo(temp);
			break;
		case DEADLINE:
			updateDeadline(temp);
			break;
		case TIMEFRAME:
			updateByTimeFrame(temp);
			break;
		default:
			break;	
		}
		
		try {
			storage.inputTasks(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return MESSAGE_UPDATED;
	}	
	
	private String viewTask(Task temp) {	
		switch (temp.getViewType()) {
		case ALL:
			viewAll();
			break;
		case NEXT:
			viewNext(temp);
			break;
		default:
			break;
		}
		
		String feedbackView = formatArrayAsString(output);
		output.clear();
		
		return feedbackView;
	}
	
	private String formatArrayAsString(ArrayList<String> taskNumberedArray) {
		String textsAsNumberedList = taskNumberedArray.get(0);
		for (int i = 1; i < taskNumberedArray.size(); i++) {
			textsAsNumberedList = textsAsNumberedList.concat("\n" + taskNumberedArray.get(i));
		}
		return textsAsNumberedList;
	}
	
	/*
	 * Four types of DELETE functions.
	 */
	private void deleteById(Task temp) {
		String id = temp.getTaskID();
		int index = getTaskByID(id);		
		list.remove(index);
		numberOfTasks--;
	}
	
	private void deleteByDate(Task temp) {
		int index = numberOfTasks - 1;
		String endtime = temp.getEndTime();
		
		while (index >= 0) {
			if (endsOnGivenDate(index, endtime)) {
				list.remove(index);
				numberOfTasks--;
			}
			index--;
		}
	}
	
	private void deleteByDeadline(Task temp) {
		int index = numberOfTasks - 1;
		String endtime = temp.getEndTime();
		
		while (index >= 0) {
			if (endsBeforeGivenTime(index, endtime)) {
				list.remove(index);
				numberOfTasks--;
			}
			index--;
		}
	}
	
	private void deleteByTimeFrame(Task temp) {
		int index = numberOfTasks - 1;
		String stime = temp.getStartTime();
		String etime = temp.getEndTime();
		
		while (index >= 0) {
			if (!endsBeforeGivenTime(index, stime) && endsBeforeGivenTime(index,etime)) {
				list.remove(index);
				numberOfTasks--;
			}
			index--;
		}
	}
	
	private void deleteAll() {
		list.clear();
	}
	
	/*
	 * Three types of UPDATE functions.
	 */
	private void updateInfo(Task temp) {
		String id = temp.getTaskID();
		int index = getTaskByID(id);
		String info = temp.getDescription();
		
		Task task = list.get(index);
		task.setDescription(info);
	}
	
	private void updateDeadline(Task temp) {
		String id = temp.getTaskID();
		int index = getTaskByID(id);
		String EndTime = temp.getEndTime();
		
		Task task = list.get(index);
		task.setEndTime(EndTime);
	}
	
	private void updateByTimeFrame(Task temp) {
		String id = temp.getTaskID();
		int index = getTaskByID(id);
		String StartTime = temp.getStartTime();
		String EndTime = temp.getEndTime();
		
		Task task = list.get(index);
		task.setStartTime(StartTime);
		task.setEndTime(EndTime);
	}
	
	/*
	 * Two types of VIEW functions.
	 */
	private void viewNext(Task task) {
		int next = 0;
		boolean found = false;
		String etime = task.getEndTime();
		Task temp = new Task();
		
		for (int i = 1; i < numberOfTasks; i++) {
			temp = list.get(next);
			if (!endsBeforeGivenTime(i, etime) && endsBeforeGivenTime(i, temp.getEndTime())) {
				found = true;
				next = i;
			}				
		}
		
		if (found) {
			temp = list.get(next);		
		    String task_Info = "Task ID: " + temp.getTaskID() + 
							"\n\t" + temp.getDescription() +
							"\n\tStart Time: " + temp.getStartTime() +
							"\n\tEnd Time: " + temp.getEndTime();
		    output.add(task_Info);
		} else 
			output.add(MESSAGE_NOTFOUND);
	}
		
	private void viewAll() {			
		for (int i = 0; i < numberOfTasks; i++) {
			Task temp = list.get(i);
			String task_Info = "Task ID: " + temp.getTaskID() + 
								"\n\t" + temp.getDescription() +
								"\n\tStart Time: " + temp.getStartTime() +
								"\n\tEnd Time: " + temp.getEndTime();
			output.add(task_Info);
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
		for (int i = 0; i < list.size(); i++) {
			if (endsBeforeGivenTime(i, deadline)) {
				taskResults.add(list.get(i));
			}
		}
		return taskResults;
	}

	private ArrayList<Task> searchByDate(String date) {
		ArrayList<Task> taskResults = new ArrayList<Task>();
		for (int i = 0; i < list.size(); i++) {
			if (endsOnGivenDate(i, date)) {
				taskResults.add(list.get(i));
			}
		}
		return taskResults;
	}

	/*
	 * Return the index of a task in the list.
	 */
	private int getTaskByID(String id) {
		int index = 0;
		Task temp = list.get(0);
		
		while ((!temp.getTaskID().equalsIgnoreCase(id)) && (index < numberOfTasks)) {
			index++;
			temp = list.get(index);
		}
		
		return index;
	}
	
	/*
	 * This check function is to check whether the end time of task(i) is before a given time.
	 */
	private boolean endsBeforeGivenTime(int index, String time) {
		Task task = list.get(index);
		
		if (task.getEndTime().isEmpty()) {
			return false;
		}
		
		long etime = Long.parseLong(task.getEndTime());
		long gtime = Long.parseLong(time);
		
		return etime <= gtime;
	}
	
	private boolean endsOnGivenDate(int index, String date) {
		Task task = list.get(index);
		
		if (task.getEndTime().isEmpty()) {
			return false;
		}
		
		long etime = Long.parseLong(task.getEndTime());
		long gdate = Long.parseLong(date);
		
		long etimeDay = etime / 10000;
		long gdateDay = gdate / 10000;
		long etimeTime = etime % 10000;
		
		return (etimeDay == gdateDay) && (etimeTime >= 0000) && (etimeTime <= 2359);
	}
}
