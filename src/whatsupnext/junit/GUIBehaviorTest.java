package whatsupnext.junit;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.logic.LogicUtilities;
import whatsupnext.storage.Storage;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.ui.WhatsUpNextGUI;

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

	private class WhatsUpNextGUIStub extends WhatsUpNextGUI {
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

	private String getTodayDateForFormat() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String twoDigitMonth = convertToTwoDigits(cal.get(Calendar.MONTH) + 1);
		String twoDigitDayOfMonth = convertToTwoDigits(cal.get(Calendar.DAY_OF_MONTH));     
		return year + twoDigitMonth + twoDigitDayOfMonth;
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
		textDisplayUpcoming = (JTextPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		textDisplayFloating = (JTextPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayFloating");
		assertNotNull("Can't acess the main text display JTextField", textDisplayMain);
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		assertNotNull("Can't acess the floating task text area JTextArea", textDisplayFloating);

		buttonEnter = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonEnter");
		buttonUpcoming = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonUpcoming");
		buttonFloating = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonFloating");
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

		assertEquals("---Please enter command below:\r\n\nEmpty command\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
	}

	@Test
	public void PressEnterOnCLIWhenEmptyTest() {
		ActionListener[] als = (ActionListener[])(textInput.getListeners(ActionListener.class));
		assertEquals(1, als.length);

		textInput.postActionEvent();

		assertEquals("---Please enter command below:\r\n\nEmpty command\n", textDisplayMain.getText());
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

		assertEquals("---Please enter command below:\r\n\nA task is successfully added.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\nNot done.", textDisplayFloating.getText());
	}

	@Test
	public void AddTaskByTest() {
		textInput.setText("add byTaskTest by " + getTomorrowDate());
		buttonEnter.doClick();

		assertEquals(
				"---Please enter command below:\r\n\nA task is successfully added.\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());

		textInput.setText("add byTaskTest2 by " + getTodayDate());
		buttonEnter.doClick();

		assertEquals(
				"---Please enter command below:\r\n\nA task is successfully added.\n\nA task is successfully added.\n",
				textDisplayMain.getText());
		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTodayDateForFormat()).replaceAll(currentYear, "");
		assertEquals("2: byTaskTest2\n\tEnd Time:" + formattedUpcomingTime + " 23:59\nNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void AddTaskFromToDateTest() {
		textInput.setText(
				"add fromToTaskTest" +
						" from 8 am " + getTomorrowDate() +
						" to 8 pm " + getTomorrowDate());
		buttonEnter.doClick();

		assertEquals(
				"---Please enter command below:\r\n\nA task is successfully added.\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());

		textInput.setText(
				"add fromToTaskTest2" +
						" from 11:59 pm " + getTodayDate() +
						" to 11:59 pm " + getTodayDate());
		buttonEnter.doClick();

		assertEquals(
				"---Please enter command below:\r\n\nA task is successfully added.\n\nA task is successfully added.\n",
				textDisplayMain.getText());
		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTodayDateForFormat()).replaceAll(currentYear, "");
		assertEquals(
				"2: fromToTaskTest2" +
						"\n\tStart Time:" + formattedUpcomingTime + " 23:59" +
						"\n\tEnd Time:" + formattedUpcomingTime + " 23:59\nNot done.",
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
	public void UndoAddTest() {

	}

	@Test
	public void UndoDeleteTest() {

	}

	@Test
	public void UndoUpdateTest() {

	}

	@Test
	public void UndoLabelTest() {

	}

	@Test
	public void UndoRedoTest() {

	}

	@Test
	public void RedoAddTest() {

	}

	@Test
	public void RedoDeleteTest() {

	}

	@Test
	public void RedoUpdateTest() {

	}

	@Test
	public void RedoLabelTest() {

	}

	@Test
	public void RedoUndoTest() {

	}

	@Test
	public void LabelDoneTest() {

	}

	@Test
	public void LabelCategoryTest() {

	}
}
