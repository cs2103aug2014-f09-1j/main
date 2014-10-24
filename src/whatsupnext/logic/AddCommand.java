package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;

public class AddCommand extends Command {
	
	private final String MESSAGE_ADDED = "A task is successfully added.";
	private ADDTYPE addType;
	
	private ArrayList<Task> list = LogicUtilities.list;
	private PriorityQueue<Integer> availableIDs = LogicUtilities.availableIDs;
	
	public AddCommand(Task task) {
		super(task);
		addType = task.getAddType();
	}

	public String executeCommand() {
		taskID = Integer.toString(availableIDs.remove());
		list.add(this.toTask());

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
