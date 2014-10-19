package whatsupnext.logic;

import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

public class DeleteTask extends Task {

	private final String MESSAGE_DELETED = "Tasks are successfully deleted.";

	private Storage storage = Storage.getInstance();

	public String execute() {
		switch (this.getDeleteType()) {
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
			storage.inputTasks(Logic.list);
			feedbackDelete = MESSAGE_DELETED;
		} catch (IOException e) {
			feedbackDelete = e.getMessage();
		}

		return feedbackDelete;
	}
	
	private void deleteAll() {
		Logic.list.clear();
		setupAvailableIDs();
	}
	
	private void deleteById() {
		int index = Logic.getTaskIndexInArray(this.getTaskID());		
		Task removed = Logic.list.remove(index);
		Logic.availableIDs.add(Integer.parseInt(removed.getTaskID()));
	}
	
	private void deleteByDeadline() {
		String endTime = this.getEndTime();
		Iterator<Task> taskIterator = Logic.list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && Logic.endsBeforeDeadline(task, endTime)) {
				Logic.availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
			}
		}
	}
	
	private void deleteByDate() {
		String endTime = this.getEndTime();
		Iterator<Task> taskIterator = Logic.list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && Logic.endsOnGivenDate(task, endTime)) {
				Logic.availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
			}
		}
	}
	
	private void deleteByTimeFrame() {
		String startTime = this.getStartTime();
		String endTime = this.getEndTime();
		Iterator<Task> taskIterator = Logic.list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && !Logic.endsBeforeDeadline(task, startTime) && Logic.endsBeforeDeadline(task, endTime)) {
				Logic.availableIDs.add(Integer.parseInt(task.getTaskID()));
				taskIterator.remove();
			}
		}
	}
	
	
	private void setupAvailableIDs() {
		Logic.availableIDs = new PriorityQueue<Integer>(Logic.maxTasks);
		
		// Populate the available ID list
		for (int i = 1; i < Logic.maxTasks; i++) {
			Logic.availableIDs.add(i);
		}
		
		// Remove the IDs that are already in use
		for (int i = 0; i < Logic.list.size(); i++) {
			int usedID = Integer.parseInt(Logic.list.get(i).getTaskID());
			Logic.availableIDs.remove(usedID);
		}
	}

}
