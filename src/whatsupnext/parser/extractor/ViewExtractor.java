//@author A0092165E
package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.enums.Types.VIEWTYPE;
import whatsupnext.structure.util.Task;

public class ViewExtractor implements Extractor {
	
	private final String MESSAGE_INVALID_END_TIME = "'view' must have an valid end time";
	private final String MESSAGE_INVALID_START_TIME = "'view' must have an valid start time";
	private final String MESSAGE_INVALID_DATE = "'view' must have an valid date";
	private final String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
	
	private ParseDate parseDate;
	
	public ViewExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public void extract(Task task, String input){
		int numOfWord = Utility.countWords(input);
		if ( numOfWord == 0){
			viewCaseUndone(task);
		} else if (numOfWord == 1) {
			if (input.equalsIgnoreCase("all")) {
				viewCaseAll(task);
			} else if (input.equalsIgnoreCase("next")) {
				viewCaseNext(task);
			} else if (input.equalsIgnoreCase("overdue")){
				viewCaseOverdue(task);
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
	}
	
	
	/**
	 *  View case for overdue tasks
	 * @param task
	 */
	private void viewCaseOverdue(Task task) {
		task.setViewType(VIEWTYPE.OVERDUE);
		task.setEndTime(parseDate.getCurrentTime());
	}

	/**
	 *  View case for only tasks not done 
	 * @param task
	 */
    private void viewCaseUndone(Task task) {
    	task.setViewType(VIEWTYPE.UNDONE);	
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
		task.setEndTime(parseDate.parseInput(viewDetail));
		if (task.getEndTime().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_DATE);
		} 
	}

	/**
	 * View case: next
	 * @param task
	 */
	private void viewCaseNext(Task task) {
		task.setViewType(VIEWTYPE.NEXT);
		task.setEndTime(parseDate.getCurrentTime());
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
		taskDetails = Utility.removeFirstWord(taskDetails);
		String[] details = taskDetails.split("\\s+(T|t)(O|o)\\s+");
		parseDate.setParsingStartTime(true);
		task.setStartTime(parseDate.parseInput(details[0]));
		if (task.getStartTime().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_START_TIME);
		}
		parseDate.setParsingStartTime(false);
		task.setEndTime(parseDate.parseInput(details[1]));
		if (task.getEndTime().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
		if (task.getStartTime().compareTo(task.getEndTime())>0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_START_END_TIME);
		}
	}
	
		
	
}



