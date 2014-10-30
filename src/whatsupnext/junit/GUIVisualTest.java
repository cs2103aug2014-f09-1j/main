package whatsupnext.junit;

import static org.junit.Assert.*;

import java.awt.Dimension;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import whatsupnext.logic.Logic;
import whatsupnext.storage.Storage;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.DELETETYPE;
import whatsupnext.ui.WhatsUpNextGUI;

public class GUIVisualTest {
	private WhatsUpNextGUIStub gui;
	
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
	
	@Before
	public void setUpGUI() {
		gui = new WhatsUpNextGUIStub("guiTest");
	}
	
	@After
	public void clearTestFile() {
		gui.clearFile();
	}
	
	@Test
	public void FrameMainTest() {
		JFrame frameMain = (JFrame)GUITestUtils.getChildNamed(gui.getMainFrame(), "frameMain");
		assertNotNull("Can't acess the main frame JFrame", frameMain);
		
		assertTrue(frameMain.isResizable());
		
		Dimension prefSize = frameMain.getPreferredSize();
		assertEquals(830, prefSize.width);
		assertEquals(360, prefSize.height);
		Dimension minSize = frameMain.getMinimumSize();
		assertEquals(830, minSize.width);
		assertEquals(360, minSize.height);
		
		assertEquals("Cambria", frameMain.getFont().getName());
		assertTrue(frameMain.getFont().isBold());
		assertEquals(12, frameMain.getFont().getSize());
		
		assertEquals("WhatsUpNext", frameMain.getTitle());
		
		assertNotNull(frameMain.getContentPane().getLayout());
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
		assertEquals(13, labelWelcome.getFont().getSize());
		
		assertEquals("Welcome to WhatsUpNext! Today is " + dateFormat.format(cal.getTime()), labelWelcome.getText());
	}
	
	@Test
	public void UpcomingTasksTextDisplayTest() {
		JScrollPane textDisplayUpcomingScrollPane = (JScrollPane)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcomingScrollPane");
		assertNotNull("Can't acess the upcoming task text display scrolling pane JScrollPane", textDisplayUpcomingScrollPane);
		
		assertEquals(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, textDisplayUpcomingScrollPane.getVerticalScrollBarPolicy());
		
		JTextArea textDisplayUpcoming = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayUpcoming");
		assertNotNull("Can't acess the upcoming task text area JTextArea", textDisplayUpcoming);
		
		assertEquals("Courier New", textDisplayUpcoming.getFont().getName());
		assertTrue(textDisplayUpcoming.getFont().isBold());
		assertEquals(12, textDisplayUpcoming.getFont().getSize());
		
		assertFalse(textDisplayUpcoming.isEditable());
	}
	
	@Test
	public void UpcomingTasksButtonTest() {
		JButton buttonUpcoming = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonUpcoming");
		assertNotNull("Can't acess the upcoming task button JButton", buttonUpcoming);
		
		assertEquals("Cambria", buttonUpcoming.getFont().getName());
		assertTrue(buttonUpcoming.getFont().isBold());
		assertEquals(12, buttonUpcoming.getFont().getSize());
		
		assertEquals("Upcoming", buttonUpcoming.getText());
	}
	
	@Test
	public void MainEnterButtonTest() {
		JButton buttonEnter = (JButton)GUITestUtils.getChildNamed(gui.getMainFrame(), "buttonEnter");
		assertNotNull("Can't acess the main enter button JButton", buttonEnter);
		
		assertEquals("Cambria", buttonEnter.getFont().getName());
		assertTrue(buttonEnter.getFont().isBold());
		assertEquals(12, buttonEnter.getFont().getSize());
		
		assertEquals(" Enter ", buttonEnter.getText());
	}
	
	@Test
	public void MainUserCLITest() {
		JTextField textInput = (JTextField)GUITestUtils.getChildNamed(gui.getMainFrame(), "textInput");
		assertNotNull("Can't acess the user CLI JTextField", textInput);
		
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
		
		JTextArea textDisplayMain = (JTextArea)GUITestUtils.getChildNamed(gui.getMainFrame(), "textDisplayMain");
		assertNotNull("Can't acess the main text display JTextField", textDisplayMain);
		
		assertFalse(textDisplayMain.isEditable());
		
		assertEquals("Courier New", textDisplayMain.getFont().getName());
		assertTrue(textDisplayMain.getFont().isBold());
		assertEquals(12, textDisplayMain.getFont().getSize());
		
		assertEquals("---Please enter command below:\r\n", textDisplayMain.getText());
	}
}
