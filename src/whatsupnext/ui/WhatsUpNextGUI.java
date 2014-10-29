package whatsupnext.ui;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import whatsupnext.logic.Logic;

/*
 * This class is used for GUI of software WhatsUpNext
 */
public class WhatsUpNextGUI {
	
	static JFrame frameMain;
	private final int FRAME_MAIN_WIDTH = 555;
	private final int FRAME_MAIN_HEIGHT = 300;
	
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
					new WhatsUpNextGUI();
					WhatsUpNextGUI.frameMain.setLocationByPlatform(true);
					WhatsUpNextGUI.frameMain.setVisible(true);
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
		frameMain.getContentPane().setBackground(new Color(204, 224, 250));
		frameMain.getContentPane().setLayout(null);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.setBounds(0, 0, FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT);
		frameMain.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
//				resetComponentSizes();
				mainDisplayWidget.resize();
			}
		});
		frameMain.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				deleteRevisions();
			}
		});
	}
	
	private void deleteRevisions() {
		logic.clearRevisionFiles();
	}
	
	/**
	 *  Display area for upcoming tasks
	 */
	

//	private void resetComponentSizes() {
//		Rectangle frameSize = frameMain.getBounds();
//		
//		int pixelsFromCLIToBottom = FRAME_MAIN_HEIGHT - (TEXT_INPUT_DIMENSIONS[1] + TEXT_INPUT_DIMENSIONS[3]);
//		int pixelsFromDisplayToCLI = TEXT_INPUT_DIMENSIONS[1] - (TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1] + TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[3]);
//		int pixelsFromUpcomingToRight = FRAME_MAIN_WIDTH - (TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0] + TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[2]);
//		int pixelsFromMainToUpcoming = TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0] - (TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0] + TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[2]);
//		
//		int upcomingDisplayRelativeX = (int)(frameSize.width * ((double)(TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[0]) / FRAME_MAIN_WIDTH));
//		int upcomingDisplayRelativeWidth = frameSize.width - (upcomingDisplayRelativeX + pixelsFromUpcomingToRight);
//
//		buttonUpcoming.setBounds(
//				upcomingDisplayRelativeX,
//				BUTTON_UPCOMING_DIMENSIONS[1],
//				upcomingDisplayRelativeWidth,
//				BUTTON_ENTER_DIMENSIONS[3]);
//		textDisplayUpcomingScrollPane.setBounds(
//				upcomingDisplayRelativeX,
//				TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[1],
//				upcomingDisplayRelativeWidth,
//				frameSize.height - TEXT_DISPLAY_UPCOMING_SCROLL_PANE_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
//		// Don't change textDisplayUpcoming x and y because they are relative to the textDisplayUpcomingScrollPane
//		textDisplayUpcoming.setBounds(
//				TEXT_DISPLAY_UPCOMING_DIMENSIONS[0],
//				TEXT_DISPLAY_UPCOMING_DIMENSIONS[1],
//				upcomingDisplayRelativeWidth,
//				frameSize.height - TEXT_DISPLAY_UPCOMING_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
//		
//		labelWelcome.setBounds(
//				LABEL_WELCOME_DIMENSIONS[0],
//				LABEL_WELCOME_DIMENSIONS[1],
//				frameSize.width - LABEL_WELCOME_DIMENSIONS[0] - (pixelsFromMainToUpcoming + upcomingDisplayRelativeWidth + pixelsFromUpcomingToRight),
//				LABEL_WELCOME_DIMENSIONS[3]);
//		textDisplayMainScrollPane.setBounds(
//				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0],
//				TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1],
//				frameSize.width - TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[0] - (pixelsFromMainToUpcoming + upcomingDisplayRelativeWidth + pixelsFromUpcomingToRight),
//				frameSize.height - TEXT_DISPLAY_MAIN_SCROLL_PANE_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
//		// Don't change textDisplayMain x and y because they are relative to the textDisplayMainScrollPane
//		textDisplayMain.setBounds(
//				TEXT_DISPLAY_MAIN_DIMENSIONS[0],
//				TEXT_DISPLAY_MAIN_DIMENSIONS[1],
//				frameSize.width - TEXT_DISPLAY_MAIN_DIMENSIONS[0] - (pixelsFromMainToUpcoming + upcomingDisplayRelativeWidth + pixelsFromUpcomingToRight),
//				frameSize.height - TEXT_DISPLAY_MAIN_DIMENSIONS[1] - (pixelsFromDisplayToCLI + TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom));
//		
//		textInput.setBounds(
//				TEXT_INPUT_DIMENSIONS[0],
//				frameSize.height - (TEXT_INPUT_DIMENSIONS[3] + pixelsFromCLIToBottom),
//				frameSize.width - TEXT_INPUT_DIMENSIONS[0] - (BUTTON_ENTER_DIMENSIONS[2] + pixelsFromUpcomingToRight),
//				TEXT_INPUT_DIMENSIONS[3]);
//		buttonEnter.setBounds(
//				frameSize.width - (BUTTON_ENTER_DIMENSIONS[2] + pixelsFromUpcomingToRight),
//				frameSize.height - (BUTTON_ENTER_DIMENSIONS[3] + pixelsFromCLIToBottom),
//				BUTTON_ENTER_DIMENSIONS[2],
//				BUTTON_ENTER_DIMENSIONS[3]);
//	}
}
