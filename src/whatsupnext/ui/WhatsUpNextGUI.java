package whatsupnext.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import whatsupnext.logic.Logic;

/*
 * This class is used for GUI of software WhatsUpNext
 */
public class WhatsUpNextGUI {
	
	private JFrame frameMain;
	private final int FRAME_MAIN_WIDTH = 530;
	private final int FRAME_MAIN_HEIGHT = 360;
	private JFrame frameFloating;
	private final int FRAME_FLOATING_WIDTH = 380;
	private final int FRAME_FLOATING_HEIGHT = 350;
	private JFrame frameUpcoming;
	private final int FRAME_UPCOMING_WIDTH = 380;
	private final int FRAME_UPCOMING_HEIGHT = 350;
	
	private JPanel mainPanel;
	private JPanel floatingPanel;
	private JPanel upcomingPanel;
	private MainDisplayWidget mainDisplayWidget;
	private CommandLineInterfaceWidget cliWidget;
	private FloatingTasksWidget floatingWidget;
	private UpcomingTasksWidget upcomingWidget;
	
	private boolean movingAllFramesToFront = false;
	static Logic logic;
	private static boolean isPerPixelTranslucencySupported;

	
	/**
	 * Launch the application.
	 * Sets window visibility and size.
	 */
	public static void main(String[] args) {
		// Determine what the GraphicsDevice can support.
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		isPerPixelTranslucencySupported = 
				gd.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT);

		JFrame.setDefaultLookAndFeelDecorated(true);
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhatsUpNextGUI gui = new WhatsUpNextGUI();
					
					gui.frameFloating.setLocation(750, 15);
					gui.frameFloating.setVisible(true);
					gui.frameFloating.pack();
					
					gui.frameUpcoming.setLocation(750, 385);
					gui.frameUpcoming.setVisible(true);
					gui.frameUpcoming.pack();
					
					gui.frameMain.setLocation(200, 150);
					gui.frameMain.setVisible(true);
					gui.frameMain.pack();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public WhatsUpNextGUI() {
		logic = new Logic();
		initGUIComponents();
		setComponentsNames();
		displayWidgetTasks();
	}
	
