//@author A0105720W
package whatsupnext.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.DELETETYPE;

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
					deleteAll();
					MESSAGE_DELETED = "All tasks are deleted.";
					break;
				case ID:
					deleteById();
					MESSAGE_DELETED = "Task " + taskID + " is deleted.";
					break;
				case DEADLINE:
					deleteByDeadline();
					MESSAGE_DELETED = deletedNumbers + " tasks are deleted.";
					break;
				case DATE:
					deleteByDate();
					MESSAGE_DELETED = deletedNumbers + " tasks are deleted.";
					break;
				case TIMEFRAME:
					deleteByTimeFrame();
					MESSAGE_DELETED = deletedNumbers + " tasks are deleted.";
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
	
	private void deleteAll() {
		list.clear();
		LogicUtilities.setupAvailableIDs();
	}
	
	private void deleteById() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);
		try {
			Task removed = list.remove(index);
			availableIDs.add(Integer.parseInt(removed.getTaskID()));
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Task ID is not valid.");
		}
	}
	
	private void deleteByDeadline() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && LogicUtilities.endsBeforeDeadline(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
				deletedNumbers++;
			}
		}
	}
	
	private void deleteByDate() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && LogicUtilities.endsOnGivenDate(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
				deletedNumbers++;
			}
		}
	}
	
	private void deleteByTimeFrame() {
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
	}
	
}
