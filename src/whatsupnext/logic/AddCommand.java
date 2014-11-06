//@author A0105720W
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;

public class AddCommand extends Command {
	
	private String MESSAGE_ADDED;
	private ArrayList<Task> list = LogicUtilities.list;
	private PriorityQueue<Integer> availableIDs = LogicUtilities.availableIDs;
	
	public AddCommand(Task task) {
		super(task);
		task.getAddType();
	}

	public String executeCommand() {
		taskID = Integer.toString(availableIDs.remove());
		list.add(this.toTask());
		MESSAGE_ADDED = "Successfully added to task " + taskID + ".";
		if (!endTime.isEmpty()) {
			MESSAGE_ADDED = MESSAGE_ADDED + "\n\tDeadline: " + LogicUtilities.getFormattedTime(endTime);
		}
		
		LogicUtilities.sortTasks(list);

		String feedbackAdd;
		try {
			storage.inputTasks(list);
			feedbackAdd = MESSAGE_ADDED;
		} catch (IOException e) {
			feedbackAdd = e.getMessage();
		}

		return feedbackAdd;
	}
	
	private Task toTask() {
		Task task = new Task();
		task.setTaskID(taskID);
		task.setDescription(description);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
		return task;
	}
	
}
