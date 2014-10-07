package whatsupnext.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.ADDTYPE;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.structure.Types.VIEWTYPE;

public class LogicTest {
	
	private Logic logic;
	private Task task;
	private static Task viewAllTask;
	
	
	@BeforeClass
	public static void createViewAllTask() {
		viewAllTask = new Task();
		viewAllTask.setOpcode(OPCODE.VIEW);
		viewAllTask.setViewType(VIEWTYPE.ALL);
	}
	
	@Before
	public void init() {
		logic = new Logic();
		task = new Task();
	}
	
	@After
	public void deleteAllTasks() {
		String tasksFeedback = logic.execute(viewAllTask);
		String[] feedbackLines = tasksFeedback.split("\n");
		int numTasks = feedbackLines.length / 4;
		
		Task delete = new Task();
		for (int i = 0; i < numTasks; i++) {
			delete.setOpcode(OPCODE.DELETE);
			delete.setDeleteType(DELETETYPE.ID);
			delete.setTaskID(Integer.toString(i + 1));
			logic.execute(delete);
		}
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
		task.setDescription("testing from 101014 to 111014");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add Time Frame - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testDeleteIdTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setTaskID("1");
		task.setDescription("testing");
		logic.execute(task);
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(DELETETYPE.ID);
		task.setTaskID("1");
		
		String feedback = logic.execute(task);
		assertEquals("Test Delete Id - Successful ", "Tasks are successfully deleted.", feedback);
	}
	
	@Test
	public void testDeleteDateTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testDeleteDeadlineTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testDeleteTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testUpdateDescriptionTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testUpdateDeadlineTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testUpdateTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testViewAllTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testViewNextTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testViewDateTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}
	
	@Test
	public void testViewTimeFrameTask() {
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added.", feedback);
	}

}
