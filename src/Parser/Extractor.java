package Parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Structure.Task;

public class Extractor {

	private String input;
	private Task task;
	private int taskID;
	
	public Extractor(Task task, String input){
		this.input = input;
		this.task = task;
	}
	
	public int getTaskID(){
		return taskID;
	}
	
	/**
	 *  Extracts ADD information from String input and
	 *  modifies the Task object accordingly
	 */
	public void extractForAddTask(){
		// First word is assumed to be action type: add
		String addDetail = removeFirstWord(input); 

		// Determine 'add' case: add by deadline or time frame
		Pattern byKeywordPattern = Pattern.compile("\\s+(B|b)(Y|y)\\s+");
		Pattern fromKeywordPattern = Pattern.compile("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		Matcher byKeywordMatcher = byKeywordPattern.matcher(addDetail);
		Matcher fromKeywordMatcher = fromKeywordPattern.matcher(addDetail);

		if (byKeywordMatcher.find()) {
			splitOnByKeyword(addDetail);
		} else if (fromKeywordMatcher.find()) {
			splitOnFromToKeyword(addDetail);
		} else {
			// Not sure if this is where it's supposed to go...
			task.setDescription(addDetail);
			// throw new IllegalArgumentException("'add' task must have an argument");
		}		
	}
	
	/**
	 *  Extracts DELETE information from String input and
	 *  modifies the Task object accordingly
    */
	public void extractForDeleteTask(){
		// TODO: The rule for judging delete type may change
		// First word is assumed to be 'delete'
		String deleteDetail = removeFirstWord(input); 
		
		if (countWords(deleteDetail) == 1) {
			if (deleteDetail.equalsIgnoreCase("deadline")) {
				deleteCaseDeadline();
			} else if (isDate(deleteDetail)) {
				deleteCaseDate(deleteDetail);
			} else {
				deleteCaseID(deleteDetail);
			}
		} else {
	        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(deleteDetail);
	        if (fromKeywordMatcher.find()) {
	        	deleteCaseTimeFrame(deleteDetail);
	        } else {
	        	deleteCaseDate(deleteDetail);
	        }
		}
	}

	/**
	 *  Extracts UPDATE information from String input and
	 *  modifies the Task object accordingly
    */
	public void extractForUpdateTask(){
		// First word is assumed to be 'update'
		String updateDetail = removeFirstWord(input);
		
		// Get the task ID and remove it from the remaining details
		try {
			taskID = Integer.parseInt(getFirstWord(updateDetail));
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Update task requires a valid integer id");
		}
		updateDetail = removeFirstWord(updateDetail);
		
		Pattern byKeywordPattern = Pattern.compile("(B|b)(Y|y)\\s+");
        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
        Matcher byKeywordMatcher = byKeywordPattern.matcher(updateDetail);
        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(updateDetail);
        
        if (byKeywordMatcher.find()){
        	// Remove 'by'
        	task.setEndTime(removeFirstWord(updateDetail));
        	task.setUpdateType("DATE");
        } else if (fromKeywordMatcher.find()){
        	// Remove 'from'
        	splitOnToKeyword(updateDetail);
        	task.setUpdateType("TIMEFRAME");
        } else {
        	task.setDescription(updateDetail);
        	task.setUpdateType("DESCRIPTION");
        }
	}

	/**
	 *  Extracts VIEW information from String input and
	 *  modifies the Task object accordingly
    */
	public void extractForViewTask(){
		// First word is assumed to be 'view'
		String viewDetail = removeFirstWord(input); 
		
		if (countWords(viewDetail) == 1) {
			if (viewDetail.equalsIgnoreCase("all")) {
				viewCaseAll();
			} else if (viewDetail.equalsIgnoreCase("next")) {
				viewCaseNext();
			} else {
				viewCaseDate(viewDetail);
			}
		} else {
	        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(viewDetail);
	        if (fromKeywordMatcher.find()) {
	        	viewCaseTimeFrame(viewDetail);
	        }
		}
	}
	

	private void deleteCaseTimeFrame(String deleteDetail) {
		task.setDeleteType("TIMEFRAME");
		splitOnToKeyword(deleteDetail);
	}

	private void deleteCaseID(String deleteDetail) {
		task.setDeleteType("ID");
		task.setDescription(deleteDetail);
	}

	private void deleteCaseDate(String deleteDetail) {
		task.setDeleteType("DATE");
		task.setEndTime(deleteDetail);
	}

	private void deleteCaseDeadline() {
		task.setDeleteType("DEADLINE");
		task.setEndTime("NOW");		
	}
	
	private void viewCaseTimeFrame(String viewDetail) {
		task.setViewType("TIMEFRAME");
		splitOnToKeyword(viewDetail);
	}

	private void viewCaseDate(String viewDetail) {
		task.setViewType("DATE");
		task.setEndTime(viewDetail);
	}

	private void viewCaseNext() {
		task.setViewType("NEXT");
	}

	private void viewCaseAll() {
		task.setViewType("ALL");
	}
	

	/**
	 * For Add:
	 * Splits the input based on keyword "from" and "to"
	 * 
	 * @param taskDetail
	 */
	private void splitOnFromToKeyword(String taskDetail) {
		String[] details = taskDetail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		task.setDescription(details[0]);
		
		String detailsTime = details[1];
		String[] detailsTimeStartAndEnd = detailsTime.split("\\s+(T|t)(O|o)\\s+");
		task.setStartTime(detailsTimeStartAndEnd[0]);
		task.setEndTime(detailsTimeStartAndEnd[1]);
	}

	/**
	 * For Add:
	 * Splits input based on keyword "by"
	 * @param taskDetails
	 * @return
	 */
	private void splitOnByKeyword(String taskDetails) {
		String[] details = taskDetails.split("\\s+(B|b)(Y|y)\\s+");	
		task.setDescription(details[0]);
		task.setEndTime(details[1]);
	}
	
	/**
	 * For Delete and Update:
	 * Splits input based on keyword "to"
	 * @param taskDetails
	 * @return
	 */
	private void splitOnToKeyword(String taskDetails) {
		// Remove "from"
		taskDetails = removeFirstWord(taskDetails);
		String[] details = taskDetails.split("\\s+(T|t)(O|o)\\s+");	
		task.setStartTime(details[0]);
		task.setEndTime(details[1]);
	}
	
	/**
	 * Returns the first word of an input string
	 * @param userCommand
	 * @return
	 */
    private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
    
	/**
	 * Removes the first word of a string
	 * @param userCommand
	 * @return
	 */
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}
	
	/**
	 * Counts the number of words in a string
	 * @param input
	 * @return
	 */
	private int countWords (String input) {
		String trim = input.trim();
		if (trim.isEmpty()) {
			return 0;
		}
		// Separate string around 1 or more spaces
		return trim.split("\\s+").length;
	}
	
	private boolean isDate(String input){
		// TODO: the rule for judging date may change
        Pattern DatePatternSix = Pattern.compile("\\d\\d\\d\\d\\d\\d");
        Pattern DatePatternEight = Pattern.compile("\\d\\d\\d\\d\\d\\d\\d\\d");
        Matcher DateMatcherSix = DatePatternSix.matcher(input);
        Matcher DateMatcherEight = DatePatternEight.matcher(input);
		if (DateMatcherSix.find() || DateMatcherEight.find()){
			return true;
		}
		return false;
	}
}
