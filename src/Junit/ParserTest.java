package Junit;
import static org.junit.Assert.*;
import Structure.OPCODE;

import org.junit.Test;

import java.util.Scanner;

import Parser.*;
import Structure.Task;
public class ParserTest {

	private Scanner scanner = new Scanner(System.in);
	private String description;
	private String expected;
	private String input;
	
	@Test
	public void testValidOpcode() {
		description = scanner.nextLine();
		expected = scanner.nextLine();
		input = scanner.nextLine();
		Parser parser = new Parser(input);
		parser.parseInput();
		String output = parser.getTask().getOpCode().toString();
		assertEquals(description, expected, output);
		
	}
	
	@Test
	public void testParserAdd() {
		description = "Parser testing: Add command";
		input = "add dine with Amy from 201409291800 to 201409292000";	
		Parser parser = new Parser(input);
		parser.parseInput();
        Task output = parser.getTask();
		assertEquals("Test Add 1 - description", "dine with Amy", output.getDescription());
		assertEquals("Test Add 1 - startTime", "201409291800", output.getStartTime());
		assertEquals("Test Add 1 - endTime", "201409292000", output.getEndTime());
        assertEquals("Test Add 1 - OPCODE",OPCODE.ADD,output.getOpCode());
	}
	
	@Test
	public void testParserUpdate() {
		description = "Parser testing: Add command";
		input = "update 19 from 201409291800 to 201409292000";
		Parser parser = new Parser(input);
		parser.parseInput();
        Task output = parser.getTask();
		assertEquals("Test update 1 - description", null, output.getDescription());
		assertEquals("Test update 1 - startTime", "201409291800", output.getStartTime());
		assertEquals("Test update 1 - endTime", "201409292000", output.getEndTime());		
        assertEquals("Test update 1 - OPCODE",OPCODE.UPDATE,output.getOpCode());
        assertEquals("Test update 1 - taskID",19,parser.getTaskID());
        assertEquals("Test update 1 - updateType","TIMEFRAME",output.getUpdateType());
	}
	

}