package whatsupnext.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StorageTest {
	Storage obj;
	Task dummyTask1;
	Task dummyTask2;
	Task dummyTask3;
	ArrayList<Task> tasksToCompare;
	ArrayList<Task> taskArray1;
	ArrayList<Task> taskArray2;
	ArrayList<Task> taskArray3;
	
	public final static String DELIMITER = "%#";	
	
	@Before
	public void initialize() {
		Storage.tryInitialize("storageTest");
		obj = Storage.getInstance();
		
		setUpDummyTasks();
		setUpTaskArrays();
	}
	
	@After
	public void deleteFiles() {
		try {
			obj.clearFile();
			obj.deleteFileVersions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStringToTask() {
		assertEquals(dummyTask1.getTaskID(), "DummyTaskID1");
		assertEquals(dummyTask1.getDescription(), "DummyDescription1");
		assertEquals(dummyTask1.getStartTime(), "DummyStartTime1");
		assertEquals(dummyTask1.getEndTime(), "DummyEndTime1");
		assertEquals(dummyTask1.getDone(), true);		
	}
	
	@Test
	public void testInputAndReadTasks() throws IOException {
		assertTrue(obj.inputTasks(taskArray3));
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray3, tasksToCompare);
	}
	
	@Test
	public void testUndoRedo() throws IOException {
		undo();
		redo();	
		undoBoundary();
		redoAfterChange();
		undoBeyondExistingVersions();
	}
	
	private void setUpDummyTasks() {
		dummyTask1 = stringToTask("DummyTaskID1" + DELIMITER + "DummyDescription1" + DELIMITER + 
				"DummyStartTime1" + DELIMITER + "DummyEndTime1" + DELIMITER + "true" + DELIMITER);
		dummyTask2 = stringToTask("DummyTaskID2" + DELIMITER + "DummyDescription2" + DELIMITER + 
				"DummyStartTime2" + DELIMITER + "DummyEndTime2" + DELIMITER + "false" + DELIMITER);
		dummyTask3 = stringToTask("DummyTaskID3" + DELIMITER + "DummyDescription3" + DELIMITER + 
				"DummyStartTime3" + DELIMITER + "DummyEndTime3" + DELIMITER + "true" + DELIMITER);
	}
	
	private void setUpTaskArrays() {
		taskArray1 = new ArrayList<Task>();
		taskArray1.add(dummyTask1);
		
		taskArray2 = new ArrayList<Task>();
		taskArray2.add(dummyTask1);
		taskArray2.add(dummyTask2);
		
		taskArray3 = new ArrayList<Task>();
		taskArray3.add(dummyTask1);
		taskArray3.add(dummyTask2);
		taskArray3.add(dummyTask3);
	}
	
	private void undo() throws IOException {
		assertTrue(obj.inputTasks(taskArray1));
		tasksToCompare = obj.readTasks();		
		assertEquals(taskArray1, tasksToCompare);		
		
		assertTrue(obj.inputTasks(taskArray2));
		tasksToCompare = obj.readTasks();		
		assertEquals(taskArray2, tasksToCompare);
		
		assertTrue(obj.goToPreviousVersion());		
		tasksToCompare = obj.readTasks();		
		assertEquals(taskArray1, tasksToCompare);
	}
	
	private void redo() throws IOException {
		assertTrue(obj.goToNextVersion());
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray2, tasksToCompare);
	}
	
	private void undoBoundary() throws IOException {
		assertTrue(obj.inputTasks(taskArray3));
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray3, tasksToCompare);
		
		assertTrue(obj.goToPreviousVersion());
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray2, tasksToCompare);
	}
	
	private void redoAfterChange() throws IOException {
		assertTrue(obj.inputTasks(taskArray2));
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray2, tasksToCompare);
		
		assertFalse(obj.goToNextVersion());
	}
		
	private void undoBeyondExistingVersions() throws IOException {
		assertTrue(obj.goToPreviousVersion());
		assertTrue(obj.goToPreviousVersion());
		assertTrue(obj.goToPreviousVersion());
		assertFalse(obj.goToPreviousVersion());
		assertFalse(obj.goToPreviousVersion());
	}
	
	private Task stringToTask(String taskInString) {
		Scanner extractFromString = new Scanner(taskInString);
		extractFromString.useDelimiter(DELIMITER);
		
		String taskID = extractFromString.next();
		String description = extractFromString.next();
		String startTime = extractFromString.next();
		String endTime = extractFromString.next();
		String booleanString = extractFromString.next();
		
		assertTrue(booleanString.equals("true") || booleanString.equals("false"));		
		boolean isDone = Boolean.parseBoolean(booleanString);
		
		Task taskFromString = new Task();
		taskFromString.setTaskID(taskID);
		taskFromString.setDescription(description);
		taskFromString.setStartTime(startTime);
		taskFromString.setEndTime(endTime);
		taskFromString.setDone(isDone);
		
		extractFromString.close();
		
		return taskFromString;
	}
}
