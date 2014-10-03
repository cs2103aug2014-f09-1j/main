package Junit;
import static org.junit.Assert.*;
import Structure.DELETETYPE;
import Structure.OPCODE;
import Structure.UPDATETYPE;
import Structure.VIEWTYPE;

import org.junit.Test;

import Parser.*;
import Structure.Task;

public class ParserTest {
	
	@Test
	//Parser testing: Add command
	public void testParserAdd1() {
		String input = "add dine with Amy from 6 PM 29/09/2014 to 8 pm 29/09/2014";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test Add 1 - description", "dine with Amy", task.getDescription());
		assertEquals("Test Add 1 - startTime", "201409291800", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "201409292000", task.getEndTime());
        assertEquals("Test Add 1 - OPCODE",OPCODE.ADD,task.getOpCode());
	}
	
	@Test
	//Parser testing: Add command
	public void testParserAdd2() {
		String input = "add dine with Bob from 1800 29092014 to 2000 29092014";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test Add 1 - description", "dine with Bob", task.getDescription());
		assertEquals("Test Add 1 - startTime", "201409291800", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "201409292000", task.getEndTime());
        assertEquals("Test Add 1 - OPCODE", OPCODE.ADD, task.getOpCode());
	}
	
	@Test
	//Parser testing: Add command
	public void testParserAdd3() {
		String input = "add dance with Amy from 6:00 am 29-09-14 to 8 am 29-09-14";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test Add 1 - description", "dance with Amy", task.getDescription());
		assertEquals("Test Add 1 - startTime", "201409290600", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "201409290800", task.getEndTime());
        assertEquals("Test Add 1 - OPCODE", OPCODE.ADD, task.getOpCode());
	}
	
	@Test
	//Parser testing: Add command
	public void testParserAdd4() {
		String input = "add dance with Bob from 9:00 29-09-14 to 13:00 29-09-14";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test Add 1 - description", "dance with Bob", task.getDescription());
		assertEquals("Test Add 1 - startTime", "201409290900", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "201409291300", task.getEndTime());
        assertEquals("Test Add 1 - OPCODE", OPCODE.ADD, task.getOpCode());
	}
	
	@Test
	//Parser testing: Add command
	public void testParserAdd5() {
		String input = "add submit report by 9 AM 29-09-14";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test Add 1 - description", "submit report", task.getDescription());
		assertEquals("Test Add 1 - startTime", "", task.getStartTime());
		assertEquals("Test Add 1 - endTime", "201409290900", task.getEndTime());
        assertEquals("Test Add 1 - OPCODE", OPCODE.ADD, task.getOpCode());
	}
	
	
	@Test
	// Parser Testing: update by timeframe
	public void testParserUpdate1() {
		String input = "update 19 from 1800 29/09/2014 to 2000 29/09/2014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test update 1 - updateType", UPDATETYPE.TIMEFRAME, task.getUpdateType());
		assertEquals("Test update 1 - description", null, task.getDescription());
		assertEquals("Test update 1 - startTime", "201409291800", task.getStartTime());
		assertEquals("Test update 1 - endTime", "201409292000", task.getEndTime());		
        assertEquals("Test update 1 - OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("Test update 1 - taskID", 19, parser.getTaskID());
        
	}
	
	@Test
	// Parser Testing: update by end time(deadline)
	public void testParserUpdate2() {
		String input = "update 19 by 18:00 29/09/2014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test update 1 - description", null, task.getDescription());
		assertEquals("Test update 1 - startTime", "", task.getStartTime());
		assertEquals("Test update 1 - endTime", "201409291800", task.getEndTime());		
        assertEquals("Test update 1 - OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("Test update 1 - taskID", 19, parser.getTaskID());
        assertEquals("Test update 1 - updateType", UPDATETYPE.DATE, task.getUpdateType());
	}
	
	@Test
	// Parser Testing: update by description
	public void testParserUpdate3() {
		String input = "update 19 new descripitions1234";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test update 1 - description", "new descripitions1234", task.getDescription());
		assertEquals("Test update 1 - startTime", null, task.getStartTime());
		assertEquals("Test update 1 - endTime", null, task.getEndTime());		
        assertEquals("Test update 1 - OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("Test update 1 - taskID", 19, parser.getTaskID());
        assertEquals("Test update 1 - updateType", UPDATETYPE.DESCRIPTION, task.getUpdateType());
	}
	
	@Test
	// Parser Testing: update by timeframe
	public void testParserView1() {
		String input = "view All";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test view 1 - viewType", VIEWTYPE.ALL, task.getViewType());
        assertEquals("Test view 1 - OPCODE", OPCODE.VIEW, task.getOpCode());
        
	}
	
	@Test
	// Parser Testing: update by end time(deadline)
	public void testParserView2() {
		String input = "v from 1900 29092014 to 2030 29092014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
		assertEquals("Test view 2 - description", null, task.getDescription());
		assertEquals("Test view 2 - startTime", "201409291900", task.getStartTime());
		assertEquals("Test view 2 - endTime", "201409292030", task.getEndTime());		
        assertEquals("Test view 2 - OPCODE", OPCODE.VIEW, task.getOpCode());
        assertEquals("Test view 2 - viewType", VIEWTYPE.TIMEFRAME, task.getViewType());
	}
	
	@Test
	// Parser Testing: update by description
	public void testParserView3() {
		String input = "view by 1900 29092014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test update 1 - viewType", VIEWTYPE.DATE, task.getViewType());
        assertEquals("Test update 1 - description", null, task.getDescription());
		assertEquals("Test update 1 - startTime", null, task.getStartTime());
		assertEquals("Test update 1 - endTime", "201409291900", task.getEndTime());		
        assertEquals("Test update 1 - OPCODE", OPCODE.VIEW, task.getOpCode());
	}
	
	@Test
	// Parser Testing: DELETE by timeframe
	public void testParserDelete1() {
		String input = "d from 1900 29092014 To 2030 29092014";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task task = parser.getTask();
        assertEquals("Test update 1 - deleteType", DELETETYPE.TIMEFRAME, task.getDeleteType());
        assertEquals("Test update 1 - description", null, task.getDescription());
		assertEquals("Test update 1 - startTime", "201409291900", task.getStartTime());
		assertEquals("Test update 1 - endTime", "201409292030", task.getEndTime());		
        assertEquals("Test update 1 - OPCODE", OPCODE.DELETE, task.getOpCode());
	}
	
	@Test
	// TODO: not working 
	public void testParseDate() {
		ParseDate parseDate = new ParseDate();
		String input1 = "6:09 29/09/2014";
		String input2 = "11 AM 29/09/2014";
		String input3 = "8 pm 29-09-2014";
		Boolean result1 = parseDate.isDate(input1);
		Boolean result2 = parseDate.isDate(input2);
		Boolean result3 = parseDate.isDate(input3);
		assertEquals("Test parseDate 1", true, result1);
		assertEquals("Test parseDate 2", true, result2);
		assertEquals("Test parseDate 3", true, result3);
	}
	
	

}