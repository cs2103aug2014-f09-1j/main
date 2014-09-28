package Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class ParseDate {
	
	private final String[] FORMATS_TIME = {"HHmm"};
	private final String[] FORMATS_DATE = {"ddMMyy", "ddMMyyyy", "dd/MM/yy", "dd/MM/yyyy", "dd-MM-yy", "dd-MM-yyyy"};
	
	public String parseInput(String input) {
		Vector<String> allFormats = getAllFormats();
		String formattedDate = getFormattedDate(input, allFormats);
		return formattedDate;
	}
	
	private Vector<String> getAllFormats() {
		Vector<String> allFormats = new Vector<String>();
		for (String time : FORMATS_TIME) {
			for (String date : FORMATS_DATE) {
				allFormats.add(time + " " + date);
			}
		}
		for (String date : FORMATS_DATE ) {
			for (String time : FORMATS_TIME) {
				allFormats.add(date + " " + time);
			}
		}
		
		return allFormats;
	}
	
	private String getFormattedDate(String input, Vector<String> allFormats) {
		String formattedDate = "";
		
		for (String format : allFormats) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				formatter.setLenient(false);
				Calendar cal = Calendar.getInstance();
				cal.setTime(formatter.parse(input));
				formattedDate = formatDate(cal);
				break;
			} catch (ParseException e) {
				//Do nothing. Continue with other formats
			}
		}
		
		return formattedDate;
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
