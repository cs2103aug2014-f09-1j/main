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
	

}
