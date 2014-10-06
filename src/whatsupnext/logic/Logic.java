/*
 * This is the Logic class.
 */
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.storage.Storage;
import whatsupnext.structure.DELETETYPE;
import whatsupnext.structure.Task;
import whatsupnext.structure.UPDATETYPE;
import whatsupnext.structure.VIEWTYPE;

public class Logic {
	
	public ArrayList<Task> list = new ArrayList<Task>();
	public ArrayList<String> output = new ArrayList<String>();
	
	public String MESSAGE_ADDED = "A task is successfully added";
	public String MESSAGE_DELETED = "A task is successfully deleted";
	public String MESSAGE_UPDATED = "A task is successfully updated";
	
	private int numberOfTasks = 0;
	
	private Storage storage;
	
	public Logic() throws IOException {
		storage = new Storage();
		list = storage.readTasks();
		if(list == null){
			list = new ArrayList<Task>();
			numberOfTasks = 0;
		} else {
			numberOfTasks = list.size();	
		}
	}
	
	public String execute(Task task) throws IOException {
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
	
	private String addTask(Task task) throws IOException {
		list.add(task);
		numberOfTasks++;
		storage.inputTasks(list);
		
		return MESSAGE_ADDED;
	}
	
	private String deleteTask(Task temp) {
		switch (temp.getDeleteType()) {
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
		String etime = task.getEndTime();
		Task temp = new Task();
		
		for (int i = 1; i < numberOfTasks; i++) {
			temp = list.get(next);
			if (!endsBeforeGivenTime(i, etime) && endsBeforeGivenTime(i, temp.getEndTime()))
				next = i;
		}
		
		temp = list.get(next);		
		String task_Info = temp.getTaskID() + "." + temp.getDescription();
		output.add(task_Info);
	}
		
	private void viewAll() {			
		for (int i = 0; i < numberOfTasks; i++) {
			Task temp = list.get(i);
			String task_Info = temp.getTaskID() + "." + temp.getDescription();
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
		int etime = Integer.parseInt(task.getEndTime());
		int gtime = Integer.parseInt(time);
		
		return etime < gtime;
	}
	
	private boolean endsOnGivenDate(int index, String date) {
		Task task = list.get(index);
		int etime = Integer.parseInt(task.getEndTime());
		int gdate = Integer.parseInt(date);
		
		int etimeDay = etime / 10000;
		int gdateDay = gdate / 10000;
		int etimeTime = etime % 10000;
		
		return (etimeDay == gdateDay) && (etimeTime >= 0000) && (etimeTime <= 2359);
	}
}
