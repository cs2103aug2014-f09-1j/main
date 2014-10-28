package whatsupnext.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import whatsupnext.logic.Logic;
import whatsupnext.parser.api.Parser;
import whatsupnext.parser.extractor.ParseDate;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

public class GUIFunctionality {
	
	private static JTextArea textDisplayUpcoming = WhatsUpNextGUI.textDisplayUpcoming;
	
	static final Logic logic = new Logic();
	private static String currentYear;
	

	GUIFunctionality() {
		DateFormat dateFormat = new SimpleDateFormat(" yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
	}
	
	/** 
	 * This method would display feedback message in main display area
	 */
	private static void displayUpcomingFeedback(String feedback) {
		feedback = feedback.replaceAll(currentYear, "");
		textDisplayUpcoming.setText(feedback);
	}
	
	
	
	/**
	 * Callback function for when the user clicks the Upcoming Tasks button
	 * or when new execution has been activated
	 */
	static void clickUpcoming() {
		// Get a list of most recent tasks and display
		Task task = generateTaskForUpcoming();
		
		String feedback;
		try {
			feedback = logic.executeTask(task);
		} catch (Exception e) {
			feedback = e.getMessage();
		}
		
		displayUpcomingFeedback(feedback);
	}

	/**
	 *  view all tasks within today
	 * @return	the task that holds the view time frame task for upcoming
	 */
	private static Task generateTaskForUpcoming() {
		ParseDate parseDate = new ParseDate();
		Task task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.TIMEFRAME);
		task.setStartTime(parseDate.getTodayDateString() + "0000");
		task.setEndTime(parseDate.getTodayDateString() + "2359");
		return task;
	}

	void deleteRevisions() {
		logic.clearRevisionFiles();
	}

}
