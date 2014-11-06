package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.UPDATETYPE;

public class UpdateCommand extends Command{

	private String MESSAGE_UPDATED;
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
				MESSAGE_UPDATED = "Successfully updated the description of task " + taskID + ".";
				break;
			case DEADLINE:
				updateDeadline();
				MESSAGE_UPDATED = "Successfully updated the deadline of task " + taskID + ".";
				break;
			case TIMEFRAME:
				updateTimeFrame();
				MESSAGE_UPDATED = "Successfully updated the time frame of task " + taskID + ".";
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
