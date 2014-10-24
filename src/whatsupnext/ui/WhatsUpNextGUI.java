package whatsupnext.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.UnsupportedLookAndFeelException;

import whatsupnext.logic.Logic;
import whatsupnext.parser.extractor.ParseDate;
import whatsupnext.parser.api.Parser;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

/*
 * This class is used for GUI of software WhatsUpNext
 */
public class WhatsUpNextGUI {
	
	protected static JFrame frameMain;
	private final int FRAME_MAIN_WIDTH = 555;
	private final int FRAME_MAIN_HEIGHT = 300;
	protected static JLabel labelWelcome;
	private final int[] LABEL_WELCOME_DIMENSIONS = {13, 10, 328, 15};
	
	protected static JScrollPane textDisplayMainScrollPane;
	private final int[] TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS = {10, 35, 328, 180};
	protected static JTextArea textDisplayMain;
	private final int[] TEXT_DISPLAY_MAIN_DIMENSIONS = {0, 0, 328, 180};
	protected static JTextField textInput;
	private final int[] TEXT_INPUT_DIMENSIONS = {10, 225, 423, 25};
	protected static JButton buttonEnter;
	private final int[] BUTTON_ENTER_DIMENSIONS = {440, 225, 90, 25};
	
	protected static JButton buttonUpcoming;
	private final int[] BUTTON_UPCOMING_DIMENSIONS = {356, 5, 174, 28};
	protected static JScrollPane textDisplayUpcomingScrollPane;
	private final int[] TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS = {356, 35, 174, 180};
	protected static JTextArea textDisplayUpcoming;
	private final int[] TEXT_DISPLAY_UPCOMING_DIMENSIONS = {0, 0, 174, 180};
	
    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";
    private GUIFunctionality guiBehavior;

	
	/**
	 * Launch the application.
	 * Sets window visibility and size.
	 */
	public static void main(String[] args) {
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhatsUpNextGUI window = new WhatsUpNextGUI();
					window.frameMain.setLocationByPlatform(true);
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 * Be sure to initialize the GUIComponents before the GUIFunctionality
	 */
	public WhatsUpNextGUI() {
		initGUIComponents();
		setComponentsNames();
		guiBehavior = new GUIFunctionality();
		guiBehavior.clickUpcoming();
	}
	
	/**
	 * Returns the main frame used in the GUI
	 * 
	 * @return The JFrame for the main application
	 */
	public JFrame getMainFrame() {
		return frameMain;
	}
	
	
	/**
	 * Names every component used in the GUI
	 */
	private void setComponentsNames() {
		frameMain.setName("frameMain");
		labelWelcome.setName("labelWelcome");
		
		textDisplayMain.setName("textDisplayMain");
		textDisplayMainScrollPane.setName("textDisplayMainScrollPane");
		textInput.setName("textInput");
		buttonEnter.setName("buttonEnter");
		
		buttonUpcoming.setName("buttonUpcoming");
		textDisplayUpcomingScrollPane.setName("textDisplayUpcomingScrollPane");
		textDisplayUpcoming.setName("textDisplayUpcoming");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initGUIComponents() {
		initializeApplicationFrame();
		initializeWelcomeMessage();		
		initializeUpcomingTasks();
		initializeMain();
	}
	
	/**
	 * Initialize frame of the application
	 */
	private void initializeApplicationFrame() {	
		frameMain = new JFrame();
		frameMain.setResizable(true);
		frameMain.setIconImage(Toolkit.getDefaultToolkit().getImage(WhatsUpNextGUI.class.getResource("/whatsupnext/ui/iconGUI.png")));
		frameMain.setType(Type.POPUP);
		frameMain.setForeground(SystemColor.controlShadow);
		frameMain.setBackground(Color.GRAY);
		frameMain.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.setTitle("WhatsUpNext");
		frameMain.getContentPane().setBackground(new Color(204, 224, 250));
		frameMain.getContentPane().setLayout(null);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.setBounds(0, 0, FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT);
		frameMain.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				resetComponentSizes();
			}
		});
	}
	
	
	/**
	 * Initialize welcome message
	 */
	private void initializeWelcomeMessage() {
		appendDateToWelcomeMessage();
		initializeWelcomeMessageLabel();
	}

	/**
	 * Gets the current date and completes the welcome message
	 */
	private void appendDateToWelcomeMessage() {
		DateFormat dateFormat = new SimpleDateFormat("EEE, yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		STRING_WELCOME = STRING_WELCOME + dateFormat.format(cal.getTime());
	}

	/**
	 * Creates the label for welcome message
	 */
	private void initializeWelcomeMessageLabel() {
		labelWelcome = new JLabel(STRING_WELCOME);
		labelWelcome.setForeground(new Color(0, 0, 128));
		labelWelcome.setBounds(
				LABEL_WELCOME_DIMENSIONS[0],
				LABEL_WELCOME_DIMENSIONS[1],
				LABEL_WELCOME_DIMENSIONS[2],
				LABEL_WELCOME_DIMENSIONS[3]);
		labelWelcome.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.getContentPane().add(labelWelcome);
	}
	
	
	/**
	 * Initialize upcoming tasks part
	 */
	private void initializeUpcomingTasks() {
		initializeUpcomingTasksTextDisplay();
		initializeUpcomingTasksButton();
	}

	/**
	 *  Display area for upcoming tasks
	 */
	private void initializeUpcomingTasksTextDisplay() {
		textDisplayUpcoming = new JTextArea();
		textDisplayUpcoming.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayUpcoming.setForeground(new Color(25, 25, 112));
		textDisplayUpcoming.setEditable(false);
		textDisplayUpcoming.setBackground(new Color(240, 255, 255));
		textDisplayUpcoming.setBounds(
				TEXT_DISPLAY_UPCOMING_DIMENSIONS[0],
				TEXT_DISPLAY_UPCOMING_DIMENSIONS[1],
				TEXT_DISPLAY_UPCOMING_DIMENSIONS[2],
				TEXT_DISPLAY_UPCOMING_DIMENSIONS[3]);
		
		textDisplayUpcomingScrollPane = new JScrollPane(textDisplayUpcoming);
		textDisplayUpcomingScrollPane.setBounds(
				TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0],
				TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[1],
				TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[2],
				TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[3]);
		textDisplayUpcomingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frameMain.getContentPane().add(textDisplayUpcomingScrollPane);
	}
	
	/**
	 * Initializes the button panel container and the clickable button
	 */
	private void initializeUpcomingTasksButton() {		
		buttonUpcoming = new JButton("Upcoming Tasks");
		buttonUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonUpcoming.setForeground(new Color(224, 255, 255));
		buttonUpcoming.setBackground(new Color(70, 130, 180));
		buttonUpcoming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiBehavior.clickUpcoming();
			}
		});
		buttonUpcoming.setBounds(
				BUTTON_UPCOMING_DIMENSIONS[0],
				BUTTON_UPCOMING_DIMENSIONS[1],
				BUTTON_UPCOMING_DIMENSIONS[2],
				BUTTON_UPCOMING_DIMENSIONS[3]);
		frameMain.getContentPane().add(buttonUpcoming);
	}

	
	/**
	 * Initialize main part: button, input area, mainDisplay
	 */
	private void initializeMain() {
		initializeMainEnterButton();
		initializeMainUserCLI();
		initializeMainTextDisplay();
	}

	/**
	 * Creates the button that users press to execute command
	 */
	private void initializeMainEnterButton() {
		buttonEnter = new JButton(" Enter ");
		buttonEnter.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonEnter.setForeground(new Color(224, 255, 255));
		buttonEnter.setBackground(new Color(70, 130, 180));
		buttonEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiBehavior.clickEnter();
			}
		});
		buttonEnter.setBounds(
				BUTTON_ENTER_DIMENSIONS[0],
				BUTTON_ENTER_DIMENSIONS[1],
				BUTTON_ENTER_DIMENSIONS[2],
				BUTTON_ENTER_DIMENSIONS[3]);
		frameMain.getContentPane().add(buttonEnter);
	}

	/**
	 * Creates the text field that user can edit, for entering command
	 */
	private void initializeMainUserCLI() {
		textInput = new JTextField();
		textInput.setBackground(new Color(240, 255, 255));
		textInput.setFont(new Font("Courier New", Font.PLAIN, 12));
		textInput.setBounds(
				TEXT_INPUT_DIMENSIONS[0],
				TEXT_INPUT_DIMENSIONS[1],
				TEXT_INPUT_DIMENSIONS[2],
				TEXT_INPUT_DIMENSIONS[3]);
		frameMain.getContentPane().add(textInput);
		
		// Pressing 'enter' key causes the command to be executed
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiBehavior.pressEnterFromCLI();
			}	
		});
		
		// Pressing 'up' or 'down' keys allows for cycling of previous commands to replace user input
		textInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_UP) {
					guiBehavior.pressUpFromCLI();
				} else if (key == KeyEvent.VK_DOWN) {
					guiBehavior.pressDownFromCLI();
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				guiBehavior.pressKeyFromCLI();
			}
		});
	}

	/**
	 * Creates the main display area for program feedback
	 */
	private void initializeMainTextDisplay() {
		textDisplayMain = new JTextArea();
		textDisplayMain.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayMain.setForeground(new Color(25, 25, 112));
		textDisplayMain.setText("---Please enter command below:\r\n");
		textDisplayMain.setEditable(false);
		textDisplayMain.setBackground(new Color(240, 255, 255));
		textDisplayMain.setBounds(
				TEXT_DISPLAY_MAIN_DIMENSIONS[0],
				TEXT_DISPLAY_MAIN_DIMENSIONS[1],
				TEXT_DISPLAY_MAIN_DIMENSIONS[2],
				TEXT_DISPLAY_MAIN_DIMENSIONS[3]);
		
		textDisplayMainScrollPane = new JScrollPane(textDisplayMain);
		textDisplayMainScrollPane.setBounds(
				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0],
				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1],
				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[2],
				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[3]);
		textDisplayMainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frameMain.getContentPane().add(textDisplayMainScrollPane);
	}
	
	
	private void resetComponentSizes() {
		Rectangle frameSize = frameMain.getBounds();
		
		int pixelsFromCLIToBottom = FRAME_MAIN_HEIGHT - (TEXT_INPUT_DIMENSIONS[1] + TEXT_INPUT_DIMENSIONS[3]);
		int pixelsFromDisplayToCLI = TEXT_INPUT_DIMENSIONS[1] - (TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1] + TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[3]);
		int pixelsFromUpcomingToRight = FRAME_MAIN_WIDTH - (TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0] + TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[2]);
		int pixelsFromMainToUpcoming = TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0] - (TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0] + TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[2]);
		
		int upcomingDisplayRelativeX = (int)(frameSize.width * ((double)(TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0]) / FRAME_MAIN_WIDTH));
		int upcomingDisplayRelativeWidth = frameSize.width - (upcomingDisplayRelativeX + pixelsFromUpcomingToRight);

		buttonUpcoming.setBounds(
				upcomingDisplayRelativeX,
				BUTTON_UPCOMING_DIMENSIONS[1],
				upcomingDisplayRelativeWidth,
				BUTTON_ENTER_DIMENSIONS[3]);
		textDisplayUpcomingScrollPane.setBounds(
				upcomingDisplayRelativeX,
				TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[1],
				upcomingDisplayRelativeWidth,
				frameSize.height - TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
		// Don't change textDisplayUpcoming x and y because they are relative to the textDisplayUpcomingScrollPane
		textDisplayUpcoming.setBounds(
				TEXT_DISPLAY_UPCOMING_DIMENSIONS[0],
				TEXT_DISPLAY_UPCOMING_DIMENSIONS[1],
				upcomingDisplayRelativeWidth,
				frameSize.height - TEXT_DISPLAY_UPCOMING_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
		
		labelWelcome.setBounds(
				LABEL_WELCOME_DIMENSIONS[0],
				LABEL_WELCOME_DIMENSIONS[1],
				frameSize.width - LABEL_WELCOME_DIMENSIONS[0] - (pixelsFromMainToUpcoming + upcomingDisplayRelativeWidth + pixelsFromUpcomingToRight),
				LABEL_WELCOME_DIMENSIONS[3]);
		textDisplayMainScrollPane.setBounds(
				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0],
				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1],
				frameSize.width - TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0] - (pixelsFromMainToUpcoming + upcomingDisplayRelativeWidth + pixelsFromUpcomingToRight),
				frameSize.height - TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
		// Don't change textDisplayMain x and y because they are relative to the textDisplayMainScrollPane
		textDisplayMain.setBounds(
				TEXT_DISPLAY_MAIN_DIMENSIONS[0],
				TEXT_DISPLAY_MAIN_DIMENSIONS[1],
				frameSize.width - TEXT_DISPLAY_MAIN_DIMENSIONS[0] - (pixelsFromMainToUpcoming + upcomingDisplayRelativeWidth + pixelsFromUpcomingToRight),
				frameSize.height - TEXT_DISPLAY_MAIN_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
		
		textInput.setBounds(
				TEXT_INPUT_DIMENSIONS[0],
				frameSize.height - (TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom),
				frameSize.width - TEXT_INPUT_DIMENSIONS[0] - (BUTTON_ENTER_DIMENSIONS[2] + pixelsFromUpcomingToRight),
				TEXT_INPUT_DIMENSIONS[3]);
		buttonEnter.setBounds(
				frameSize.width - (BUTTON_ENTER_DIMENSIONS[2] + pixelsFromUpcomingToRight),
				frameSize.height - (BUTTON_ENTER_DIMENSIONS[3] + pixelsFromCLIToBottom),
				BUTTON_ENTER_DIMENSIONS[2],
				BUTTON_ENTER_DIMENSIONS[3]);
	}
	
}
