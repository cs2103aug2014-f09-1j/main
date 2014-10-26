package whatsupnext.logic;

import java.util.ArrayList;
import java.util.StringTokenizer;

import whatsupnext.structure.Task;

public class SearchCommand extends Command {

	private enum RELEVANCE {
		HIGH, LOW, NO
	}
	
	private final String MESSAGE_NOTFOUND = "No tasks are found.";
	private ArrayList<Task> list = LogicUtilities.list;
	private ArrayList<Task> highRelevance = new ArrayList<Task>();
	private ArrayList<Task> lowRelevance = new ArrayList<Task>();
	private ArrayList<String> output = LogicUtilities.output;
	
	public SearchCommand(Task task) {
		super(task);
	}
	
	public String executeCommand () {
		searchByDescription(description);		
		getOutput();
		
		String feedbackSearch = LogicUtilities.formatArrayAsString(output);
		output.clear();
		
		return feedbackSearch;
	}

	private void searchByDescription(String keywords) {
			
		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			String taskDescription = task.getDescription();			
			
		    RELEVANCE relevance;
		    relevance = compareTasks(taskDescription, keywords);	
		    
		    if (relevance == RELEVANCE.HIGH) {
		    	highRelevance.add(task);
			} else if (relevance == RELEVANCE.LOW) {
				lowRelevance.add(task);
			}
		}	
	}
	
	/*
	 * Compare the relevance between the current task and the keyword.
	 */
	private RELEVANCE compareTasks(String taskDescription, String keywords) {
		RELEVANCE r = RELEVANCE.NO;
		StringTokenizer keywordToken = new StringTokenizer(keywords);
		int number = 0;
		int keyword = keywordToken.countTokens();
		
		while (keywordToken.hasMoreTokens()) {
			if (taskDescription.contains(keywordToken.nextToken())) {
				number++;
			} else {
				break;
			}
		}
		
		if (number >= keyword) {
			r = RELEVANCE.LOW;
		}
		
		number = 0;
		StringTokenizer keywordToken2 = new StringTokenizer(keywords);
		while (keywordToken2.hasMoreTokens()) {
			StringTokenizer descriptionToken = new StringTokenizer(taskDescription);
			String temp = keywordToken2.nextToken();
			
			while (descriptionToken.hasMoreTokens()) {
				if (descriptionToken.nextToken().equalsIgnoreCase(temp)) {
					number++;
				}
			}
		}
		
		if ((number >= keyword) && (r==RELEVANCE.LOW)) {
			r = RELEVANCE.HIGH;
		} 
			
		return r;
	}
	
	private void getOutput() {
		int high_size = highRelevance.size();
		int low_size = lowRelevance.size();
		
		if (high_size > 0) {
			for (int i = 0; i < high_size; i++) {
				Task task = highRelevance.get(i);
				String taskInfo = LogicUtilities.getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
		
		if (low_size > 0) {
			for (int i = 0; i < low_size; i++) {
				Task task = lowRelevance.get(i);
				String taskInfo = LogicUtilities.getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
			
		if ((high_size == 0) && (low_size == 0)) {
			output.add(MESSAGE_NOTFOUND);
		}
	}
}
