package whatsupnext.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.logic.LogicUtilities;
import whatsupnext.storage.Storage;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.structure.Types.UPDATETYPE;
import whatsupnext.structure.Types.VIEWTYPE;

public class LogicTest {
	
	private LogicStub logic;
	private Task task;
	private static Task viewAllTask;	
	
	private class LogicStub extends Logic {
		public LogicStub(String fileName) {
			super(fileName);
		}
		
		public String formatTime(String time){
			return LogicUtilities.getFormattedTime(time);
		}
	}
	
	private String getLastYearTodayDate() {
		Calendar cal = Calendar.getInstance();
		String lastYear = Integer.toString(cal.get(Calendar.YEAR) - 1);
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));
		
		return lastYear + twoDigitMonth + twoDigitDayOfMonth;
	}
	
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
	
	@BeforeClass
	public static void createViewAllTask() {
		viewAllTask = new Task();
		viewAllTask.setOpcode(OPCODE.VIEW);
		viewAllTask.setViewType(VIEWTYPE.ALL);
	}
	
	@Before
	public void init() {
		logic = new LogicStub("logicTest");
	}
	
	@After
	public void deleteAllTasks() {
		Task delete = new Task();
		delete.setOpcode(OPCODE.DELETE);
		delete.setDeleteType(DELETETYPE.ALL);
		logic.executeTask(delete);
		
		Storage storage = Storage.getInstance();
		try {
			storage.clearFile();
			storage.deleteFileVersions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void deleteAllFileVersions() {
		Storage storage = Storage.getInstance();
		try {
			storage.clearFile();
			storage.deleteFileVersions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddFloatingTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Add Floating - Successful ", feedback, "A task is successfully added.");
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\nNot done.");
	}
	
	@Test
	public void testAddDeadlineTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Add Deadline - Successful ", "A task is successfully added.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Oct 10 12:00\nNot done.");
	}
	
	@Test
	public void testAddTimeFrameTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101200");
		task.setEndTime("201410111200");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Add Time Frame - Successful ", "A task is successfully added.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tStart Time: 2014 Oct 10 12:00\n\tEnd Time: 2014 Oct 11 12:00\nNot done.");
	}
	
	@Test
	public void testDeleteIdTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setTaskID("1");
		task.setDescription("testing");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setTaskID("2");
		task.setDescription("testing");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.ID);
		task.setTaskID("2");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Id - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\nNot done.");
	}
	
	@Test
	public void testDeleteDateTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.DATE);
		task.setEndTime("201410102359");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Date - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "2: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:00\nNot done.");
	}
	
	@Test
	public void testDeleteDeadlineTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111230");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.DEADLINE);
		task.setEndTime("201410111200");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Deadline - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "3: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:30\nNot done.");
	}
	
	@Test
	public void testDeleteTimeFrameTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111230");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.TIMEFRAME);
		task.setStartTime("201410100000");
		task.setEndTime("201410111220");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Time Frame - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "3: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:30\nNot done.");
	}
	
	@Test
	public void testUpdateDescriptionTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.UPDATE);
		task.setUpdateType(UPDATETYPE.DESCRIPTION);
		task.setTaskID("1");
		task.setDescription("new description");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Update Description - Successful ", "A task is successfully updated.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: new description\nNot done.");
	}
	
	@Test
	public void testUpdateDeadlineTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.UPDATE);
		task.setUpdateType(UPDATETYPE.DEADLINE);
		task.setTaskID("1");
		task.setEndTime("201411111111");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Update Deadline - Successful ", "A task is successfully updated.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Nov 11 11:11\nNot done.");
	}
	
	@Test
	public void testUpdateTimeFrameTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101000");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.UPDATE);
		task.setUpdateType(UPDATETYPE.TIMEFRAME);
		task.setTaskID("1");
		task.setStartTime("201410101010");
		task.setEndTime("201411111111");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Update Deadline - Successful ", "A task is successfully updated.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tStart Time: 2014 Oct 10 10:10\n\tEnd Time: 2014 Nov 11 11:11\nNot done.");
	}
	
	@Test
	public void testViewAllTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		logic.executeTask(task);
		
		String feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\nNot done.");
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing2");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback,
				"1: testing\nNot done.\n" +
				"2: testing2\n\tEnd Time: 2014 Oct 10 12:00\nNot done."
		);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing3");
		task.setStartTime("201410102000");
		task.setEndTime("201411111200");
		logic.executeTask(task);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback,
				"1: testing\nNot done.\n" +
				"2: testing2\n\tEnd Time: 2014 Oct 10 12:00\nNot done.\n" + 
				"3: testing3\n\tStart Time: 2014 Oct 10 20:00\n\tEnd Time: 2014 Nov 11 12:00\nNot done."
		);
	}
	
	@Test
	public void testViewNextTask() {		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime(getLastYearTodayDate() + "0000");
		task.setEndTime(getLastYearTodayDate() + "2359");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime(getLastYearTodayDate() + "0000");
		task.setEndTime(getTodayDate() + "2359");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime(getTodayDate() + "0000");
		task.setEndTime(getTodayDate() + "2359");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.NEXT);
		task.setEndTime(getTodayDateTime());
		
		String feedback = logic.executeTask(task);
		assertEquals(feedback,
				"2: testing\n\t" +
				"Start Time: " + logic.formatTime(getLastYearTodayDate() + "0000") + "\n\t" +
				"End Time: " + logic.formatTime(getTodayDate() + "2359") + "\nNot done.\n" +
				"3: testing\n\t" +
				"Start Time: " + logic.formatTime(getTodayDate() + "0000") + "\n\t" +
				"End Time: " + logic.formatTime(getTodayDate() + "2359")+ "\nNot done."
		);
	}
	
	@Test
	public void testViewDateTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.DATE);
		task.setEndTime("201410102359");
		
		String feedback = logic.executeTask(task);
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Oct 10 12:00\nNot done.");
	}
	
	@Test
	public void testViewTimeFrameTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101100");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.TIMEFRAME);
		task.setStartTime("201410110900");
		task.setEndTime("201410112359");
		
		String feedback = logic.executeTask(task);
		assertEquals(feedback,
				"2: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:00\nNot done."
		);
	}

	@Test
	public void testSearch() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("task");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("task a b");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("a b");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.SEARCH);
		task.setDescription("a");
		
		String feedback = logic.executeTask(task);
		assertEquals(feedback,
				"1: task\nNot done." + "\n" +
		        "2: task a b\nNot done." + "\n" +
		        "3: a b\nNot done."
		);
		
		task = new Task();
		task.setOpcode(OPCODE.SEARCH);
		task.setDescription("task b");
		
		feedback = logic.executeTask(task);
		assertEquals(feedback,
				"2: task a b\nNot done."
		);
	}
}
