package whatsupnext.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import whatsupnext.logic.Logic;

/*
 * This class is used for GUI of software WhatsUpNext
 */
public class WhatsUpNextGUI {
	
	private JFrame frameMain;
	private final int FRAME_MAIN_WIDTH = 580;
	private final int FRAME_MAIN_HEIGHT = 300;
	
	private JPanel mainPanel;
	private MainDisplayWidget mainDisplayWidget;
	private CommandLineInterfaceWidget cliWidget;
	private UpcomingTasksWidget upcomingWidget;
	
	static Logic logic;
	
	/**
	 * Launch the application.
	 * Sets window visibility and size.
	 */
	public static void main(String[] args) {
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhatsUpNextGUI gui = new WhatsUpNextGUI();
					gui.frameMain.setLocationByPlatform(true);
					gui.frameMain.setVisible(true);
					gui.frameMain.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 * Be sure to initialize the GUIComponents before the GUIFunctionality
	 */
	public WhatsUpNextGUI() {
		logic = new Logic();
		initGUIComponents();
		setComponentsNames();
		upcomingWidget.clickUpcoming();
	}
	
	public WhatsUpNextGUI(String fileName) {
		logic = new Logic(fileName);
		initGUIComponents();
		setComponentsNames();
		upcomingWidget.clickUpcoming();
	}
	
	/**
	 * Returns the main frame used in the GUI
	 * 
	 * @return The JFrame for the main application
	 */
	public JFrame getMainFrame() {
		return frameMain;
	}
	
	/**
	 * Names every component used in the GUI
	 */
	private void setComponentsNames() {
		frameMain.setName("frameMain");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initGUIComponents() {
		initializeApplicationFrame();
		
		mainDisplayWidget = new MainDisplayWidget();
		upcomingWidget = new UpcomingTasksWidget();
		cliWidget = new CommandLineInterfaceWidget(mainDisplayWidget, upcomingWidget);
		
		initializeMainPanel();
	}
	
	/**
	 * Initialize frame of the application
	 */
	private void initializeApplicationFrame() {
		frameMain = new JFrame();
		frameMain.setResizable(true);
		frameMain.setIconImage(Toolkit.getDefaultToolkit().getImage(WhatsUpNextGUI.class.getResource("/whatsupnext/ui/iconGUI.png")));
		frameMain.setType(Type.POPUP);
		frameMain.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.setTitle("WhatsUpNext");
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.setPreferredSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		frameMain.setMinimumSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		
		frameMain.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				deleteRevisions();
			}
		});
	}
	
	private void initializeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(204, 224, 250));
		mainPanel.setPreferredSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		mainPanel.setMinimumSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{330, 200};
		gbl_mainPanel.rowHeights = new int[]{210, 25};
		gbl_mainPanel.columnWeights = new double[]{0.8, 0.2};
		gbl_mainPanel.rowWeights = new double[]{1.0, 0.0};
		mainPanel.setLayout(gbl_mainPanel);
		
		GridBagConstraints gbc_mainDisplayWidget = new GridBagConstraints();
		gbc_mainDisplayWidget.fill = GridBagConstraints.BOTH;
		gbc_mainDisplayWidget.anchor = GridBagConstraints.NORTHWEST;
		gbc_mainDisplayWidget.insets = new Insets(5, 10, 10, 10);
		gbc_mainDisplayWidget.gridx = 0;
		gbc_mainDisplayWidget.gridy = 0;
		mainPanel.add(mainDisplayWidget.getWidgetPanel(), gbc_mainDisplayWidget);
		
		GridBagConstraints gbc_upcomingWidget = new GridBagConstraints();
		gbc_upcomingWidget.fill = GridBagConstraints.BOTH;
		gbc_upcomingWidget.anchor = GridBagConstraints.NORTHWEST;
		gbc_upcomingWidget.insets = new Insets(5, 0, 10, 10);
		gbc_upcomingWidget.gridx = 1;
		gbc_upcomingWidget.gridy = 0;
		mainPanel.add(upcomingWidget.getWidgetPanel(), gbc_upcomingWidget);
		
		GridBagConstraints gbc_cliWidget = new GridBagConstraints();
		gbc_cliWidget.fill = GridBagConstraints.HORIZONTAL;
		gbc_cliWidget.anchor = GridBagConstraints.WEST;
		gbc_cliWidget.insets = new Insets(0, 10, 10, 10);
		gbc_cliWidget.gridx = 0;
		gbc_cliWidget.gridy = 1;
		gbc_cliWidget.gridwidth = 2;
		mainPanel.add(cliWidget.getWidgetPanel(), gbc_cliWidget);
		
		frameMain.getContentPane().add(mainPanel);
	}
	
	private void deleteRevisions() {
		logic.clearRevisionFiles();
	}
	
}
