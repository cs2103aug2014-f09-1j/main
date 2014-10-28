package whatsupnext.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import whatsupnext.parser.extractor.ParseDate;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

public class UpcomingTasksWidget {

	private JFrame frameMain = WhatsUpNextGUI.frameMain;

	private JButton buttonUpcoming;
	private final int[] BUTTON_UPCOMING_DIMENSIONS = {356, 5, 174, 28};
	private JScrollPane textDisplayUpcomingScrollPane;
	private final int[] TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS = {356, 35, 174, 180};
	private JTextArea textDisplayUpcoming;
	private final int[] TEXT_DISPLAY_UPCOMING_DIMENSIONS = {0, 0, 174, 180};
	
	private String currentYear;
	
	public UpcomingTasksWidget() {
		initializeCurrentYear();
		initializeUpcomingTasksPanel();
		setComponentNames();
	}
	
	private void initializeCurrentYear() {
    	DateFormat dateFormat = new SimpleDateFormat(" yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
	}
	
	private void setComponentNames() {
		buttonUpcoming.setName("buttonUpcoming");
		textDisplayUpcomingScrollPane.setName("textDisplayUpcomingScrollPane");
		textDisplayUpcoming.setName("textDisplayUpcoming");
	}

	private void initializeUpcomingTasksPanel() {
		initializeUpcomingTasksTextDisplay();
		initializeUpcomingTasksButton();
	}
	
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
		buttonUpcoming.setBounds(
				BUTTON_UPCOMING_DIMENSIONS[0],
				BUTTON_UPCOMING_DIMENSIONS[1],
				BUTTON_UPCOMING_DIMENSIONS[2],
				BUTTON_UPCOMING_DIMENSIONS[3]);
		frameMain.getContentPane().add(buttonUpcoming);
	}
	
	private void displayUpcomingFeedback(String feedback) {
		feedback = feedback.replaceAll(currentYear, "");
		textDisplayUpcoming.setText(feedback);
	}
	
	public void clickUpcoming() {
		// Get a list of most recent tasks and display
		Task task = generateTaskForUpcoming();
		
		String feedback;
		try {
			feedback = WhatsUpNextGUI.logic.executeTask(task);
		} catch (Exception e) {
			feedback = e.getMessage();
		}
		
		displayUpcomingFeedback(feedback);
	}

	/**
	 *  view all tasks within today
	 * @return	the task that holds the view time frame task for upcoming
	 */
	private static Task generateTaskForUpcoming() {
		ParseDate parseDate = new ParseDate();
		Task task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.TIMEFRAME);
		task.setStartTime(parseDate.getTodayDateString() + "0000");
		task.setEndTime(parseDate.getTodayDateString() + "2359");
		return task;
	}
}
