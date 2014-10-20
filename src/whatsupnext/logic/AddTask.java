package whatsupnext.logic;

import java.io.IOException;
import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

public class AddTask extends Task {
	
	private final String MESSAGE_ADDED = "A task is successfully added.";
	
	private Storage storage = Storage.getInstance();
	
	public AddTask() {
		super();
	}
	
	public AddTask(Task task) {
		super();
		this.setOpcode(task.getOpCode());
		this.setAddType(task.getAddType());
		this.setTaskID(task.getTaskID());
		this.setDescription(task.getDescription());
		this.setStartTime(task.getStartTime());
		this.setEndTime(task.getEndTime());
	}

	public String execute() {
		String taskID = Integer.toString(Logic.availableIDs.remove());
		this.setTaskID(taskID);
		Logic.list.add(this);

		String feedbackAdd;
		try {
			storage.inputTasks(Logic.list);
			feedbackAdd = MESSAGE_ADDED;
		} catch (IOException e) {
			feedbackAdd = e.getMessage();
		}

		return feedbackAdd;
	}
	
}
