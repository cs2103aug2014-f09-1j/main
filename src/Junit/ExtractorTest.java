package Junit;

import static org.junit.Assert.*;

import org.junit.Test;

import Parser.Extractor;
import Structure.Task;

public class ExtractorTest {

	@Test
	public void testAdd() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "add dine from 2am tmr to 9");
		ex.extractForAddTask();
		assertEquals("Test Add 1 - description", "dine", task.getDescription());
		assertEquals("Test Add 1 - startTime", "2am tmr", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "9", task.getEndTime());
	}
	
	@Test
	public void testAdd2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "add dine By 1pm");
		ex.extractForAddTask();
		assertEquals("Test Add 2 - description", "dine", task.getDescription());
		assertEquals("Test Add 2 - startTime", null, task.getStartTime());
		assertEquals("Test Add 2 - endTime", "1pm", task.getEndTime());
	}
	
	@Test
	public void testDelete1() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete 190");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 1  - description/ID", "190", task.getDescription());
		assertEquals("Test Delete 1  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 1  - endTime", null, task.getEndTime());
		assertEquals("Test Delete 1  - deleteType", "ID", task.getDeleteType());
	}
	
	@Test
	public void testDelete2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete 101014");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 2  - description", null, task.getDescription());
		assertEquals("Test Delete 2  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 2  - endTime", "101014", task.getEndTime());
		assertEquals("Test Delete 2  - deleteType", "DATE", task.getDeleteType());
	}
	
	@Test
	public void testDelete3() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete Deadline");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 3  - description", null, task.getDescription());
		assertEquals("Test Delete 3  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 3  - endTime", "NOW", task.getEndTime());
		assertEquals("Test Delete 3  - deleteType", "DEADLINE", task.getDeleteType());
	}
	
	@Test
	public void testDelete4() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete from mondayf fd to friday");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 4  - description", null, task.getDescription());
		assertEquals("Test Delete 4  - startTime", "mondayf fd", task.getStartTime());
		assertEquals("Test Delete 4  - endTime", "friday", task.getEndTime());
		assertEquals("Test Delete 4  - deleteType", "TIMEFRAME", task.getDeleteType());
	}
	
	@Test
	public void testUpdate1() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 new description");
		ex.extractForUpdateTask();
		assertEquals("Test Update 1  - description", "new description", task.getDescription());
		assertEquals("Test Update 1  - startTime", null, task.getStartTime());
		assertEquals("Test Update 1  - endTime", null, task.getEndTime());
		assertEquals("Test Update 1  - updateType", "DESCRIPTION", task.getUpdateType());
		assertEquals("Test Update 1  - taskID", 10, ex.getTaskID());
	}
	
	@Test
	public void testUpdate2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 by friday");
		ex.extractForUpdateTask();
		assertEquals("Test Update 2  - description", null, task.getDescription());
		assertEquals("Test Update 2  - startTime", null, task.getStartTime());
		assertEquals("Test Update 2  - endTime", "friday", task.getEndTime());
		assertEquals("Test Update 2  - updateType", "DATE", task.getUpdateType());
		assertEquals("Test Update 2  - taskID", 10, ex.getTaskID());
	}
	
	@Test
	public void testUpdate3() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 from mondayf fd to friday");
		ex.extractForUpdateTask();
		assertEquals("Test Update 3  - description", null, task.getDescription());
		assertEquals("Test Update 3  - startTime", "mondayf fd", task.getStartTime());
		assertEquals("Test Update 3  - endTime", "friday", task.getEndTime());
		assertEquals("Test Update 3  - updateType", "TIMEFRAME", task.getUpdateType());
		assertEquals("Test Update 3  - taskID", 10, ex.getTaskID());
	}
	
	@Test
	public void testViewAll() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view all");
		ex.extractForViewTask();
		assertEquals("Test View 1  - description", null, task.getDescription());
		assertEquals("Test View 1  - startTime", null, task.getStartTime());
		assertEquals("Test View 1  - endTime", null, task.getEndTime());
		assertEquals("Test View 1  - viewType", "ALL", task.getViewType());
	}
	
	@Test
	public void testViewNext() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view next");
		ex.extractForViewTask();
		assertEquals("Test View 2  - description", null, task.getDescription());
		assertEquals("Test View 2  - startTime", null, task.getStartTime());
		assertEquals("Test View 2  - endTime", null, task.getEndTime());
		assertEquals("Test View 2  - viewType", "NEXT", task.getViewType());
	}
	
	@Test
	public void testViewDate() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view Monday");
		ex.extractForViewTask();
		assertEquals("Test View 3  - description", null, task.getDescription());
		assertEquals("Test View 3  - startTime", null, task.getStartTime());
		assertEquals("Test View 3  - endTime", "Monday", task.getEndTime());
		assertEquals("Test View 3  - viewType", "DATE", task.getViewType());
	}
	
	@Test
	public void testViewTimeFrame() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view from monday to friday");
		ex.extractForViewTask();
		assertEquals("Test View 4  - description", null, task.getDescription());
		assertEquals("Test View 4  - startTime", "monday", task.getStartTime());
		assertEquals("Test View 4  - endTime", "friday", task.getEndTime());
		assertEquals("Test View 4  - viewType", "TIMEFRAME", task.getViewType());
	}
}
