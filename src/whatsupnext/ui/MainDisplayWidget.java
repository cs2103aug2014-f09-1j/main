package whatsupnext.ui;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MainDisplayWidget {
	
	private JFrame frameMain = WhatsUpNextGUI.frameMain;

    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";
    private static JLabel labelWelcome;
	private final int[] LABEL_WELCOME_DIMENSIONS = {13, 10, 328, 15};
	
	private static JScrollPane textDisplayMainScrollPane;
	private final int[] TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS = {10, 35, 328, 180};
	static JTextArea textDisplayMain;
	private final int[] TEXT_DISPLAY_MAIN_DIMENSIONS = {0, 0, 328, 180};
	
	private String currentYear;
	

    public MainDisplayWidget() {
    	initializeCurrentYear();
		initializeMainDisplayPanel();
		setComponentNames();
	}
    
    private void initializeCurrentYear() {
    	DateFormat dateFormat = new SimpleDateFormat(" yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
	}

	private void setComponentNames() {
    	labelWelcome.setName("labelWelcome");
		textDisplayMain.setName("textDisplayMain");
		textDisplayMainScrollPane.setName("textDisplayMainScrollPane");
    }

	private void initializeMainDisplayPanel() {
		initializeWelcomeMessage();
		initializeMainTextDisplay();
	}
	
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
	
//	private void resetComponentSizes() {
//		
//	}

	public void setText(String string) {
		textDisplayMain.setText("---Please enter command below:\r\n");
	}

	public void displayFeedback(String feedback) {
		feedback = feedback.replaceAll(currentYear, "");
		textDisplayMain.append("\n"+feedback+"\n");
	}
}
