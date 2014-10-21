package whatsupnext.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import whatsupnext.parser.extractor.AddExtractor;
import whatsupnext.parser.extractor.DeleteExtractor;
import whatsupnext.parser.extractor.DoneExtractor;
import whatsupnext.parser.extractor.SearchExtractor;
import whatsupnext.parser.extractor.UpdateExtractor;
import whatsupnext.parser.extractor.ViewExtractor;
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
		AddExtractor ex = new AddExtractor();
		ex.extract(task, "dine from 0200 301014 to 21:00 301014");
		assertEquals("Test Add - description", "dine", task.getDescription());
		assertEquals("Test Add - startTime", "201410300200", task.getStartTime());
		assertEquals("Test Add - endTime", "201410302100", task.getEndTime());
		assertEquals("Test Add - addType",ADDTYPE.TIMEFRAME,task.getAddType());
	}
	
	@Test
	public void testAdd2() {
		Task task = new Task();
		AddExtractor ex = new AddExtractor();
		ex.extract(task, "dine By 13:00");
		assertEquals("Test Add - description", "dine", task.getDescription());
		assertEquals("Test Add - startTime", "", task.getStartTime());
		assertEquals("Test Add - endTime", getTodayDate() + "1300", task.getEndTime());
		assertEquals("Test Add - addType",ADDTYPE.DEADLINE,task.getAddType());
	}
	
	@Test
	public void testAddInvalidDescription() {
		String MESSAGE_INVALID_DESCRIPTION = "'add' must have a valid description";
		Task task = new Task();
		AddExtractor ex = new AddExtractor();
		try{
			ex.extract(task, "By 13:00");
		} catch (Exception e) {
			assertEquals("Test invalid description", MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
		try{
			ex.extract(task, "");
		} catch (Exception e) {
			assertEquals("Test invalid description", MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
		try{
			ex.extract(task, "from 1234 to 1235");
		} catch (Exception e) {
			assertEquals("Test invalid description", MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
		
	}

	@Test
	public void testAddInvalidEndTime() {
		String MESSAGE_INVALID_END_TIME = "'add' must have a valid end time";
		Task task = new Task();
		AddExtractor ex = new AddExtractor();
		try{
			ex.extract(task, "test By rubbish");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_END_TIME, e.getMessage());
		}
		try{
			ex.extract(task, "test from 1234 to rubbish");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testAddInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'add' must have a valid start time";
		Task task = new Task();
		AddExtractor ex = new AddExtractor();
		try{
			ex.extract(task, "test from rubbish to 1234");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_START_TIME, e.getMessage());
		}
		try{
			ex.extract(task, "test from rubbish to time");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testDelete1() {
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		ex.extract(task, "1");
		assertEquals("Test Delete - ID", "1", task.getTaskID());
		assertEquals("Test Delete - startTime", "", task.getStartTime());
		assertEquals("Test Delete - endTime", "", task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.ID, task.getDeleteType());
	}
	
	@Test
	public void testDelete2() {
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		ex.extract(task, "23:59 101014");
		assertEquals("Test Delete - description", "", task.getDescription());
		assertEquals("Test Delete - startTime", "", task.getStartTime());
		assertEquals("Test Delete - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.DATE, task.getDeleteType());
	}
	
	@Test
	public void testDelete3() {
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		ex.extract(task, "Deadline");
		assertEquals("Test Delete - description", "", task.getDescription());
		assertEquals("Test Delete - startTime", "", task.getStartTime());
		assertEquals("Test Delete - endTime", getTodayDateTime(), task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.DEADLINE, task.getDeleteType());
	}
	
	@Test
	public void testDelete4() {
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		ex.extract(task, "from 00:00 061014 to 23:59 101014");
		assertEquals("Test Delete - description", "", task.getDescription());
		assertEquals("Test Delete - startTime", "201410060000", task.getStartTime());
		assertEquals("Test Delete - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Delete - deleteType", DELETETYPE.TIMEFRAME, task.getDeleteType());
	}
	
	@Test
	public void testDeleteInvalidArgument() {
		String MESSAGE_INVALID_ARGUMENT = "'delete' must have an argument";
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		try{
			ex.extract(task, "");
		} catch (Exception e) {
			assertEquals("Test invalid argument", MESSAGE_INVALID_ARGUMENT, e.getMessage());
		}
	}
	
	@Test
	public void testDeleteInvalidTaskIDOrDate() {
		String MESSAGE_INVALID_TASKID_OR_DATE = "'delete' must have a valid Task ID or Date";
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		try{
			ex.extract(task, "ID");
		} catch (Exception e) {
			assertEquals("Test invalid task ID", MESSAGE_INVALID_TASKID_OR_DATE, e.getMessage());
		}
		try{
			ex.extract(task, "-1");
		} catch (Exception e) {
			assertEquals("Test invalid task ID", MESSAGE_INVALID_TASKID_OR_DATE, e.getMessage());
		}
		try{
			ex.extract(task, "2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid time", MESSAGE_INVALID_TASKID_OR_DATE, e.getMessage());
		}
	}
	
	@Test
	public void testDeleteInvalidEndTime() {
		String MESSAGE_INVALID_END_TIME = "'delete' must have an valid end time";
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		try{
			ex.extract(task, "from 1234 to 2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testDeleteInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'delete' must have an valid start time";
		Task task = new Task();
		DeleteExtractor ex = new DeleteExtractor();
		try{
			ex.extract(task, "from 2014/12/12 to 2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid start time", MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testUpdate1() {
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		ex.extract(task, "10 new description");
		assertEquals("Test Update - description", "new description", task.getDescription());
		assertEquals("Test Update - startTime", "", task.getStartTime());
		assertEquals("Test Update - endTime", "", task.getEndTime());
		assertEquals("Test Update - updateType", UPDATETYPE.DESCRIPTION, task.getUpdateType());
		assertEquals("Test Update - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdate2() {
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		ex.extract(task, "10 by 1400 061014");
		assertEquals("Test Update - description", "", task.getDescription());
		assertEquals("Test Update - startTime", "", task.getStartTime());
		assertEquals("Test Update - endTime", "201410061400", task.getEndTime());
		assertEquals("Test Update - updateType", UPDATETYPE.DEADLINE, task.getUpdateType());
		assertEquals("Test Update - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdate3() {
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		ex.extract(task, "10 from 00:00 06102014 to 101014");
		assertEquals("Test Update - description", "", task.getDescription());
		assertEquals("Test Update - startTime", "201410060000", task.getStartTime());
		assertEquals("Test Update - endTime", "201410102359", task.getEndTime());
		assertEquals("Test Update - updateType", UPDATETYPE.TIMEFRAME, task.getUpdateType());
		assertEquals("Test Update - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testUpdateInvalidTaskID() {
		String MESSAGE_INVALID_TASKID = "'update' must have a valid Task ID";
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		try{
			ex.extract(task, "ID by 1400 061014");
		} catch (Exception e) {
			assertEquals("Test invalid task ID", MESSAGE_INVALID_TASKID, e.getMessage());
		}
		try{
			ex.extract(task, "-1 by 1400 061014");
		} catch (Exception e) {
			assertEquals("Test invalid task ID", MESSAGE_INVALID_TASKID, e.getMessage());
		}
	}

	@Test
	public void testUpdateInvalidDescription() {
		String MESSAGE_INVALID_DESCRIPTION = "'update' must have an valid description";
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		try{
			ex.extract(task, "14 update Desc");
		} catch (Exception e) {
			assertEquals("Test invalid time", MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
	}
	
	@Test
	public void testUpdateInvalidEndTime() {
		String MESSAGE_INVALID_END_TIME = "'update' must have a valid end time";
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		try{
			ex.extract(task, "14 by 2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_END_TIME, e.getMessage());
		}
		try{
			ex.extract(task, "14 from 1234 to 2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testUpdateInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'update' must have a valid start time";
		Task task = new Task();
		UpdateExtractor ex = new UpdateExtractor();
		try{
			ex.extract(task, "14 from 2014/12/12 to 2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid start time", MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
		
	@Test
	public void testViewAll() {
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		ex.extract(task, "all");
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "", task.getStartTime());
		assertEquals("Test View - endTime", "", task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.ALL, task.getViewType());
	}
	
	@Test
	public void testViewNext() {
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		ex.extract(task, "next");
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "", task.getStartTime());
		assertEquals("Test View - endTime", getTodayDateTime(), task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.NEXT, task.getViewType());
	}
	
	@Test
	public void testViewDate() {
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		ex.extract(task, "101014");
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "", task.getStartTime());
		assertEquals("Test View - endTime", "201410102359", task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.DATE, task.getViewType());
	}
	
	@Test
	public void testViewTimeFrame() {
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		ex.extract(task, "from 05102014 to 20:00 101014");
		assertEquals("Test View - description", "", task.getDescription());
		assertEquals("Test View - startTime", "201410050000", task.getStartTime());
		assertEquals("Test View - endTime", "201410102000", task.getEndTime());
		assertEquals("Test View - viewType", VIEWTYPE.TIMEFRAME, task.getViewType());
	}
	
	@Test
	public void testDone() {
		Task task = new Task();
		DoneExtractor ex = new DoneExtractor();
		ex.extract(task, "10");
		assertEquals("Test Done - description", "", task.getDescription());
		assertEquals("Test Done - startTime", "", task.getStartTime());
		assertEquals("Test Done - endTime", "", task.getEndTime());
		assertEquals("Test Done - taskID", "10", task.getTaskID());
	}
	
	@Test
	public void testSearch() {
		Task task = new Task();
		SearchExtractor ex = new SearchExtractor();
		ex.extract(task, "FYP presentation");
		assertEquals("Test Done - description", "FYP presentation", task.getDescription());
		assertEquals("Test Done - startTime", "", task.getStartTime());
		assertEquals("Test Done - endTime", "", task.getEndTime());
		assertEquals("Test Done - taskID", "", task.getTaskID());
	}
	
	@Test
	public void testViewInvalidArgument() {
		String MESSAGE_INVALID_ARGUMENT = "'view' must have an arguement";
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		try{
			ex.extract(task, "");
		} catch (Exception e) {
			assertEquals("Test invalid argument", MESSAGE_INVALID_ARGUMENT, e.getMessage());
		}
	}

	@Test
	public void testViewInvalidEndTime() {
		String MESSAGE_INVALID_END_TIME = "'view' must have an valid end time";
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		try{
			ex.extract(task, "from 1234 to 2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid end time", MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testViewInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'view' must have an valid start time";
		Task task = new Task();
		ViewExtractor ex = new ViewExtractor();
		try{
			ex.extract(task, "from 2014/12/12 to 1234");
		} catch (Exception e) {
			assertEquals("Test invalid start time", MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	public void testViewInvalidDate() {
		String MESSAGE_INVALID_DATE = "'view' must have an valid date";
		Task task = new Task();
		AddExtractor ex = new AddExtractor();
		try{
			ex.extract(task, "2014/12/12");
		} catch (Exception e) {
			assertEquals("Test invalid time", MESSAGE_INVALID_DATE, e.getMessage());
		}
	}
}
