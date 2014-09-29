package Junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import Parser.ParseDate;

public class ParseDateTest {

	@Test
	public void testParseTimeDate() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("2359 010414");
		assertEquals("Test HHmm ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 04012014");
		assertEquals("Test HHmm ddMMyyyy", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("1315 25/12/14");
		assertEquals("Test HHmm dd/MM/yy", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("1315 25/12/2014");
		assertEquals("Test HHmm dd/MM/yyyy", "201412251315", formattedDate);
	
		formattedDate = parseDate.parseInput("0909 03-04-14");
		assertEquals("Test HHmm dd-MM-yy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("0909 03-04-2014");
		assertEquals("Test HHmm dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("23:59 010414");
		assertEquals("Test HH:mm ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("01:02 04012014");
		assertEquals("Test HH:mm ddMMyyyy", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("13:15 25/12/14");
		assertEquals("Test HH:mm dd/MM/yy", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("13:15 25/12/2014");
		assertEquals("Test HH:mm dd/MM/yyyy", "201412251315", formattedDate);
	
		formattedDate = parseDate.parseInput("09:09 03-04-14");
		assertEquals("Test HH:mm dd-MM-yy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("09:09 03-04-2014");
		assertEquals("Test HH:mm dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("11:59 PM 010414");
		assertEquals("Test h:mm a ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("9:09 am 03-04-2014");
		assertEquals("Test h:mm a dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("1 am 04012014");
		assertEquals("Test h a ddMMyyyy", "201401040100", formattedDate);
		
		formattedDate = parseDate.parseInput("1 pm 25/12/14");
		assertEquals("Test h a dd/MM/yy", "201412251300", formattedDate);
	}
	
	@Test
	public void testParseDateTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("010414 2359");
		assertEquals("Test ddMMyy HHmm", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 0102");
		assertEquals("Test ddMMyyyy HHmm", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 1315");
		assertEquals("Test dd/MM/yy HHmm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/2014 1315");
		assertEquals("Test dd/MM/yyyy HHmm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-14 0909");
		assertEquals("Test dd-MM-yy HHmm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 0909");
		assertEquals("Test dd-MM-yyyy HHmm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("010414 23:59");
		assertEquals("Test ddMMyy HH:mm", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 01:02");
		assertEquals("Test ddMMyyyy HH:mm", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 13:15");
		assertEquals("Test dd/MM/yy HH:mm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/2014 13:15");
		assertEquals("Test dd/MM/yyyy HH:mm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-14 09:09");
		assertEquals("Test dd-MM-yy HH:mm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 09:09");
		assertEquals("Test dd-MM-yyyy HH:mm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("010414 23:59 pm");
		assertEquals("Test ddMMyy h:mm a", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 09:09 AM");
		assertEquals("Test dd-MM-yyyy h:mm a", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 1 AM");
		assertEquals("Test ddMMyyyy h a", "201401040100", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 1 PM");
		assertEquals("Test dd/MM/yy h a", "201412251300", formattedDate);
		
	}
	
	@Test
	public void testParseStandaloneTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("1234");
		assertEquals("Test HHmm", getToday()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("12:34");
		assertEquals("Test HH:mm", getToday()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("12:34 pm");
		assertEquals("Test h:mm a", getToday()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("12:43 am");
		assertEquals("Test h:mm a", getToday()+"0043", formattedDate);
		
		formattedDate = parseDate.parseInput("1:59 PM");
		assertEquals("Test h:mm a", getToday()+"1359", formattedDate);
		
		formattedDate = parseDate.parseInput("11 PM");
		assertEquals("Test h a", getToday()+"2300", formattedDate);
		
		formattedDate = parseDate.parseInput("5 AM");
		assertEquals("Test h a", getToday()+"0500", formattedDate);
	}
	
	@Test
	public void testParseStandaloneDate() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("290914");
		assertEquals("Test ddMMyy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29092014");
		assertEquals("Test ddMMyyyy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29/09/14");
		assertEquals("Test dd/MM/yy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29/09/2014");
		assertEquals("Test dd/MM/yyyy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29-09-14");
		assertEquals("Test dd-MM-yy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29-09-2014");
		assertEquals("Test dd-MM-yyyy", "201409292359", formattedDate);
	}
	
	@Test
	public void testParseOthers() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("0000 04012014");
		assertEquals("Test 0000 ddMMyy ", "201401040000", formattedDate);
		
		formattedDate = parseDate.parseInput("rubbish");
		assertEquals("Test invalid", "", formattedDate);
		
		formattedDate = parseDate.parseInput("rubbish Date");
		assertEquals("Test invalid", "", formattedDate);
		
		formattedDate = parseDate.parseInput("23591 040114");
		assertEquals("Test invalid time", "", formattedDate);
		
		formattedDate = parseDate.parseInput("2459 040114");
		assertEquals("Test invalid time", "", formattedDate);
		
		formattedDate = parseDate.parseInput("2360 040114");
		assertEquals("Test invalid time", "", formattedDate);
		
		formattedDate = parseDate.parseInput("2360");
		assertEquals("Test invalid time", "", formattedDate);

		formattedDate = parseDate.parseInput("1144 PM 040114");
		assertEquals("Test invalid time", "", formattedDate);
		
		formattedDate = parseDate.parseInput("13 AM 040114");
		assertEquals("Test invalid time", "", formattedDate);
		
		formattedDate = parseDate.parseInput("12 Am 040114");
		assertEquals("Test invalid time", "201401040000", formattedDate);
		
		formattedDate = parseDate.parseInput("12 pM 040114");
		assertEquals("Test invalid time", "201401041200", formattedDate);
		
		formattedDate = parseDate.parseInput("2360 041314");
		assertEquals("Test invalid date", "", formattedDate);
		
		formattedDate = parseDate.parseInput("300214");
		assertEquals("Test invalid date", "", formattedDate);
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
