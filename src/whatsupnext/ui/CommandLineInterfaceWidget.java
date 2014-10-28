package whatsupnext.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import whatsupnext.parser.api.Parser;
import whatsupnext.structure.Task;

public class CommandLineInterfaceWidget {

	private JFrame frameMain = WhatsUpNextGUI.frameMain;
	private MainDisplayWidget linkedDisplay;
	private UpcomingTasksWidget linkedUpcomingOptional;

	private JTextField textInput;
	private final int[] TEXT_INPUT_DIMENSIONS = {10, 225, 423, 25};
	private JButton buttonEnter;
	private final int[] BUTTON_ENTER_DIMENSIONS = {440, 225, 90, 25};

	private final ArrayList<String> STRINGS_CLEAR = new ArrayList<String>(Arrays.asList("clear", "Clear", "CLEAR", "clc"));

	private final LinkedList<String> usedCommands = new LinkedList<String>();
	private ListIterator<String> commandIterator;
	private boolean upLastPressed;
	private boolean downLastPressed;


	public CommandLineInterfaceWidget(MainDisplayWidget displayForCLI) {
		linkedDisplay = displayForCLI;
		constructCLI();
	}
	
	public CommandLineInterfaceWidget(MainDisplayWidget displayForCLI, UpcomingTasksWidget upcomingOptionalForCLI) {
		linkedDisplay = displayForCLI;
		linkedUpcomingOptional = upcomingOptionalForCLI;
		constructCLI();
	}
	
	private void constructCLI() {
		initializeCLIPanel();
		setComponentNames();

		commandIterator = usedCommands.listIterator();
		upLastPressed = false;
		downLastPressed = false;
	}

	private void setComponentNames() {
		textInput.setName("textInput");
		buttonEnter.setName("buttonEnter");
	}

	private void initializeCLIPanel() {
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
		buttonEnter.setBounds(
				BUTTON_ENTER_DIMENSIONS[0],
				BUTTON_ENTER_DIMENSIONS[1],
				BUTTON_ENTER_DIMENSIONS[2],
				BUTTON_ENTER_DIMENSIONS[3]);
		frameMain.getContentPane().add(buttonEnter);
	}

	private void initializeMainUserCLI() {
		textInput = new JTextField();
		textInput.setBackground(new Color(240, 255, 255));
		textInput.setFont(new Font("Courier New", Font.PLAIN, 12));
		textInput.setBounds(
				TEXT_INPUT_DIMENSIONS[0],
				TEXT_INPUT_DIMENSIONS[1],
				TEXT_INPUT_DIMENSIONS[2],
				TEXT_INPUT_DIMENSIONS[3]);
		frameMain.getContentPane().add(textInput);

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
					feedback = WhatsUpNextGUI.logic.executeTask(currentTask);
				} catch (Exception e) {
					feedback = e.getMessage();
				}
			}
			linkedDisplay.displayFeedback(feedback);
			if (linkedUpcomingOptional != null) {
				linkedUpcomingOptional.clickUpcoming();
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
		linkedDisplay.setText("---Please enter command below:\r\n");
	}

	private void pressEnterFromCLI() {
		clickEnter();
		pressKeyFromCLI();
	}

	private void pressKeyFromCLI() {
		commandIterator = usedCommands.listIterator();
	}
}
