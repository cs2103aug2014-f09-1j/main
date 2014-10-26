package whatsupnext.logic;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
		
		String feedbackSearch = LogicUtilities.formatArrayAsString(output);
		output.clear();
		
		return feedbackSearch;
	}

	private void searchByDescription(String keywords) {
			
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			String taskDescription = task.getDescription();
			int number = 0;
			
			StringTokenizer keywordToken = new StringTokenizer(keywords);
		    int tokenNumber = keywordToken.countTokens();
		    
			while (keywordToken.hasMoreTokens()) {
				if (taskDescription.equalsIgnoreCase(keywordToken.nextToken())) {
					number++;
				} else {
					break;
				}
			}
			
			if (number == tokenNumber) {
				String taskInfo = LogicUtilities.getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
		
		if (output.isEmpty()) {
			output.add(MESSAGE_NOTFOUND);
		}
	}

}
