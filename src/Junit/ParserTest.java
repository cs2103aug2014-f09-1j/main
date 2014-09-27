package Junit;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Scanner;

import Parser.*;
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

}