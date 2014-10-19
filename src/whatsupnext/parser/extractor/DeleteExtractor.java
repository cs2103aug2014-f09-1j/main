package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.DELETETYPE;

public class DeleteExtractor implements Extractor {
	private ParseDate parseDate;
	
	public DeleteExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public void extract(Task task, String input){

		if (countWords(input) == 1) {
			// check if input string is formatted date
		    String formattedDate = parseDate.parseInput(input);
		    if (input.equalsIgnoreCase("deadline")) {
				deleteCaseDeadline(task);
			} else if (!formattedDate.equals("")) {
			// TODO; should judge for valid ID instead of valid date	
				deleteCaseDate(task,input);
			} else {
				deleteCaseID(task,input);
			}
		} else {
	        Pattern fromKeywordPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);
	        if (fromKeywordMatcher.find()) {
	        	deleteCaseTimeFrame(task,input);
	        } else {
	        	deleteCaseDate(task,input);
	        }
		}
	}
	
	/**
	 * Delete by ID. delete detail should be valid task ID
	 * @param task
	 * @param deleteDetail
	 */
	private void deleteCaseID(Task task,String deleteDetail) {
		task.setDeleteType(DELETETYPE.ID);
		try {
			int deleteID = Integer.parseInt(deleteDetail);
			task.setTaskID(deleteDetail);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Delete task requires a valid integer id");
		}
	}

	/**
	 * Delete by Date
	 * @param task
	 * @param deleteDetail
	 */
	private void deleteCaseDate(Task task,String deleteDetail) {
		task.setDeleteType(DELETETYPE.DATE);
		String endTime = parseDate.parseInput(deleteDetail);
		if (!endTime.equals("")){
		     task.setEndTime(endTime);
		} else {
			 throw new IllegalArgumentException("'delete' must be followed by valid ID or date");
		}
	}

	/**
	 * Delete by Deadline
	 * @param task
	 */
	private void deleteCaseDeadline(Task task) {
		task.setDeleteType(DELETETYPE.DEADLINE);
		task.setEndTime(parseDate.getTodayDateTimeString());		
	}
	
	/**
	 * Delete by Time frame
	 * @param task
	 * @param deleteDetail
	 */
	private void deleteCaseTimeFrame(Task task,String deleteDetail) {
		task.setDeleteType(DELETETYPE.TIMEFRAME);
		splitOnToKeyword(task,deleteDetail);
	}

	
	/**
	 * For Delete 
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
			throw new IllegalArgumentException("'delete' must be followed by valid ID or date");
		} else {
			task.setStartTime(startTime);
		    task.setEndTime(endTime);
		}
	}
	
	
	/**
	 * Count the total number of words in a string
	 * @param userCommand
	*/
	private int countWords (String input) {
		String trim = input.trim();
		if (trim.isEmpty()) {
			return 0;
		}
		// Separate string around 1 or more spaces
		return trim.split("\\s+").length;
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

