package Junit;

import static org.junit.Assert.*;

import org.junit.Test;

import Structure.Task;
import Storage.Storage;

public class StorageTest {

	@Test
	public void testStringToTask() {	
		Storage obj = new Storage();
		Task dummyTask1 = obj.StringToTask("DummyDescription DummyStartTime DummyEndTime");
		assertEquals(dummyTask1.getDescription(), "DummyDescription");
		assertEquals(dummyTask1.getStartTime(), "DummyStartTime");
		assertEquals(dummyTask1.getEndTime(), "DummyEndTime");
		
	}

}
