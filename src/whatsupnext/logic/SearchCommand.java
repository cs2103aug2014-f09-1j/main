package whatsupnext.logic;

import java.util.ArrayList;

import whatsupnext.structure.Task;

public class SearchCommand extends Command {

	private final String MESSAGE_NOTFOUND = "No tasks are found.";
	private ArrayList<Task> list = LogicUtilities.list;
	private ArrayList<String> output = LogicUtilities.output;
	
	public SearchCommand(Task task) {
		super(task);
	}
	
	public String executeCommand () {
		searchByDescription(description);
		
		String feedbackSearch = Logic.formatArrayAsString(output);
		output.clear();
		
		return feedbackSearch;
	}
	
	/*
	 * One type of SEARCH function.
	 */
	private void searchByDescription(String description) {
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			if (task.getDescription().contains(description)) {
				String taskInfo = Logic.getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
		
		if (output.isEmpty()) {
			output.add(MESSAGE_NOTFOUND);
		}
	}

}
