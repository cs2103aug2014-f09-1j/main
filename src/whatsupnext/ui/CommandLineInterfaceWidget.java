//@author A0126730M
package whatsupnext.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import whatsupnext.parser.api.Parser;
import whatsupnext.structure.enums.OPCODE;
import whatsupnext.structure.util.Task;

public class CommandLineInterfaceWidget {

	private MainDisplayWidget linkedDisplay;
	private ArrayList<TasksWidget> linkedOptionalDisplays;

	private JPanel widgetPanel;
	private final int PANEL_WIDTH = 520;
	private final int PANEL_HEIGHT = 25;
	
	private JTextField textInput;
	private JButton buttonEnter;

	private final ArrayList<String> STRINGS_CLEAR = new ArrayList<String>(Arrays.asList("clear", "Clear", "CLEAR", "clc"));
	private final LinkedList<String> usedCommands = new LinkedList<String>();
	private ListIterator<String> commandIterator;
	private boolean upLastPressed;
	private boolean downLastPressed;


	public CommandLineInterfaceWidget(MainDisplayWidget displayForCLI) {
		linkedDisplay = displayForCLI;
		initializeCLIPanel();
		setComponentNames();
		linkedOptionalDisplays = new ArrayList<TasksWidget>();
		
		commandIterator = usedCommands.listIterator();
		upLastPressed = false;
		downLastPressed = false;
	}
	
	public void linkToWidget(TasksWidget widget) {
		linkedOptionalDisplays.add(widget);
	}
	
	public JPanel getWidgetPanel() {
    	return widgetPanel;
    }

	private void setComponentNames() {
		widgetPanel.setName("commandLineInterfaceWidgetPanel");
		textInput.setName("textInput");
		buttonEnter.setName("buttonEnter");
	}

	private void initializeCLIPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		widgetPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWidths = new int[]{430, 90};
		gbl_widgetPanel.rowHeights = new int[]{PANEL_HEIGHT};
		gbl_widgetPanel.columnWeights = new double[]{1.0, 0.0};
		gbl_widgetPanel.rowWeights = new double[]{1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeMainEnterButton();
		initializeMainUserCLI();
	}

	private void initializeMainEnterButton() {
		buttonEnter = new JButton(" Enter ");
		buttonEnter.setFont(new Font("Cambria", Font.BOLD, 12));
		buttonEnter.setForeground(new Color(224, 255, 255));
		buttonEnter.setBackground(new Color(70, 130, 180));
		buttonEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickEnter();
			}
		});
		
		GridBagConstraints gbc_buttonEnter = new GridBagConstraints();
		gbc_buttonEnter.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonEnter.anchor = GridBagConstraints.WEST;
		gbc_buttonEnter.gridx = 1;
		gbc_buttonEnter.gridy = 0;
		
		widgetPanel.add(buttonEnter, gbc_buttonEnter);
	}

	private void initializeMainUserCLI() {
		textInput = new JTextField();
		textInput.setBackground(new Color(240, 255, 255));
		textInput.setFont(new Font("Courier New", Font.PLAIN, 12));
		
		GridBagConstraints gbc_textInput = new GridBagConstraints();
		gbc_textInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_textInput.anchor = GridBagConstraints.WEST;
		gbc_textInput.insets = new Insets(0, 0, 0, 10);
		gbc_textInput.gridx = 0;
		gbc_textInput.gridy = 0;
		
		widgetPanel.add(textInput, gbc_textInput);

		// Pressing 'enter' key causes the command to be executed
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pressEnterFromCLI();
			}	
		});

		// Pressing 'up' or 'down' keys allows for cycling of previous commands to replace user input
		textInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_UP) {
					pressUpFromCLI();
				} else if (key == KeyEvent.VK_DOWN) {
					pressDownFromCLI();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				pressKeyFromCLI();
			}
		});
	}

	/**
	 * This method is activated as 'input command'
	 * It is called whenever user clicks the input button or presses the enter key
	 */
	private void clickEnter(){
		String commandInput = textInput.getText();
		String feedback;

		if (commandIsClear(commandInput)) {
			usedCommands.addFirst(commandInput);
			clearLinkedDisplay();

		} else {
			if (commandInput.trim().isEmpty()) {
				feedback = "Empty command";
			} else {
				try {
					usedCommands.addFirst(commandInput);
					Parser parser = new Parser(commandInput);
					Task currentTask = parser.parseInput();
					
					if (currentTask.getOpCode() == OPCODE.EXIT) {
						Window frame = findWindow(widgetPanel);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						frame.dispose();
					}
					if (currentTask.getOpCode() == OPCODE.HELP) {
						feedback = currentTask.getHelpMessage();
					} else {
						feedback = WhatsUpNextGUI.logic.executeTask(currentTask);
					}
				} catch (Exception e) {
					feedback = e.getMessage();
				}
			}
			linkedDisplay.displayFeedback(feedback);
			for (TasksWidget widget : linkedOptionalDisplays) {
				widget.doActionOnClick();
			}
		}
		clearTextInput();
	}

	/**
	 * pressUp and pressDown must check if the opposite key was pressed
	 * because the pointer must move twice if so
	 */
	private void pressUpFromCLI() {
		upLastPressed = true;
		if (commandIterator.hasNext()) {
			textInput.setText(commandIterator.next());
		}
		if (downLastPressed) {
			textInput.setText(commandIterator.next());
			downLastPressed = false;
		}
	}

	private void pressDownFromCLI() {
		downLastPressed = true;
		if (commandIterator.hasPrevious()) {
			textInput.setText(commandIterator.previous());
		}
		if (upLastPressed) {
			textInput.setText(commandIterator.previous());
			upLastPressed = false;
		}
	}
	
	private boolean commandIsClear(String commandInput) {
		for (String stringClear : STRINGS_CLEAR) {
			if (stringClear.equals(commandInput.trim())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Clears the user command prompt
	 */
	private void clearTextInput() {
		textInput.setText("");
	}

	/**
	 * Clears the main display area
	 */
	private void clearLinkedDisplay() {
		linkedDisplay.setText("---Please enter command below:\r\n   type \"help\" for instructions\n ");
	}

	private void pressEnterFromCLI() {
		clickEnter();
		pressKeyFromCLI();
	}

	private void pressKeyFromCLI() {
		commandIterator = usedCommands.listIterator();
	}
	
	private Window findWindow(Component c) {
		if (c == null) {
			return JOptionPane.getRootFrame();
		} else if (c instanceof Window) {
			return (Window) c;
		} else {
			return findWindow(c.getParent());
		}
	}
}
