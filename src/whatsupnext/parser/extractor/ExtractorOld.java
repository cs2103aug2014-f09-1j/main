package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.structure.Types.UPDATETYPE;
import whatsupnext.structure.Types.VIEWTYPE;

public class ExtractorOld {
	private ParseDate parseDate;
	private String input;
	private Task task;

	public ExtractorOld(Task task, String input){
		this.parseDate = new ParseDate();
		this.input = input;
		this.task = task;
	}
	
	
	
	/**
	 *  Extracts DELETE information from String input and
	 *  modifies the Task object accordingly
    */
	public void extractForDeleteTask(){
		// TODO: The rule for judging delete type may change
		
		if (countWords(input) == 1) {
			if (input.equalsIgnoreCase("deadline")) {
				deleteCaseDeadline();
			} else if (isDate(input)) {
				deleteCaseDate(input);
			} else {
				deleteCaseID(input);
			}
		} else {
	        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);
	        if (fromKeywordMatcher.find()) {
	        	deleteCaseTimeFrame(input);
	        } else {
	        	deleteCaseDate(input);
	        }
		}
	}

	/**
	 *  Extracts UPDATE information from String input and
	 *  modifies the Task object accordingly
    */
	public void extractForUpdateTask(){
		
		// Get the task ID and remove it from the remaining details
		try {
			String taskID = getFirstWord(input);
			Integer.parseInt(taskID);
			task.setTaskID(taskID);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Update task requires a valid integer id");
		}
		input = removeFirstWord(input);
		
		Pattern byKeywordPattern = Pattern.compile("(B|b)(Y|y)\\s+");
        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
        Matcher byKeywordMatcher = byKeywordPattern.matcher(input);
        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);
        
        if (byKeywordMatcher.find()){
        	// Remove 'by'
        	task.setEndTime(parseDate.parseInput(removeFirstWord(input)));
        	task.setUpdateType(UPDATETYPE.DEADLINE);
        } else if (fromKeywordMatcher.find()){
        	// Remove 'from'
        	splitOnToKeyword(input);
        	task.setUpdateType(UPDATETYPE.TIMEFRAME);
        } else {
        	task.setDescription(input);
        	task.setUpdateType(UPDATETYPE.DESCRIPTION);
        }
	}

	/**
	 *  Extracts VIEW information from String input and
	 *  modifies the Task object accordingly
    */
	public void extractForViewTask(){
		// First word is assumed to be 'view'
		
		if (countWords(input) == 1) {
			if (input.equalsIgnoreCase("all")) {
				viewCaseAll();
			} else if (input.equalsIgnoreCase("next")) {
				viewCaseNext();
			} else {
				viewCaseDate(input);
			}
		} else {
	        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);
	        if (fromKeywordMatcher.find()) {
	        	viewCaseTimeFrame(input);
	        } else {
	        	viewCaseDate(input);
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
		task.setEndTime(parseDate.getTodayDateTimeString());		
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
		task.setEndTime(parseDate.getTodayDateTimeString());
	}

	private void viewCaseAll() {
		task.setViewType(VIEWTYPE.ALL);
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
