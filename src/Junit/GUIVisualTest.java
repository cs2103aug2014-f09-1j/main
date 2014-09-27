package Junit;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.Window.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import UI.WhatsUpNextGUI;

public class GUIVisualTest {
	private WhatsUpNextGUI gui;
	
	@Before
	public void setUpGUI() {
		gui = new WhatsUpNextGUI();
	}
	
	@Test
	public void FrameMainTest() {
		JFrame frameMain = (JFrame)GUITestUtils.getChildNamed(gui.getMainFrame(), "frameMain");
		assertNotNull("Can't acess the main frame JFrame", frameMain);
		
		assertFalse(frameMain.isResizable());
		
		assertEquals(Type.POPUP, frameMain.getType());
		
		assertEquals("Cambria", frameMain.getFont().getName());
		assertTrue(frameMain.getFont().isBold());
		assertEquals(12, frameMain.getFont().getSize());
		
		assertEquals("WhatsUpNext", frameMain.getTitle());
		
		assertNull(frameMain.getContentPane().getLayout());
	}

	@Test
	public void WelcomeMessageTest() {
		JLabel labelWelcome = (JLabel)GUITestUtils.getChildNamed(gui.getMainFrame(), "labelWelcome");
		assertNotNull("Can't acess the text input JTextField", labelWelcome);
		
		DateFormat dateFormat = new SimpleDateFormat("EEE, yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		assertEquals("Cambria", labelWelcome.getFont().getName());
		assertTrue(labelWelcome.getFont().isBold());
		assertEquals(12, labelWelcome.getFont().getSize());
		
		assertEquals(10, labelWelcome.getBounds().x);
		assertEquals(10, labelWelcome.getBounds().y);
		assertEquals(328, labelWelcome.getBounds().width);
		assertEquals(15, labelWelcome.getBounds().height);
		
		assertEquals("Welcome to WhatsUpNext! Today is " + dateFormat.format(cal.getTime()), labelWelcome.getText());
	}
	
	@Test
	public void UpcomingTasksTextDisplayTest() {
		JTextArea textDisplayUpcoming = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		
		assertEquals("Courier New", textDisplayUpcoming.getFont().getName());
		assertTrue(textDisplayUpcoming.getFont().isBold());
		assertEquals(12, textDisplayUpcoming.getFont().getSize());
		
		assertFalse(textDisplayUpcoming.isEditable());
		
		assertEquals(356, textDisplayUpcoming.getBounds().x);
		assertEquals(31, textDisplayUpcoming.getBounds().y);
		assertEquals(124, textDisplayUpcoming.getBounds().width);
		assertEquals(184, textDisplayUpcoming.getBounds().height);
	}
	
	@Test
	public void UpcomingTasksButtonTest() {
		JPanel panelUpcoming = (JPanel)GUITestUtils.getChildNamed(gui.getMainFrame(), "panelUpcoming");
		JButton buttonUpcoming = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonUpcoming");
		assertNotNull("Can't acess the upcoming task button panel JPanel", panelUpcoming);
		assertNotNull("Can't acess the upcoming task button JButton", buttonUpcoming);
		
		assertEquals(349, panelUpcoming.getBounds().x);
		assertEquals(0, panelUpcoming.getBounds().y);
		assertEquals(138, panelUpcoming.getBounds().width);
		assertEquals(215, panelUpcoming.getBounds().height);
		
		assertEquals("Cambria", buttonUpcoming.getFont().getName());
		assertTrue(buttonUpcoming.getFont().isBold());
		assertEquals(12, buttonUpcoming.getFont().getSize());
		
		assertEquals("Upcoming Tasks", buttonUpcoming.getText());
	}
	
	@Test
	public void MainEnterButtonTest() {
		JButton buttonEnter = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonEnter");
		assertNotNull("Can't acess the main enter button JButton", buttonEnter);
		
		assertEquals(394, buttonEnter.getBounds().x);
		assertEquals(230, buttonEnter.getBounds().y);
		assertEquals(79, buttonEnter.getBounds().width);
		assertEquals(23, buttonEnter.getBounds().height);
		
		assertEquals("Cambria", buttonEnter.getFont().getName());
		assertTrue(buttonEnter.getFont().isBold());
		assertEquals(12, buttonEnter.getFont().getSize());
		
		assertEquals(" Enter ", buttonEnter.getText());
	}
	
	@Test
	public void MainUserCLITest() {
		JTextField textInput = (JTextField)GUITestUtils.getChildNamed(gui.getMainFrame(), "textInput");
		assertNotNull("Can't acess the user CLI JTextField", textInput);
		
		assertEquals(10, textInput.getBounds().x);
		assertEquals(230, textInput.getBounds().y);
		assertEquals(366, textInput.getBounds().width);
		assertEquals(23, textInput.getBounds().height);
		
		assertEquals("Courier New", textInput.getFont().getName());
		assertTrue(textInput.getFont().isPlain());
		assertEquals(12, textInput.getFont().getSize());
		
		assertEquals("", textInput.getText());
	}
	
	@Test
	public void MainTextDisplayTest() {
		JTextArea textDisplayMain = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMain");
		assertNotNull("Can't acess the main text display JTextField", textDisplayMain);
		
		assertFalse(textDisplayMain.isEditable());
		
		assertEquals(10, textDisplayMain.getBounds().x);
		assertEquals(31, textDisplayMain.getBounds().y);
		assertEquals(328, textDisplayMain.getBounds().width);
		assertEquals(184, textDisplayMain.getBounds().height);
		
		assertEquals("Courier New", textDisplayMain.getFont().getName());
		assertTrue(textDisplayMain.getFont().isBold());
		assertEquals(12, textDisplayMain.getFont().getSize());
		
		assertEquals("---Please enter command into editPane below:\r\n", textDisplayMain.getText());
	}
}
