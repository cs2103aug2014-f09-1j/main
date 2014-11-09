package whatsupnext.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import whatsupnext.logic.Logic;
import whatsupnext.ui.widgets.CommandLineInterfaceWidget;
import whatsupnext.ui.widgets.FloatingTasksWidget;
import whatsupnext.ui.widgets.MainDisplayWidget;
import whatsupnext.ui.widgets.ResetButtonWidget;
import whatsupnext.ui.widgets.UpcomingTasksWidget;

public class GUIOneWindow extends GUIAbstract{

	private JFrame frameMain;
	private final int FRAME_MAIN_WIDTH = 780;
	private final int FRAME_MAIN_HEIGHT = 442;
	
	private final int PANEL_MAIN_WIDTH = 350;
	private final int PANEL_MAIN_HEIGHT = 442;
	private final int PANEL_FLOATING_WIDTH = 350;
	private final int PANEL_FLOATING_HEIGHT = 221;
	private final int PANEL_UPCOMING_WIDTH = 350;
	private final int PANEL_UPCOMING_HEIGHT = 221;
	
	private JPanel mainPanel;
	private JPanel floatingPanel;
	private JPanel upcomingPanel;
	private JPanel resetPanel;
	private MainDisplayWidget mainDisplayWidget;
	private CommandLineInterfaceWidget cliWidget;
	private FloatingTasksWidget floatingWidget;
	private UpcomingTasksWidget upcomingWidget;
	private ResetButtonWidget resetWidget;
	
	private static Logic logic;

	
	public GUIOneWindow() {
		logic = new Logic();
		initGUIComponents();
		setComponentsNames();
		displayWidgetTasks();
	}
	
	public GUIOneWindow(String fileName) {
		logic = new Logic(fileName);
		initGUIComponents();
		setComponentsNames();
		displayWidgetTasks();
	}
	
	/**
	 * Returns the main frame used in the GUI
	 * 
	 * @return The JFrame for the main application
	 */
	public JFrame getMainFrame() {
		return frameMain;
	}

	public static Logic getLogic() {
		return logic;
	}
	
	public void showWindows() {
		frameMain.setLocationRelativeTo(null);
		frameMain.setVisible(true);
		frameMain.pack();
		
		cliWidget.requestFocus();
	}
	
	public void hideWindows() {
		frameMain.setVisible(false);
	}
	
	@Override
	public void reset() {
		frameMain.pack();
		displayWidgetTasks();
		
		cliWidget.requestFocus();
	}
	
	/**
	 * Names every component used in the GUI
	 */
	private void setComponentsNames() {
		frameMain.setName("frameMain");
	}
	
