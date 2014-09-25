package Parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Structure.Task;

public class Extractor {

	private String input;
	private Task task;
	
	public Extractor(Task task, String input){
		this.input = input;
		this.task = task;
	}
	
	/**
	 *  This method extracts information from String input
	 * @return String vector containing info
	 */
	public void extractorAdd(){
		
		// first word is assumed to be action type
		String taskDetail = removeFirstWord(input); 

		// determine case: add by deadline or timeframe
		// define keyword
        Pattern byPattern = Pattern.compile("\\s+(B|b)(Y|y)\\s+");
        Pattern fromPattern = Pattern.compile("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
        Matcher byMatcher = byPattern.matcher(taskDetail);
        Matcher fromMatcher = fromPattern.matcher(taskDetail);
        
        if (byMatcher.find()){
        	splitBy(taskDetail);
        }else if (fromMatcher.find()){
        	splitFromTo(taskDetail);
        }else{
        	throw new Error("no keyword");
        }
		
	}

	/**
	 * * this method splits input based on keyword "from" and "to"
	 * @param operand_string
	 * @param task_detail
	 * @return outputString
	 */
	private void splitFromTo(String task_detail) {
		String[] details = task_detail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		task.setDescription(details[0]);
		String details_time = details[1];
		String[] details_time_splitted = details_time.split("\\s+(T|t)(O|o)\\s+");
		task.setStartTime(details_time_splitted[0]);
		task.setEndTime(details_time_splitted[1]);
	}

	/**
	 *  this method splits input based on keyword "by"
	 * @param operand_string
	 * @param task_detail
	 * @return
	 */
	private void splitBy(String task_detail) {
		String[] details = task_detail.split("\\s+(B|b)(Y|y)\\s+");	
		task.setDescription(details[0]);
		task.setEndTime(details[1]);
	}
	
	
	// this method returns the first word of an input string
    private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
    
	// this method will remove the first word of a string
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}
	
}
