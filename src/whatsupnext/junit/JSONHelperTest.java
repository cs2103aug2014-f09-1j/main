//@author A0118897J
package whatsupnext.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import whatsupnext.storage.JSONHelper;
import whatsupnext.structure.Task;

public class JSONHelperTest {
	JSONHelper obj;
	String inputJSONString;
	
	@Before
	public void initialize() {
		obj = new JSONHelper();
		inputJSONString = "[\"DummyTaskID1\",\"DummyDescription1\",\"DummyStartTime1\",\"DummyEndTime1\",true]";
	}
	
	@Test
	public void testJSONStringToTask() {
		Task testTask = obj.JSONStringToTask(inputJSONString);
		assertEquals(testTask.getTaskID(), "DummyTaskID1");
		assertEquals(testTask.getDescription(), "DummyDescription1");
		assertEquals(testTask.getStartTime(), "DummyStartTime1");
		assertEquals(testTask.getEndTime(), "DummyEndTime1");
		assertEquals(testTask.getDone(), true);		
	}
	
	@Test
	public void testTaskToJSONString() {
		Task task = new Task();
    	task.setTaskID("DummyTaskID1");
    	task.setDescription("DummyDescription1");
    	task.setStartTime("DummyStartTime1");
    	task.setEndTime("DummyEndTime1");
    	task.setDone(true);
    	
    	String testString = obj.taskToJSONString(task);    	
    	
    	assertEquals(inputJSONString, testString);
    	
	}
}
