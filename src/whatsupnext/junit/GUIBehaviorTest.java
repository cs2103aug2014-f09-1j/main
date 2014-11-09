//@author A0126730M
package whatsupnext.junit;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.logic.LogicUtilities;
import whatsupnext.storage.Storage;
import whatsupnext.ui.GUIMultipleWindows;
import whatsupnext.structure.enums.OPCODE;
import whatsupnext.structure.enums.Types.DELETETYPE;
import whatsupnext.structure.util.Task;

public class GUIBehaviorTest {
	private WhatsUpNextGUIStub gui;
	private JTextPane textDisplayMain;
	private JTextPane textDisplayUpcoming;
	private JTextPane textDisplayFloating;
	private JButton buttonEnter;
	private JButton buttonUpcoming;
	private JButton buttonFloating;
	private JTextField textInput;

	private String currentYear;

	private class WhatsUpNextGUIStub extends GUIMultipleWindows {
		private Logic logic;

		public WhatsUpNextGUIStub(String fileName) {
			super(fileName);
			logic = new Logic(fileName);
		}

		public void clearFile() {
			Task delete = new Task();
			delete.setOpcode(OPCODE.DELETE);
			delete.setDeleteType(DELETETYPE.ALL);
			logic.executeTask(delete);

			Storage storage = Storage.getInstance();
			try {
				storage.clearFile();
				storage.deleteFileVersions();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns in ddmmyyyy
	 */
	private String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return twoDigitDayOfMonth + twoDigitMonth + year;
	}

	private String getTodayDateForFormat(String time) {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return year + twoDigitMonth + twoDigitDayOfMonth + time;
	}

	/**
	 * Returns in ddmmyyyy
	 */
	private String getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return twoDigitDayOfMonth + twoDigitMonth + year;
	}
	
	private String getTomorrowDateForFormat(String time) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return year + twoDigitMonth + twoDigitDayOfMonth + time;
	}

	private String convertToTwoDigits(int possibleSingleDigit) {
		if (possibleSingleDigit < 10) {
			return "0" + possibleSingleDigit;
		} else {
			return "" + possibleSingleDigit;
		}
	}

	@Before
	public void setupGUI() {
		gui = new WhatsUpNextGUIStub("guiTest");

		Calendar cal = Calendar.getInstance();
		currentYear = Integer.toString(cal.get(Calendar.YEAR));
	}

