package whatsupnext.junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.structure.ADDTYPE;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;

public class LogicTest {

	@Test
	public void testAdd1() throws IOException {
		Logic logic = new Logic();
		Task task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(ADDTYPE.FLOATING);
		task.setDescription("testing");
		String feedback = logic.execute(task);
		assertEquals("Test Add - Successful ", "A task is successfully added", feedback);
	}

}
