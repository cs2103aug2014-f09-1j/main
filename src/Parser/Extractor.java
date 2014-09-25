package Parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Structure.Operation.OPCODE;

public class Extractor {

	private String string_full;
	private String task;
	private String startTime;
	private String endTime;
	
	private static final int INDEX_FOR_TASK = 0;
	// private static final int INDEX_FOR_TASKID = 0;
	private static final int INDEX_FOR_STARTTIME = 1;
	private static final int INDEX_FOR_ENDTIME = 2;
	// private static final int INDEX_FOR_TASKTYPE = 3;
	// TODO determine max words allowed
	private static final int MAX_COMPONENT_ALLOWED= 20;
	
	private final String[] ALIASES_ADD = {"add", "a"};
	private final String[] ALIASES_VIEW = {"view", "v", "list", "ls", "l"};
	private final String[] ALIASES_UPDATE = {"edit", "e", "modify", "m"};
	private final String[] ALIASES_DELETE = {"delete", "del", "d"};
	private final String[] ALIASES_HELP = {"help", "h", "?"};
	private final String[] ALIASES_EXIT = {"exit", "e", "quit", "q"};
	
	public Extractor(String input){
		this.string_full=input;
	}
	
	/**
	 *  This method extracts information from String input
	 * @return String vector containing info
	 */
	public String[] extractorAdd(){
		String[] operand_string = new String[INDEX_FOR_ENDTIME+1];
		
		// first word is assumed to be action type
		String task_detail = removeFirstWord(string_full); 

		// determine case: add by deadline or timeframe
		// define keyword
        Pattern byPattern = Pattern.compile("\\s+(B|b)(Y|y)\\s+");
        Pattern fromPattern = Pattern.compile("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
        Matcher byMatcher = byPattern.matcher(task_detail);
        Matcher fromMatcher = fromPattern.matcher(task_detail);
        
        if (byMatcher.find()){
        	return splitBy(operand_string, task_detail);
        }else if (fromMatcher.find()){
        	return splitFromTo(operand_string, task_detail);
        }else{
        	throw new Error("no keyword");
        }
		
	}

	/**
	 * * this method splits input based on keyword "from" and "to"
	 * @param operand_string
	 * @param task_detail
	 * @return outputString
	 */
	private String[] splitFromTo(String[] operand_string, String task_detail) {
		String[] details = task_detail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		operand_string[INDEX_FOR_TASK] = details[0];
		String details_time = details[1];
		String[] details_time_splitted = details_time.split("\\s+(T|t)(O|o)\\s+");
		operand_string[INDEX_FOR_STARTTIME] = details_time_splitted[0];
		operand_string[INDEX_FOR_ENDTIME] = details_time_splitted[1];
		return operand_string;
	}

	/**
	 *  this method splits input based on keyword "by"
	 * @param operand_string
	 * @param task_detail
	 * @return
	 */
	private String[] splitBy(String[] operand_string, String task_detail) {
		String[] details = task_detail.split("\\s+(B|b)(Y|y)\\s+");	
		operand_string[INDEX_FOR_TASK] = details[0];
		operand_string[INDEX_FOR_ENDTIME] = details[1];
		return operand_string;
	}
	
	
	// this method returns the first word of an input string
    private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
    
	// this method will remove the first word of a string
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}
	
}
