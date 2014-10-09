package whatsupnext.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import whatsupnext.parser.Extractor;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.structure.Types.UPDATETYPE;
import whatsupnext.structure.Types.VIEWTYPE;

public class ExtractorTest {

	public String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	public String getTodayDateTime() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));
		String twoDigitHour = convertToTwoDigits(cal.get(Calendar.HOUR_OF_DAY));
		String twoDigitMinute = convertToTwoDigits(cal.get(Calendar.MINUTE));
		return year + twoDigitMonth + twoDigitDayOfMonth + twoDigitHour + twoDigitMinute;
	}
	
	private String convertToTwoDigits(int possibleSingleDigit) {
		if (possibleSingleDigit < 10) {
        	return "0" + possibleSingleDigit;
		} else {
			return "" + possibleSingleDigit;
		}
	}
	
	@Test
	public void testAdd() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "add dine from 0200 301014 to 21:00 301014");
		ex.extractForAddTask();
		assertEquals("Test Add - description", "dine", task.getDescription());
		assertEquals("Test Add - startTime", "201410300200", task.getStartTime());
		assertEquals("Test Add - endTime", "201410302100", task.getEndTime());
		assertEquals("Test Add - addType",ADDTYPE.TIMEFRAME,task.getAddType());
	}
	
	@Test
	public void testAdd2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "add dine By 1 pm");
		ex.extractForAddTask();
		assertEquals("Test Add - description", "dine", task.getDescription());
		assertEquals("Test Add - startTime", "", task.getStartTime());
		assertEquals("Test Add - endTime", getTodayDate() + "1300", task.getEndTime());
		assertEquals("Test Add - addType",ADDTYPE.DEADLINE,task.getAddType());
	}
	
	@Test
	public void testDelete1() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete 190");
		ex.extractForDeleteTask();
		assertEquals("Test Delete - ID", "190", task.getTaskID());
		assertEquals("Test Delete - startTime", "", task.getStartTime());
		assertEquals("Test Delete - endTime", "", task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.ID, task.getDeleteType());
	}
	
	@Test
	public void testDelete2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete 101014");
		ex.extractForDeleteTask();
		assertEquals("Test Delete - description", "", task.getDescription());
		assertEquals("Test Delete - startTime", "", task.getStartTime());
		assertEquals("Test Delete - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.DATE, task.getDeleteType());
	}
	
	@Test
	public void testDelete3() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete Deadline");
		ex.extractForDeleteTask();
		assertEquals("Test Delete - description", "", task.getDescription());
		assertEquals("Test Delete - startTime", "", task.getStartTime());
		assertEquals("Test Delete - endTime", getTodayDateTime(), task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.DEADLINE, task.getDeleteType());
	}
	
	@Test
	public void testDelete4() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "delete from 12 am 06102014 to 101014");
		ex.extractForDeleteTask();
		assertEquals("Test Delete - description", "", task.getDescription());
		assertEquals("Test Delete - startTime", "201410060000", task.getStartTime());
		assertEquals("Test Delete - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.TIMEFRAME, task.getDeleteType());
	}
	
	@Test
	public void testUpdate1() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 new description");
		ex.extractForUpdateTask();
		assertEquals("Test Update - description", "new description", task.getDescription());
		assertEquals("Test Update - startTime", "", task.getStartTime());
		assertEquals("Test Update - endTime", "", task.getEndTime());
		assertEquals("Test Update - updateType", UPDATETYPE.DESCRIPTION, task.getUpdateType());
		assertEquals("Test Update - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdate2() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 by 1400 061014");
		ex.extractForUpdateTask();
		assertEquals("Test Update - description", "", task.getDescription());
		assertEquals("Test Update - startTime", "", task.getStartTime());
		assertEquals("Test Update - endTime", "201410061400", task.getEndTime());
		assertEquals("Test Update - updateType", UPDATETYPE.DEADLINE, task.getUpdateType());
		assertEquals("Test Update - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdate3() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "update 10 from 12 am 06102014 to 101014");
		ex.extractForUpdateTask();
		assertEquals("Test Update - description", "", task.getDescription());
		assertEquals("Test Update - startTime", "201410060000", task.getStartTime());
		assertEquals("Test Update - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Update - updateType", UPDATETYPE.TIMEFRAME, task.getUpdateType());
		assertEquals("Test Update - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testViewAll() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view all");
		ex.extractForViewTask();
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "", task.getStartTime());
		assertEquals("Test View - endTime", "", task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.ALL, task.getViewType());
	}
	
	@Test
	public void testViewNext() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view next");
		ex.extractForViewTask();
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "", task.getStartTime());
		assertEquals("Test View - endTime", getTodayDateTime(), task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.NEXT, task.getViewType());
	}
	
	@Test
	public void testViewDate() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view 101014");
		ex.extractForViewTask();
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "", task.getStartTime());
		assertEquals("Test View - endTime", "201410102359", task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.DATE, task.getViewType());
	}
	
	@Test
	public void testViewTimeFrame() {
		Task task = new Task();
		Extractor ex = new Extractor(task, "view from 05102014 to 8 pm 101014");
		ex.extractForViewTask();
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "201410052359", task.getStartTime());
		assertEquals("Test View - endTime", "201410102000", task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.TIMEFRAME, task.getViewType());
	}
}
