package whatsupnext.junit;

import static org.junit.Assert.*;

import java.awt.Window.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.junit.Before;
import org.junit.Test;

import whatsupnext.ui.WhatsUpNextGUI;

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
		assertEquals(0, frameMain.getBounds().x);
		assertEquals(0, frameMain.getBounds().y);
		assertEquals(555, frameMain.getBounds().width);
		assertEquals(295, frameMain.getBounds().height);
		
		assertEquals("Cambria", frameMain.getFont().getName());
		assertTrue(frameMain.getFont().isBold());
		assertEquals(12, frameMain.getFont().getSize());
		
		assertEquals("WhatsUpNext", frameMain.getTitle());
		
		assertNull(frameMain.getContentPane().getLayout());
		assertEquals(Type.POPUP, frameMain.getType());
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
		
		assertEquals(13, labelWelcome.getBounds().x);
		assertEquals(10, labelWelcome.getBounds().y);
		assertEquals(328, labelWelcome.getBounds().width);
		assertEquals(15, labelWelcome.getBounds().height);
		
		assertEquals("Welcome to WhatsUpNext! Today is " + dateFormat.format(cal.getTime()), labelWelcome.getText());
	}
	
	@Test
	public void UpcomingTasksTextDisplayTest() {
		JScrollPane textDisplayUpcomingScrollPane = (JScrollPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcomingScrollPane");
		assertNotNull("Can't acess the upcoming task text display scrolling pane JScrollPane", textDisplayUpcomingScrollPane);
		
		assertEquals(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, textDisplayUpcomingScrollPane.getVerticalScrollBarPolicy());
		
		assertEquals(356, textDisplayUpcomingScrollPane.getBounds().x);
		assertEquals(35, textDisplayUpcomingScrollPane.getBounds().y);
		assertEquals(174, textDisplayUpcomingScrollPane.getBounds().width);
		assertEquals(184, textDisplayUpcomingScrollPane.getBounds().height);
		
		JTextArea textDisplayUpcoming = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		
		assertEquals("Courier New", textDisplayUpcoming.getFont().getName());
		assertTrue(textDisplayUpcoming.getFont().isBold());
		assertEquals(12, textDisplayUpcoming.getFont().getSize());
		
		assertFalse(textDisplayUpcoming.isEditable());
		
		assertEquals(0, textDisplayUpcoming.getBounds().x);
		assertEquals(0, textDisplayUpcoming.getBounds().y);
		assertEquals(174, textDisplayUpcoming.getBounds().width);
		assertEquals(184, textDisplayUpcoming.getBounds().height);
	}
	
	@Test
	public void UpcomingTasksButtonTest() {
		JButton buttonUpcoming = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonUpcoming");
		assertNotNull("Can't acess the upcoming task button JButton", buttonUpcoming);
		
		assertEquals(356, buttonUpcoming.getBounds().x);
		assertEquals(5, buttonUpcoming.getBounds().y);
		assertEquals(174, buttonUpcoming.getBounds().width);
		assertEquals(28, buttonUpcoming.getBounds().height);
		
		assertEquals("Cambria", buttonUpcoming.getFont().getName());
		assertTrue(buttonUpcoming.getFont().isBold());
		assertEquals(12, buttonUpcoming.getFont().getSize());
		
		assertEquals("Upcoming Tasks", buttonUpcoming.getText());
	}
	
	@Test
	public void MainEnterButtonTest() {
		JButton buttonEnter = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonEnter");
		assertNotNull("Can't acess the main enter button JButton", buttonEnter);
		
		assertEquals(451, buttonEnter.getBounds().x);
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
		assertEquals(423, textInput.getBounds().width);
		assertEquals(23, textInput.getBounds().height);
		
		assertEquals("Courier New", textInput.getFont().getName());
		assertTrue(textInput.getFont().isPlain());
		assertEquals(12, textInput.getFont().getSize());
		
		assertEquals("", textInput.getText());
	}
	
	@Test
	public void MainTextDisplayTest() {
		JScrollPane textDisplayMainScrollPane = (JScrollPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMainScrollPane");
		assertNotNull("Can't acess the main text display scrolling pane JScrollPane", textDisplayMainScrollPane);
		
		assertEquals(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, textDisplayMainScrollPane.getVerticalScrollBarPolicy());
		
		assertEquals(10, textDisplayMainScrollPane.getBounds().x);
		assertEquals(35, textDisplayMainScrollPane.getBounds().y);
		assertEquals(328, textDisplayMainScrollPane.getBounds().width);
		assertEquals(184, textDisplayMainScrollPane.getBounds().height);
		
		JTextArea textDisplayMain = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMain");
		assertNotNull("Can't acess the main text display JTextField", textDisplayMain);
		
		assertFalse(textDisplayMain.isEditable());
		
		assertEquals(0, textDisplayMain.getBounds().x);
		assertEquals(0, textDisplayMain.getBounds().y);
		assertEquals(328, textDisplayMain.getBounds().width);
		assertEquals(184, textDisplayMain.getBounds().height);
		
		assertEquals("Courier New", textDisplayMain.getFont().getName());
		assertTrue(textDisplayMain.getFont().isBold());
		assertEquals(12, textDisplayMain.getFont().getSize());
		
		assertEquals("---Please enter command below:\r\n", textDisplayMain.getText());
	}
}
