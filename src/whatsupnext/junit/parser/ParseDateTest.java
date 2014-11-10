//@author A0111773L
package whatsupnext.junit.parser;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import whatsupnext.parser.extractor.ParseDate;

public class ParseDateTest {

	private final int DAYS_IN_WEEK = 7;
	
	@Test
	/* This is a boundary case for the 'time date' partition */
	public void testParseTimeDate() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("2359 010414");
		assertEquals("HHmm ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 04012014");
		assertEquals("HHmm ddMMyyyy", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("1315 25/12/14");
		assertEquals("HHmm dd/MM/yy", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("1315 25/12/2014");
		assertEquals("HHmm dd/MM/yyyy", "201412251315", formattedDate);
	
		formattedDate = parseDate.parseInput("0909 03-04-14");
		assertEquals("HHmm dd-MM-yy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("0909 03-04-2014");
		assertEquals("HHmm dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("23:59 010414");
		assertEquals("HH:mm ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("01:02 04012014");
		assertEquals("HH:mm ddMMyyyy", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("13:15 25/12/14");
		assertEquals("HH:mm dd/MM/yy", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("13:15 25/12/2014");
		assertEquals("HH:mm dd/MM/yyyy", "201412251315", formattedDate);
	
		formattedDate = parseDate.parseInput("09:09 03-04-14");
		assertEquals("HH:mm dd-MM-yy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("09:09 03-04-2014");
		assertEquals("HH:mm dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("11:59 PM 010414");
		assertEquals("h:mm a ddMMyy", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("9:09 am 03-04-2014");
		assertEquals("h:mm a dd-MM-yyyy", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("1 am 04012014");
		assertEquals("h a ddMMyyyy", "201401040100", formattedDate);
		
		formattedDate = parseDate.parseInput("1 pm 25/12/14");
		assertEquals("h a dd/MM/yy", "201412251300", formattedDate);
		
		formattedDate = parseDate.parseInput("1 pm 25 dec 14");
		assertEquals("h a dd MMM yy", "201412251300", formattedDate);
		
		formattedDate = parseDate.parseInput("1 pm 25 december 2014");
		assertEquals("h a dd MMMM yyyy", "201412251300", formattedDate);
		
	}
	
	@Test
	/* This is a boundary case for the 'date time' partition */
	public void testParseDateTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("010414 2359");
		assertEquals("ddMMyy HHmm", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 0102");
		assertEquals("ddMMyyyy HHmm", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 1315");
		assertEquals("dd/MM/yy HHmm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/2014 1315");
		assertEquals("dd/MM/yyyy HHmm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-14 0909");
		assertEquals("dd-MM-yy HHmm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 0909");
		assertEquals("dd-MM-yyyy HHmm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("010414 23:59");
		assertEquals("ddMMyy HH:mm", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 01:02");
		assertEquals("ddMMyyyy HH:mm", "201401040102", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 13:15");
		assertEquals("dd/MM/yy HH:mm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/2014 13:15");
		assertEquals("dd/MM/yyyy HH:mm", "201412251315", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-14 09:09");
		assertEquals("dd-MM-yy HH:mm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 09:09");
		assertEquals("dd-MM-yyyy HH:mm", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("010414 23:59 pm");
		assertEquals("ddMMyy h:mm a", "201404012359", formattedDate);
		
		formattedDate = parseDate.parseInput("03-04-2014 09:09 AM");
		assertEquals("dd-MM-yyyy h:mm a", "201404030909", formattedDate);
		
		formattedDate = parseDate.parseInput("04012014 1 AM");
		assertEquals("ddMMyyyy h a", "201401040100", formattedDate);
		
		formattedDate = parseDate.parseInput("25/12/14 1 PM");
		assertEquals("dd/MM/yy h a", "201412251300", formattedDate);
		
		formattedDate = parseDate.parseInput("04 jan 14 1 AM");
		assertEquals("dd MMM yy h a", "201401040100", formattedDate);
		
		formattedDate = parseDate.parseInput("25 december 2014 1 PM");
		assertEquals("dd MMMM yy h a", "201412251300", formattedDate);
		
	}
	
	@Test
	/* This is a boundary case for the 'time' partition */
	public void testParseStandaloneTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("1234");
		assertEquals("HHmm", getTodayDate()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("12:34");
		assertEquals("HH:mm", getTodayDate()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("12:34 pm");
		assertEquals("h:mm a", getTodayDate()+"1234", formattedDate);
		
		formattedDate = parseDate.parseInput("12:43 am");
		assertEquals("h:mm a", getTodayDate()+"0043", formattedDate);
		
		formattedDate = parseDate.parseInput("1:59 PM");
		assertEquals("h:mm a", getTodayDate()+"1359", formattedDate);
		
		formattedDate = parseDate.parseInput("11 PM");
		assertEquals("h a", getTodayDate()+"2300", formattedDate);
		
		formattedDate = parseDate.parseInput("5 AM");
		assertEquals("h a", getTodayDate()+"0500", formattedDate);
	}
	
	@Test
	/* This is a boundary case for the 'date' partition */
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
		
		formattedDate = parseDate.parseInput("29 sep 14");
		assertEquals("dd MMM yy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29 sep 2014");
		assertEquals("dd MMM yyyy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29 september 14");
		assertEquals("dd MMMM yy", "201409292359", formattedDate);
		
		formattedDate = parseDate.parseInput("29 september 2014");
		assertEquals("dd MMMM yyyy", "201409292359", formattedDate);
		
		parseDate.setParsingStartTime(true);
		formattedDate = parseDate.parseInput("29-09-2014");
		assertEquals("dd-MM-yyyy 0000", "201409290000", formattedDate);
		
		parseDate.setParsingStartTime(false);
		formattedDate = parseDate.parseInput("29-09-2014");
		assertEquals("dd-MM-yyyy 2359", "201409292359", formattedDate);
	}
	
	@Test
	/* This is a boundary case for the 'time day' partition */
	public void testParseTimeDay() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("0102 today");
		assertEquals("HHmm today", getTodayDate()+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 tomorrow");
		assertEquals("HHmm tomorrow", getTomorrowDate()+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 sunday");
		assertEquals("HHmm sunday", getNextDayOfWeekDate(Calendar.SUNDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 monday");
		assertEquals("HHmm monday", getNextDayOfWeekDate(Calendar.MONDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 tuesday");
		assertEquals("HHmm tuesday", getNextDayOfWeekDate(Calendar.TUESDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 wednesday");
		assertEquals("HHmm wednesday", getNextDayOfWeekDate(Calendar.WEDNESDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 thursday");
		assertEquals("HHmm thursday", getNextDayOfWeekDate(Calendar.THURSDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 friday");
		assertEquals("HHMm friday", getNextDayOfWeekDate(Calendar.FRIDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 saturday");
		assertEquals("HHMm saturday", getNextDayOfWeekDate(Calendar.SATURDAY)+"0102", formattedDate);
		
		
	}
	
	@Test
	/* This is a boundary case for the 'day time' partition */
	public void testParseDayTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("Today 2222");
		assertEquals("Today HHmm", getTodayDate()+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Tml 2222");
		assertEquals("Tml HHmm", getTomorrowDate()+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Sun 2222");
		assertEquals("Sun HHmm ", getNextDayOfWeekDate(Calendar.SUNDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Mon 2222");
		assertEquals("Mon HHmm", getNextDayOfWeekDate(Calendar.MONDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Tue 2222");
		assertEquals("Tue HHmm", getNextDayOfWeekDate(Calendar.TUESDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Wed 2222");
		assertEquals("Wed HHmm", getNextDayOfWeekDate(Calendar.WEDNESDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Thu 2222");
		assertEquals("Thu HHmm", getNextDayOfWeekDate(Calendar.THURSDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Fri 2222");
		assertEquals("Fri HHmm", getNextDayOfWeekDate(Calendar.FRIDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Sat 2222");
		assertEquals("Sat HHmm", getNextDayOfWeekDate(Calendar.SATURDAY)+"2222", formattedDate);
		
	}
	
	@Test
	/* This is a boundary case for the 'day' partition */
	public void testParseStandaloneDay() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("today");
		assertEquals("today", getTodayDate()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Tomorrow");
		assertEquals("Tomorrow", getTomorrowDate()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("tml");
		assertEquals("tml", getTomorrowDate()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Sunday");
		assertEquals("Sunday", getNextDayOfWeekDate(Calendar.SUNDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("sun");
		assertEquals("sun", getNextDayOfWeekDate(Calendar.SUNDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Monday");
		assertEquals("Monday", getNextDayOfWeekDate(Calendar.MONDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("mon");
		assertEquals("mon", getNextDayOfWeekDate(Calendar.MONDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Tuesday");
		assertEquals("Tuesday", getNextDayOfWeekDate(Calendar.TUESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("tue");
		assertEquals("tue", getNextDayOfWeekDate(Calendar.TUESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Wednesday");
		assertEquals("Wednesday", getNextDayOfWeekDate(Calendar.WEDNESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("wed");
		assertEquals("wed", getNextDayOfWeekDate(Calendar.WEDNESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Thursday");
		assertEquals("Thursday", getNextDayOfWeekDate(Calendar.THURSDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("thu");
		assertEquals("thu", getNextDayOfWeekDate(Calendar.THURSDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Friday");
		assertEquals("Friday", getNextDayOfWeekDate(Calendar.FRIDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("fri");
		assertEquals("fri", getNextDayOfWeekDate(Calendar.FRIDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Saturday");
		assertEquals("Saturday", getNextDayOfWeekDate(Calendar.SATURDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("sat");
		assertEquals("sat", getNextDayOfWeekDate(Calendar.SATURDAY)+"2359", formattedDate);
		
		parseDate.setParsingStartTime(true);
		formattedDate = parseDate.parseInput("today");
		assertEquals("Today 0000", getTodayDate()+"0000", formattedDate);
		
		parseDate.setParsingStartTime(false);
		formattedDate = parseDate.parseInput("today");
		assertEquals("Today 2359", getTodayDate()+"2359", formattedDate);
	}
	
	@Test
	/* This is a test case for the 'now' partition */
	public void testParseCurrentTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("now");
		assertEquals("Today", getCurrentTime(), formattedDate);
		
		formattedDate = parseDate.parseInput("Now");
		assertEquals("Today", getCurrentTime(), formattedDate);
		
	}
	
	@Test
	public void testParseInvalid() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		/* This is a boundary case for the 'any other days' partition */
		formattedDate = parseDate.parseInput("rubbish");
		assertEquals("Invalid day", "", formattedDate);
		
		/* This is a boundary case for the 'any other years' partition */
		formattedDate = parseDate.parseInput("03/03/20154");
		assertEquals("Invalid year", "", formattedDate);
		
		/* This is a boundary case for the 'any other time format' partition */
		formattedDate = parseDate.parseInput("1144 PM");
		assertEquals("Invalid time format", "", formattedDate);
		
		/* This is a boundary case for the 'any other date format' partition */
		formattedDate = parseDate.parseInput("04:04:14");
		assertEquals("Invalid date format", "", formattedDate);
		
		/* This is a boundary case for the 'MAX_24_HOUR+1' partition */
		formattedDate = parseDate.parseInput("2459 040114");
		assertEquals("Invalid HH", "", formattedDate);
		
		/* This is a boundary case for the 'MAX_MINUTE+1' partition */
		formattedDate = parseDate.parseInput("2360 040114");
		assertEquals("Invalid mm", "", formattedDate);
		
		/* This is a boundary case for the 'MAX_12_HOUR+1' partition */
		formattedDate = parseDate.parseInput("13 AM 040114");
		assertEquals("Invalid h", "", formattedDate);
		
		/* This is a boundary case for the 'MAX_MONTH+1' partition */
		formattedDate = parseDate.parseInput("2359 041314");
		assertEquals("Invalid MM", "", formattedDate);
		
		/* This is a boundary case for the 'MAX_DAYOFMONTH+1' partition */
		formattedDate = parseDate.parseInput("320314");
		assertEquals("Invalid dd", "", formattedDate);
	
	}
	
	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth);
		String twoDigitHour = convertToTwoDigits(hour);
		String twoDigitMinute = convertToTwoDigits(minute);

		return year + twoDigitMonth + twoDigitDayOfMonth + twoDigitHour + twoDigitMinute; 
	}
	
	private String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth); 
          
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private String getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth); 
          
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	/**
	 * Get the expected day of week date based on today
	 */
	private String getNextDayOfWeekDate(int expectedDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		int numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), expectedDayOfWeek);
		cal.add(Calendar.DAY_OF_YEAR, numOfDay);
		
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth); 
          
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private int getNumOfDay(int currentDayOfWeek, int expectedDayOfWeek) {
		int numOfDay = 0;
		int newDay = 0;
		for(int i = 1; i <= DAYS_IN_WEEK; i++) {
			newDay = currentDayOfWeek + i; 
			if(newDay > DAYS_IN_WEEK) {
				newDay = (currentDayOfWeek + i) % DAYS_IN_WEEK;
			}
			if (newDay == expectedDayOfWeek) {
				numOfDay = i;
				break;
			}
		}
		
		return numOfDay;
	}
	
	private String convertToTwoDigits(int value){
		if(value < 10) {
			return "0" + value;
		} else {
			return "" + value;
		}
	}
	
	
	
}
