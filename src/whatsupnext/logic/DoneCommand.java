//@author A0105720W
package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.structure.Task;

public class DoneCommand extends Command {

	private ArrayList<Task> list = LogicUtilities.list;
	
	public DoneCommand(Task task) {
		super(task);
	}

	@Override
	public String executeCommand() {	
		String feedbackDone;
		try {		
			feedbackDone = labelTask(taskID);
			storage.inputTasks(list);
		} catch (IOException e) {
			feedbackDone = e.getMessage();
		}

		return feedbackDone;
	}
	
	private String labelTask(String id) {
		int index = LogicUtilities.getTaskIndexInArray(id);
		Task temp = list.get(index);
		temp.setDone(true);
		String MESSAGE_DONE = "Task " + id + " is successfully labeled as done.";
		return MESSAGE_DONE;		
	}
}
