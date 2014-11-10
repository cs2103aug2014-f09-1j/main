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
import whatsupnext.ui.GUIOneWindow;
import whatsupnext.structure.enums.OPCODE;
import whatsupnext.structure.enums.Types;
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

	private class WhatsUpNextGUIStub extends GUIOneWindow {
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
		textDisplayUpcoming = (JTextPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		textDisplayFloating = (JTextPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayFloating");
		assertNotNull("Can't acess the main text display JTextPane", textDisplayMain);
		assertNotNull("Can't acess the upcoming task text area JTextPane", textDisplayUpcoming);
		assertNotNull("Can't acess the floating task text area JTextPane", textDisplayFloating);

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
	public void ViewAllTest() {
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
		
		textInput.setText("add floating task test 2");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 2.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("2: floating task test 2\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("view all");
		buttonEnter.doClick();
		
		assertEquals("\n1: floating task test\n\tDone.\n2: floating task test 2\n\tNot done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("2: floating task test 2\n\tNot done.", textDisplayFloating.getText());
	}

	@Test
	public void UpdateDescriptionTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("update 1 floating task with update test");
		buttonEnter.doClick();
		
		assertEquals("\nSuccessfully updated the description of task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task with update test\n\tNot done.", textDisplayFloating.getText());
	}

	@Test
	public void SearchEmptyTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("search xyz");
		buttonEnter.doClick();
		
		assertEquals("\nNo tasks are found.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
	}

	@Test
	public void SearchTextTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("search floating");
		buttonEnter.doClick();
		
		assertEquals("\n1: floating task test\n\tNot done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
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
		
		textInput.setText("undo");
		buttonEnter.doClick();
		
		assertEquals("\nCannot execute undo command.\n", textDisplayMain.getText());
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
		
		textInput.setText("redo");
		buttonEnter.doClick();
		
		assertEquals("\nCannot execute redo command.\n", textDisplayMain.getText());
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
	
	//@author A0111773L
	@Test
	public void FreeTest() {
		String formattedUpcomingTime1 = LogicUtilities.getFormattedTime(getTodayDateForFormat("0600")).replaceAll(currentYear, "");
		String formattedUpcomingTime2 = LogicUtilities.getFormattedTime(getTodayDateForFormat("1500")).replaceAll(currentYear, "");
		String formattedUpcomingTime3 = LogicUtilities.getFormattedTime(getTodayDateForFormat("1700")).replaceAll(currentYear, "");
		String formattedUpcomingTime4 = LogicUtilities.getFormattedTime(getTodayDateForFormat("2200")).replaceAll(currentYear, "");
		
		Logic logic = new Logic("guiTest");
		Task task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(Types.ADDTYPE.TIMEFRAME);
		task.setDescription("todayTask");
		task.setStartTime(getTodayDateForFormat("1500"));
		task.setEndTime(getTodayDateForFormat("1700"));
		logic.executeTask(task);
		
		textInput.setText("free 2");
		buttonEnter.doClick();
		
		assertEquals(
				"\nAvailable time slots:" +
						"\n" + formattedUpcomingTime1.trim() + " -" + formattedUpcomingTime2 +
						"\n" + formattedUpcomingTime3.trim() + " -" + formattedUpcomingTime4 + "\n", 
						textDisplayMain.getText());
		assertEquals("1: todayTask" +
				"\n\tStart Time:" + formattedUpcomingTime2 +
					"\n\tEnd Time:" + formattedUpcomingTime3 + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void FreeByTest() {
		String formattedTodayTime1 = LogicUtilities.getFormattedTime(getTodayDateForFormat("0600")).replaceAll(currentYear, "");
		String formattedTodayTime2 = LogicUtilities.getFormattedTime(getTodayDateForFormat("2200")).replaceAll(currentYear, "");
		String formattedTmlTime1 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("0600")).replaceAll(currentYear, "");
		String formattedTmlTime2 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1500")).replaceAll(currentYear, "");
		String formattedTmlTime3 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1700")).replaceAll(currentYear, "");
		String formattedTmlTime4 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2200")).replaceAll(currentYear, "");
		
		textInput.setText("add tmlTask from 1500 tml to 1700 tml");
		buttonEnter.doClick();

		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedTmlTime3 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("free 2 by tml");
		buttonEnter.doClick();

		assertEquals(
				"\nAvailable time slots:" +
						"\n" + formattedTodayTime1.trim() + " -" + formattedTodayTime2 +
						"\n" + formattedTmlTime1.trim() + " -" + formattedTmlTime2 +
						"\n" + formattedTmlTime3.trim() + " -" + formattedTmlTime4 + "\n", 
						textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void FreeOnTest() {
		String formattedUpcomingTime1 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("0600")).replaceAll(currentYear, "");
		String formattedUpcomingTime2 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1500")).replaceAll(currentYear, "");
		String formattedUpcomingTime3 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1700")).replaceAll(currentYear, "");
		String formattedUpcomingTime4 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2200")).replaceAll(currentYear, "");
		
		textInput.setText("add tmlTask from 1500 tomorrow to 1700 tomorrow");
		buttonEnter.doClick();

		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime3 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("free 2 on " + getTomorrowDate());
		buttonEnter.doClick();
		
		assertEquals(
				"\nAvailable time slots:" +
						"\n" + formattedUpcomingTime1.trim() + " -" + formattedUpcomingTime2 +
						"\n" + formattedUpcomingTime3.trim() + " -" + formattedUpcomingTime4 + "\n", 
						textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void FreeFromToTest() {
		String formattedTodayTime1 = LogicUtilities.getFormattedTime(getTodayDateForFormat("0600")).replaceAll(currentYear, "");
		String formattedTodayTime2 = LogicUtilities.getFormattedTime(getTodayDateForFormat("2200")).replaceAll(currentYear, "");
		String formattedTmlTime1 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("0600")).replaceAll(currentYear, "");
		String formattedTmlTime2 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1500")).replaceAll(currentYear, "");
		String formattedTmlTime3 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1700")).replaceAll(currentYear, "");
		String formattedTmlTime4 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2200")).replaceAll(currentYear, "");
		
		textInput.setText("add tmlTask from 1500 tml to 1700 tml");
		buttonEnter.doClick();

		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedTmlTime3 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("free 2 from now to " + getTomorrowDate());
		buttonEnter.doClick();

		assertEquals(
				"\nAvailable time slots:" +
						"\n" + formattedTodayTime1.trim() + " -" + formattedTodayTime2 +
						"\n" + formattedTmlTime1.trim() + " -" + formattedTmlTime2 +
						"\n" + formattedTmlTime3.trim() + " -" + formattedTmlTime4 + "\n", 
						textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}
	
	@Test
	public void UpdateByDeadlineDateTest() {
		textInput.setText("add update deadline test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: update deadline test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("update 1 by today");
		buttonEnter.doClick();
		
		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTodayDateForFormat("2359")).replaceAll(currentYear, "");
		
		assertEquals("\nSuccessfully updated the deadline of task 1.\n", textDisplayMain.getText());
		assertEquals("1: update deadline test" +
				"\n\tEnd Time:" + formattedUpcomingTime + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void UpdateFromToDateTest() {
		textInput.setText("add update timeframe test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: update timeframe test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("update 1 from 2359 today to 2359 today");
		buttonEnter.doClick();
		
		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTodayDateForFormat("2359")).replaceAll(currentYear, "");
		
		assertEquals("\nSuccessfully updated the time frame of task 1.\n", textDisplayMain.getText());
		assertEquals("1: update timeframe test" +
				"\n\tStart Time:" + formattedUpcomingTime
 				+"\n\tEnd Time:" + formattedUpcomingTime + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void DeleteTaskIdTest() {
		textInput.setText("add floating task test");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 1.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("1: floating task test\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("delete 1");
		buttonEnter.doClick();
		
		assertEquals("\nTask 1 is deleted.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}
	
	@Test
	public void DeleteDoneTest() {
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
		
		textInput.setText("delete done");
		buttonEnter.doClick();
		
		assertEquals("\n1 tasks are deleted.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}
	
	@Test
	public void DeleteDeadlineTest() {
		Logic logic = new Logic("guiTest");
		Task task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(Types.ADDTYPE.DEADLINE);
		task.setDescription("overdue");
		task.setEndTime("201301012359");
		logic.executeTask(task);
		
		textInput.setText("done 1");
		buttonEnter.doClick();
		
		assertEquals("\nTask 1 is successfully labeled as done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("delete deadline");
		buttonEnter.doClick();
		
		assertEquals("\n1 tasks are deleted.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
	}

	@Test
	public void DeleteDateTest() {
		textInput.setText("add tmlTask1 by 1800 tml");
		buttonEnter.doClick();
		String formattedUpcomingTime1 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1800")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime1 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("add tmlTask2 by 2000 tml");
		buttonEnter.doClick();
		String formattedUpcomingTime2 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2000")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedUpcomingTime2 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("done 1");
		buttonEnter.doClick();
		
		assertEquals("\nTask 1 is successfully labeled as done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("done 2");
		buttonEnter.doClick();
		
		assertEquals("\nTask 2 is successfully labeled as done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("delete " + getTomorrowDate());
		buttonEnter.doClick();
		
		assertEquals("\n2 tasks are deleted.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void DeleteFromToDateTest() {
		textInput.setText("add tmlTask by 2000 tml");
		buttonEnter.doClick();
		String formattedUpcomingTime1 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2000")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime1 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("add todayTask by 1800 today");
		buttonEnter.doClick();
		String formattedUpcomingTime2 = LogicUtilities.getFormattedTime(getTodayDateForFormat("1800")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedUpcomingTime2 + "\n",
				textDisplayMain.getText());
		assertEquals("2: todayTask" +
 				"\n\tEnd Time:" + formattedUpcomingTime2 + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("done 1");
		buttonEnter.doClick();
		
		assertEquals("\nTask 1 is successfully labeled as done.\n", textDisplayMain.getText());
		assertEquals("2: todayTask" +
 				"\n\tEnd Time:" + formattedUpcomingTime2 + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("done 2");
		buttonEnter.doClick();
		
		assertEquals("\nTask 2 is successfully labeled as done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
				
		textInput.setText("delete from " + getTodayDate() + " to " + getTomorrowDate());
		buttonEnter.doClick();
		
		assertEquals("\n2 tasks are deleted.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}
	
	@Test
	public void ViewTest() {
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
		
		textInput.setText("add floating task test 2");
		buttonEnter.doClick();

		assertEquals("\nSuccessfully added to task 2.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("2: floating task test 2\n\tNot done.", textDisplayFloating.getText());
		
		textInput.setText("view");
		buttonEnter.doClick();
		
		assertEquals("\n2: floating task test 2\n\tNot done.\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("2: floating task test 2\n\tNot done.", textDisplayFloating.getText());
	}
	
	@Test
	public void ViewNextTest() {
		textInput.setText("add next next by 2000 tml");
		buttonEnter.doClick();
		String formattedUpcomingTime = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2000")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("add next by 1800 tml");
		buttonEnter.doClick();
		formattedUpcomingTime = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1800")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedUpcomingTime + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("view next");
		buttonEnter.doClick();
		assertEquals(
				"\n2: next" +
						"\n\tEnd Time:" + formattedUpcomingTime + "\n\tNot done.\n", 
						textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void ViewOverdueTest() {
		Logic logic = new Logic("guiTest");
		Task task = new Task();
		task.setOpcode(OPCODE.ADD);
		task.setAddType(Types.ADDTYPE.DEADLINE);
		task.setDescription("overdue");
		task.setEndTime("201301012359");
		logic.executeTask(task);
		
		textInput.setText("view overdue");
		buttonEnter.doClick();
		assertEquals(
				"\n1: overdue" +
						"\n\tEnd Time: 2013 Jan 01 23:59\n\tNot done.\n", 
						textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		task = new Task();
		task.setOpcode(OPCODE.DELETE);
		task.setDeleteType(Types.DELETETYPE.ID);
		task.setTaskID("1");
		logic.executeTask(task);
		
		textInput.setText("view overdue");
		buttonEnter.doClick();
		assertEquals("\nNo tasks to display!\n", textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void ViewDateTest() {
		textInput.setText("add tmlTask1 by 1800 tml");
		buttonEnter.doClick();
		String formattedUpcomingTime1 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1800")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedUpcomingTime1 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("add tmlTask2 by 2000 tml");
		buttonEnter.doClick();
		String formattedUpcomingTime2 = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("2000")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedUpcomingTime2 + "\n",
				textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("view " + getTomorrowDate());
		buttonEnter.doClick();
		assertEquals(
				"\n1: tmlTask1" +
						"\n\tEnd Time:" + formattedUpcomingTime1 + "\n\tNot done.\n"
				+ "2: tmlTask2" +
						"\n\tEnd Time:" + formattedUpcomingTime2 + "\n\tNot done.\n", 
						textDisplayMain.getText());
		assertEquals("No tasks to display!", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}

	@Test
	public void ViewFromToDateTest() {
		textInput.setText("add todayTask by 2359 today");
		buttonEnter.doClick();
		String formattedTodayTime = LogicUtilities.getFormattedTime(getTodayDateForFormat("2359")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 1.\n\tDeadline:" + formattedTodayTime + "\n",
				textDisplayMain.getText());
		assertEquals("1: todayTask" +
				"\n\tEnd Time:" + formattedTodayTime + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("add tmlTask by 1800 tml");
		buttonEnter.doClick();
		String formattedTmlTime = LogicUtilities.getFormattedTime(getTomorrowDateForFormat("1800")).replaceAll(currentYear, "");
		assertEquals(
				"\nSuccessfully added to task 2.\n\tDeadline:" + formattedTmlTime + "\n",
				textDisplayMain.getText());
		assertEquals("1: todayTask" +
				"\n\tEnd Time:" + formattedTodayTime + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
		
		textInput.setText("view from " + getTodayDate() + " to " + getTomorrowDate());
		buttonEnter.doClick();
		assertEquals(
				"\n1: todayTask" +
						"\n\tEnd Time:" + formattedTodayTime + "\n\tNot done.\n"
				+ "2: tmlTask" +
						"\n\tEnd Time:" + formattedTmlTime + "\n\tNot done.\n", 
						textDisplayMain.getText());
		assertEquals("1: todayTask" +
				"\n\tEnd Time:" + formattedTodayTime + "\n\tNot done.", textDisplayUpcoming.getText());
		assertEquals("No tasks to display!", textDisplayFloating.getText());
	}
}
