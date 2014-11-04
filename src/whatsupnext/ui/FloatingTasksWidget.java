package whatsupnext.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

public class FloatingTasksWidget implements TasksWidget {
	private JPanel widgetPanel;
	private final int PANEL_WIDTH = 150;
	private final int PANEL_HEIGHT = 210;
	
	private JButton buttonFloating;
	private JScrollPane textDisplayFloatingScrollPane;
	private JTextPane textDisplayFloating;
	
	private Random random = new Random();
	private int currentColorIndex;
	private Color[] colors = {new Color(153,0,76),new Color(102,102,0),
			                  new Color(210,105,30)};
	private Color titleBackground = new Color(255,251,180);
	private Color titleForeground = new Color(102,0,0);

	private StyledDocument  doc = new DefaultStyledDocument();
	
	
	public FloatingTasksWidget() {
		initializeFloatingTasksPanel();
		setComponentNames();
	}
	
	@Override
	public void doActionOnClick() {
		clickFloating();
	}
	
	public JPanel getWidgetPanel() {
    	return widgetPanel;
    }
	
	private void setComponentNames() {
		widgetPanel.setName("floatingTasksWidgetPanel");
		buttonFloating.setName("buttonFloating");
		textDisplayFloatingScrollPane.setName("textDisplayFloatingScrollPane");
		textDisplayFloating.setName("textDisplayFloating");
	}

	private void initializeFloatingTasksPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		widgetPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWidths = new int[]{PANEL_WIDTH};
		gbl_widgetPanel.rowHeights = new int[]{28, 180};
		gbl_widgetPanel.columnWeights = new double[]{1.0};
		gbl_widgetPanel.rowWeights = new double[]{0.0, 1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeFloatingTasksTextDisplay();
		initializeFloatingTasksButton();
	}
	
	private void initializeFloatingTasksTextDisplay() {
		textDisplayFloating = new JTextPane(doc);
		textDisplayFloating.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayFloating.setForeground(new Color(25, 25, 112));
		textDisplayFloating.setEditable(false);
		textDisplayFloating.setBackground(new Color(240, 255, 255));
		
		textDisplayFloatingScrollPane = new JScrollPane(textDisplayFloating);
		textDisplayFloatingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		GridBagConstraints gbc_textDisplayUpcomingScrollPane = new GridBagConstraints();
		gbc_textDisplayUpcomingScrollPane.fill = GridBagConstraints.BOTH;
		gbc_textDisplayUpcomingScrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_textDisplayUpcomingScrollPane.gridx = 0;
		gbc_textDisplayUpcomingScrollPane.gridy = 1;
		gbc_textDisplayUpcomingScrollPane.gridwidth = 2;
		
		widgetPanel.add(textDisplayFloatingScrollPane, gbc_textDisplayUpcomingScrollPane);
	}
	
	private void initializeFloatingTasksButton() {
		buttonFloating = new JButton("Floating Tasks");
		buttonFloating.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonFloating.setForeground(new Color(224, 255, 255));
		buttonFloating.setBackground(new Color(70, 130, 180));
		buttonFloating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickFloating();
			}
		});
		
		GridBagConstraints gbc_buttonUpcoming = new GridBagConstraints();
		gbc_buttonUpcoming.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonUpcoming.anchor = GridBagConstraints.WEST;
		gbc_buttonUpcoming.insets = new Insets(0, 0, 2, 0);
		gbc_buttonUpcoming.gridx = 0;
		gbc_buttonUpcoming.gridy = 0;
		
		widgetPanel.add(buttonFloating, gbc_buttonUpcoming);
	}
	
	public void clickFloating() {
		Task task = generateTaskForFloating();
		
		String feedback;
		try {
			feedback = WhatsUpNextGUI.logic.executeTask(task);
		} catch (Exception e) {
			feedback = e.getMessage();
		}
		
		displayFloatingFeedback(feedback);
	}
	
	private void displayFloatingFeedback(String feedback) {
	//	textDisplayFloating.setText(feedback);
		appendToPane(feedback);
	}
	
	private Task generateTaskForFloating() {
		Task task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.FLOATING);
		return task;
	}

	
	/**
	 * This function displays feedback with customized coloring
	 * @param feedback
	 */
	private void appendToPane(String feedback) {
		textDisplayFloating.setText(feedback);
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
            	 StyleConstants.setFontSize(set, 12);      
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
