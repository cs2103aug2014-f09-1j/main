package Junit;

import static org.junit.Assert.*;

import org.junit.Test;

import Parser.Extractor;
import Structure.Task;

public class ExtractorTest {

	@Test
	public void testAdd() {
		Task task = new Task();
		Extractor ex = new Extractor(task,"add dine from 2am tmr to 9");
		ex.extractorAdd();
		assertEquals("Test Add 1 - description", "dine", task.getDescription());
		assertEquals("Test Add 1 - startTime", "2am tmr", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "9", task.getEndTime());
	}
	
	@Test
	public void testAdd2() {
		Task task = new Task();
		Extractor ex = new Extractor(task,"add dine By 1pm");
		ex.extractorAdd();
		assertEquals("Test Add 2 - description", "dine", task.getDescription());
		assertEquals("Test Add 2 - startTime", null, task.getStartTime());
		assertEquals("Test Add 2 - endTime", "1pm", task.getEndTime());
	}
	
	@Test
	public void testDelete1() {
		Task task = new Task();
		Extractor ex = new Extractor(task,"delete 190");
		ex.extractorDelete();
		assertEquals("Test Delete 1  - description/ID", "190", task.getDescription());
		assertEquals("Test Delete 1  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 1  - endTime", null, task.getEndTime());
		assertEquals("Test Delete 1  - deleteType", "ID", task.getDeleteType());
	}
	
	@Test
	public void testDelete2() {
		Task task = new Task();
		Extractor ex = new Extractor(task,"delete 101014");
		ex.extractorDelete();
		assertEquals("Test Delete 2  - description", null, task.getDescription());
		assertEquals("Test Delete 2  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 2  - endTime", "101014", task.getEndTime());
		assertEquals("Test Delete 2  - deleteType", "DATE", task.getDeleteType());
	}
	
	@Test
	public void testDelete3() {
		Task task = new Task();
		Extractor ex = new Extractor(task,"delete Deadline");
		ex.extractorDelete();
		assertEquals("Test Delete 3  - description", null, task.getDescription());
		assertEquals("Test Delete 3  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 3  - endTime", "NOW", task.getEndTime());
		assertEquals("Test Delete 3  - deleteType", "DEADLINE", task.getDeleteType());
	}
	
	@Test
	public void testDelete4() {
		Task task = new Task();
		Extractor ex = new Extractor(task,"delete from mondayf fd to friday");
		ex.extractorDelete();
		assertEquals("Test Delete 4  - description", null, task.getDescription());
		assertEquals("Test Delete 4  - startTime", "mondayf fd", task.getStartTime());
		assertEquals("Test Delete 4  - endTime", "friday", task.getEndTime());
		assertEquals("Test Delete 4  - deleteType", "TIMEFRAME", task.getDeleteType());
	}
	
	

}
