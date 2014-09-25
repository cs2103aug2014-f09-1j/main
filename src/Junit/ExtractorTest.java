package Junit;

import static org.junit.Assert.*;

import org.junit.Test;
import Parser.Extractor;

public class ExtractorTest {

	@Test
	public void test() {
		Extractor ex = new Extractor("add dine from 2am  asd to 9");
		String[] output = ex.extractor();
		String[] expected = {"dine", "2am  asd","9"};
		assertEquals(output,expected);
	}
	@Test
	public void test2() {
		Extractor ex = new Extractor("add dine By 1");
		String[] output = ex.extractor();
		String[] expected = {"dine", null,"1"};
		assertEquals(output,expected);
	}

}
