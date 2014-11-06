//@author A0118897J
package whatsupnext.storage;

import static org.junit.Assert.assertNotNull;
import whatsupnext.structure.Task;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHelper {
	
	/**
	 * Converts a String in JSON format to a Task object
	 * @param input
	 * @return
	 */
	public Task JSONStringToTask(String input) {
		
		try {
			JSONParser parser = new JSONParser();
		    Object parsedJSON = parser.parse(input);
		    JSONArray arr = (JSONArray) parsedJSON;
		    
		    String taskID = (String) arr.get(0);
	    	String description = (String) arr.get(1);
	    	String startTime = (String) arr.get(2);
	    	String endTime = (String) arr.get(3);
	    	boolean isDone = (boolean) arr.get(4);
	    	
	    	Task taskFromJSON = new Task();
	    	taskFromJSON.setTaskID(taskID);
	    	taskFromJSON.setDescription(description);
	    	taskFromJSON.setStartTime(startTime);
	    	taskFromJSON.setEndTime(endTime);
	    	taskFromJSON.setDone(isDone);
	    	
			return taskFromJSON;
		}
		catch (ParseException p) {
			return null;
		}
	}
	
	/**
	 * Converts a Task object to a String in JSON format
	 * @param task
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String taskToJSONString(Task task) {
		assertNotNull(task.getTaskID());
		assertNotNull(task.getDescription());
		assertNotNull(task.getStartTime());
		assertNotNull(task.getEndTime());
		assertNotNull(task.getDone());
		
		JSONArray taskDetailsArray = new JSONArray();
		
		taskDetailsArray.add(task.getTaskID());
		taskDetailsArray.add(task.getDescription());
		taskDetailsArray.add(task.getStartTime());
		taskDetailsArray.add(task.getEndTime());
		taskDetailsArray.add(task.getDone());
		
		return taskDetailsArray.toJSONString();
	}

}
