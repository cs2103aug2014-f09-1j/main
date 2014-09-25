package UI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;

import Parser.Parser;

/*
 * This class is used for GUI testing of software WhatsUpNext
 */
public class WhatsUpNextGUI {

	
    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";    
    private String commandInput;
    
	private JFrame frameMain;
	private JLabel labelWelcome;
	private JTextArea textDisplayMain;
	private JTextField textInput;
	private JPanel panelUpcoming;
	private JButton buttomUpcoming;
	private JTextArea textDisplayToday;
	
	private Parser parser;
	/**
	 * @wbp.nonvisual location=274,-3
	 */


	
	/**
	 * Launch the application.
	 * set window visibility and size
	 */
	public static void main(String[] args) {
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		    System.out.println(UIManager.getLookAndFeel().toString());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhatsUpNextGUI window = new WhatsUpNextGUI();
					window.frameMain.setVisible(true);
					window.frameMain.setBounds(0, 0, 505, 295);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		initializeFrame();
	
		intializeWelcomeMessage();		

		intializeMainPart();

		intializeUpcoming();
		
	}
	
	/**
	 * Initialize upcoming tasks part
	 */
	private void intializeUpcoming() {
		// display area for upcoming tasks
		textDisplayToday = new JTextArea();
		textDisplayToday.setFont(new Font("Cordia New", Font.BOLD, 12));
		textDisplayToday.setForeground(new Color(25, 25, 112));
		textDisplayToday.setEditable(false);
		textDisplayToday.setBackground(new Color(240, 255, 255));
		textDisplayToday.setBounds(356, 31, 124, 184);
		frameMain.getContentPane().add(textDisplayToday);
		
		// Panel to hold the upcoming task button 
		panelUpcoming = new JPanel();
		panelUpcoming.setBackground(new Color(176, 196, 222));
		panelUpcoming.setBounds(349, 0, 138, 215);
		frameMain.getContentPane().add(panelUpcoming);
				
		// This is the button for upcoming task
		buttomUpcoming = new JButton("Upcoming Tasks");
		buttomUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		buttomUpcoming.setForeground(new Color(224, 255, 255));
		buttomUpcoming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    // Insert here the response of pressing button
				clickUpcoming();
			}
		});
		buttomUpcoming.setBackground(new Color(100, 149, 237));
		panelUpcoming.add(buttomUpcoming);
	}

	
	/**
	 * Initialize main part: button, input area, mainDisplay
	 */
	private void intializeMainPart() {

		// main display area for feedback
		textDisplayMain = new JTextArea();
		textDisplayMain.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayMain.setForeground(new Color(25, 25, 112));
		textDisplayMain.setText("---Please enter command into editPane below:\r\n");
		textDisplayMain.setEditable(false);
		textDisplayMain.setBackground(new Color(240, 255, 255));
		textDisplayMain.setBounds(10, 31, 328, 184);
		frameMain.getContentPane().add(textDisplayMain);

        // This is the textFiled that user can edit, for entering command
		textInput = new JTextField();
		textInput.setBackground(new Color(240, 255, 255));
		textInput.setFont(new Font("Dialog", Font.PLAIN, 13));
		textInput.setBounds(10, 230, 366, 23);
		frameMain.getContentPane().add(textInput);
		// Add listening for 'enter' 
		// So by pressing key, the command is to be executed
		textInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
			    if (key == KeyEvent.VK_ENTER) {
			         Toolkit.getDefaultToolkit().beep(); 
			         clickEnter();
			         textInput.setText(""); // clean input area
			    }
			}	
		 }
		);		
		
		// This is the button that user press to execute command
		JButton buttonEnter = new JButton(" Enter ");
		buttonEnter.setForeground(new Color(224, 255, 255));
		buttonEnter.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonEnter.setBackground(new Color(70, 130, 180));
		buttonEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickEnter();
			}
		});
		buttonEnter.setBounds(394, 230, 79, 23);
		frameMain.getContentPane().add(buttonEnter);
	}

	/**
	 * Initialize welcome message
	 */
	private void intializeWelcomeMessage() {
		// get current date and complete welcome message
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEE");
		Calendar cal = Calendar.getInstance();
		STRING_WELCOME = STRING_WELCOME + dateFormat.format(cal.getTime());

		// label for welcome message
		labelWelcome = new JLabel(STRING_WELCOME);
		labelWelcome.setForeground(new Color(0, 0, 128));
		labelWelcome.setBounds(10, 10, 328, 15);
		labelWelcome.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.getContentPane().add(labelWelcome);
	}

	
	/**
	 * Initialize frame
	 */
	private void initializeFrame() {	
		frameMain = new JFrame();
		frameMain.setResizable(false);
		frameMain.setIconImage(Toolkit.getDefaultToolkit().getImage(WhatsUpNextGUI.class.getResource("/UI/iconGUI.png")));
		frameMain.setType(Type.POPUP);
		frameMain.setForeground(SystemColor.controlShadow);
		frameMain.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.setTitle("WhatsUpNext");
		frameMain.setBackground(Color.GRAY);
		frameMain.getContentPane().setBackground(new Color(176, 196, 222));
		frameMain.getContentPane().setLayout(null);
	}
	
	
	/**
	 * This method is activated as "input command"
	 * It is called whenever user click input button, or press enter key
	 * */
	public void clickEnter(){
		commandInput = textInput.getText();
		// TODO parse command
		parser = new Parser(commandInput);
		parser.parseInput();
		String feedback = "dummy";
		displayFeedback(feedback);
		clickUpcoming();
	}

	/** 
	 * This method would display feedback message in main display area
	 * */
	private void displayFeedback(String feedback) {
		textDisplayMain.append("\n"+feedback);
	}
	
	/**
	 * called when user click Upcoming Tasks button
	 * or when new execution has been activated
	 * */
	private void clickUpcoming() {
		// TODO response for click upcoming
		// get a list of most recent tasks and display
		textDisplayToday.setText("No saved tasks!");		
	}
}
