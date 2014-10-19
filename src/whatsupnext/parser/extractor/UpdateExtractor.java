package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.UPDATETYPE;

public class UpdateExtractor implements Extractor {
	private ParseDate parseDate;
	
	public UpdateExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public String extract(Task task, String input){

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
			String endTime = parseDate.parseInput(removeFirstWord(input));
			if (endTime.equals("")){
				throw new IllegalArgumentException("'update' must be followed by valid date");
			} else {
				task.setEndTime(endTime);
		        task.setUpdateType(UPDATETYPE.DEADLINE);
			}

		} else if (fromKeywordMatcher.find()){
		    // Remove 'from'
		    splitOnToKeyword(task,input);
		    task.setUpdateType(UPDATETYPE.TIMEFRAME);
		} else {
		    if (input==""){
		    	throw new IllegalArgumentException("'update' must be followed by content");
		    } else {
		        task.setDescription(input);
		        task.setUpdateType(UPDATETYPE.DESCRIPTION);		    	
		    }

		}
		
		return "";
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
			throw new IllegalArgumentException("'update' must be followed by valid date");
		} else {
			task.setStartTime(startTime);
		    task.setEndTime(endTime);
		}
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
	
}