	public WhatsUpNextGUI(String fileName) {
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
	
	public JFrame getFloatingFrame() {
		return frameFloating;
	}
	
	public JFrame getUpcomingFrame() {
		return frameUpcoming;
	}
	
	/**
	 * Names every component used in the GUI
	 */
	private void setComponentsNames() {
		frameMain.setName("frameMain");
		frameFloating.setName("frameFloating");
		frameUpcoming.setName("frameUpcoming");
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
		createCLIFrame();
		createFloatingFrame();
		createUpcomingFrame();
	}
	
	private void createCLIFrame() {
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
			@Override
			public void windowClosing(WindowEvent e) {
				deleteRevisions();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				if (movingAllFramesToFront) {
			        return;
			    }

			    movingAllFramesToFront = true;

			    frameFloating.toFront();
				frameUpcoming.toFront();

			    e.getWindow().toFront();
			    e.getWindow().requestFocus();

			    EventQueue.invokeLater(new Runnable() {
			        public void run() {
			            movingAllFramesToFront = false;
			        }
			    });
			};
		});
	}


	private void createFloatingFrame() {
		if (!isPerPixelTranslucencySupported) {
			frameFloating = new JFrame();
		} else {
			frameFloating = new TransparentJFrame();
		}
		frameFloating.setResizable(true);
		frameFloating.setType(Type.POPUP);
		frameFloating.setFont(new Font("Cambria", Font.BOLD, 12));
		frameFloating.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameFloating.setPreferredSize(new Dimension(FRAME_FLOATING_WIDTH, FRAME_FLOATING_HEIGHT));
		frameFloating.setMinimumSize(new Dimension(FRAME_FLOATING_WIDTH, FRAME_FLOATING_HEIGHT));
		
		frameFloating.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				deleteRevisions();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				if (movingAllFramesToFront) {
			        return;
			    }

			    movingAllFramesToFront = true;

				frameUpcoming.toFront();
				frameMain.toFront();

			    e.getWindow().toFront();
			    e.getWindow().requestFocus();

			    EventQueue.invokeLater(new Runnable() {
			        public void run() {
			            movingAllFramesToFront = false;
			        }
			    });
			}
		});
		
		ComponentResizer resizer = new ComponentResizer();
		resizer.setSnapSize(new Dimension(2, 2));
		resizer.setMinimumSize(new Dimension(FRAME_FLOATING_WIDTH, FRAME_FLOATING_HEIGHT));
		resizer.registerComponent(frameFloating);
		
		ComponentMover mover = new ComponentMover();
		mover.setDragInsets(new Insets(2, 4, 4, 4));
		mover.setEdgeInsets(new Insets(0, 0, 0, -65));
		mover.registerComponent(frameFloating);
	}


	private void createUpcomingFrame() {
		if (!isPerPixelTranslucencySupported) {
			frameUpcoming = new JFrame();
		} else {
			frameUpcoming = new TransparentJFrame();
		}
		frameUpcoming.setResizable(true);
		frameUpcoming.setType(Type.POPUP);
		frameUpcoming.setFont(new Font("Cambria", Font.BOLD, 12));
		frameUpcoming.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameUpcoming.setPreferredSize(new Dimension(FRAME_UPCOMING_WIDTH, FRAME_UPCOMING_HEIGHT));
		frameUpcoming.setMinimumSize(new Dimension(FRAME_UPCOMING_WIDTH, FRAME_UPCOMING_HEIGHT));
		
		frameUpcoming.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				deleteRevisions();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				if (movingAllFramesToFront) {
			        return;
			    }

			    movingAllFramesToFront = true;

			    frameFloating.toFront();
				frameMain.toFront();

			    e.getWindow().toFront();
			    e.getWindow().requestFocus();

			    EventQueue.invokeLater(new Runnable() {
			        public void run() {
			            movingAllFramesToFront = false;
			        }
			    });
			}
		});
		
		ComponentResizer resizer = new ComponentResizer();
		resizer.setSnapSize(new Dimension(2, 2));
		resizer.setMinimumSize(new Dimension(FRAME_UPCOMING_WIDTH, FRAME_UPCOMING_HEIGHT));
		resizer.registerComponent(frameUpcoming);
		
		ComponentMover mover = new ComponentMover();
		mover.setDragInsets(new Insets(2, 4, 4, 4));
		mover.setEdgeInsets(new Insets(0, 0, 0, -65));
		mover.registerComponent(frameUpcoming);
	}

	
	private void initializeWidgets() {
		mainDisplayWidget = new MainDisplayWidget();
		floatingWidget = new FloatingTasksWidget();
		upcomingWidget = new UpcomingTasksWidget();
		
		cliWidget = new CommandLineInterfaceWidget(mainDisplayWidget);
		cliWidget.linkToWidget(floatingWidget);
		cliWidget.linkToWidget(upcomingWidget);
	}
	

	private void initializeWindowPanels() {
		initializeMainPanel();
		initializeFloatingPanel();
		initializeUpcomingPanel();
	}
	
	private void initializeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(204, 224, 250));
		mainPanel.setPreferredSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		mainPanel.setMinimumSize(new Dimension(FRAME_MAIN_WIDTH, FRAME_MAIN_HEIGHT));
		
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{400};
		gbl_mainPanel.rowHeights = new int[]{235};
		gbl_mainPanel.columnWeights = new double[]{1.0};
		gbl_mainPanel.rowWeights = new double[]{1.0};
		mainPanel.setLayout(gbl_mainPanel);
		
		GridBagConstraints gbc_mainDisplayWidget = new GridBagConstraints();
		gbc_mainDisplayWidget.fill = GridBagConstraints.BOTH;
		gbc_mainDisplayWidget.anchor = GridBagConstraints.CENTER;
		gbc_mainDisplayWidget.insets = new Insets(5, 10, 5, 10);
		gbc_mainDisplayWidget.gridx = 0;
		gbc_mainDisplayWidget.gridy = 0;
		mainPanel.add(mainDisplayWidget.getWidgetPanel(), gbc_mainDisplayWidget);
		
		GridBagConstraints gbc_cliWidget = new GridBagConstraints();
		gbc_cliWidget.fill = GridBagConstraints.HORIZONTAL;
		gbc_cliWidget.anchor = GridBagConstraints.CENTER;
		gbc_cliWidget.insets = new Insets(0, 10, 10, 10);
		gbc_cliWidget.gridx = 0;
		gbc_cliWidget.gridy = 1;
		mainPanel.add(cliWidget.getWidgetPanel(), gbc_cliWidget);
		
		frameMain.getContentPane().add(mainPanel);
	}
	
	private void initializeFloatingPanel() {
		floatingPanel = new JPanel();
		floatingPanel.setBackground(new Color(204, 224, 250, 170));
		floatingPanel.setPreferredSize(new Dimension(FRAME_FLOATING_WIDTH, FRAME_FLOATING_HEIGHT));
		floatingPanel.setMinimumSize(new Dimension(FRAME_FLOATING_WIDTH, FRAME_FLOATING_HEIGHT));
		
		GridBagLayout gbl_floatingPanel = new GridBagLayout();
		gbl_floatingPanel.columnWidths = new int[]{300};
		gbl_floatingPanel.rowHeights = new int[]{235};
		gbl_floatingPanel.columnWeights = new double[]{1.0};
		gbl_floatingPanel.rowWeights = new double[]{1.0};
		floatingPanel.setLayout(gbl_floatingPanel);
		
		GridBagConstraints gbc_floatingWidget = new GridBagConstraints();
		gbc_floatingWidget.fill = GridBagConstraints.BOTH;
		gbc_floatingWidget.anchor = GridBagConstraints.CENTER;
		gbc_floatingWidget.insets = new Insets(22, 15, 15, 15);
		gbc_floatingWidget.gridx = 0;
		gbc_floatingWidget.gridy = 0;
		floatingPanel.add(floatingWidget.getWidgetPanel(), gbc_floatingWidget);
		
		frameFloating.getContentPane().add(floatingPanel);
	}
	
	private void initializeUpcomingPanel() {
		upcomingPanel = new JPanel();
		upcomingPanel.setBackground(new Color(204, 224, 250, 170));
		upcomingPanel.setPreferredSize(new Dimension(FRAME_UPCOMING_WIDTH, FRAME_UPCOMING_HEIGHT));
		upcomingPanel.setMinimumSize(new Dimension(FRAME_UPCOMING_WIDTH, FRAME_UPCOMING_HEIGHT));
		
		GridBagLayout gbl_upcomingPanel = new GridBagLayout();
		gbl_upcomingPanel.columnWidths = new int[]{350};
		gbl_upcomingPanel.rowHeights = new int[]{235};
		gbl_upcomingPanel.columnWeights = new double[]{1.0};
		gbl_upcomingPanel.rowWeights = new double[]{1.0};
		upcomingPanel.setLayout(gbl_upcomingPanel);
		
		GridBagConstraints gbc_upcomingWidget = new GridBagConstraints();
		gbc_upcomingWidget.fill = GridBagConstraints.BOTH;
		gbc_upcomingWidget.anchor = GridBagConstraints.CENTER;
		gbc_upcomingWidget.insets = new Insets(22, 15, 15, 15);
		gbc_upcomingWidget.gridx = 0;
		gbc_upcomingWidget.gridy = 0;
		upcomingPanel.add(upcomingWidget.getWidgetPanel(), gbc_upcomingWidget);
		
		frameUpcoming.getContentPane().add(upcomingPanel);
	}
	
	private void deleteRevisions() {
		logic.clearRevisionFiles();
	}
	
}
