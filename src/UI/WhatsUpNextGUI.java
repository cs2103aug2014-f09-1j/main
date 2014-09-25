package UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

import java.awt.GridLayout;

import javax.swing.JDesktopPane;

import java.awt.Color;

import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Window.Type;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;

import java.awt.TextArea;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.text.JTextComponent;

import java.awt.ScrollPane;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Panel;
import java.awt.Button;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private TextArea textDisplayMain;
	private TextField textInput;
	private Panel panelUpcoming;
	private Button buttomUpcoming;
	private TextArea textDisplayToday;
	
	private Parser parser;
	/**
	 * @wbp.nonvisual location=274,-3
	 */


	
	/**
	 * Launch the application.
	 * set window visibility and size
	 */
	public static void main(String[] args) {
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
		textDisplayToday = new TextArea();
		textDisplayToday.setEditable(false);
		textDisplayToday.setBackground(SystemColor.inactiveCaptionBorder);
		textDisplayToday.setBounds(356, 42, 119, 164);
		frameMain.getContentPane().add(textDisplayToday);
		
		// Panel to hold the upcoming task button 
		panelUpcoming = new Panel();
		panelUpcoming.setBackground(Color.LIGHT_GRAY);
		panelUpcoming.setBounds(349, 10, 138, 205);
		frameMain.getContentPane().add(panelUpcoming);
				
		// This is the button for upcoming task
		buttomUpcoming = new Button("Upcoming Tasks");
		buttomUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		buttomUpcoming.setForeground(SystemColor.window);
		buttomUpcoming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    // Insert here the response of pressing button
				clickUpcoming();
			}
		});
		buttomUpcoming.setBackground(SystemColor.activeCaption);
		panelUpcoming.add(buttomUpcoming);
	}

	
	/**
	 * Initialize main part: button, input area, mainDisplay
	 */
	private void intializeMainPart() {

		// main display area for feedback
		textDisplayMain = new TextArea();
		textDisplayMain.setText("---- Please enter command from editPane below ----\r\n");
		textDisplayMain.setEditable(false);
		textDisplayMain.setBackground(UIManager.getColor("InternalFrame.inactiveBorderColor"));
		textDisplayMain.setBounds(10, 31, 328, 184);
		frameMain.getContentPane().add(textDisplayMain);

        // This is the textFiled that user can edit, for entering command
		textInput = new TextField();
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
		labelWelcome.setForeground(Color.DARK_GRAY);
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
		frameMain.setFont(new Font("Cambria", Font.BOLD, 6));
		frameMain.setTitle("WhatsUpNext");
		frameMain.setBackground(Color.LIGHT_GRAY);
		frameMain.getContentPane().setBackground(SystemColor.activeCaptionBorder);
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
