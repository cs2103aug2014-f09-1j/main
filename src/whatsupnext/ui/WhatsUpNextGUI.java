package whatsupnext.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
import whatsupnext.parser.ParseDate;
import whatsupnext.parser.Parser;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

/*
 * This class is used for GUI of software WhatsUpNext
 */
public class WhatsUpNextGUI {
	
    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";    
    
	private JFrame frameMain;
	private JLabel labelWelcome;
	
	private JScrollPane textDisplayMainScrollPane;
	private JTextArea textDisplayMain;
	private JTextField textInput;
	private JButton buttonEnter;
	
	private JButton buttonUpcoming;
	private JScrollPane textDisplayUpcomingScrollPane;
	private JTextArea textDisplayUpcoming;
	
	private Logic logicHandler;

	
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
	 */
	public WhatsUpNextGUI() {
		logicHandler = new Logic();
		initGUIComponents();
		setComponentsNames();
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
		intializeWelcomeMessage();		
		intializeUpcomingTasks();
		initializeMain();
	}
	
	/**
	 * Initialize frame of the application
	 */
	private void initializeApplicationFrame() {	
		frameMain = new JFrame();
		frameMain.setResizable(false);
		frameMain.setIconImage(Toolkit.getDefaultToolkit().getImage(WhatsUpNextGUI.class.getResource("/whatsupnext/ui/iconGUI.png")));
		frameMain.setType(Type.POPUP);
		frameMain.setForeground(SystemColor.controlShadow);
		frameMain.setBackground(Color.GRAY);
		frameMain.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.setTitle("WhatsUpNext");
		frameMain.getContentPane().setBackground(new Color(204, 224, 250));
		frameMain.getContentPane().setLayout(null);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.setBounds(0, 0, 555, 295);
	}
	
	
	/**
	 * Initialize welcome message
	 */
	private void intializeWelcomeMessage() {
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
		labelWelcome.setBounds(13, 10, 328, 15);
		labelWelcome.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.getContentPane().add(labelWelcome);
	}
	
	
	/**
	 * Initialize upcoming tasks part
	 */
	private void intializeUpcomingTasks() {
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
		textDisplayUpcoming.setBounds(0, 0, 174, 184);
		
		textDisplayUpcomingScrollPane = new JScrollPane(textDisplayUpcoming);
		textDisplayUpcomingScrollPane.setBounds(356, 35, 174, 184);
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
				clickUpcoming();
			}
		});
		buttonUpcoming.setBounds(356, 5, 174, 28);
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
				clickEnter();
			}
		});
		buttonEnter.setBounds(451, 230, 79, 23);
		frameMain.getContentPane().add(buttonEnter);
	}

	/**
	 * Creates the text field that user can edit, for entering command
	 */
	private void initializeMainUserCLI() {
		textInput = new JTextField();
		textInput.setBackground(new Color(240, 255, 255));
		textInput.setFont(new Font("Courier New", Font.PLAIN, 12));
		textInput.setBounds(10, 230, 423, 23);
		frameMain.getContentPane().add(textInput);
		
		// Pressing 'enter' key causes the command to be executed
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep(); 
				clickEnter();
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
		textDisplayMain.setBounds(0, 0, 328, 184);
		
		textDisplayMainScrollPane = new JScrollPane(textDisplayMain);
		textDisplayMainScrollPane.setBounds(10, 35, 328, 184);
		textDisplayMainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frameMain.getContentPane().add(textDisplayMainScrollPane);
	}
	
	
	/**
	 * This method is activated as 'input command'
	 * It is called whenever user clicks the input button or presses the enter key
	 */
	private void clickEnter(){
		String commandInput = textInput.getText();
		String feedback;
		
		if (commandInput.trim().isEmpty()) {
			feedback = "Empty command";
		} else {
			try {
				Parser parser = new Parser(commandInput);
				Task currentTask = parser.parseInput();
				feedback = logicHandler.execute(currentTask);
			} catch (Exception e) {
				feedback = e.getMessage();
			}
		}
		
		displayFeedback(feedback);
		clearTextInput();
		clickUpcoming();
	}

	/**
	 * Clears the user command prompt
	 */
	private void clearTextInput() {
		textInput.setText("");
	}


	/** 
	 * This method would display feedback message in main display area
	 */
	private void displayFeedback(String feedback) {
		textDisplayMain.append("\n"+feedback);
	}
	
	/** 
	 * This method would display feedback message in main display area
	 */
	private void displayUpcomingFeedback(String feedback) {
		textDisplayUpcoming.setText(feedback);
	}
	
	
	
	/**
	 * Callback function for when the user clicks the Upcoming Tasks button
	 * or when new execution has been activated
	 */
	private void clickUpcoming() {
		// Get a list of most recent tasks and display
		Task task = generateTaskForUpcoming();
		
		String feedback;
		try {
			feedback = logicHandler.execute(task);
		} catch (Exception e) {
			feedback = e.getMessage();
		}
		
		displayUpcomingFeedback(feedback);
	}

	/**
	 *  view all tasks within today
	 * @return	the task that holds the view time frame task for upcoming
	 */
	private Task generateTaskForUpcoming() {
		ParseDate parseDate = new ParseDate();
		Task task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.TIMEFRAME);
		task.setStartTime(parseDate.getTodayDateString() + "0000");
		task.setEndTime(parseDate.getTodayDateString() + "2300");
		return task;
	}
}
