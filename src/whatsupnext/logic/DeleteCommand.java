package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.DELETETYPE;

public class DeleteCommand extends Command {

	private final String MESSAGE_DELETED = "Tasks are successfully deleted.";
	private DELETETYPE deleteType;
	
	private ArrayList<Task> list = LogicUtilities.list;
	private PriorityQueue<Integer> availableIDs = LogicUtilities.availableIDs;
	
	public DeleteCommand(Task task) {
		super(task);
		deleteType = task.getDeleteType();
	}

	public String executeCommand() {
		switch (deleteType) {
			case ALL:
				deleteAll();
				break;
			case ID:
				deleteById();
				break;
			case DEADLINE:
				deleteByDeadline();
				break;
			case DATE:
				deleteByDate();
				break;
			case TIMEFRAME:
				deleteByTimeFrame();
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
	
	private void deleteAll() {
		list.clear();
		LogicUtilities.setupAvailableIDs();
	}
	
	private void deleteById() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);		
		Task removed = list.remove(index);
		availableIDs.add(Integer.parseInt(removed.getTaskID()));
	}
	
	private void deleteByDeadline() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && Logic.endsBeforeDeadline(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
			}
		}
	}
	
	private void deleteByDate() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && Logic.endsOnGivenDate(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
			}
		}
	}
	
	private void deleteByTimeFrame() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && !Logic.endsBeforeDeadline(task, startTime) && Logic.endsBeforeDeadline(task, endTime)) {
				availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
			}
		}
	}
	
	

}
