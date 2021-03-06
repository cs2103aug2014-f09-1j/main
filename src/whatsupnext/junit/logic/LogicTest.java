//@author A0105720W
package whatsupnext.junit.logic;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.logic.LogicUtilities;
import whatsupnext.storage.Storage;
import whatsupnext.structure.enums.OPCODE;
import whatsupnext.structure.enums.Types.ADDTYPE;
import whatsupnext.structure.enums.Types.DELETETYPE;
import whatsupnext.structure.enums.Types.FREETYPE;
import whatsupnext.structure.enums.Types.UPDATETYPE;
import whatsupnext.structure.enums.Types.VIEWTYPE;
import whatsupnext.structure.util.Task;

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
		cal.add(Calendar.YEAR, -1);
		String lastYear = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));
		
		return lastYear + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private String getTodayDateTime() {
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
		assertEquals("Test Add Floating - Successful ", feedback, "Successfully added to task 1.");
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tNot done.");
	}
	
	@Test
	public void testAddDeadlineTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Add Deadline - Successful ",
				"Successfully added to task 1.\n\tDeadline: 2014 Oct 10 12:00", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Oct 10 12:00\n\tNot done.");
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
		assertEquals("Test Add Time Frame - Successful ",
				"Successfully added to task 1.\n\tDeadline: 2014 Oct 11 12:00", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tStart Time: 2014 Oct 10 12:00\n\tEnd Time: 2014 Oct 11 12:00\n\tNot done.");
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
		assertEquals("Test Delete Id - Successful ", "Task 2 is deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tNot done.");
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
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101300");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DONE);
		task.setTaskID("1");
		task.setDone(true);
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DONE);
		task.setTaskID("2");
		task.setDone(true);
		logic.executeTask(task);

		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.DATE);
		task.setEndTime("201410102359");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Date - Successful ", "1 tasks are deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals("3: testing\n\tEnd Time: 2014 Oct 10 13:00\n\tNot done.\n2: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:00\n\tDone.", feedback);
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
		task.setOpcode(OPCODE.DONE);
		task.setTaskID("1");
		task.setDone(true);
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DONE);
		task.setTaskID("2");
		task.setDone(true);
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.DEADLINE);
		task.setEndTime("201410111200");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Deadline - Successful ", "2 tasks are deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "3: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:30\n\tNot done.");
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
		task.setOpcode(OPCODE.DONE);
		task.setTaskID("2");
		task.setDone(true);
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DONE);
		task.setTaskID("3");
		task.setDone(true);
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.TIMEFRAME);
		task.setStartTime("201410100000");
		task.setEndTime("201410111220");
		
		String feedback = logic.executeTask(task);
		assertEquals("Test Delete Time Frame - Successful ", "1 tasks are deleted.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Oct 10 12:00\n\tNot done.\n"
				+ "3: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:30\n\tDone.");
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
		assertEquals("Test Update Description - Successful ", "Successfully updated the description of task 1.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: new description\n\tNot done.");
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
		assertEquals("Test Update Deadline - Successful ", "Successfully updated the deadline of task 1.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Nov 11 11:11\n\tNot done.");
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
		assertEquals("Test Update Deadline - Successful ", "Successfully updated the time frame of task 1.", feedback);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tStart Time: 2014 Oct 10 10:10\n\tEnd Time: 2014 Nov 11 11:11\n\tNot done.");
	}
	
	@Test
	public void testViewAllTask() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		logic.executeTask(task);
		
		String feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback, "1: testing\n\tNot done.");
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing2");
		task.setEndTime("201410101200");
		logic.executeTask(task);
		
		feedback = logic.executeTask(viewAllTask);
		assertEquals(feedback,
				"1: testing\n\tNot done.\n" +
				"2: testing2\n\tEnd Time: 2014 Oct 10 12:00\n\tNot done."
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
				"1: testing\n\tNot done.\n" +
				"2: testing2\n\tEnd Time: 2014 Oct 10 12:00\n\tNot done.\n" + 
				"3: testing3\n\tStart Time: 2014 Oct 10 20:00\n\tEnd Time: 2014 Nov 11 12:00\n\tNot done."
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
				"End Time: " + logic.formatTime(getTodayDate() + "2359") + "\n\tNot done.\n" +
				"3: testing\n\t" +
				"Start Time: " + logic.formatTime(getTodayDate() + "0000") + "\n\t" +
				"End Time: " + logic.formatTime(getTodayDate() + "2359")+ "\n\tNot done."
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
		assertEquals(feedback, "1: testing\n\tEnd Time: 2014 Oct 10 12:00\n\tNot done.");
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
				"2: testing\n\tStart Time: 2014 Oct 11 10:00\n\tEnd Time: 2014 Oct 11 12:00\n\tNot done."
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
				"3: a b\n\tNot done." + "\n" +
		        "2: task a b\n\tNot done." + "\n" +
		        "1: task\n\tNot done."
		);
		
		task = new Task();
		task.setOpcode(OPCODE.SEARCH);
		task.setDescription("task b");
		
		feedback = logic.executeTask(task);
		assertEquals(feedback,
				"2: task a b\n\tNot done."
		);
	}
	
	@Test
	public void testFreeDate() {
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101100");
		task.setEndTime("201410101500");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101800");
		task.setEndTime("201410102100");
		logic.executeTask(task);
		
		task = new Task();
		task.setOpcode(OPCODE.FREE);
		task.setFreeType(FREETYPE.DATE);
		task.setDescription("3");
		task.setEndTime("201410102359");
		
		String feedback = logic.executeTask(task);
		assertEquals(feedback,
				"Available time slots:\n2014 Oct 10 06:00 - 2014 Oct 10 11:00\n2014 Oct 10 15:00 - 2014 Oct 10 18:00"
		);
	}
}
