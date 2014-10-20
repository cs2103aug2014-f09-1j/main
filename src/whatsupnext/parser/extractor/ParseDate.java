package whatsupnext.parser.extractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ParseDate {
	
	private final String FORMAT_LAST_MINUTE = "HHmm";
	private final String FORMAT_TODAY = "ddMMyyyy";
	
	private final ArrayList<String> FORMATS_TIME = new ArrayList<String>(Arrays.asList("HHmm",
																						"HH:mm",
																						"h:mm a",
																						"h a"));
	private final ArrayList<String> FORMATS_DATE = new ArrayList<String>(Arrays.asList("ddMMyy", 
																						"ddMMyyyy", 
																						"dd/MM/yy", 
																						"dd/MM/yyyy", 
																						"dd-MM-yy", 
																						"dd-MM-yyyy"));
	private final ArrayList<String> ALIASES_TOMORROW = new ArrayList<String>(Arrays.asList("tomorrow",
																						"tml"));
	
	private final String LAST_MINUTE = "2359";
	private final String SINGLE_QUOTE = "'";
	
	private ArrayList<String> allTimeDateFormats;
	private ArrayList<String> allDayFormats;
	
	public ParseDate(){
		allTimeDateFormats = getAllTimeDateFormats();
		allDayFormats = getAllDayFormats();
	}
	
	public String parseInput(String input) {
		String formattedDate = "";
		formattedDate = parseAllTimeDateFormats(input);
		if(formattedDate.isEmpty()){
			formattedDate = parseAllDayFormats(input);
		}
		return formattedDate;
	}
	
	private ArrayList<String> getAllTimeDateFormats() {
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
	
	private ArrayList<String> getAllDayFormats() {
		ArrayList<String> allFormats = new ArrayList<String>();
		for (String time : FORMATS_TIME) {
			for (String day : ALIASES_TOMORROW){
				allFormats.add(time + " " + SINGLE_QUOTE + day + SINGLE_QUOTE);
			}
		}
		for (String day : ALIASES_TOMORROW){
			for (String time : FORMATS_TIME) {
				allFormats.add(SINGLE_QUOTE + day + SINGLE_QUOTE + " " + time);
			}
		}
		/*for (String dayOfWeek: ALIASES_TOMORROW){
			allFormats.add(dayOfWeek);
		}*/
		return allFormats;
	}
	
	private String parseAllTimeDateFormats(String input) {
		String formattedDate = "";
		String formattedInput = "";
		SimpleDateFormat formatter = null;
		for (String format : allTimeDateFormats) {
			try {
				formattedInput = input;
				formatter = new SimpleDateFormat(format);
				if (FORMATS_TIME.contains(format)) {
					formatter = new SimpleDateFormat(format + " " + FORMAT_TODAY);
					formattedInput = formattedInput + " " + getToday();
				}
				if (FORMATS_DATE.contains(format)) {
					formatter = new SimpleDateFormat(format + " " + FORMAT_LAST_MINUTE);
					formattedInput = formattedInput + " " + LAST_MINUTE;
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
		for (String format : allDayFormats) {
			try {
				formattedInput = input + " " + getToday();
				formatter = new SimpleDateFormat(format + " " + FORMAT_TODAY);
				if (ALIASES_TOMORROW.contains(input)){
					formatter = new SimpleDateFormat(FORMAT_TODAY + " " + FORMAT_LAST_MINUTE);
					formattedInput = getToday() + " " + LAST_MINUTE;
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
	

	private String getToday() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigitMonth(month);
		String twoDigitDayOfMonth = convertToTwoDigitDayOfMonth(dayOfMonth); 
          
		return twoDigitDayOfMonth + twoDigitMonth + year;
	}
	
	private Calendar setNewDay(Calendar cal, String input){
		for (String tomorrow: ALIASES_TOMORROW){
			if(input.contains(tomorrow)){
				cal.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		
		return cal;
	}

	private String formatDate(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String twoDigitMonth = convertToTwoDigitMonth(month);
		String twoDigitDayOfMonth = convertToTwoDigitDayOfMonth(dayOfMonth);
		String twoDigitHour = convertToTwoDigitHour(hour);
		String twoDigitMinute = convertToTwoDigitMinute(minute);

		return year + twoDigitMonth + twoDigitDayOfMonth + twoDigitHour + twoDigitMinute; 
	}
	
	private String convertToTwoDigitMonth(int month){
		if (month < 10) {
			return "0" + month;
		} else {
			return "" + month;
		}
	}
	
	private String convertToTwoDigitDayOfMonth(int dayOfMonth){
		if (dayOfMonth < 10) {
			return "0" + dayOfMonth;
		} else {
			return "" + dayOfMonth;
		}
	}
	
	private String convertToTwoDigitHour(int hour){
		if (hour < 10) {
			return "0" + hour;
		} else {
			return "" + hour;
		}
	}
	
	private String convertToTwoDigitMinute(int minute){
		if (minute < 10) {
			return "0" + minute;
		} else {
			return "" + minute;
		}
	}
	
	/**
	 * This function reports the date string of today
	 * In format of : yyyymmdd
	 * @return
	 */
	public String getTodayDateString() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = convertToTwoDigitMonth(month);
		String twoDigitDayOfMonth = convertToTwoDigitDayOfMonth(dayOfMonth);       
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	/**
	 * This function reports the date string of today
	 * In format of : yyyymmddhhss
	 * @return
	 */
	public String getTodayDateTimeString() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String twoDigitMonth = convertToTwoDigitMonth(month);
		String twoDigitDayOfMonth = convertToTwoDigitDayOfMonth(dayOfMonth);
		String twoDigitHour = convertToTwoDigitHour(hour);
		String twoDigitMinute = convertToTwoDigitMinute(minute);

		return year + twoDigitMonth + twoDigitDayOfMonth + twoDigitHour + twoDigitMinute; 
	}

}


