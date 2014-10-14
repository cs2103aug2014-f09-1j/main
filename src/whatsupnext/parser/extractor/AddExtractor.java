package whatsupnext.parser.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;

public class AddExtractor implements Extractor {
	private ParseDate parseDate;
	
	public AddExtractor(){
		this.parseDate = new ParseDate();
	}
	
	public String extract(Task task, String input){

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
			// throw new IllegalArgumentException("'add' task must have an argument");
		}		
		
		return "";
	}
	
	/**
	 * Splits the input based on keyword "from" and "to"
	 * 
	 * @param taskDetail
	 */
	private void splitOnFromToKeyword(Task task, String taskDetail) {
		String[] details = taskDetail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		task.setDescription(details[0]);
		
		String detailsTime = details[1];
		String[] detailsTimeStartAndEnd = detailsTime.split("\\s+(T|t)(O|o)\\s+");
		task.setStartTime(parseDate.parseInput(detailsTimeStartAndEnd[0]));
		task.setEndTime(parseDate.parseInput(detailsTimeStartAndEnd[1]));
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
	}
	
	
}
