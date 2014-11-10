//@author A0126730M
package whatsupnext.ui.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import whatsupnext.parser.extractor.ParseDate;
import whatsupnext.structure.enums.OPCODE;
import whatsupnext.structure.enums.Types.VIEWTYPE;
import whatsupnext.structure.util.Task;
import whatsupnext.ui.GUIAbstract;

public class UpcomingTasksWidget implements TasksWidget{

	private JPanel widgetPanel;

	private final String[] timeOptions = {"1 day", "2 days", "3 days", "4 days", "5 days", "6 days", "1 week", "2 weeks", "3 weeks", "1 month"};
	private String selectedTimeOption;
	private JComboBox<String> timeLengthCombobox;
	private JButton buttonUpcoming;
	
	private JScrollPane textDisplayUpcomingScrollPane;
	private JTextPane textDisplayUpcoming;
	
	private String currentYear;
	
	private Random random = new Random();
	private int currentColorIndex;
	private Color[] colors = {new Color(220,20,60),new Color(184,134,11),
                              new Color(139,0,139),new Color(188,143,143)};
	private Color titleBackground = new Color(240,255,240);
	private Color titleForeground = new Color(46,139,87);

	private StyledDocument doc = new DefaultStyledDocument();
	
	
	public UpcomingTasksWidget() {
		initializeCurrentYear();
		selectedTimeOption = timeOptions[0];
		initializeUpcomingTasksPanel();
		setComponentNames();
	}
	
	@Override
	public void doActionOnClick() {
		clickUpcoming();
	}
	
	public JPanel getWidgetPanel() {
    	return widgetPanel;
    }
	
	private void initializeCurrentYear() {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
	}
	
	private void setComponentNames() {
		widgetPanel.setName("upcomingTasksWidgetPanel");
		buttonUpcoming.setName("buttonUpcoming");
		timeLengthCombobox.setName("timeLengthCombobox");
		textDisplayUpcomingScrollPane.setName("textDisplayUpcomingScrollPane");
		textDisplayUpcoming.setName("textDisplayUpcoming");
	}

	private void initializeUpcomingTasksPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWeights = new double[]{0.9, 0.1};
		gbl_widgetPanel.rowWeights = new double[]{0.0, 1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeUpcomingTasksTextDisplay();
		initializeUpcomingTasksSelector();
	}
	
	private void initializeUpcomingTasksTextDisplay() {
		textDisplayUpcoming = new JTextPane(doc);
		textDisplayUpcoming.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayUpcoming.setForeground(new Color(25, 25, 112));
		textDisplayUpcoming.setEditable(false);
		textDisplayUpcoming.setBackground(new Color(240, 255, 255));
		
		textDisplayUpcomingScrollPane = new JScrollPane(textDisplayUpcoming);
		textDisplayUpcomingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		GridBagConstraints gbc_textDisplayUpcomingScrollPane = new GridBagConstraints();
		gbc_textDisplayUpcomingScrollPane.fill = GridBagConstraints.BOTH;
		gbc_textDisplayUpcomingScrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_textDisplayUpcomingScrollPane.gridx = 0;
		gbc_textDisplayUpcomingScrollPane.gridy = 1;
		gbc_textDisplayUpcomingScrollPane.gridwidth = 2;
		
		widgetPanel.add(textDisplayUpcomingScrollPane, gbc_textDisplayUpcomingScrollPane);
	}
	
	private void initializeUpcomingTasksSelector() {
		createUpcomingButton();
		createTimeLengthCombobox();
	}
	
