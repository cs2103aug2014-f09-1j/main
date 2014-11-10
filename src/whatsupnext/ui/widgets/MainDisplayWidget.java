//@author A0126730M
package whatsupnext.ui.widgets;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainDisplayWidget {
	
	private JPanel widgetPanel;
    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";
    private final String DEFAULT_MESSAGE = "----Please enter command below:\n    type \"help\" for instructions\n";
    private JLabel labelWelcome;
	private JScrollPane textDisplayMainScrollPane;
	private JTextPane textDisplayMain;
	
	private String currentYear;
	
	private Random random = new Random();
	private int currentColorIndex;
	private Color[] colors = {
			new Color(153, 0, 76),
			new Color(102, 102, 0),
			new Color(0, 128, 128), 
			new Color(220, 20, 60),
			new Color(210, 105, 30)
	};
	private Color titleBackground = new Color(230,230,250);
	private Color titleForeground = new Color(25,25,112);

	private StyledDocument  doc = new DefaultStyledDocument();
	

    public MainDisplayWidget() {
    	initializeCurrentYear();
		initializeMainDisplayPanel();
		setComponentNames();
	}
    
    public JPanel getWidgetPanel() {
    	return widgetPanel;
    }
    
    public String getDefaultMessage() {
    	return DEFAULT_MESSAGE;
    }
    
    public void setText(String string) {
		textDisplayMain.setText(DEFAULT_MESSAGE);
	}

	public void displayFeedback(String feedback) {
		feedback = feedback.replaceAll(" " + currentYear, "");
		feedback = feedback.replaceAll(currentYear + " ", "");
		feedback = feedback.replaceAll(currentYear, "");
		appendToPane(feedback);
	}
	
    
    private void initializeCurrentYear() {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
	}

	private void setComponentNames() {
		widgetPanel.setName("mainDisplayWidgetPanel");
    	labelWelcome.setName("labelWelcome");
		textDisplayMain.setName("textDisplayMain");
		textDisplayMainScrollPane.setName("textDisplayMainScrollPane");
    }

	private void initializeMainDisplayPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWeights = new double[]{1.0};
		gbl_widgetPanel.rowWeights = new double[]{0.0, 1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
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

	private void initializeWelcomeMessageLabel() {
		labelWelcome = new JLabel(STRING_WELCOME);
		labelWelcome.setForeground(new Color(0, 0, 128));
		labelWelcome.setFont(new Font("Cambria", Font.BOLD, 13));
		
		GridBagConstraints gbc_labelWelcome = new GridBagConstraints();
		gbc_labelWelcome.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelWelcome.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelWelcome.insets = new Insets(5, 0, 5, 0);
		gbc_labelWelcome.gridx = 0;
		gbc_labelWelcome.gridy = 0;
		
		widgetPanel.add(labelWelcome, gbc_labelWelcome);
	}
	
	private void initializeMainTextDisplay() {
		doc = new DefaultStyledDocument();
		textDisplayMain = new JTextPane(doc);
		textDisplayMain.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayMain.setForeground(new Color(25, 25, 112));
		textDisplayMain.setText(DEFAULT_MESSAGE);
		textDisplayMain.setEditable(false);
		textDisplayMain.setBackground(new Color(240, 255, 255));
		
		textDisplayMainScrollPane = new JScrollPane(textDisplayMain);
		textDisplayMainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		GridBagConstraints gbc_textDisplayMainScrollPane = new GridBagConstraints();
		gbc_textDisplayMainScrollPane.fill = GridBagConstraints.BOTH;
		gbc_textDisplayMainScrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_textDisplayMainScrollPane.insets = new Insets(3, 0, 0, 0);
		gbc_textDisplayMainScrollPane.gridx = 0;
		gbc_textDisplayMainScrollPane.gridy = 1;
		
		widgetPanel.add(textDisplayMainScrollPane, gbc_textDisplayMainScrollPane);
	}
	
	//@author A0092165E
	/**
	 * This function displays feedback with customized coloring
	 * @param feedback
	 */
	private void appendToPane(String feedback) {
		textDisplayMain.setText("\n"+feedback+"\n");
		int numOfNewline = countSubstring("\n", feedback);
		String[] subStrings = feedback.trim().split("\n");
		int currentStart = 0;
		int currentEnd = feedback.indexOf("\n",1);
		String subString;
		boolean newtask=true;
		Color lastColor = generateNewColor();
      			
	     for (int i = 0; i <= numOfNewline; i++) {
	    	 currentEnd = feedback.indexOf("\n",currentStart+1);
	    	 if (currentEnd<0) {
	    		 currentEnd=doc.getLength();
	    	 }
             SimpleAttributeSet set = new SimpleAttributeSet();
 
             subString = subStrings[i]; 
             // This line is a task title
             if (isnewTask(subString)){
            	 StyleConstants.setBold(set, true); 
            	 StyleConstants.setFontSize(set, 13);      
            	 StyleConstants.setBackground(set, titleBackground);
            	 StyleConstants.setForeground(set, titleForeground);
            	 newtask=true;
             } else {
            	 StyleConstants.setBold(set, false); 
            	 StyleConstants.setFontSize(set, 11);
            	 if (newtask){
                    lastColor = generateNewColor();
                    newtask = false;
            	 } 
            	 StyleConstants.setForeground(set, lastColor);
             }
             doc.setCharacterAttributes(currentStart, currentEnd-currentStart+1, set, true);
             currentStart = currentEnd + 1;
         }
	}
	
		
	/**
	 * This function generates a new font color without repeating last one
	 * @return
	 */
	private Color generateNewColor(){
		int newColorIndex = random.nextInt(colors.length-1);
		while (newColorIndex == currentColorIndex){
			newColorIndex = random.nextInt(colors.length-1);
		}
		currentColorIndex = newColorIndex;
		return colors[newColorIndex];
	}
	
	/**
	 * This function judges if a string is title of a task
	 * based on if it starts with "<taskID:>"
	 * @param subString
	 * @return
	 */
	private boolean isnewTask(String subString) {
		subString = subString.trim().split("\n")[0];
		String taskID = subString.trim().split(":")[0];
		try {
			Integer.parseInt(taskID); 
			return true;
		}  catch (NumberFormatException e) {
			return false;
		}	
	}

	/**
	 * This function counts the number of lines inside a string
	 * @param subStr
	 * @param str
	 * @return
	 */
	public static int countSubstring(String subStr, String str){
		return (str.length() - str.replace(subStr, "").length()) / subStr.length();
	}
	
}
