//@author A0118897J
package whatsupnext.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import whatsupnext.storage.Storage;
import whatsupnext.storage.JSONHelper;
import whatsupnext.structure.Task;

import java.io.IOException;
import java.util.ArrayList;

public class StorageTest {
	Storage obj;
	JSONHelper jsonHelperObject;
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
		jsonHelperObject = new JSONHelper();
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
	
	/**
	 * Tests whether entering tasks into the storage file and reading from the storage file
	 * is working or not
	 * @throws IOException
	 */
	@Test
	public void testInputAndReadTasks() throws IOException {
		assertTrue(obj.inputTasks(taskArray3));
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray3, tasksToCompare);
	}
	
	/**
	 * Runs a series of tests in a certain order
	 * @throws IOException
	 */
	@Test
	public void testUndoRedo() throws IOException {
		undo();
		redo();	
		undoBoundary();
		redoAfterChange();
		undoBeyondExistingVersions();
	}
	
	/**
	 * Creates dummy tasks that will later be written to the file for testing
	 */
	private void setUpDummyTasks() {		
		dummyTask1 = jsonHelperObject.JSONStringToTask("[\"DummyTaskID1\",\"DummyDescription1\","
				+ "\"DummyStartTime1\",\"DummyEndTime1\",true]");
		
		dummyTask2 = jsonHelperObject.JSONStringToTask("[\"DummyTaskID2\",\"DummyDescription2\","
				+ "\"DummyStartTime2\",\"DummyEndTime2\",false]");
		
		dummyTask3 = jsonHelperObject.JSONStringToTask("[\"DummyTaskID3\",\"DummyDescription3\","
				+ "\"DummyStartTime3\",\"DummyEndTime3\",true]");
	}
	
	/**
	 * Creates ArrayLists of tasks for common use in functions
	 */
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
	
	/**
	 * Attempts a single undo after two entries to the file
	 * @throws IOException
	 */
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
	
	/**
	 * Attempts a single redo as a continuation of the method above
	 * @throws IOException
	 */
	private void redo() throws IOException {
		assertTrue(obj.goToNextVersion());
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray2, tasksToCompare);
	}
	
	/**
	 * Attempts an undo right after a redo
	 * @throws IOException
	 */
	private void undoBoundary() throws IOException {
		assertTrue(obj.inputTasks(taskArray3));
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray3, tasksToCompare);
		
		assertTrue(obj.goToPreviousVersion());
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray2, tasksToCompare);
	}
	
	/** 
	 * Attempts to redo right after a change has been made following an undo
	 * @throws IOException
	 */
	private void redoAfterChange() throws IOException {
		assertTrue(obj.inputTasks(taskArray2));
		tasksToCompare = obj.readTasks();
		assertEquals(taskArray2, tasksToCompare);
		
		assertFalse(obj.goToNextVersion());
	}
	
	/**
	 * Attempts to access a version of tasks list beyond the ones which exist
	 * @throws IOException
	 */
	private void undoBeyondExistingVersions() throws IOException {
		assertTrue(obj.goToPreviousVersion());
		assertTrue(obj.goToPreviousVersion());
		assertTrue(obj.goToPreviousVersion());
		assertFalse(obj.goToPreviousVersion());
		assertFalse(obj.goToPreviousVersion());
	}
}
