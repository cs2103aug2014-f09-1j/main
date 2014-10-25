package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.UPDATETYPE;

public class UpdateCommand extends Command{

	private final String MESSAGE_UPDATED = "A task is successfully updated.";
	private UPDATETYPE updateType;
	
	private ArrayList<Task> list = LogicUtilities.list;

	public UpdateCommand(Task task) {
		super(task);
		updateType = task.getUpdateType();
	}

	public String executeCommand() {	
		switch (updateType) {
			case DESCRIPTION:
				updateInfo();
				break;
			case DEADLINE:
				updateDeadline();
				break;
			case TIMEFRAME:
				updateTimeFrame();
				break;
			default:
				break;
		}
		
		LogicUtilities.sortTasks(list);
		
		String feedbackUpdate;
		try {
			storage.inputTasks(list);
			feedbackUpdate = MESSAGE_UPDATED;
		} catch (IOException e) {
			feedbackUpdate = e.getMessage();
		}

		return feedbackUpdate;
	}
	
	/*
	 * Three types of UPDATE functions.
	 */
	private void updateInfo() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);
		Task task = list.get(index);
		task.setDescription(description);
	}
	
	private void updateDeadline() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);	
		Task task = list.get(index);
		task.setEndTime(endTime);
	}
	
	private void updateTimeFrame() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);		
		Task task = list.get(index);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
	}
}
