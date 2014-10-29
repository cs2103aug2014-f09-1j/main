package whatsupnext.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import whatsupnext.parser.extractor.ParseDate;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

public class UpcomingTasksWidget {

	private JPanel widgetPanel;
	private final int PANEL_WIDTH = 174;
	private final int PANEL_HEIGHT = 210;
	
	private JButton buttonUpcoming;
	private JScrollPane textDisplayUpcomingScrollPane;
	private JTextArea textDisplayUpcoming;
	
	private String currentYear;
	
	public UpcomingTasksWidget() {
		initializeCurrentYear();
		initializeUpcomingTasksPanel();
		setComponentNames();
	}
	
	public JPanel getWidgetPanel() {
    	return widgetPanel;
    }
	
	private void initializeCurrentYear() {
    	DateFormat dateFormat = new SimpleDateFormat(" yyyy");
		Calendar cal = Calendar.getInstance();
		currentYear = dateFormat.format(cal.getTime());
	}
	
	private void setComponentNames() {
		widgetPanel.setName("upcomingTasksWidgetPanel");
		buttonUpcoming.setName("buttonUpcoming");
		textDisplayUpcomingScrollPane.setName("textDisplayUpcomingScrollPane");
		textDisplayUpcoming.setName("textDisplayUpcoming");
	}

	private void initializeUpcomingTasksPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		widgetPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWidths = new int[]{PANEL_WIDTH};
		gbl_widgetPanel.rowHeights = new int[]{28, 180};
		gbl_widgetPanel.columnWeights = new double[]{1.0};
		gbl_widgetPanel.rowWeights = new double[]{0.0, 1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeUpcomingTasksTextDisplay();
		initializeUpcomingTasksSelector();
	}
	
	private void initializeUpcomingTasksTextDisplay() {
		textDisplayUpcoming = new JTextArea();
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
		gbc_textDisplayUpcomingScrollPane.gridwidth = 3;
		
		widgetPanel.add(textDisplayUpcomingScrollPane, gbc_textDisplayUpcomingScrollPane);
	}
	
	private void initializeUpcomingTasksSelector() {
		createUpcomingButton();
		createForLabel();
		createTimeLengthCombobox();
	}
	
	private void createUpcomingButton() {
		buttonUpcoming = new JButton("Upcoming Tasks");
		buttonUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonUpcoming.setForeground(new Color(224, 255, 255));
		buttonUpcoming.setBackground(new Color(70, 130, 180));
		buttonUpcoming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickUpcoming();
			}
		});
		
		GridBagConstraints gbc_buttonUpcoming = new GridBagConstraints();
		gbc_buttonUpcoming.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonUpcoming.anchor = GridBagConstraints.NORTHWEST;
		gbc_buttonUpcoming.insets = new Insets(0, 0, 2, 0);
		gbc_buttonUpcoming.gridx = 0;
		gbc_buttonUpcoming.gridy = 0;
		
		widgetPanel.add(buttonUpcoming, gbc_buttonUpcoming);
	}

	private void createForLabel() {
		// TODO Auto-generated method stub
		
	}

	private void createTimeLengthCombobox() {
		// TODO Auto-generated method stub
		
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
