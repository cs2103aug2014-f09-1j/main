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
	Task dummyTask1;
	Task dummyTask2;
	Task dummyTask3;
	ArrayList<Task> tasksToCompare;
	ArrayList<Task> taskArray1;
	ArrayList<Task> taskArray2;
	ArrayList<Task> taskArray3;
	
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
		dummyTask1 = obj.stringToTask("DummyTaskID1" + Storage.DELIMITER + "DummyDescription1" + Storage.DELIMITER + 
				"DummyStartTime1" + Storage.DELIMITER + "DummyEndTime1" + Storage.DELIMITER + "true" + Storage.DELIMITER);
		dummyTask2 = obj.stringToTask("DummyTaskID2" + Storage.DELIMITER + "DummyDescription2" + Storage.DELIMITER + 
				"DummyStartTime2" + Storage.DELIMITER + "DummyEndTime2" + Storage.DELIMITER + "false" + Storage.DELIMITER);
		dummyTask3 = obj.stringToTask("DummyTaskID3" + Storage.DELIMITER + "DummyDescription3" + Storage.DELIMITER + 
				"DummyStartTime3" + Storage.DELIMITER + "DummyEndTime3" + Storage.DELIMITER + "true" + Storage.DELIMITER);
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
		assertFalse(obj.goToPreviousVersion());
	}
}
