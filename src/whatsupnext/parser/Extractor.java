package whatsupnext.parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.structure.Types.UPDATETYPE;
import whatsupnext.structure.Types.VIEWTYPE;

public class Extractor {

	private ParseDate parseDate;
	private String input;
	private Task task;

	public Extractor(Task task, String input){
		this.parseDate = new ParseDate();
		this.input = input;
		this.task = task;
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
			task.setAddType(ADDTYPE.DEADLINE);
			splitOnByKeyword(addDetail);
		} else if (fromKeywordMatcher.find()) {
			task.setAddType(ADDTYPE.TIMEFRAME);
			splitOnFromToKeyword(addDetail);
		} else {
			task.setAddType(ADDTYPE.FLOATING);
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
			String taskID = getFirstWord(updateDetail);
			Integer.parseInt(taskID);
			task.setTaskID(taskID);
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
        	task.setEndTime(parseDate.parseInput(removeFirstWord(updateDetail)));
        	task.setUpdateType(UPDATETYPE.DEADLINE);
        } else if (fromKeywordMatcher.find()){
        	// Remove 'from'
        	splitOnToKeyword(updateDetail);
        	task.setUpdateType(UPDATETYPE.TIMEFRAME);
        } else {
        	task.setDescription(updateDetail);
        	task.setUpdateType(UPDATETYPE.DESCRIPTION);
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
	        } else {
	        	viewCaseDate(viewDetail);
	        }
		}
	}
	

	private void deleteCaseTimeFrame(String deleteDetail) {
		task.setDeleteType(DELETETYPE.TIMEFRAME);
		splitOnToKeyword(deleteDetail);
	}

	private void deleteCaseID(String deleteDetail) {
		task.setDeleteType(DELETETYPE.ID);
		try {
			Integer.parseInt(deleteDetail);
			task.setTaskID(deleteDetail);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Delete task requires a valid integer id");
		}
	}

	private void deleteCaseDate(String deleteDetail) {
		task.setDeleteType(DELETETYPE.DATE);
		task.setEndTime(parseDate.parseInput(deleteDetail));
	}

	private void deleteCaseDeadline() {
		task.setDeleteType(DELETETYPE.DEADLINE);
		// TODO get current date and time
		task.setEndTime("NOW");		
	}
	
	private void viewCaseTimeFrame(String viewDetail) {
		task.setViewType(VIEWTYPE.TIMEFRAME);
		splitOnToKeyword(viewDetail);
	}

	private void viewCaseDate(String viewDetail) {
		task.setViewType(VIEWTYPE.DATE);
		task.setEndTime(parseDate.parseInput(viewDetail));
	}

	private void viewCaseNext() {
		task.setViewType(VIEWTYPE.NEXT);
	}

	private void viewCaseAll() {
		task.setViewType(VIEWTYPE.ALL);
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
		task.setStartTime(parseDate.parseInput(detailsTimeStartAndEnd[0]));
		task.setEndTime(parseDate.parseInput(detailsTimeStartAndEnd[1]));
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
		task.setEndTime(parseDate.parseInput(details[1]));
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
		task.setStartTime(parseDate.parseInput(details[0]));
		task.setEndTime(parseDate.parseInput(details[1]));
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
		String commandString;
		try {
			commandString = userCommand.trim().split("\\s+", 2)[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			commandString = "";
		}
		return commandString;
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
        Pattern datePatternSix = Pattern.compile("\\d\\d\\d\\d\\d\\d");
        Pattern datePatternEight = Pattern.compile("\\d\\d\\d\\d\\d\\d\\d\\d");
        Matcher dateMatcherSix = datePatternSix.matcher(input);
        Matcher dateMatcherEight = datePatternEight.matcher(input);
		return (dateMatcherSix.find() || dateMatcherEight.find());
	}
}