	@Before
	public void initializeWidgets() {
		textDisplayMain = (JTextPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMain");
		textDisplayUpcoming = (JTextPane)GUITestUtils.getChildNamed(gui.getUpcomingFrame(), "textDisplayUpcoming");
		textDisplayFloating = (JTextPane)GUITestUtils.getChildNamed(gui.getFloatingFrame(), "textDisplayFloating");
		assertNotNull("Can't acess the main text display JTextPane", textDisplayMain);
		assertNotNull("Can't acess the upcoming task text area JTextPane", textDisplayUpcoming);
		assertNotNull("Can't acess the floating task text area JTextPane", textDisplayFloating);

		buttonEnter = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonEnter");
		buttonUpcoming = (JButton)GUITestUtils.getChildNamed(gui.getUpcomingFrame(), "buttonUpcoming");
		buttonFloating = (JButton)GUITestUtils.getChildNamed(gui.getFloatingFrame(), "buttonFloating");
		assertNotNull("Can't acess the main enter button JButton", buttonEnter);
		assertNotNull("Can't acess the upcoming task button JButton", buttonUpcoming);
		assertNotNull("Can't acess the floating task button JButton", buttonFloating);

		textInput = (JTextField)GUITestUtils.getChildNamed(gui.getMainFrame(), "textInput");
		assertNotNull("Can't acess the user CLI JTextField", textInput);
	}

	@After
	public void clearTestFile() {
		gui.clearFile();
	}

	@Test
	public void ClickUpcomingTasksButtonWhenEmptyTest() {
		ActionListener[] als = (ActionListener[])(buttonUpcoming.getListeners(ActionListener.class));
		assertEquals(1, als.length);

		buttonUpcoming.doClick();

		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
	}

	@Test
	public void ClickEnterButtonWhenEmptyTest() {
		ActionListener[] als = (ActionListener[])(buttonEnter.getListeners(ActionListener.class));
		assertEquals(1, als.length);

		buttonEnter.doClick();

		assertEquals("\nEmpty command\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
	}

	@Test
	public void PressEnterOnCLIWhenEmptyTest() {
		ActionListener[] als = (ActionListener[])(textInput.getListeners(ActionListener.class));
		assertEquals(1, als.length);

		textInput.postActionEvent();

		assertEquals("\nEmpty command\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
	}

	@Test
	public void UserInputTextFieldTest() {
		textInput.setText("test input text");
		assertEquals("test input text", textInput.getText());
	}

	@Test
	public void AddTaskTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
	}

	@Test
	public void AddTaskByTest() {
		textInput.setText("add byTaskTest by " + getTomorrowDate());
		buttonEnter.doClick();

		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2359")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());

		textInput.setText("add byTaskTest2 by " + getTodayDate());
		buttonEnter.doClick();

		formattedUpcomingTime = LogicUtilities.getFormattedTime(getTodayDateForFormat("2359")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedUpcomingTime + "\n",
				textDisplayMain.getText());
		assertEquals("2: byTaskTest2\n\tEnd Time:" + formattedUpcomingTime + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void AddTaskFromToDateTest() {
		textInput.setText(
				"add fromToTaskTest" +
						" from 8 am " + getTomorrowDate() +
						" to 8 pm " + getTomorrowDate());
		buttonEnter.doClick();

		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2000")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());

		textInput.setText(
				"add fromToTaskTest2" +
						" from 11:59 pm " + getTodayDate() +
						" to 11:59 pm " + getTodayDate());
		buttonEnter.doClick();

		formattedUpcomingTime = LogicUtilities.getFormattedTime(getTodayDateForFormat("2359")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedUpcomingTime + "\n",
				textDisplayMain.getText());
		assertEquals(
				"2: fromToTaskTest2" +
						"\n\tStart Time:" + formattedUpcomingTime +
						"\n\tEnd Time:" + formattedUpcomingTime + "\n\tNot done.",
						textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void AddTaskFromToDayTest() {
		
	}

	@Test
	public void ViewAllTest() {

	}

	@Test
	public void ViewNextTest() {

	}

	@Test
	public void ViewDayTest() {

	}

	@Test
	public void ViewDateTest() {

	}

	@Test
	public void ViewFromToDayTest() {

	}

	@Test
	public void ViewFromToDateTest() {

	}

	@Test
	public void UpdateDescriptionTest() {

	}

	@Test
	public void UpdateByDeadlineDayTest() {

	}

	@Test
	public void UpdateByDeadlineDateTest() {

	}

	@Test
	public void UpdateFromToDayTest() {

	}

	@Test
	public void UpdateFromToDateTest() {

	}

	@Test
	public void DeleteTaskIdTest() {

	}

	@Test
	public void DeleteDeadlineTest() {

	}

	@Test
	public void DeleteDateTest() {

	}

	@Test
	public void DeleteFromToDayTest() {

	}

	@Test
	public void DeleteFromToDateTest() {

	}

	@Test
	public void SearchEmptyTest() {

	}

	@Test
	public void SearchTextTest() {

	}

	@Test
	public void UndoTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("undo");
		buttonEnter.doClick();
		
		assertEquals("\nThe execution was canceled.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());

	}

	@Test
	public void RedoTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("undo");
		buttonEnter.doClick();
		
		assertEquals("\nThe execution was canceled.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("redo");
		buttonEnter.doClick();
		
		assertEquals("\nThe execution was re executed.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
	}

	@Test
	public void LabelDoneTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("done 1");
		buttonEnter.doClick();
		
		assertEquals("\nTask 1 is successfully labeled as done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}
}
