package whatsupnext.junit;
import static org.junit.Assert.*;

import org.junit.Test;

import whatsupnext.parser.*;
import whatsupnext.structure.ADDTYPE;
import whatsupnext.structure.DELETETYPE;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.UPDATETYPE;
import whatsupnext.structure.VIEWTYPE;

public class ParserTest {
	
	@Test
	//Parser testing: Add timeframe task
	public void testParserAdd1() {
		String input = "add dine with Amy from 6 PM 29/09/2014 to 8 pm 29/09/2014";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE",OPCODE.ADD,task.getOpCode());
        assertEquals("Test Add - addType",ADDTYPE.TIMEFRAME,task.getAddType());
		assertEquals("Test Add - description", "dine with Amy", task.getDescription());
		assertEquals("Test Add - startTime", "201409291800", task.getStartTime());
		assertEquals("Test Add - endTime", "201409292000", task.getEndTime());
	}
	
	@Test
	//Parser testing: Add timeframe task
	public void testParserAdd2() {
		String input = "add dine with Bob from 1800 29092014 to 2000 29092014";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("Test Add - addType",ADDTYPE.TIMEFRAME,task.getAddType());
		assertEquals("Test Add - description", "dine with Bob", task.getDescription());
		assertEquals("Test Add - startTime", "201409291800", task.getStartTime());
		assertEquals("Test Add - endTime", "201409292000", task.getEndTime());
	}
	
	@Test
	//Parser testing: Add timeframe task
	public void testParserAdd3() {
		String input = "add dance with Amy from 6:00 am 29-09-14 to 8 am 29-09-14";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("Test Add - addType",ADDTYPE.TIMEFRAME,task.getAddType());
		assertEquals("Test Add - description", "dance with Amy", task.getDescription());
		assertEquals("Test Add - startTime", "201409290600", task.getStartTime());
		assertEquals("Test Add - endTime", "201409290800", task.getEndTime());
	}
	
	@Test
	//Parser testing: Add timeframe task
	public void testParserAdd4() {
		String input = "add dance with Bob from 9:00 29-09-14 to 13:00 29-09-14";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("Test Add - addType",ADDTYPE.TIMEFRAME,task.getAddType());
		assertEquals("Test Add - description", "dance with Bob", task.getDescription());
		assertEquals("Test Add - startTime", "201409290900", task.getStartTime());
		assertEquals("Test Add - endTime", "201409291300", task.getEndTime());
	}
	
	@Test
	//Parser testing: Add deadline task
	public void testParserAdd5() {
		String input = "add submit report by 9 AM 29-09-14";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("Test Add - addType",ADDTYPE.DEADLINE,task.getAddType());
		assertEquals("Test Add - description", "submit report", task.getDescription());
		assertEquals("Test Add - startTime", "", task.getStartTime());
		assertEquals("Test Add - endTime", "201409290900", task.getEndTime());
	}
	
	@Test
	//Parser testing: Add floating task
	public void testParserAdd6() {
		String input = "add submit report";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("Test Add - addType",ADDTYPE.FLOATING,task.getAddType());
		assertEquals("Test Add - description", "submit report", task.getDescription());
		assertEquals("Test Add - startTime", "", task.getStartTime());
		assertEquals("Test Add - endTime", "", task.getEndTime());
	}
	
	@Test
	//Parser testing: Add empty task
	public void testParserAdd7() {
		String input = "add";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test Add - OPCODE", OPCODE.ADD, task.getOpCode());
		assertEquals("Test Add - description", "", task.getDescription());
		assertEquals("Test Add - startTime", "", task.getStartTime());
		assertEquals("Test Add - endTime", "", task.getEndTime());
	}
	
	@Test
	// Parser Testing: update by timeframe
	public void testParserUpdate1() {
		String input = "update 19 from 1800 29/09/2014 to 2000 29/09/2014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test update - OPCODE", OPCODE.UPDATE, task.getOpCode());
		assertEquals("Test update - updateType", UPDATETYPE.TIMEFRAME, task.getUpdateType());
        assertEquals("Test update - taskID", "19", task.getTaskID());
		assertEquals("Test update - description", "", task.getDescription());
		assertEquals("Test update - startTime", "201409291800", task.getStartTime());
		assertEquals("Test update - endTime", "201409292000", task.getEndTime());		
	}
	
	@Test
	// Parser Testing: update by end time(deadline)
	public void testParserUpdate2() {
		String input = "update 19 by 18:00 29/09/2014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test update - OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("Test update - updateType", UPDATETYPE.DEADLINE, task.getUpdateType());
        assertEquals("Test update - taskID", "19", task.getTaskID());
		assertEquals("Test update - description", "", task.getDescription());
		assertEquals("Test update - startTime", "", task.getStartTime());
		assertEquals("Test update - endTime", "201409291800", task.getEndTime());		
	}
	
	@Test
	// Parser Testing: update by description
	public void testParserUpdate3() {
		String input = "update 19 new descripitions1234";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test update - OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("Test update - updateType", UPDATETYPE.DESCRIPTION, task.getUpdateType());
        assertEquals("Test update - taskID", "19", task.getTaskID());
		assertEquals("Test update - description", "new descripitions1234", task.getDescription());
		assertEquals("Test update - startTime", "", task.getStartTime());
		assertEquals("Test update - endTime", "", task.getEndTime());		
	}
	
	@Test
	// Parser Testing: view all
	public void testParserView1() {
		String input = "view All";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test view - OPCODE", OPCODE.VIEW, task.getOpCode());
		assertEquals("Test view - viewType", VIEWTYPE.ALL, task.getViewType());
	}
	
	@Test
	// Parser Testing: view by timeframe
	public void testParserView2() {
		String input = "v from 1900 29092014 to 2030 29092014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test view - OPCODE", OPCODE.VIEW, task.getOpCode());
        assertEquals("Test view - viewType", VIEWTYPE.TIMEFRAME, task.getViewType());
		assertEquals("Test view - description", "", task.getDescription());
		assertEquals("Test view - startTime", "201409291900", task.getStartTime());
		assertEquals("Test view - endTime", "201409292030", task.getEndTime());
	}
	
	@Test
	// Parser Testing: view by deadline
	public void testParserView3() {
		String input = "view 1900 29092014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test view - OPCODE", OPCODE.VIEW, task.getOpCode());
        assertEquals("Test view - viewType", VIEWTYPE.DATE, task.getViewType());
        assertEquals("Test view - description", "", task.getDescription());
		assertEquals("Test view - startTime", "", task.getStartTime());
		assertEquals("Test view - endTime", "201409291900", task.getEndTime());		
	}
	
	@Test
	// Parser Testing: delete by timeframe
	public void testParserDelete1() {
		String input = "d from 1900 29092014 To 2030 29092014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test delete - OPCODE", OPCODE.DELETE, task.getOpCode());
        assertEquals("Test delete - deleteType", DELETETYPE.TIMEFRAME, task.getDeleteType());
        assertEquals("Test delete - description", "", task.getDescription());
		assertEquals("Test delete - startTime", "201409291900", task.getStartTime());
		assertEquals("Test update - endTime", "201409292030", task.getEndTime());		
	}
	
}