//@author A0105720W
package whatsupnext.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.structure.enums.Types.DELETETYPE;
import whatsupnext.structure.util.Task;

public class DeleteCommand extends Command {

	private String MESSAGE_DELETED;
	private DELETETYPE deleteType;
	
	private ArrayList<Task> list = LogicUtilities.list;
	private PriorityQueue<Integer> availableIDs = LogicUtilities.availableIDs;
	private int deletedNumbers = 0;
	
	public DeleteCommand(Task task) {
		super(task);
		deleteType = task.getDeleteType();
	}

	public String executeCommand() {
		String feedbackDelete;
		
		try {
			switch (deleteType) {
				case ALL:			
					MESSAGE_DELETED = deleteAll();;
					break;
				case ID:					
					MESSAGE_DELETED = deleteById();;
					break;
				case DEADLINE:					
					MESSAGE_DELETED = deleteByDeadline();
					break;
				case DATE:				
					MESSAGE_DELETED = deleteByDate();
					break;
				case TIMEFRAME:					
					MESSAGE_DELETED = deleteByTimeFrame();
					break;
				default:
					break;
			}
		
			storage.inputTasks(list);
			feedbackDelete = MESSAGE_DELETED;
			
		} catch (Exception e) {
			feedbackDelete = e.getMessage();
		}

		return feedbackDelete;
	}
	
	private String deleteAll() {
		list.clear();
		LogicUtilities.setupAvailableIDs();
		
		return "All tasks are deleted.";
	}
	
	private String deleteById() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);
		
		if (index >= 0) {
			try {
				Task removed = list.remove(index);
				availableIDs.add(Integer.parseInt(removed.getTaskID()));
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("Task ID is not valid.");
			}
			
			return "Task " + taskID + " is deleted.";
		} else {
			return "Task " + taskID + " doesn't exist.";
		}
	}
	
	private String deleteByDeadline() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && LogicUtilities.endsBeforeDeadline(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
				deletedNumbers++;
			}
		}
		
		return deletedNumbers + " tasks are deleted.";
	}
	
	private String deleteByDate() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && LogicUtilities.endsOnGivenDate(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
				deletedNumbers++;
			}
		}
		
		return deletedNumbers + " tasks are deleted.";
	}
	
	private String deleteByTimeFrame() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() &&
					!LogicUtilities.endsBeforeDeadline(task, startTime) &&
					LogicUtilities.endsBeforeDeadline(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
				deletedNumbers++;
			}
		}
		
		return deletedNumbers + " tasks are deleted.";
	}
	
}
