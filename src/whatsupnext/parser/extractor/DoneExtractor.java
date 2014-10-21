package whatsupnext.parser.extractor;

import whatsupnext.structure.Task;

public class DoneExtractor implements Extractor{
	private final String MESSAGE_INVALID_TASKID = "'Done' must have a valid ID";
	
	public void extract(Task task, String input){
		try {
			String taskID = Utility.getFirstWord(input);
			if(Integer.parseInt(taskID) < 0 ){
				throw new IllegalArgumentException(MESSAGE_INVALID_TASKID);
			}
			task.setTaskID(taskID);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(MESSAGE_INVALID_TASKID);
		}	
	}
	
}