	private void createUpcomingButton() {
		buttonUpcoming = new JButton("Upcoming");
		buttonUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonUpcoming.setForeground(new Color(224, 255, 255));
		buttonUpcoming.setBackground(new Color(70, 130, 180));
		buttonUpcoming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickUpcoming();
			}
		});
		
		GridBagConstraints gbc_buttonUpcoming = new GridBagConstraints();
		gbc_buttonUpcoming.fill = GridBagConstraints.BOTH;
		gbc_buttonUpcoming.anchor = GridBagConstraints.WEST;
		gbc_buttonUpcoming.insets = new Insets(0, 0, 2, 0);
		gbc_buttonUpcoming.gridx = 0;
		gbc_buttonUpcoming.gridy = 0;
		
		widgetPanel.add(buttonUpcoming, gbc_buttonUpcoming);
	}

	private void createTimeLengthCombobox() {
		timeLengthCombobox = new JComboBox<String>(timeOptions);
		timeLengthCombobox.setSelectedIndex(0);
		timeLengthCombobox.setFont(new Font("Cambria", Font.PLAIN, 12));
		timeLengthCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
		        selectedTimeOption = (String)cb.getSelectedItem();
		        clickUpcoming();
			}
		});
		
		GridBagConstraints gbc_timeLengthCombobox = new GridBagConstraints();
		gbc_timeLengthCombobox.fill = GridBagConstraints.BOTH;
		gbc_timeLengthCombobox.anchor = GridBagConstraints.EAST;
		gbc_timeLengthCombobox.insets = new Insets(0, 0, 2, 0);
		gbc_timeLengthCombobox.gridx = 1;
		gbc_timeLengthCombobox.gridy = 0;
		
		widgetPanel.add(timeLengthCombobox, gbc_timeLengthCombobox);
	}

	private void displayUpcomingFeedback(String feedback) {
		feedback = feedback.replaceAll(" " + currentYear, "");
		feedback = feedback.replaceAll(currentYear + " ", "");
		feedback = feedback.replaceAll(currentYear, "");
		appendToPane(feedback);
	}
	
	public void clickUpcoming() {
		// Get a list of most recent tasks and display
		Task task = generateTaskForUpcoming(selectedTimeOption);
		
		String feedback;
		try {
			feedback = GUIAbstract.getLogic().executeTask(task);
		} catch (Exception e) {
			feedback = e.getMessage();
		}
		
		displayUpcomingFeedback(feedback);
	}

	/**
	 *  view all tasks within today
	 * @return	the task that holds the view time frame task for upcoming
	 */
	private Task generateTaskForUpcoming(String timeOption) {
		ParseDate parseDate = new ParseDate();
		Task task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.TIMEFRAME);
		task.setStartTime(parseDate.getTodayDate() + "0000");
		
		Calendar cal = Calendar.getInstance();
		if (timeOption.equalsIgnoreCase(timeOptions[0])) {
			cal.add(Calendar.DATE, 0);
		} else if (timeOption.equalsIgnoreCase(timeOptions[1])) {
			cal.add(Calendar.DATE, 1);
		} else if (timeOption.equalsIgnoreCase(timeOptions[2])) {
			cal.add(Calendar.DATE, 2);
		} else if (timeOption.equalsIgnoreCase(timeOptions[3])) {
			cal.add(Calendar.DATE, 3);
		} else if (timeOption.equalsIgnoreCase(timeOptions[4])) {
			cal.add(Calendar.DATE, 4);
		} else if (timeOption.equalsIgnoreCase(timeOptions[5])) {
			cal.add(Calendar.DATE, 5);
		} else if (timeOption.equalsIgnoreCase(timeOptions[6])) {
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		} else if (timeOption.equalsIgnoreCase(timeOptions[7])) {
			cal.add(Calendar.WEEK_OF_YEAR, 2);
		} else if (timeOption.equalsIgnoreCase(timeOptions[8])) {
			cal.add(Calendar.WEEK_OF_YEAR, 3);
		} else if (timeOption.equalsIgnoreCase(timeOptions[9])) {
			cal.add(Calendar.MONTH, 1);
		}
		task.setEndTime(parseDate.getDateString(cal) + "2359");
		
		return task;
	}
	
	
	
	//@author A0092165E
	/**
	 * This function displays feedback with customized coloring
	 * @param feedback
	 */
	private void appendToPane(String feedback) {
		textDisplayUpcoming.setText(feedback);
		int numOfNewline = countSubstring("\n", feedback);
		
		String[] subStrings = feedback.trim().split("\n");
		int currentStart = 0;
		int currentEnd = feedback.indexOf("\n",1);
		
		String subString;
		boolean newtask = true;
		Color lastColor = generateNewColor();
      			
	     for (int i = 0; i <= numOfNewline; i++) {
	    	 currentEnd = feedback.indexOf("\n",currentStart+1);
	    	 if (currentEnd < 0) {
	    		 currentEnd = doc.getLength();
	    	 }
             SimpleAttributeSet set = new SimpleAttributeSet();
 
             subString = subStrings[i]; 
             // This line is a task title
             if (isNewTask(subString)) {
            	 StyleConstants.setBold(set, true); 
            	 StyleConstants.setFontSize(set, 12);      
            	 StyleConstants.setBackground(set, titleBackground);
            	 StyleConstants.setForeground(set, titleForeground);
            	 newtask = true;
            	 
             } else {
            	 StyleConstants.setBold(set, false); 
            	 StyleConstants.setFontSize(set, 11);
            	 if (newtask) {
                    lastColor = generateNewColor();
                    newtask = false;
            	 } 
            	 StyleConstants.setForeground(set, lastColor);
             }
             doc.setCharacterAttributes(currentStart, currentEnd - currentStart + 1, set, true);
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
	private boolean isNewTask(String subString) {
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
