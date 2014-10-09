package whatsupnext.junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.parser.ParseDate;
import whatsupnext.storage.Storage;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.structure.Types.UPDATETYPE;
import whatsupnext.structure.Types.VIEWTYPE;

public class LogicTest {
	
	private Logic logic;
	private Task task;
	private static Task viewAllTask;	
	
	private class LogicStub extends Logic {
		public LogicStub(String fileName) {
			storage = new Storage(fileName);
			try {
				list = storage.readTasks();
			} catch (IOException e) {
				e.printStackTrace();
			}
			super.setupAvailableIDs();
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
		logic = new LogicStub("logicTest.txt");
		task = new Task();
	}
	
	@After
	public void deleteAllTasks() {
		Task delete = new Task();
		delete.setOpcode(OPCODE.DELETE);
		delete.setDeleteType(DELETETYPE.ALL);
		logic.execute(delete);
	}
	
	@Test
	public void testAddFloatingTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add Floating - Successful ", feedback, "A task is successfully added.");
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: ");
	}
	
	@Test
	public void testAddDeadlineTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add Deadline - Successful ", "A task is successfully added.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: 201410101200");
	}
	
	@Test
	public void testAddTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101200");
		task.setEndTime("201410111200");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add Time Frame - Successful ", "A task is successfully added.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: 201410101200\n\tEnd Time: 201410111200");
	}
	
	@Test
	public void testDeleteIdTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setTaskID("1");
		task.setDescription("testing");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setTaskID("2");
		task.setDescription("testing");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.ID);
		task.setTaskID("2");
		
		String feedback = logic.execute(task);
		assertEquals("Test Delete Id - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: ");
	}
	
	@Test
	public void testDeleteDateTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.DATE);
		task.setEndTime("201410102359");
		
		String feedback = logic.execute(task);
		assertEquals("Test Delete Date - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 2\n\ttesting\n\tStart Time: 201410111000\n\tEnd Time: 201410111200");
	}
	
	@Test
	public void testDeleteDeadlineTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111230");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.DEADLINE);
		task.setEndTime("201410111200");
		
		String feedback = logic.execute(task);
		assertEquals("Test Delete Deadline - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 3\n\ttesting\n\tStart Time: 201410111000\n\tEnd Time: 201410111230");
	}
	
	@Test
	public void testDeleteTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111230");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.TIMEFRAME);
		task.setStartTime("201410100000");
		task.setEndTime("201410111220");
		
		String feedback = logic.execute(task);
		assertEquals("Test Delete Time Frame - Successful ", "Tasks are successfully deleted.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 3\n\ttesting\n\tStart Time: 201410111000\n\tEnd Time: 201410111230");
	}
	
	@Test
	public void testUpdateDescriptionTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.UPDATE);
		task.setUpdateType(UPDATETYPE.DESCRIPTION);
		task.setTaskID("1");
		task.setDescription("new description");
		
		String feedback = logic.execute(task);
		assertEquals("Test Update Description - Successful ", "A task is successfully updated.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\tnew description\n\tStart Time: \n\tEnd Time: ");
	}
	
	@Test
	public void testUpdateDeadlineTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.UPDATE);
		task.setUpdateType(UPDATETYPE.DEADLINE);
		task.setTaskID("1");
		task.setEndTime("201411111111");
		
		String feedback = logic.execute(task);
		assertEquals("Test Update Deadline - Successful ", "A task is successfully updated.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: 201411111111");
	}
	
	@Test
	public void testUpdateTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101000");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.UPDATE);
		task.setUpdateType(UPDATETYPE.TIMEFRAME);
		task.setTaskID("1");
		task.setStartTime("201410101010");
		task.setEndTime("201411111111");
		
		String feedback = logic.execute(task);
		assertEquals("Test Update Deadline - Successful ", "A task is successfully updated.", feedback);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: 201410101010\n\tEnd Time: 201411111111");
	}
	
	@Test
	public void testViewAllTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		logic.execute(task);
		
		String feedback = logic.execute(viewAllTask);
		assertEquals(feedback, "Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: ");
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing2");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback,
				"Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: \n" +
				"Task ID: 2\n\ttesting2\n\tStart Time: \n\tEnd Time: 201410101200"
		);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing3");
		task.setStartTime("201410102000");
		task.setEndTime("201411111200");
		logic.execute(task);
		
		feedback = logic.execute(viewAllTask);
		assertEquals(feedback,
				"Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: \n" +
				"Task ID: 2\n\ttesting2\n\tStart Time: \n\tEnd Time: 201410101200\n" +
				"Task ID: 3\n\ttesting3\n\tStart Time: 201410102000\n\tEnd Time: 201411111200"
		);
	}
	
	@Test
	public void testViewNextTask() {		
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime(getLastYearTodayDate() + "0000");
		task.setEndTime(getLastYearTodayDate() + "2359");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime(getLastYearTodayDate() + "0000");
		task.setEndTime(getTodayDate() + "2359");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime(getTodayDate() + "0000");
		task.setEndTime(getTodayDate() + "2359");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.NEXT);
		task.setEndTime(getTodayDateTime());
		
		String feedback = logic.execute(task);
		assertEquals(feedback,
				"Task ID: 2\n\ttesting\n\t" +
				"Start Time: " + (getLastYearTodayDate() + "0000") + "\n\t" +
				"End Time: " + (getTodayDate() + "2359") + "\n" +
				"Task ID: 3\n\ttesting\n\t" +
				"Start Time: " + (getTodayDate() + "0000") + "\n\t" +
				"End Time: " + (getTodayDate() + "2359")
		);
	}
	
	@Test
	public void testViewDateTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.DEADLINE);
		task.setDescription("testing");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.DATE);
		task.setEndTime("201410102359");
		
		String feedback = logic.execute(task);
		assertEquals(feedback,
				"Task ID: 1\n\ttesting\n\tStart Time: \n\tEnd Time: 201410101200"
		);
	}
	
	@Test
	public void testViewTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410101100");
		task.setEndTime("201410101200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.TIMEFRAME);
		task.setDescription("testing");
		task.setStartTime("201410111000");
		task.setEndTime("201410111200");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.TIMEFRAME);
		task.setStartTime("201410110900");
		task.setEndTime("201410112359");
		
		String feedback = logic.execute(task);
		assertEquals(feedback,
				"Task ID: 2\n\ttesting\n\tStart Time: 201410111000\n\tEnd Time: 201410111200"
		);
	}

}
