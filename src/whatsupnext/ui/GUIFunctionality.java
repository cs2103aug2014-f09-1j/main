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
	
	private JTextArea textDisplayMain = MainDisplayWidget.textDisplayMain;
	private JTextField textInput = WhatsUpNextGUI.textInput;
	private JTextArea textDisplayUpcoming = WhatsUpNextGUI.textDisplayUpcoming;
	
	private final ArrayList<String> STRINGS_CLEAR = new ArrayList<String>(Arrays.asList("clear", "Clear", "CLEAR", "clc"));
	
	private Logic logic;
	private String currentYear;
	
	private LinkedList<String> usedCommands;
	private ListIterator<String> commandIterator;
	private boolean upLastPressed;
	private boolean downLastPressed;

	GUIFunctionality() {
		logic = new Logic();
		
		DateFormat dateFormat = new SimpleDateFormat(" yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
		
		usedCommands = new LinkedList<String>();
		commandIterator = usedCommands.listIterator();
		upLastPressed = false;
		downLastPressed = false;
	}
	
	/**
	 * This method is activated as 'input command'
	 * It is called whenever user clicks the input button or presses the enter key
	 */
	 void clickEnter(){
		String commandInput = textInput.getText();
		String feedback;
		
		if (commandIsClear(commandInput)) {
			usedCommands.addFirst(commandInput);
			clearMainTextDisplay();
			
		} else {
			if (commandInput.trim().isEmpty()) {
				feedback = "Empty command";
			} else {
				try {
					usedCommands.addFirst(commandInput);
					Parser parser = new Parser(commandInput);
					Task currentTask = parser.parseInput();
					feedback = logic.executeTask(currentTask);
				} catch (Exception e) {
					feedback = e.getMessage();
				}
			}
			displayFeedback(feedback);
			clickUpcoming();
		}
		clearTextInput();
	}
	
	private boolean commandIsClear(String commandInput) {
		for (String stringClear : STRINGS_CLEAR) {
			if (stringClear.equals(commandInput.trim())){
				return true;
			}
		}
		return false;
	}

	/**
	 * pressUp and pressDown must check if the opposite key was pressed
	 * because the pointer must move twice if so
	 */
	 void pressUpFromCLI() {
		upLastPressed = true;
		if (commandIterator.hasNext()) {
			textInput.setText(commandIterator.next());
		}
		if (downLastPressed) {
			textInput.setText(commandIterator.next());
			downLastPressed = false;
		}
	}
	
	 void pressDownFromCLI() {
		downLastPressed = true;
		if (commandIterator.hasPrevious()) {
			textInput.setText(commandIterator.previous());
		}
		if (upLastPressed) {
			textInput.setText(commandIterator.previous());
			upLastPressed = false;
		}
	}

	/**
	 * Clears the user command prompt
	 */
	private void clearTextInput() {
		textInput.setText("");
	}
 
	/**
	 * Clears the main display area
	 */
	private void clearMainTextDisplay() {
		textDisplayMain.setText("---Please enter command below:\r\n");
	}

	/** 
	 * This method would display feedback message in main display area
	 */
	private void displayFeedback(String feedback) {
		feedback = feedback.replaceAll(currentYear, "");
		textDisplayMain.append("\n"+feedback+"\n");
	}
	
	/** 
	 * This method would display feedback message in main display area
	 */
	private void displayUpcomingFeedback(String feedback) {
		feedback = feedback.replaceAll(currentYear, "");
		textDisplayUpcoming.setText(feedback);
	}
	
	
	
	/**
	 * Callback function for when the user clicks the Upcoming Tasks button
	 * or when new execution has been activated
	 */
	void clickUpcoming() {
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

	void pressEnterFromCLI() {
		clickEnter();
		pressKeyFromCLI();
	}

	void pressKeyFromCLI() {
		commandIterator = usedCommands.listIterator();
	}

	void deleteRevisions() {
		logic.clearRevisionFiles();
	}

}
