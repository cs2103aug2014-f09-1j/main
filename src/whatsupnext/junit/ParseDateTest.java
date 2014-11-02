package whatsupnext.junit;

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
	/* This is a boundary case for the 'date time' partition */
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
	/* This is a boundary case for the 'time' partition */
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
		
		parseDate.setParsingStartTime(true);
		formattedDate = parseDate.parseInput("29-09-2014");
		assertEquals("Test dd-MM-yyyy 0000", "201409290000", formattedDate);
		
		parseDate.setParsingStartTime(false);
		formattedDate = parseDate.parseInput("29-09-2014");
		assertEquals("Test dd-MM-yyyy 2359", "201409292359", formattedDate);
	}
	
	@Test
	/* This is a boundary case for the 'time day' partition */
	public void testParseTimeDay() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("0102 today");
		assertEquals("HHmm today", getToday()+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 tomorrow");
		assertEquals("HHmm tomorrow", getTomorrow()+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 sunday");
		assertEquals("HHmm sunday", getTodayToNextDayOfWeek(Calendar.SUNDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 monday");
		assertEquals("HHmm monday", getTodayToNextDayOfWeek(Calendar.MONDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 tuesday");
		assertEquals("HHmm tuesday", getTodayToNextDayOfWeek(Calendar.TUESDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 wednesday");
		assertEquals("HHmm wednesday", getTodayToNextDayOfWeek(Calendar.WEDNESDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 thursday");
		assertEquals("HHmm thursday", getTodayToNextDayOfWeek(Calendar.THURSDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 friday");
		assertEquals("HHMm friday", getTodayToNextDayOfWeek(Calendar.FRIDAY)+"0102", formattedDate);
		
		formattedDate = parseDate.parseInput("0102 saturday");
		assertEquals("HHMm saturday", getTodayToNextDayOfWeek(Calendar.SATURDAY)+"0102", formattedDate);
		
		
	}
	
	@Test
	/* This is a boundary case for the 'day time' partition */
	public void testParseDayTime() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("Today 2222");
		assertEquals("Today HHmm", getToday()+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Tml 2222");
		assertEquals("Tml HHmm", getTomorrow()+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Sun 2222");
		assertEquals("Sun HHmm ", getTodayToNextDayOfWeek(Calendar.SUNDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Mon 2222");
		assertEquals("Mon HHmm", getTodayToNextDayOfWeek(Calendar.MONDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Tue 2222");
		assertEquals("Tue HHmm", getTodayToNextDayOfWeek(Calendar.TUESDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Wed 2222");
		assertEquals("Wed HHmm", getTodayToNextDayOfWeek(Calendar.WEDNESDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Thu 2222");
		assertEquals("Thu HHmm", getTodayToNextDayOfWeek(Calendar.THURSDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Fri 2222");
		assertEquals("Fri HHmm", getTodayToNextDayOfWeek(Calendar.FRIDAY)+"2222", formattedDate);
		
		formattedDate = parseDate.parseInput("Sat 2222");
		assertEquals("Sat HHmm", getTodayToNextDayOfWeek(Calendar.SATURDAY)+"2222", formattedDate);
		
	}
	
	@Test
	/* This is a boundary case for the 'day' partition */
	public void testParseStandaloneDay() {
		ParseDate parseDate = new ParseDate();
		String formattedDate = "";
		
		formattedDate = parseDate.parseInput("Today");
		assertEquals("Today", getToday()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("today");
		assertEquals("today", getToday()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Tomorrow");
		assertEquals("Tomorrow", getTomorrow()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("tml");
		assertEquals("tml", getTomorrow()+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Sunday");
		assertEquals("Sunday", getTodayToNextDayOfWeek(Calendar.SUNDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("sun");
		assertEquals("sun", getTodayToNextDayOfWeek(Calendar.SUNDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Monday");
		assertEquals("Monday", getTodayToNextDayOfWeek(Calendar.MONDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("mon");
		assertEquals("mon", getTodayToNextDayOfWeek(Calendar.MONDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Tuesday");
		assertEquals("Tuesday", getTodayToNextDayOfWeek(Calendar.TUESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("tue");
		assertEquals("tue", getTodayToNextDayOfWeek(Calendar.TUESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Wednesday");
		assertEquals("Wednesday", getTodayToNextDayOfWeek(Calendar.WEDNESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("wed");
		assertEquals("wed", getTodayToNextDayOfWeek(Calendar.WEDNESDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Thursday");
		assertEquals("Thursday", getTodayToNextDayOfWeek(Calendar.THURSDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("thu");
		assertEquals("thu", getTodayToNextDayOfWeek(Calendar.THURSDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Friday");
		assertEquals("Friday", getTodayToNextDayOfWeek(Calendar.FRIDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("fri");
		assertEquals("fri", getTodayToNextDayOfWeek(Calendar.FRIDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("Saturday");
		assertEquals("Saturday", getTodayToNextDayOfWeek(Calendar.SATURDAY)+"2359", formattedDate);
		
		formattedDate = parseDate.parseInput("sat");
		assertEquals("sat", getTodayToNextDayOfWeek(Calendar.SATURDAY)+"2359", formattedDate);
		
		parseDate.setParsingStartTime(true);
		formattedDate = parseDate.parseInput("today");
		assertEquals("Today 0000", getToday()+"0000", formattedDate);
		
		parseDate.setParsingStartTime(false);
		formattedDate = parseDate.parseInput("today");
		assertEquals("Today 2359", getToday()+"2359", formattedDate);
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
		assertEquals("Invalid", "", formattedDate);
		
		/* This is a boundary case for the 'MAX_DAYOFMONTH+1' partition */
		formattedDate = parseDate.parseInput("320314");
		assertEquals("Invalid", "", formattedDate);
	}
	
	public String getCurrentTime() {
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
	
	private String getToday() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth); 
          
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private String getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth); 
          
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private String getTodayToNextDayOfWeek(int expectedDay) {
		Calendar cal = Calendar.getInstance();
		int numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), expectedDay);
		cal.add(Calendar.DAY_OF_YEAR, numOfDay);
		
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth); 
          
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private int getNumOfDay(int currentDay, int expectedDay) {
		int numOfDay = 0;
		int newDay = 0;
		for(int i = 1; i <= DAYS_IN_WEEK; i++) {
			newDay = currentDay + i; 
			if(newDay > DAYS_IN_WEEK) {
				newDay = (currentDay + i) % DAYS_IN_WEEK;
			}
			if (newDay == expectedDay) {
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
