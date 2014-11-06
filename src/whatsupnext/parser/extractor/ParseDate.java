package whatsupnext.parser.extractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ParseDate {
	
	private final String FORMAT_LAST_MINUTE = "HHmm";
	private final String FORMAT_TODAY = "yyyyMMdd";
	private final ArrayList<String> FORMATS_TIME = new ArrayList<String>(Arrays.asList("HHmm", "HH:mm",
																						"h:mm a", "h a"));
	private final ArrayList<String> FORMATS_DATE = new ArrayList<String>(Arrays.asList("ddMMyy", "ddMMyyyy", 
																						"dd/MM/yy", "dd/MM/yyyy", 
																						"dd-MM-yy", "dd-MM-yyyy"));
	private final ArrayList<String> ALIASES_TODAY = new ArrayList<String>(Arrays.asList("Today", "today"));
	private final ArrayList<String> ALIASES_TOMORROW = new ArrayList<String>(Arrays.asList("tomorrow", "tml"));
	private final ArrayList<String> ALIASES_SUNDAY = new ArrayList<String>(Arrays.asList("sunday", "sun"));
	private final ArrayList<String> ALIASES_MONDAY = new ArrayList<String>(Arrays.asList("monday", "mon"));
	private final ArrayList<String> ALIASES_TUESDAY = new ArrayList<String>(Arrays.asList("tuesday", "tue"));
	private final ArrayList<String> ALIASES_WEDNESDAY = new ArrayList<String>(Arrays.asList("wednesday", "wed"));
	private final ArrayList<String> ALIASES_THURSDAY = new ArrayList<String>(Arrays.asList("thursday", "thu"));
	private final ArrayList<String> ALIASES_FRIDAY = new ArrayList<String>(Arrays.asList("friday", "fri"));
	private final ArrayList<String> ALIASES_SATURDAY = new ArrayList<String>(Arrays.asList("saturday", "sat"));
	
	private final String NOW = "now";
	private final String FIRST_MINUTE = "0000";
	private final String LAST_MINUTE = "2359";
	private final String SINGLE_QUOTE = "'";
	private final int DAYS_IN_WEEK = 7;
	
	private ArrayList<String> listOfTimeDateFormats;
	private ArrayList<String> listOfTimeDayFormats;
	private ArrayList<String> listOfAliasesDay;
	
	private boolean isParsingStartTime;
	
	public ParseDate(){
		listOfTimeDateFormats = getTimeDateFormats();
		listOfAliasesDay = getAliasesDay();
		listOfTimeDayFormats = getDayFormats();
		isParsingStartTime = false;
	}
	
	public String parseInput(String input) {
		String formattedDate = "";
		input = input.toLowerCase();
		
		if(input.equalsIgnoreCase(NOW)){
			formattedDate = getCurrentTime();
		}
		if(formattedDate.isEmpty()){
			formattedDate = parseAllTimeDateFormats(input);
		}
		if(formattedDate.isEmpty()){
			formattedDate = parseAllDayFormats(input);
		}
		return formattedDate;
	}
	
	public void setParsingStartTime(boolean isParsingStartTime) {
		this.isParsingStartTime = isParsingStartTime;
	}
	
	/**
	 * Returns a 12-length string of current time 
	 * @return In format of: yyyyMMddHHmm
	 */
	public String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		String formattedDate = formatDate(cal);
		return formattedDate; 
	}
	
	/**
	 * Returns a 8-length string of today date 
	 * @return In format of: yyyyMMdd
	 * @return
	 */
	public String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth);       
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	private ArrayList<String> getTimeDateFormats() {
		ArrayList<String> allFormats = new ArrayList<String>();
		for (String time : FORMATS_TIME) {
			for (String date : FORMATS_DATE) {
				allFormats.add(time + " " + date);
			}
		}
		for (String date : FORMATS_DATE) {
			for (String time : FORMATS_TIME) {
				allFormats.add(date + " " + time);
			}
		}
		for (String time : FORMATS_TIME) {
			allFormats.add(time);
		}
		for (String date : FORMATS_DATE) {
			allFormats.add(date);
		}
		return allFormats;
	}
	
	private ArrayList<String> getAliasesDay(){
		ArrayList<String> allAliasesDay = new ArrayList<String>();
		allAliasesDay.addAll(ALIASES_TODAY);
		allAliasesDay.addAll(ALIASES_TOMORROW);
		allAliasesDay.addAll(ALIASES_SUNDAY);
		allAliasesDay.addAll(ALIASES_MONDAY);
		allAliasesDay.addAll(ALIASES_TUESDAY);
		allAliasesDay.addAll(ALIASES_WEDNESDAY);
		allAliasesDay.addAll(ALIASES_THURSDAY);
		allAliasesDay.addAll(ALIASES_FRIDAY);
		allAliasesDay.addAll(ALIASES_SATURDAY);
		
		return allAliasesDay;
	}
	private ArrayList<String> getDayFormats() {
		ArrayList<String> allFormats = new ArrayList<String>();
		for (String time : FORMATS_TIME) {
			for (String day : listOfAliasesDay){
				allFormats.add(time + " " + SINGLE_QUOTE + day + SINGLE_QUOTE);
			}
		}
		for (String day : listOfAliasesDay){
			for (String time : FORMATS_TIME) {
				allFormats.add(SINGLE_QUOTE + day + SINGLE_QUOTE + " " + time);
			}
		}
		
		return allFormats;
	}
	
	private String parseAllTimeDateFormats(String input) {
		String formattedDate = "";
		String formattedInput = "";
		SimpleDateFormat formatter = null;
		for (String format : listOfTimeDateFormats) {
			try {
				formattedInput = input;
				formatter = new SimpleDateFormat(format);
				if (FORMATS_TIME.contains(format)) {
					formatter = new SimpleDateFormat(format + " " + FORMAT_TODAY);
					formattedInput = formattedInput + " " + getTodayDate();
				}
				if (FORMATS_DATE.contains(format)) {
					formatter = new SimpleDateFormat(format + " " + FORMAT_LAST_MINUTE);
					if(isParsingStartTime) {
						formattedInput = formattedInput + " " + FIRST_MINUTE;
					} else {
						formattedInput = formattedInput + " " + LAST_MINUTE;
					}
				}
				formatter.setLenient(false);
				Calendar cal = Calendar.getInstance();
				cal.setTime(formatter.parse(formattedInput));
				formattedDate = formatDate(cal);
				break;
			} catch (ParseException e) {
				//Do nothing. Continue with other formats
			}
		}

		return formattedDate;
	}
	
	private String parseAllDayFormats(String input) {
		String formattedDate = "";
		String formattedInput = "";
		SimpleDateFormat formatter = null;
		for (String format : listOfTimeDayFormats) {
			try {
				formattedInput = input + " " + getTodayDate();
				formatter = new SimpleDateFormat(format + " " + FORMAT_TODAY);
				if (listOfAliasesDay.contains(input)){
					formatter = new SimpleDateFormat(FORMAT_TODAY + " " + FORMAT_LAST_MINUTE);
					if(isParsingStartTime) {
						formattedInput = getTodayDate() + " " + FIRST_MINUTE;
					} else {
						formattedInput = getTodayDate() + " " + LAST_MINUTE;
					}					
				}
				formatter.setLenient(false);
				Calendar cal = Calendar.getInstance();
				cal.setTime(formatter.parse(formattedInput));
				cal = setNewDay(cal, input);
				formattedDate = formatDate(cal);
				break;
			} catch (ParseException e) {
				//Do nothing. Continue with other formats
			}
		}
		
		return formattedDate;
	}
	
	private Calendar setNewDay(Calendar cal, String input){
		int numOfDay = 0;
		for (String today : ALIASES_TODAY) {
			if(input.contains(today)) {
				cal.add(Calendar.DAY_OF_YEAR, 0);
				return cal;
			}
		}
		for (String tomorrow : ALIASES_TOMORROW) {
			if(input.contains(tomorrow)) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
				return cal;
			}
		}
		for (String sunday : ALIASES_SUNDAY) {
			if(input.contains(sunday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.SUNDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		for (String monday : ALIASES_MONDAY) {
			if(input.contains(monday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.MONDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		for (String tuesday : ALIASES_TUESDAY) {
			if(input.contains(tuesday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.TUESDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		for (String wednesday : ALIASES_WEDNESDAY) {
			if(input.contains(wednesday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.WEDNESDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		for (String thursday : ALIASES_THURSDAY) {
			if(input.contains(thursday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.THURSDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		for (String friday : ALIASES_FRIDAY) {
			if(input.contains(friday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.FRIDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		for (String saturday : ALIASES_SATURDAY) {
			if(input.contains(saturday)) {
				numOfDay = getNumOfDay(cal.get(Calendar.DAY_OF_WEEK), Calendar.SATURDAY);
				cal.add(Calendar.DAY_OF_YEAR, numOfDay);
				return cal;
			}
		}
		
		return cal;
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
	
	/**
	 * Returns a 12-length string based on calendar
	 * @param cal Calendar to be formatted 
	 * @return In format of: yyyyMMddHHmm
	 */
	private String formatDate(Calendar cal) {
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
	
	private String convertToTwoDigits(int value){
		if(value < 10) {
			return "0" + value;
		} else {
			return "" + value;
		}
	}
	
	
	/**
	 * This function reports the date string of the provided Calendar object
	 * In format of : yyyymmdd
	 * @return
	 */
	public String getDateString(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigits(month);
		String twoDigitDayOfMonth = convertToTwoDigits(dayOfMonth);       
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}

}