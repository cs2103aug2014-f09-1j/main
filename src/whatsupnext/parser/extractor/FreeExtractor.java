//@author A0092165E
package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.enums.Types.FREETYPE;
import whatsupnext.structure.util.Task;

public class FreeExtractor implements Extractor {
	
	private final String MESSAGE_INVALID_DURATION = "'free' must have a valid duration: # of hours";
	private final String MESSAGE_INVALID_END_TIME = "'free' must have a valid end time";
	private final String MESSAGE_INVALID_START_TIME = "'free' must have a valid start time";
	private final String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
	
	private ParseDate parseDate;
	
	public FreeExtractor(){
		this.parseDate = new ParseDate();
	}
	
	
	public void extract(Task task, String input){
		Pattern onKeywordPattern = Pattern.compile("\\s+(O|o)(N|n)\\s+");
		Pattern fromKeywordPattern = Pattern.compile("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		Matcher onKeywordMatcher = onKeywordPattern.matcher(input);
		Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);

		if (onKeywordMatcher.find()) {
			task.setFreeType(FREETYPE.DATE);
			splitOnOnKeyword(task, input);
		} else if (fromKeywordMatcher.find()) {
			task.setFreeType(FREETYPE.TIMEFRAME);
			splitOnFromToKeyword(task, input);
		} else {
			task.setFreeType(FREETYPE.TIMEFRAME);
			getFreeTimeToday(task,input);
		}	
	
	}

	
	/**
	 * Find free time slots today
	 * @param task
	 * @param input
	 */
	private void getFreeTimeToday(Task task, String input) {
		try {
			if(Integer.parseInt(input) < 0) {
				throw new IllegalArgumentException(MESSAGE_INVALID_DURATION) ;
			}
			task.setDescription(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(MESSAGE_INVALID_DURATION);
		}
		task.setStartTime(parseDate.getCurrentTime());
		parseDate.setParsingStartTime(false);
		task.setEndTime(parseDate.getTodayDate()+"2359");
	}

	
	/**
	 * Splits the input based on keyword "from" and "to"
	 * @param taskDetail
	 */
	private void splitOnFromToKeyword(Task task, String taskDetail) {
		String[] details = taskDetail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		task.setDescription(details[0]);
		if(task.getDescription().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_DURATION);
		}
		try {
			if(Integer.parseInt(task.getDescription()) < 0) {
				throw new IllegalArgumentException(MESSAGE_INVALID_DURATION) ;
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException(MESSAGE_INVALID_DURATION);
		}
		
		String detailsTime = details[1];
		String[] detailsTimeStartAndEnd = detailsTime.split("\\s+(T|t)(O|o)\\s+");
		if(detailsTimeStartAndEnd.length == 1) {
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
		parseDate.setParsingStartTime(true);
		task.setStartTime(parseDate.parseInput(detailsTimeStartAndEnd[0]));
		if(task.getStartTime().isEmpty() 
				|| task.getStartTime().compareTo(parseDate.getCurrentTime()) < 0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_START_TIME);
		}
		parseDate.setParsingStartTime(false);
		task.setEndTime(parseDate.parseInput(detailsTimeStartAndEnd[1]));
		if(task.getEndTime().isEmpty() 
				|| task.getEndTime().compareTo(parseDate.getCurrentTime()) < 0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
		if (task.getStartTime().compareTo(task.getEndTime())>0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_START_END_TIME);
		}
	}

	/**
	 * Splits input based on keyword "on"
	 * @param taskDetails
	 * @return
	 */
	private void splitOnOnKeyword(Task task, String taskDetails) {
		String[] details = taskDetails.split("\\s+(O|o)(N|n)\\s+");
		parseDate.setParsingStartTime(false);
		task.setDescription(details[0]);
		try {
			if(Integer.parseInt(task.getDescription()) < 0) {
				throw new IllegalArgumentException(MESSAGE_INVALID_DURATION) ;
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException(MESSAGE_INVALID_DURATION);
		}
		
		task.setEndTime(parseDate.parseInput(details[1]));
		if(task.getEndTime().isEmpty() 
				|| task.getEndTime().compareTo(parseDate.getCurrentTime()) < 0) {
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
	}

	

	
}
