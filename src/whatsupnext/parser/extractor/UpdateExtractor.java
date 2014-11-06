//@author A0092165E
package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.UPDATETYPE;

public class UpdateExtractor implements Extractor {
	
	private final String MESSAGE_INVALID_TASKID = "'update' must have a valid Task ID";
	private final String MESSAGE_INVALID_DESCRIPTION = "'update' must have a valid description";
	private final String MESSAGE_INVALID_END_TIME = "'update' must have a valid end time";
	private final String MESSAGE_INVALID_START_TIME = "'update' must have a valid start time";
	private final String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
	
	private ParseDate parseDate;
	
	public UpdateExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public void extract(Task task, String input){

		// Get the task ID and remove it from the remaining details
		try {
			String taskID = Utility.getFirstWord(input);
			if(Integer.parseInt(taskID) < 0 ){
				throw new IllegalArgumentException(MESSAGE_INVALID_TASKID);
			}
			task.setTaskID(taskID);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(MESSAGE_INVALID_TASKID);
		}
		input = Utility.removeFirstWord(input);
				
		Pattern byKeywordPattern = Pattern.compile("(B|b)(Y|y)\\s+");
		Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
		Matcher byKeywordMatcher = byKeywordPattern.matcher(input);
		Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);
		        
		if (byKeywordMatcher.find()){
		    // Remove 'by'
			input = Utility.removeFirstWord(input);
					
			task.setUpdateType(UPDATETYPE.DEADLINE);
			task.setEndTime(parseDate.parseInput(input));
			if (task.getEndTime().isEmpty()
					|| task.getEndTime().compareTo(parseDate.getCurrentTime()) < 0) {
				throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
			} 

		} else if (fromKeywordMatcher.find()){
		    splitOnToKeyword(task,input);
		    task.setUpdateType(UPDATETYPE.TIMEFRAME);
		} else {
		    if (input.isEmpty()){
		    	throw new IllegalArgumentException(MESSAGE_INVALID_DESCRIPTION);
		    } else {
		        task.setDescription(input);
		        task.setUpdateType(UPDATETYPE.DESCRIPTION);		    	
		    }

		}
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
		if (task.getStartTime().isEmpty()
				|| task.getStartTime().compareTo(parseDate.getCurrentTime()) < 0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_START_TIME);
		}
		parseDate.setParsingStartTime(false);
		task.setEndTime(parseDate.parseInput(details[1]));
		if (task.getEndTime().isEmpty()
				|| task.getEndTime().compareTo(parseDate.getCurrentTime()) < 0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
		if (task.getStartTime().compareTo(task.getEndTime())>0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_START_END_TIME);
		}
	}
	
	

	
}


