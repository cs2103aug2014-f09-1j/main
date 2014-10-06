package whatsupnext.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

import java.io.IOException;
import java.util.ArrayList;

public class StorageTest {
	Storage obj;
	
	@Before
	public void initialize() {
		obj = new Storage();
	}

	@After
	public void clearFile() {
		try {
			obj.clearFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStringToTask() {	
		obj = new Storage();
		Task dummyTask1 = obj.StringToTask("DummyTaskID,DummyDescription,DummyStartTime,DummyEndTime");
		assertEquals(dummyTask1.getTaskID(), "DummyTaskID");
		assertEquals(dummyTask1.getDescription(), "DummyDescription");
		assertEquals(dummyTask1.getStartTime(), "DummyStartTime");
		assertEquals(dummyTask1.getEndTime(), "DummyEndTime");		
	}
	
	@Test
	public void testInputAndReadTasks() throws IOException {
		obj = new Storage();
		obj.clearFile();
		
		Task dummyTask1 = obj.StringToTask("DummyTaskID1,DummyDescription1,DummyStartTime1,DummyEndTime1");
		Task dummyTask2 = obj.StringToTask("DummyTaskID2,DummyDescription2,DummyStartTime2,DummyEndTime2");
		Task dummyTask3 = obj.StringToTask("DummyTaskID3,DummyDescription3,DummyStartTime3,DummyEndTime3");
		
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
		return (t.getTaskID() + "," + t.getDescription() + "," + t.getStartTime() + "," + t.getEndTime());
	}

}
