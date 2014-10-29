package whatsupnext.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;
import whatsupnext.structure.Types.VIEWTYPE;

public class FloatingTasksWidget {
	private JPanel widgetPanel;
	private final int PANEL_WIDTH = 174;
	private final int PANEL_HEIGHT = 210;
	
	private JButton buttonFloating;
	private JScrollPane textDisplayFloatingScrollPane;
	private JTextArea textDisplayFloating;
	
	
	public FloatingTasksWidget() {
		initializeFloatingTasksPanel();
		setComponentNames();
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
		gbl_widgetPanel.columnWidths = new int[]{80, 94};
		gbl_widgetPanel.rowHeights = new int[]{28, 180};
		gbl_widgetPanel.columnWeights = new double[]{0.9, 0.1};
		gbl_widgetPanel.rowWeights = new double[]{0.0, 1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeFloatingTasksTextDisplay();
		initializeFloatingTasksButton();
	}
	
	private void initializeFloatingTasksTextDisplay() {
		textDisplayFloating = new JTextArea();
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
		buttonFloating = new JButton("Upcoming");
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
	
	private void clickFloating() {
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
		textDisplayFloating.setText(feedback);
	}
	
	private Task generateTaskForFloating() {
		Task task = new Task();
		task.setOpcode(OPCODE.VIEW);
		task.setViewType(VIEWTYPE.FLOATING);
		return task;
	}
}
