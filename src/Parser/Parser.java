/*
 *  This is the Parser class for WhatsUpNext
 */
package Parser;

import Structure.OPCODE;
import Structure.Task;

import java.util.StringTokenizer;

public class Parser {

	private final String[] ALIASES_ADD = {"add", "a"};
	private final String[] ALIASES_VIEW = {"view", "v", "list", "ls", "l"};
	private final String[] ALIASES_UPDATE = {"update", "u", "edit", "e", "modify", "m"};
	private final String[] ALIASES_DELETE = {"delete", "del", "d"};
	private final String[] ALIASES_HELP = {"help", "h", "?"};
	private final String[] ALIASES_EXIT = {"exit", "e", "quit", "q"};
	
	private String input;
	private Task task;
	private int taskID;
	
	public Parser(String inputCommand) {
		input = inputCommand;
		setTask(new Task());
	}
	
	public Task parseInput() {
		StringTokenizer tokenizedInput = new StringTokenizer(input);
		if (tokenizedInput.hasMoreTokens()){
			String operation = tokenizedInput.nextToken();
			task.setOpcode(determineOperation(operation));
			parseTaskArguments();
		} else {
			task.setOpcode(OPCODE.INVALID);
		}
		return task;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task t) {
		task = t;
	}
	
	public int getTaskID() {
		return taskID;
	}
	
	
	private void parseTaskArguments() {
		Extractor ex = new Extractor(task, input);
		switch (task.getOpCode()) {
			case ADD:
				ex.extractForAddTask();
				break;
			case DELETE:
				ex.extractForDeleteTask();
				break;
			case UPDATE:
				ex.extractForUpdateTask();
				taskID = ex.getTaskID();
				break;
			case VIEW:
				ex.extractForViewTask();
				break;
			case EXIT:
				System.exit(0);
			default:
				throw new IllegalArgumentException("Unrecognized command type");
		}
	}

	private OPCODE determineOperation(String operation) {
		if (isAddOperation(operation)) {
			return OPCODE.ADD;
		} else if (isViewOperation(operation)) {
			return OPCODE.VIEW;
		} else if (isUpdateOperation(operation)) {
			return OPCODE.UPDATE;
		} else if (isDeleteOperation(operation)) {
			return OPCODE.DELETE;
		} else if (isHelpOperation(operation)) {
			return OPCODE.HELP;
		} else if (isExitOperation(operation)) {
			return OPCODE.EXIT;
		} else {
			return OPCODE.INVALID;
		}
	}

	private boolean isAddOperation(String operation) {
		for (String alias : ALIASES_ADD) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isViewOperation(String operation) {
		for (String alias : ALIASES_VIEW) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isUpdateOperation(String operation) {
		for (String alias : ALIASES_UPDATE) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isDeleteOperation(String operation) {
		for (String alias : ALIASES_DELETE) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}

	private boolean isHelpOperation(String operation) {
		for (String alias : ALIASES_HELP) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isExitOperation(String operation) {
		for (String alias : ALIASES_EXIT) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
}