package whatsupnext.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

import java.io.IOException;
import java.util.ArrayList;

public class StorageTest {

	@Test
	public void testStringToTask() {	
		Storage obj = new Storage();
		Task dummyTask1 = obj.StringToTask("DummyDescription,DummyStartTime,DummyEndTime");
		assertEquals(dummyTask1.getDescription(), "DummyDescription");
		assertEquals(dummyTask1.getStartTime(), "DummyStartTime");
		assertEquals(dummyTask1.getEndTime(), "DummyEndTime");		
	}
	
	@Test
	public void testInputAndReadTasks() throws IOException {
		Storage obj = new Storage();
		Task dummyTask1 = obj.StringToTask("DummyDescription1,DummyStartTime1,DummyEndTime1");
		Task dummyTask2 = obj.StringToTask("DummyDescription2,DummyStartTime2,DummyEndTime2");
		Task dummyTask3 = obj.StringToTask("DummyDescription3,DummyStartTime3,DummyEndTime3");
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(dummyTask1);
		tasks.add(dummyTask2);
		tasks.add(dummyTask3);
		
		obj.inputTasks(tasks);
		
		ArrayList<Task> tasksToCompare = obj.readTasks();
		
		assertEquals(taskToString(tasks.get(0)), taskToString(tasksToCompare.get(0)));
		assertEquals(taskToString(tasks.get(1)), taskToString(tasksToCompare.get(1)));
		assertEquals(taskToString(tasks.get(2)), taskToString(tasksToCompare.get(2)));
	}
	
	public String taskToString(Task t) {
		return (t.getDescription() + "," + t.getStartTime() + "," + t.getEndTime());
	}

}
