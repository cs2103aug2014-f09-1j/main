package Parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Structure.Task;

public class Extractor {

	private String input;
	private Task task;
	private int taskID;
	
	public Extractor(Task task, String input){
		this.input = input;
		this.task = task;
	}
	public int gettaskID(){
		return taskID;
	}
	
	/**
	 *  This method extracts ADD information from String input
	 *  Add modify the Task object accordingly
	 */
	public void extractorAdd(){
		
		// first word is assumed to be action type: add
		String taskDetail = removeFirstWord(input); 

		// determine case: add by deadline or timeframe
		// define keyword
        Pattern byPattern = Pattern.compile("\\s+(B|b)(Y|y)\\s+");
        Pattern fromPattern = Pattern.compile("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
        Matcher byMatcher = byPattern.matcher(taskDetail);
        Matcher fromMatcher = fromPattern.matcher(taskDetail);
        
        if (byMatcher.find()){
        	splitBy(taskDetail);
        }else if (fromMatcher.find()){
        	splitFromTo(taskDetail);
        }else{
        	throw new Error("no keyword");
        }
		
	}
	
	/**
	 *  This method extracts delete information from String input
	 *  Add modify the Task object accordingly
    */
	public void extractorDelete(){
		// TODO  the rule for judging delete type may change
		// first word is assumed to be action type :delete
		String deleteDetail = removeFirstWord(input); 
		
		if (countWords(deleteDetail)==1 ){
			if (deleteDetail.equalsIgnoreCase("deadline")){
				// case deadline
				deleteCaseDeadline();
			    return;
			} else if(isDate(deleteDetail)){
			    // case Date
				deleteCaseDate(deleteDetail);
				return;
			} else {
			    // case ID
				deleteCaseID(deleteDetail);
				return;
			}
		} else {
	        Pattern fromPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
	        Matcher fromMatcher = fromPattern.matcher(deleteDetail);
	        if (fromMatcher.find()){
	        	// case timeframe
	        	deleteCaseTimeFrame(deleteDetail);
	        	return;
	        } else {
	        	// case Date
	        	deleteCaseDate(deleteDetail);
	        	return;
	        }
		}				
	}

	
	public void extractorUpdate(){
		// first word is action type: update
		String updateDetail = removeFirstWord(input);
		// get task ID, remove from detail string
		taskID = Integer.parseInt(getFirstWord(updateDetail));
		updateDetail = removeFirstWord(updateDetail);
		
		Pattern byPattern = Pattern.compile("(B|b)(Y|y)\\s+");
        Pattern fromPattern = Pattern.compile("(F|f)(R|r)(O|o)(M|m)\\s+");
        Matcher byMatcher = byPattern.matcher(updateDetail);
        Matcher fromMatcher = fromPattern.matcher(updateDetail);
        
        if (byMatcher.find()){
        	// removing by
        	task.setEndTime(removeFirstWord(updateDetail));
        	task.setUpdateType("DATE");
        } else if (fromMatcher.find()){
        	// remove from
        	splitTo(updateDetail);
        	task.setUpdateType("TIMEFRAME");
        	return;
        } else {
        	task.setDescription(updateDetail);
        	task.setUpdateType("DESCRIPTION");
        }
		
	}

	

	private void deleteCaseTimeFrame(String deleteDetail) {
		task.setDeleteType("TIMEFRAME");
		splitTo(deleteDetail);
	}

	private void deleteCaseID(String deleteDetail) {
		task.setDeleteType("ID");
		task.setDescription(deleteDetail);
	}

	private void deleteCaseDate(String deleteDetail) {
		task.setDeleteType("DATE");
		task.setEndTime(deleteDetail);
	}

	private void deleteCaseDeadline() {
		task.setDeleteType("DEADLINE");
		task.setEndTime("NOW");		
	}
	

	/**
	 * * For Add
	 * this method splits input based on keyword "from" and "to"
	 * @param operand_string
	 * @param task_detail
	 * @return outputString
	 */
	private void splitFromTo(String task_detail) {
		String[] details = task_detail.split("\\s+(F|f)(R|r)(O|o)(M|m)\\s+");
		task.setDescription(details[0]);
		String details_time = details[1];
		String[] details_time_splitted = details_time.split("\\s+(T|t)(O|o)\\s+");
		task.setStartTime(details_time_splitted[0]);
		task.setEndTime(details_time_splitted[1]);
	}

	/**
	 * For Add
	 *  this method splits input based on keyword "by"
	 * @param task_detail
	 * @return
	 */
	private void splitBy(String task_detail) {
		String[] details = task_detail.split("\\s+(B|b)(Y|y)\\s+");	
		task.setDescription(details[0]);
		task.setEndTime(details[1]);
	}
	
	/**
	 * For Delete
	 *  this method splits input based on keyword "to"
	 * @param delete_detail
	 * @return
	 */
	private void splitTo(String delete_detail) {
		// remove "from"
		delete_detail=removeFirstWord(delete_detail);
		String[] details = delete_detail.split("\\s+(T|t)(O|o)\\s+");	
		task.setStartTime(details[0]);
		task.setEndTime(details[1]);
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
	
	// this method counts number of words in a string
	private int countWords (String input) {
		String trim = input.trim();
		if (trim.isEmpty()) return 0;
		return trim.split("\\s+").length; //separate string around spaces
	}
	
	private boolean isDate(String input){
		// TODO the rule for judging date may change
        Pattern DatePatternSix = Pattern.compile("\\d\\d\\d\\d\\d\\d");
        Pattern DatePatternEight = Pattern.compile("\\d\\d\\d\\d\\d\\d\\d\\d");
        Matcher DateMatcherSix = DatePatternSix.matcher(input);
        Matcher DateMatcherEight = DatePatternEight.matcher(input);
		if (DateMatcherSix.find() || DateMatcherEight.find()){
			return true;
		}
		return false;
	}
}
