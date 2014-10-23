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
		obj = Storage.getInstance("storageTest.txt");
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
		Task dummyTask1 = obj.stringToTask("DummyTaskID" + Storage.DELIMITER + "DummyDescription" + 
				Storage.DELIMITER + "DummyStartTime" + Storage.DELIMITER + "DummyEndTime" + Storage.DELIMITER + 
				"true" + Storage.DELIMITER);
		assertEquals(dummyTask1.getTaskID(), "DummyTaskID");
		assertEquals(dummyTask1.getDescription(), "DummyDescription");
		assertEquals(dummyTask1.getStartTime(), "DummyStartTime");
		assertEquals(dummyTask1.getEndTime(), "DummyEndTime");
		assertEquals(dummyTask1.getDone(), true);		
	}
	
	@Test
	public void testInputAndReadTasks() throws IOException {
		
		Task dummyTask1 = obj.stringToTask("DummyTaskID1" + Storage.DELIMITER + "DummyDescription1" + Storage.DELIMITER + 
				"DummyStartTime1" + Storage.DELIMITER + "DummyEndTime1" + Storage.DELIMITER + "true" + Storage.DELIMITER);
		Task dummyTask2 = obj.stringToTask("DummyTaskID2" + Storage.DELIMITER + "DummyDescription2" + Storage.DELIMITER + 
				"DummyStartTime2" + Storage.DELIMITER + "DummyEndTime2" + Storage.DELIMITER + "false" + Storage.DELIMITER);
		Task dummyTask3 = obj.stringToTask("DummyTaskID3" + Storage.DELIMITER + "DummyDescription3" + Storage.DELIMITER + 
				"DummyStartTime3" + Storage.DELIMITER + "DummyEndTime3" + Storage.DELIMITER + "true" + Storage.DELIMITER);
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(dummyTask1);
		tasks.add(dummyTask2);
		tasks.add(dummyTask3);
		
		assertTrue(obj.inputTasks(tasks));
		
		ArrayList<Task> tasksToCompare = obj.readTasks();
		
		assertEquals(tasks, tasksToCompare);
		
	}
}
