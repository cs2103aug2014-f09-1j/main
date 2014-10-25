package whatsupnext.logic;

import java.io.IOException;
import java.util.ArrayList;

import whatsupnext.structure.Task;

public class DoneCommand extends Command {

	private final String MESSAGE_DONE = "Tasks are successfully labeled.";
	private ArrayList<Task> list = LogicUtilities.list;
	
	public DoneCommand(Task task) {
		super(task);
	}

	@Override
	public String executeCommand() {
		labelTask(taskID);
		
		String feedbackDone;
		try {
			storage.inputTasks(list);
			feedbackDone = MESSAGE_DONE;
		} catch (IOException e) {
			feedbackDone = e.getMessage();
		}

		return feedbackDone;
	}
	
	private void labelTask(String id) {
		int index = LogicUtilities.getTaskIndexInArray(id);
		Task temp = list.get(index);
		temp.setDone(true);
	}
}
