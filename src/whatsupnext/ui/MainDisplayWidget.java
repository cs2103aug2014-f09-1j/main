package whatsupnext.ui;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainDisplayWidget {
	
	private JFrame frameMain = WhatsUpNextGUI.frameMain;
	
	private JPanel widgetPanel;
	private final int[] PANEL_DIMENSIONS = {10, 10, 328, 205};

    private String STRING_WELCOME = "Welcome to WhatsUpNext! Today is ";
    private JLabel labelWelcome;
	private JScrollPane textDisplayMainScrollPane;
	private JTextArea textDisplayMain;
	
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
		widgetPanel.setName("mainDisplayWidgetPanel");
    	labelWelcome.setName("labelWelcome");
		textDisplayMain.setName("textDisplayMain");
		textDisplayMainScrollPane.setName("textDisplayMainScrollPane");
    }

	private void initializeMainDisplayPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		widgetPanel.setBounds(
				PANEL_DIMENSIONS[0],
				PANEL_DIMENSIONS[1],
				PANEL_DIMENSIONS[2],
				PANEL_DIMENSIONS[3]);
//		widgetPanel.setPreferredSize(new Dimension(PANEL_DIMENSIONS[2], PANEL_DIMENSIONS[3]));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWidths = new int[]{PANEL_DIMENSIONS[2]};
		gbl_widgetPanel.rowHeights = new int[]{15, 180};
		gbl_widgetPanel.columnWeights = new double[]{1.0};
		gbl_widgetPanel.rowWeights = new double[]{0.0, 1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeWelcomeMessage();
		initializeMainTextDisplay();
		
		frameMain.getContentPane().add(widgetPanel);
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
		labelWelcome.setFont(new Font("Cambria", Font.BOLD, 12));
		
		GridBagConstraints gbc_labelWelcome = new GridBagConstraints();
		gbc_labelWelcome.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelWelcome.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelWelcome.insets = new Insets(0, 0, 5, 0);
		gbc_labelWelcome.gridx = 0;
		gbc_labelWelcome.gridy = 0;
		
		widgetPanel.add(labelWelcome, gbc_labelWelcome);
	}
	
	private void initializeMainTextDisplay() {
		textDisplayMain = new JTextArea();
		textDisplayMain.setFont(new Font("Courier New", Font.BOLD, 12));
		textDisplayMain.setForeground(new Color(25, 25, 112));
		textDisplayMain.setText("---Please enter command below:\r\n");
		textDisplayMain.setEditable(false);
		textDisplayMain.setBackground(new Color(240, 255, 255));
		
		textDisplayMainScrollPane = new JScrollPane(textDisplayMain);
		textDisplayMainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		GridBagConstraints gbc_textDisplayMainScrollPane = new GridBagConstraints();
		gbc_textDisplayMainScrollPane.fill = GridBagConstraints.BOTH;
		gbc_textDisplayMainScrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_textDisplayMainScrollPane.gridx = 0;
		gbc_textDisplayMainScrollPane.gridy = 1;
		
		widgetPanel.add(textDisplayMainScrollPane, gbc_textDisplayMainScrollPane);
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

	public void resize() {
		widgetPanel.revalidate();
		widgetPanel.repaint();
	}
}
