package Junit;

import static org.junit.Assert.*;

import org.junit.Test;
import Parser.Extractor;

public class ExtractorTest {

	@Test
	public void testAdd() {
		Extractor ex = new Extractor("add dine from 2am tmr to 9");
		String[] output = ex.extractorAdd();
		String[] expected = {"dine", "2am tmr","9"};
		assertArrayEquals(output,expected);
	}
	
	@Test
	public void testAdd2() {
		Extractor ex = new Extractor("add dine By 1pm");
		String[] output = ex.extractorAdd();
		String[] expected = {"dine", null,"1pm"};
		assertArrayEquals(output,expected);
	}
	

}
