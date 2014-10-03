package whatsupnext.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import whatsupnext.parser.Extractor;
import whatsupnext.structure.DELETETYPE;
import whatsupnext.structure.Task;
import whatsupnext.structure.UPDATETYPE;
import whatsupnext.structure.VIEWTYPE;

public class ExtractorTest {

	private String getToday() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = "";
		String twoDigitDayOfMonth = ""; 
        if (month < 10) {
        	twoDigitMonth = "0" + month;
 		} else {
 			twoDigitMonth = "" + month;
 		}
 		if (dayOfMonth < 10) {
 			twoDigitDayOfMonth = "0" + dayOfMonth;
 		} else {
 			twoDigitDayOfMonth = "" + dayOfMonth;
 		}
        
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	@Test
	public void testAdd() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "add dine from 0200 301014 to 21:00 301014");
		ex.extractForAddTask();
		assertEquals("Test Add 1 - description", "dine", task.getDescription());
		assertEquals("Test Add 1 - startTime", "201410300200", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "201410302100", task.getEndTime());
	}
	
	@Test
	public void testAdd2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "add dine By 1 pm");
		ex.extractForAddTask();
		assertEquals("Test Add 2 - description", "dine", task.getDescription());
		assertEquals("Test Add 2 - startTime", "", task.getStartTime());
		assertEquals("Test Add 2 - endTime", getToday() + "1300", task.getEndTime());
	}
	
	@Test
	public void testDelete1() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete 190");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 1  - ID", "190", task.getTaskID());
		assertEquals("Test Delete 1  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 1  - endTime", null, task.getEndTime());
		assertEquals("Test Delete 1  - deleteType", DELETETYPE.ID, task.getDeleteType());
	}
	
	@Test
	public void testDelete2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete 101014");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 2  - description", null, task.getDescription());
		assertEquals("Test Delete 2  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 2  - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Delete 2  - deleteType", DELETETYPE.DATE, task.getDeleteType());
	}
	
	@Test
	public void testDelete3() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete Deadline");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 3  - description", null, task.getDescription());
		assertEquals("Test Delete 3  - startTime", null, task.getStartTime());
		assertEquals("Test Delete 3  - endTime", "NOW", task.getEndTime());
		assertEquals("Test Delete 3  - deleteType", DELETETYPE.DEADLINE, task.getDeleteType());
	}
	
	@Test
	public void testDelete4() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete from 12 am 06102014 to 101014");
		ex.extractForDeleteTask();
		assertEquals("Test Delete 4  - description", null, task.getDescription());
		assertEquals("Test Delete 4  - startTime", "201410060000", task.getStartTime());
		assertEquals("Test Delete 4  - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Delete 4  - deleteType", DELETETYPE.TIMEFRAME, task.getDeleteType());
	}
	
	@Test
	public void testUpdate1() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 new description");
		ex.extractForUpdateTask();
		assertEquals("Test Update 1  - description", "new description", task.getDescription());
		assertEquals("Test Update 1  - startTime", null, task.getStartTime());
		assertEquals("Test Update 1  - endTime", null, task.getEndTime());
		assertEquals("Test Update 1  - updateType", UPDATETYPE.DESCRIPTION, task.getUpdateType());
		assertEquals("Test Update 1  - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdate2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 by 1400 061014");
		ex.extractForUpdateTask();
		assertEquals("Test Update 2  - description", null, task.getDescription());
		assertEquals("Test Update 2  - startTime", "", task.getStartTime());
		assertEquals("Test Update 2  - endTime", "201410061400", task.getEndTime());
		assertEquals("Test Update 2  - updateType", UPDATETYPE.DEADLINE, task.getUpdateType());
		assertEquals("Test Update 2  - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdate3() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 from 12 am 06102014 to 101014");
		ex.extractForUpdateTask();
		assertEquals("Test Update 3  - description", null, task.getDescription());
		assertEquals("Test Update 3  - startTime", "201410060000", task.getStartTime());
		assertEquals("Test Update 3  - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Update 3  - updateType", UPDATETYPE.TIMEFRAME, task.getUpdateType());
		assertEquals("Test Update 3  - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testViewAll() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view all");
		ex.extractForViewTask();
		assertEquals("Test View 1  - description", null, task.getDescription());
		assertEquals("Test View 1  - startTime", null, task.getStartTime());
		assertEquals("Test View 1  - endTime", null, task.getEndTime());
		assertEquals("Test View 1  - viewType", VIEWTYPE.ALL, task.getViewType());
	}
	
	@Test
	public void testViewNext() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view next");
		ex.extractForViewTask();
		assertEquals("Test View 2  - description", null, task.getDescription());
		assertEquals("Test View 2  - startTime", null, task.getStartTime());
		assertEquals("Test View 2  - endTime", null, task.getEndTime());
		assertEquals("Test View 2  - viewType", VIEWTYPE.NEXT, task.getViewType());
	}
	
	@Test
	public void testViewDate() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view 101014");
		ex.extractForViewTask();
		assertEquals("Test View 3  - description", null, task.getDescription());
		assertEquals("Test View 3  - startTime", null, task.getStartTime());
		assertEquals("Test View 3  - endTime", "201410102359", task.getEndTime());
		assertEquals("Test View 3  - viewType", VIEWTYPE.DATE, task.getViewType());
	}
	
	@Test
	public void testViewTimeFrame() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view from 05102014 to 8 pm 101014");
		ex.extractForViewTask();
		assertEquals("Test View 4  - description", null, task.getDescription());
		assertEquals("Test View 4  - startTime", "201410052359", task.getStartTime());
		assertEquals("Test View 4  - endTime", "201410102000", task.getEndTime());
		assertEquals("Test View 4  - viewType", VIEWTYPE.TIMEFRAME, task.getViewType());
	}
}
