package whatsupnext.junit;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import whatsupnext.ui.WhatsUpNextGUI;

public class GUIBehaviorTest {
	private WhatsUpNextGUI gui;
	
	@Before
	public void setUpGUI() {
		gui = new WhatsUpNextGUI();
	}

	@Test
	public void ClickUpcomingTasksButtonWhenEmptyTest() {
		JTextArea textDisplayUpcoming = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		JButton buttonUpcoming = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonUpcoming");
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		assertNotNull("Can't acess the upcoming task button JButton", buttonUpcoming);
		
		ActionListener[] als = (ActionListener[])(buttonUpcoming.getListeners(ActionListener.class));
		assertEquals(1, als.length);
		
		buttonUpcoming.doClick();
		
		assertEquals("No saved tasks!", textDisplayUpcoming.getText());
	}
	
	@Test
	public void ClickEnterButtonWhenEmptyTest() {
		JTextArea textDisplayMain = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMain");
		JButton buttonEnter = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonEnter");
		JTextArea textDisplayUpcoming = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		assertNotNull("Can't acess the main text display JTextField", textDisplayMain);
		assertNotNull("Can't acess the main enter button JButton", buttonEnter);
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		
		ActionListener[] als = (ActionListener[])(buttonEnter.getListeners(ActionListener.class));
		assertEquals(1, als.length);
		
		buttonEnter.doClick();
		
		assertEquals("---Please enter command below:\r\n\nEmpty command", textDisplayMain.getText());
		assertEquals("No saved tasks!", textDisplayUpcoming.getText());
	}
	
	@Test
	public void PressEnterOnCLIWhenEmptyTest() {
		JTextArea textDisplayMain = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMain");
		JTextField textInput = (JTextField)GUITestUtils.getChildNamed(gui.getMainFrame(), "textInput");
		JTextArea textDisplayUpcoming = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		assertNotNull("Can't acess the main text display JTextField", textDisplayMain);
		assertNotNull("Can't acess the user CLI JTextField", textInput);
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		
		ActionListener[] als = (ActionListener[])(textInput.getListeners(ActionListener.class));
		assertEquals(1, als.length);
		
		textInput.postActionEvent();
		
		assertEquals("---Please enter command below:\r\n\nEmpty command", textDisplayMain.getText());
		assertEquals("No saved tasks!", textDisplayUpcoming.getText());
	}
	
	@Test
	public void UserInputTextFieldTest() {
		JTextField textInput = (JTextField)GUITestUtils.getChildNamed(gui.getMainFrame(), "textInput");
		assertNotNull("Can't acess the text input JTextField", textInput);
		
		textInput.setText("test input text");
		
		assertEquals("test input text", textInput.getText());
	}
	
	@Test
	public void AddTaskTest() {
		
	}
	
	@Test
	public void AddTaskByTest() {
		
	}
	
	@Test
	public void AddTaskFromToDayTest() {
		
	}
	
	@Test
	public void AddTaskFromToDateTest() {
		
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