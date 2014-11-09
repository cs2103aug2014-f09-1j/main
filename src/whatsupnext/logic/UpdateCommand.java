//@author A0105720W
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.structure.enums.Types.UPDATETYPE;
import whatsupnext.structure.util.Task;

public class UpdateCommand extends Command{

	private String MESSAGE_UPDATED;
	private UPDATETYPE updateType;	
	private ArrayList<Task> list = LogicUtilities.list;

	public UpdateCommand(Task task) {
		super(task);
		updateType = task.getUpdateType();
	}

	public String executeCommand() {
		int index = LogicUtilities.getTaskIndexInArray(taskID);
		
		if (index < 0) {
			MESSAGE_UPDATED = "Task " + taskID + " doesn't exist.";
		} else {
			switch (updateType) {
			case DESCRIPTION:				
				MESSAGE_UPDATED = updateInfo(index);
				break;
			case DEADLINE:				
				MESSAGE_UPDATED = updateDeadline(index);;
				break;
			case TIMEFRAME:			
				MESSAGE_UPDATED = updateTimeFrame(index);
				break;
			default:
				break;
		    }
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
	private String updateInfo(int index) {
		Task task = list.get(index);
		task.setDescription(description);
		
		return "Successfully updated the description of task " + taskID + ".";
	}
	
	private String updateDeadline(int index) {
		Task task = list.get(index);
		task.setEndTime(endTime);
		
		return "Successfully updated the deadline of task " + taskID + ".";
	}
	
	private String updateTimeFrame(int index) {	
		Task task = list.get(index);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
		
		return "Successfully updated the time frame of task " + taskID + ".";
	}
}
