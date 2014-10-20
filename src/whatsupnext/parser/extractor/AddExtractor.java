package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;

public class AddExtractor implements Extractor {
	
	private final String MESSAGE_INVALID_DESCRIPTION = "'add' must have a valid description";
	private final String MESSAGE_INVALID_END_TIME = "'add' must have a valid end time";
	private final String MESSAGE_INVALID_START_TIME = "'add' must have a valid start time";
	
	private ParseDate parseDate;
	
	public AddExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public void extract(Task task, String input){
		// Determine 'add' case: add by deadline or time frame
		Pattern byKeywordPattern = Pattern.compile("\\s+(B|b)(Y|y)\\s+");
		Pattern fromKeywordPattern = Pattern.compile("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		Matcher byKeywordMatcher = byKeywordPattern.matcher(input);
		Matcher fromKeywordMatcher = fromKeywordPattern.matcher(input);

		if (byKeywordMatcher.find()) {
			task.setAddType(ADDTYPE.DEADLINE);
			splitOnByKeyword(task, input);
		} else if (fromKeywordMatcher.find()) {
			task.setAddType(ADDTYPE.TIMEFRAME);
			splitOnFromToKeyword(task, input);
		} else {
			task.setAddType(ADDTYPE.FLOATING);
			task.setDescription(input);
			if(task.getDescription().isEmpty() 
					|| task.getDescription().matches(".*(B|b)(Y|y).*") 
					|| task.getDescription().matches(".*(F|f)(R|r)(O|o)(M|m).*")){
				throw new IllegalArgumentException(MESSAGE_INVALID_DESCRIPTION);
			}
		}	
	
	}

	/**
	 * Splits the input based on keyword "from" and "to"
	 * 
	 * @param taskDetail
	 */
	private void splitOnFromToKeyword(Task task, String taskDetail) {
		String[] details = taskDetail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		task.setDescription(details[0]);
		if(task.getDescription().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_DESCRIPTION);
		}
		String detailsTime = details[1];
		String[] detailsTimeStartAndEnd = detailsTime.split("\\s+(T|t)(O|o)\\s+");
		task.setStartTime(parseDate.parseInput(detailsTimeStartAndEnd[0]));
		if(task.getStartTime().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_START_TIME);
		}
		task.setEndTime(parseDate.parseInput(detailsTimeStartAndEnd[1]));
		if(task.getEndTime().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
	}

	/**
	 * Splits input based on keyword "by"
	 * @param taskDetails
	 * @return
	 */
	private void splitOnByKeyword(Task task, String taskDetails) {
		String[] details = taskDetails.split("\\s+(B|b)(Y|y)\\s+");
		task.setDescription(details[0]);
		task.setEndTime(parseDate.parseInput(details[1]));
		if(task.getEndTime().isEmpty()){
			throw new IllegalArgumentException(MESSAGE_INVALID_END_TIME);
		}
	}
	
}