	private void displayWidgetTasks() {
		floatingWidget.clickFloating();
		upcomingWidget.clickUpcoming();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initGUIComponents() {
		initializeApplicationFrames();
		initializeWidgets();
		initializeWindowPanels();
	}
	
	/**
	 * Initialize frame of the application
	 */
	private void initializeApplicationFrames() {
		createMainFrame();
	}
	
	private void createMainFrame() {
		frameMain = new JFrame();
		frameMain.setResizable(true);
		frameMain.setIconImage(Toolkit.getDefaultToolkit().getImage(GUIOneWindow.class.getResource("/whatsupnext/ui/iconGUI.png")));
		frameMain.setType(Type.POPUP);
		frameMain.setFont(new Font("Cambria", Font.BOLD, 12));
		frameMain.getContentPane().setBackground(new Color(204, 224, 250));
		frameMain.setTitle("WhatsUpNext");
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.setPreferredSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		frameMain.setMinimumSize(new Dimension(FRAME_MAIN_WIDTH - 40, FRAME_MAIN_HEIGHT - 110));
		
		GridBagLayout gbl_mainFrame = new GridBagLayout();
		gbl_mainFrame.columnWidths = new int[]{400, 300, 15};
		gbl_mainFrame.rowHeights = new int[]{15, 235, 235};
		gbl_mainFrame.columnWeights = new double[]{0.5, 0.5, 0.0};
		gbl_mainFrame.rowWeights = new double[]{0.0, 0.5, 0.5};
		frameMain.getContentPane().setLayout(gbl_mainFrame);
		
		frameMain.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				deleteRevisions();
			}
		});
	}
	
	private void initializeWidgets() {
		mainDisplayWidget = new MainDisplayWidget();
		floatingWidget = new FloatingTasksWidget();
		upcomingWidget = new UpcomingTasksWidget();
		
		cliWidget = new CommandLineInterfaceWidget(mainDisplayWidget);
		cliWidget.linkToWidget(floatingWidget);
		cliWidget.linkToWidget(upcomingWidget);
		
		resetWidget = new ResetButtonWidget(this);
	}
	

	private void initializeWindowPanels() {
		initializeMainPanel();
		initializeFloatingPanel();
		initializeUpcomingPanel();
		initializeResetPanel();
	}
	
	private void initializeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(204, 224, 250));
		mainPanel.setPreferredSize(new Dimension(PANEL_MAIN_WIDTH, PANEL_MAIN_HEIGHT));
		mainPanel.setMinimumSize(new Dimension(PANEL_MAIN_WIDTH, PANEL_MAIN_HEIGHT));
		
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{400};
		gbl_mainPanel.rowHeights = new int[]{235};
		gbl_mainPanel.columnWeights = new double[]{1.0};
		gbl_mainPanel.rowWeights = new double[]{1.0};
		mainPanel.setLayout(gbl_mainPanel);
		
		GridBagConstraints gbc_mainDisplayWidget = new GridBagConstraints();
		gbc_mainDisplayWidget.fill = GridBagConstraints.BOTH;
		gbc_mainDisplayWidget.anchor = GridBagConstraints.CENTER;
		gbc_mainDisplayWidget.insets = new Insets(0, 0, 5, 0);
		gbc_mainDisplayWidget.gridx = 0;
		gbc_mainDisplayWidget.gridy = 0;
		mainPanel.add(mainDisplayWidget.getWidgetPanel(), gbc_mainDisplayWidget);
		
		GridBagConstraints gbc_cliWidget = new GridBagConstraints();
		gbc_cliWidget.fill = GridBagConstraints.HORIZONTAL;
		gbc_cliWidget.anchor = GridBagConstraints.CENTER;
		gbc_cliWidget.insets = new Insets(0, 0, 0, 0);
		gbc_cliWidget.gridx = 0;
		gbc_cliWidget.gridy = 1;
		mainPanel.add(cliWidget.getWidgetPanel(), gbc_cliWidget);
		
		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.fill = GridBagConstraints.BOTH;
		gbc_mainPanel.anchor = GridBagConstraints.CENTER;
		gbc_mainPanel.insets = new Insets(0, 15, 15, 15);
		gbc_mainPanel.gridx = 0;
		gbc_mainPanel.gridy = 1;
		gbc_mainPanel.gridheight = 2;
		frameMain.getContentPane().add(mainPanel, gbc_mainPanel);
	}
	
	private void initializeFloatingPanel() {
		floatingPanel = new JPanel();
		floatingPanel.setBackground(new Color(204, 224, 250));
		floatingPanel.setPreferredSize(new Dimension(PANEL_FLOATING_WIDTH, PANEL_FLOATING_HEIGHT));
		floatingPanel.setMinimumSize(new Dimension(PANEL_FLOATING_WIDTH, PANEL_FLOATING_HEIGHT));
		
		GridBagLayout gbl_floatingPanel = new GridBagLayout();
		gbl_floatingPanel.columnWidths = new int[]{300};
		gbl_floatingPanel.rowHeights = new int[]{235};
		gbl_floatingPanel.columnWeights = new double[]{1.0};
		gbl_floatingPanel.rowWeights = new double[]{1.0};
		floatingPanel.setLayout(gbl_floatingPanel);
		
		GridBagConstraints gbc_floatingWidget = new GridBagConstraints();
		gbc_floatingWidget.fill = GridBagConstraints.BOTH;
		gbc_floatingWidget.anchor = GridBagConstraints.CENTER;
		gbc_floatingWidget.insets = new Insets(0, 0, 0, 0);
		gbc_floatingWidget.gridx = 0;
		gbc_floatingWidget.gridy = 0;
		floatingPanel.add(floatingWidget.getWidgetPanel(), gbc_floatingWidget);
		
		GridBagConstraints gbc_floatingPanel = new GridBagConstraints();
		gbc_floatingPanel.fill = GridBagConstraints.BOTH;
		gbc_floatingPanel.anchor = GridBagConstraints.CENTER;
		gbc_floatingPanel.insets = new Insets(0, 0, 15, 0);
		gbc_floatingPanel.gridx = 1;
		gbc_floatingPanel.gridy = 1;
		frameMain.getContentPane().add(floatingPanel, gbc_floatingPanel);
	}
	
	private void initializeUpcomingPanel() {
		upcomingPanel = new JPanel();
		upcomingPanel.setBackground(new Color(204, 224, 250));
		upcomingPanel.setPreferredSize(new Dimension(PANEL_UPCOMING_WIDTH, PANEL_UPCOMING_HEIGHT));
		upcomingPanel.setMinimumSize(new Dimension(PANEL_UPCOMING_WIDTH, PANEL_UPCOMING_HEIGHT));
		
		GridBagLayout gbl_upcomingPanel = new GridBagLayout();
		gbl_upcomingPanel.columnWidths = new int[]{350};
		gbl_upcomingPanel.rowHeights = new int[]{235};
		gbl_upcomingPanel.columnWeights = new double[]{1.0};
		gbl_upcomingPanel.rowWeights = new double[]{1.0};
		upcomingPanel.setLayout(gbl_upcomingPanel);
		
		GridBagConstraints gbc_upcomingWidget = new GridBagConstraints();
		gbc_upcomingWidget.fill = GridBagConstraints.BOTH;
		gbc_upcomingWidget.anchor = GridBagConstraints.CENTER;
		gbc_upcomingWidget.insets = new Insets(0, 0, 0, 0);
		gbc_upcomingWidget.gridx = 0;
		gbc_upcomingWidget.gridy = 0;
		upcomingPanel.add(upcomingWidget.getWidgetPanel(), gbc_upcomingWidget);
		
		GridBagConstraints gbc_upcomingPanel = new GridBagConstraints();
		gbc_upcomingPanel.fill = GridBagConstraints.BOTH;
		gbc_upcomingPanel.anchor = GridBagConstraints.CENTER;
		gbc_upcomingPanel.insets = new Insets(0, 0, 15, 0);
		gbc_upcomingPanel.gridx = 1;
		gbc_upcomingPanel.gridy = 2;
		frameMain.getContentPane().add(upcomingPanel, gbc_upcomingPanel);
	}
	
	private void initializeResetPanel() {
		resetPanel = new JPanel();
		resetPanel.setBackground(new Color(204, 224, 250));
		resetPanel.setPreferredSize(new Dimension(28, 28));
		resetPanel.setMinimumSize(new Dimension(28, 28));
		
		GridBagLayout gbl_resetPanel = new GridBagLayout();
		gbl_resetPanel.columnWidths = new int[]{28};
		gbl_resetPanel.rowHeights = new int[]{28};
		gbl_resetPanel.columnWeights = new double[]{1.0};
		gbl_resetPanel.rowWeights = new double[]{1.0};
		resetPanel.setLayout(gbl_resetPanel);
		
		GridBagConstraints gbc_resetWidget = new GridBagConstraints();
		gbc_resetWidget.fill = GridBagConstraints.BOTH;
		gbc_resetWidget.anchor = GridBagConstraints.CENTER;
		gbc_resetWidget.insets = new Insets(0, 0, 0, 0);
		gbc_resetWidget.gridx = 0;
		gbc_resetWidget.gridy = 0;
		resetPanel.add(resetWidget.getWidgetPanel(), gbc_resetWidget);
		
		GridBagConstraints gbc_resetPanel = new GridBagConstraints();
		gbc_resetPanel.anchor = GridBagConstraints.NORTHEAST;
		gbc_resetPanel.insets = new Insets(0, 0, 0, 15);
		gbc_resetPanel.gridx = 0;
		gbc_resetPanel.gridy = 1;
		frameMain.getContentPane().add(resetPanel, gbc_resetPanel, 0);
	}
	
	private void deleteRevisions() {
		getLogic().clearRevisionFiles();
	}

}
