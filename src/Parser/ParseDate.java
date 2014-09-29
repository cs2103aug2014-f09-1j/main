package Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ParseDate {
	
	private final String FORMAT_LAST_MINUTE = "HHmm";
	private final String FORMAT_TODAY = "ddMMyyyy";
	private final ArrayList<String> FORMATS_TIME = new ArrayList<String>(Arrays.asList("HHmm"));
	private final ArrayList<String> FORMATS_DATE = new ArrayList<String>(Arrays.asList("ddMMyy", 
																						"ddMMyyyy", 
																						"dd/MM/yy", 
																						"dd/MM/yyyy", 
																						"dd-MM-yy", 
																						"dd-MM-yyyy"));
	private final String LAST_MINUTE = "2359";
	
	public String parseInput(String input) {
		ArrayList<String> allFormats = getAllFormats();
		String formattedDate = getFormattedDate(input, allFormats);
		return formattedDate;
	}
	
	private ArrayList<String> getAllFormats() {
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
	
	private String getFormattedDate(String input, ArrayList<String> allFormats) {
		String formattedDate = "";
		String formattedInput = "";
		for (String format : allFormats) {
			try {
				formattedInput = input;
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				if(FORMATS_TIME.contains(format)){
					formatter = new SimpleDateFormat(format+" "+FORMAT_TODAY);
					formattedInput = formattedInput + " " + getToday();
				}
				if(FORMATS_DATE.contains(format)){
					formatter = new SimpleDateFormat(format+" "+FORMAT_LAST_MINUTE);
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
         
		return twoDigitDayOfMonth + twoDigitMonth + year;
	}

	private String formatDate(Calendar cal) {
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String twoDigitMonth = "";
		String twoDigitDayOfMonth = "";
		String twoDigitHour = "";
		String twoDigitMinute = "";
		
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
		if (hour < 10) {
			twoDigitHour = "0" + hour;
		} else {
			twoDigitHour = "" + hour;
		}
		if (minute < 10) {
			twoDigitMinute = "0" + minute;
		} else {
			twoDigitMinute = "" + minute;
		}
		
		return year + twoDigitMonth + twoDigitDayOfMonth + twoDigitHour + twoDigitMinute; 
	}
}
