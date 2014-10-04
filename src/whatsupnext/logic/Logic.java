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
	
	public static ArrayList<Task> list = new ArrayList<Task>();
	public static ArrayList<String> output = new ArrayList<String>();
	
	public static String MESSAGE_ADDED = "A task is successfully added";
	public static String MESSAGE_DELETED = "A task is successfully deleted";
	public static String MESSAGE_UPDATED = "A task is successfully updated";
	
	static int numberOfTasks = 0;
	
	public Logic() throws IOException {
		Storage storage = new Storage();
		list = storage.readTasks();
		numberOfTasks = list.size();
	}
	
	public static String execute(Task task) {
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
	
	public static String addTask(Task task) {
		list.add(task);
		numberOfTasks++;
		
		return MESSAGE_ADDED;
	}
	
	public static String deleteTask(Task temp) {
		String id = temp.getTaskID();
				
		switch (temp.getDeleteType()) {
		case ID:
			deleteById(id);
			break;
		case DEADLINE:
			break;
		case DATE:
			break;
		case TIMEFRAME:
			break;
		default:
			break;	
		}
		
		return MESSAGE_DELETED;
	}	
	
	public static String updateTask(Task temp) {
		String info = temp.getDescription();
		String id = temp.getTaskID();
		String StartTime = temp.getStartTime();
		String EndTime = temp.getEndTime();
		
		switch (temp.getUpdateType()) {
		case DESCRIPTION:
			updateInfo(id, info);
			break;
		case DEADLINE:
			updateDeadline(id, EndTime);
			break;
		case TIMEFRAME:
			updateByTimeFrame(id, StartTime, EndTime);
			break;
		}
		
		return MESSAGE_UPDATED;
	}	
	
	public static String viewTask(Task temp) {
		VIEWTYPE viewType = temp.getViewType();
		
		switch (viewType) {
		case ALL:
			viewAll();
			break;
		case NEXT:
			break;
		default:
			break;
		}
		
		String feedbackView = formatArrayAsString(output);
		output.clear();
		
		return feedbackView;
	}
	
	private static String formatArrayAsString(ArrayList<String> taskNumberedArray) {
		String textsAsNumberedList = taskNumberedArray.get(0);
		for (int i = 1; i < taskNumberedArray.size(); i++) {
			textsAsNumberedList = textsAsNumberedList.concat("\n" + taskNumberedArray.get(i));
		}
		return textsAsNumberedList;
	}
	
	private static String getOutput(int id) {
		Task task = list.get(id);
		String temp = (id + 1) + ". " + task.getDescription();
		
		return temp;
	}
	
	/*
	 * Four types of DELETE functions.
	 */
	private static void deleteById(String id) {
		int index = getTaskByID(id);		
		list.remove(index);
		numberOfTasks--;
	}
	
	private static void deleteByDate(String time) {
		
	}
	
	private static void deleteByDeadline(String time) {
		int id = 0;	
		id = searchByDeadline(time);	
		
		list.remove(id);
		numberOfTasks--;
	}
	
	private static void deleteByTimeFrame() {
	}
	
	/*
	 * Three types of UPDATE functions.
	 */
	private static void updateInfo(String id, String info) {
		int index = getTaskByID(id);
		Task temp = list.get(index);
		temp.setDescription(info);
	}
	
	private static void updateDeadline(String id, String time) {
		int index = getTaskByID(id);
		Task temp = list.get(index);
		temp.setEndTime(time);
	}
	
	private static void updateByTimeFrame(String id, String stime, String etime) {
		int index = getTaskByID(id);
		Task temp = list.get(index);
		temp.setStartTime(stime);
		temp.setEndTime(etime);
	}
	
	/*
	 * Two types of VIEW functions.
	 */
	private static void viewNext(Task task) {
	}
		
	private static void viewAll() {	
		
		for (int i = 0; i < numberOfTasks; i++) {
			String task_Info = getOutput(i);
			
			output.add(i, task_Info);
		}
	}	
	
	/*
	 * Three types of SEARCH functions.
	 */
	private static int searchByDescription(String task_Info) {
		int index = 0;
		Task temp = list.get(0);
		
		while (!temp.getDescription().equalsIgnoreCase(task_Info)) {
			index++;
			temp = list.get(index);
		}
		
		return index;
	}
	
	private static int searchByDeadline(String time) {
		int index = 0;
		Task temp = list.get(0);
		
		while (!temp.getEndTime().equalsIgnoreCase(time)) {
			index++;
			temp = list.get(index);
		}
		
		return index;
	}
	
	private static void searchByDate() {
	}
	
	private static int getTaskByID(String id) {
		int index = 0;
		Task temp = list.get(0);
		
		while ((!temp.getTaskID().equalsIgnoreCase(id)) && (index < numberOfTasks)) {
			index++;
			temp = list.get(index);
		}
		
		return index;
	}
}
