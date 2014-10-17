package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

public class ViewExtractor implements Extractor {
	private ParseDate parseDate;
	
	public ViewExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public String extract(Task task, String input){

		if (countWords(input) == 1) {
			if (input.equalsIgnoreCase("all")) {
				viewCaseAll(task);
			} else if (input.equalsIgnoreCase("next")) {
				viewCaseNext(task);
			} else {
				viewCaseDate(task,input);
			}
		} else {
	        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);
	        if (fromKeywordMatcher.find()) {
	        	viewCaseTimeFrame(task,input);
	        } else {
	        	viewCaseDate(task,input);
	        }
		}
		
		return "";
	}
	
    /**
     * View case for Time frame: start time and end time gven
     * @param task
     * @param viewDetail
     */
	private void viewCaseTimeFrame(Task task, String viewDetail) {
		task.setViewType(VIEWTYPE.TIMEFRAME);
		splitOnToKeyword(task,viewDetail);
	}

	/**
	 * View case: date
	 * @param task
	 * @param viewDetail
	 */
	private void viewCaseDate(Task task, String viewDetail) {
		task.setViewType(VIEWTYPE.DATE);
		String endTime = parseDate.parseInput(viewDetail);
		if (endTime.equals("")){
			throw new IllegalArgumentException("'view' must be followed by valid date");
		} else {
			task.setEndTime(endTime);	
		}

	}

	/**
	 * View case: next
	 * @param task
	 */
	private void viewCaseNext(Task task) {
		task.setViewType(VIEWTYPE.NEXT);
		task.setEndTime(parseDate.getTodayDateTimeString());
	}

	/**
	 * View case: all
	 * @param task
	 */
	private void viewCaseAll(Task task) {
		task.setViewType(VIEWTYPE.ALL);
	}
	
	
	/**
	 * For Update 
	 * Splits input based on keyword "to"
	 * @param taskDetails
	 * @return
	 */
	private void splitOnToKeyword(Task task,String taskDetails) {
		// Remove "from"
		taskDetails = removeFirstWord(taskDetails);
		String[] details = taskDetails.split("\\s+(T|t)(O|o)\\s+");	
		String startTime = parseDate.parseInput(details[0]);
		String endTime = parseDate.parseInput(details[1]);
		if (startTime.equals("") || endTime.equals("") ){
			throw new IllegalArgumentException("'view' must be followed by valid date");
		} else {
			task.setStartTime(startTime);
		    task.setEndTime(endTime);
		}
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
	
}



