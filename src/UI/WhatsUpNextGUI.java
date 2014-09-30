package UI;

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
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import Logic.Logic;
import Parser.Parser;
import Structure.Task;

/*
 * This class is used for GUI of software WhatsUpNext
 */
public class WhatsUpNextGUI {
	
    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";    
    
	private JFrame frameMain;
	private JLabel labelWelcome;
	
	private JScrollPane textDisplayScrollingPane;
	private JTextArea textDisplayMain;
	private JTextField textInput;
	private JButton buttonEnter;
	
	private JPanel panelUpcoming;
	private JButton buttonUpcoming;
	private JTextArea textDisplayUpcoming;

	
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
					window.frameMain.setVisible(true);
					window.frameMain.setBounds(0, 0, 505, 295);
					window.frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		initialize();
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
		textDisplayScrollingPane.setName("textDisplayScrollingPane");
		textInput.setName("textInput");
		buttonEnter.setName("buttonEnter");
		
		panelUpcoming.setName("panelUpcoming");
		buttonUpcoming.setName("buttonUpcoming");
		textDisplayUpcoming.setName("textDisplayUpcoming");
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		frameMain.setIconImage(Toolkit.getDefaultToolkit().getImage(WhatsUpNextGUI.class.getResource("/UI/iconGUI.png")));
		frameMain.setType(Type.POPUP);
		frameMain.setForeground(SystemColor.controlShadow);
		frameMain.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.setTitle("WhatsUpNext");
		frameMain.setBackground(Color.GRAY);
		frameMain.getContentPane().setBackground(new Color(204, 224, 250));
		frameMain.getContentPane().setLayout(null);
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
		labelWelcome.setBounds(10, 10, 328, 15);
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
		textDisplayUpcoming.setBounds(356, 31, 124, 184);
		frameMain.getContentPane().add(textDisplayUpcoming);
	}
	
	/**
	 * Initializes the button panel container and the clickable button
	 */
	private void initializeUpcomingTasksButton() {
		// Panel to hold the upcoming task button 
		panelUpcoming = new JPanel();
		panelUpcoming.setBackground(new Color(204, 224, 250));
		panelUpcoming.setBounds(349, 0, 138, 215);
		frameMain.getContentPane().add(panelUpcoming);
				
		// Button for upcoming task
		buttonUpcoming = new JButton("Upcoming Tasks");
		buttonUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonUpcoming.setForeground(new Color(224, 255, 255));
		buttonUpcoming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickUpcoming();
			}
		});
		buttonUpcoming.setBackground(new Color(100, 149, 237));
		panelUpcoming.add(buttonUpcoming);
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
		buttonEnter.setForeground(new Color(224, 255, 255));
		buttonEnter.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonEnter.setBackground(new Color(70, 130, 180));
		buttonEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickEnter();
			}
		});
		buttonEnter.setBounds(394, 230, 79, 23);
		frameMain.getContentPane().add(buttonEnter);
	}

	/**
	 * Creates the text field that user can edit, for entering command
	 */
	private void initializeMainUserCLI() {
		textInput = new JTextField();
		textInput.setBackground(new Color(240, 255, 255));
		textInput.setFont(new Font("Courier New", Font.PLAIN, 12));
		textInput.setBounds(10, 230, 366, 23);
		frameMain.getContentPane().add(textInput);
		// Pressing 'enter' key causes the command to be executed
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep(); 
				clickEnter();
			}	
		});
		
		// NOT THE SLIGHTEST CLUE WHY THIS CANNOT BE PROPERLY TESTED
		// DISPATCHED KEYEVENT FROM THE TEST SUITE REFUSES TO BE CAUGHT BY KEYLISTENER HERE
		//
		//		textInput.addKeyListener(new KeyAdapter() {
		//			@Override
		//			public void keyPressed(KeyEvent e) {
		//				int key = e.getKeyCode();
		//				if (key == KeyEvent.VK_ENTER) {
		//					Toolkit.getDefaultToolkit().beep(); 
		//					clickEnter();
		//					textInput.setText(""); // clean input area
		//				}
		//			}	
		//		});
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
		
		textDisplayScrollingPane = new JScrollPane(textDisplayMain);
		textDisplayScrollingPane.setBounds(10, 31, 328, 184);
		textDisplayScrollingPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frameMain.getContentPane().add(textDisplayScrollingPane);
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
				feedback = Logic.execute(currentTask);
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
	 * Callback function for when the user clicks the Upcoming Tasks button
	 * or when new execution has been activated
	 */
	private void clickUpcoming() {
		// TODO: Implement response for click upcoming
		// Get a list of most recent tasks and display
		textDisplayUpcoming.setText("No saved tasks!");		
	}
}
