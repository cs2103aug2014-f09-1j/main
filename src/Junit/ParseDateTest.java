package Junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import Parser.ParseDate;

public class ParseDateTest {

	@Test
	public void testParseDate() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		formattedDate = parseDate.parseInput("2359 010414");
		assertEquals("Test HHmm ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 04012014");
		assertEquals("Test HHmm ddMMyyyy", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("0000 04012014");
		assertEquals("Test time 0000 ", "201401040000", formattedDate);
		
		formattedDate = parseDate.parseInput("010414 2359");
		assertEquals("Test ddMMyy HHmm", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 0102");
		assertEquals("Test ddMMyyyy HHmm", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("1315 25/12/14");
		assertEquals("Test HHmm dd/MM/yy", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("1315 25/12/2014");
		assertEquals("Test HHmm dd/MM/yyyy", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 1315");
		assertEquals("Test dd/MM/yy HHmm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/2014 1315");
		assertEquals("Test dd/MM/yyyy HHmm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("0909 03-04-14");
		assertEquals("Test HHmm dd-MM-yy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("0909 03-04-2014");
		assertEquals("Test HHmm dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-14 0909");
		assertEquals("Test dd-MM-yy HHmm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 0909");
		assertEquals("Test dd-MM-yyyy HHmm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("1234");
		assertEquals("Test HHmm", getToday()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("29/09/14");
		assertEquals("Test dd/MM/yy", "201409292359", formattedDate);
		
	}
	
	private String getToday() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = "";
		String twoDigitDayOfMonth = ""; 
        if (month < 10) {
        	twoDigitMonth = "0" + month;
 		} else {
 			twoDigitMonth = "" + month;
 		}
 		if (dayOfMonth < 10) {
 			twoDigitDayOfMonth = "0" + dayOfMonth;
 		} else {
 			twoDigitDayOfMonth = "" + dayOfMonth;
 		}
         
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}

}
