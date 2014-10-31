/*
 *  This is the Parser class for WhatsUpNext
 */
package whatsupnext.parser.api;

import java.util.StringTokenizer;

import whatsupnext.parser.extractor.AddExtractor;
import whatsupnext.parser.extractor.DeleteExtractor;
import whatsupnext.parser.extractor.DoneExtractor;
import whatsupnext.parser.extractor.HelpExtractor;
import whatsupnext.parser.extractor.SearchExtractor;
import whatsupnext.parser.extractor.UpdateExtractor;
import whatsupnext.parser.extractor.ViewExtractor;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.Task;

public class Parser {

	private final String MESSAGE_INVALID_OPCODE = "Unrecognized command type";
	
	private final String[] ALIASES_ADD = {"add", "a"};
	private final String[] ALIASES_VIEW = {"view", "v", "list", "ls", "l"};
	private final String[] ALIASES_UPDATE = {"update", "u", "edit", "e", "modify", "m"};
	private final String[] ALIASES_DELETE = {"delete", "del", "d"};
	private final String[] ALIASES_SEARCH = {"search", "s", "find", "f"};
	private final String[] ALIASES_DONE = {"done"};
	private final String[] ALIASES_UNDO = {"undo"};
	private final String[] ALIASES_REDO = {"redo"};
	private final String[] ALIASES_HELP = {"help", "h", "?"};
	private final String[] ALIASES_EXIT = {"exit", "e", "quit", "q"};
	
	private String input;
	private Task task;
	
	public Parser(String inputCommand) {
		input = inputCommand;
		task = new Task();
	}
	
	public Task parseInput() {
		StringTokenizer tokenizedInput = new StringTokenizer(input);
		if (tokenizedInput.hasMoreTokens()){
			String operation = tokenizedInput.nextToken();
			task.setOpcode(determineOperation(operation));
			input = removeFirstWord(input);
			parseTaskArguments();
		} else {
			task.setOpcode(OPCODE.INVALID);
		}
		return task;
	}

	private void parseTaskArguments() {
		switch (task.getOpCode()) {
			case ADD:
				AddExtractor exAdd = new AddExtractor();
				exAdd.extract(task, input);
				break;
			case VIEW:
				ViewExtractor exView = new ViewExtractor();
				exView.extract(task, input);
				break;
			case UPDATE:
				UpdateExtractor exUpdate = new UpdateExtractor();
				exUpdate.extract(task, input);
				break;
			case DELETE:
				DeleteExtractor exDelete = new DeleteExtractor();
				exDelete.extract(task, input);
				break;
			case SEARCH:
				SearchExtractor exSearch = new SearchExtractor();
				exSearch.extract(task, input);
				break;
			case DONE:
				DoneExtractor exDone = new DoneExtractor();
				exDone.extract(task, input);
				break;
			case UNDO:
				break;
			case REDO:
				break;
			case HELP:
				HelpExtractor exHelp = new HelpExtractor();
				exHelp.extract(task, input);
				break;
			case EXIT:
				break;
			default:
				throw new IllegalArgumentException(MESSAGE_INVALID_OPCODE);
		}
	}

	private OPCODE determineOperation(String operation) {
		if (isInOperationAliases(operation, ALIASES_ADD)) {
			return OPCODE.ADD;
		} else if (isInOperationAliases(operation, ALIASES_VIEW)) {
			return OPCODE.VIEW;
		} else if (isInOperationAliases(operation, ALIASES_UPDATE)) {
			return OPCODE.UPDATE;
		} else if (isInOperationAliases(operation, ALIASES_DELETE)) {
			return OPCODE.DELETE;
		} else if (isInOperationAliases(operation, ALIASES_SEARCH)) {
			return OPCODE.SEARCH;
		} else if (isInOperationAliases(operation, ALIASES_DONE)) {
			return OPCODE.DONE;
		} else if (isInOperationAliases(operation, ALIASES_UNDO)) {
			return OPCODE.UNDO;
		} else if (isInOperationAliases(operation, ALIASES_REDO)) {
			return OPCODE.REDO;
		}else if (isInOperationAliases(operation, ALIASES_HELP)) {
			return OPCODE.HELP;
		} else if (isInOperationAliases(operation, ALIASES_EXIT)) {
			return OPCODE.EXIT;
		} else {
			return OPCODE.INVALID;
		}
	}

	private boolean isInOperationAliases(String operation, String[] aliases) {
		for (String alias : aliases) {
			if (operation.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the first word of a string
	 * @param userCommand
	 * @return
	 */
	private static String removeFirstWord(String userCommand) {
		String commandString;
		try {
			commandString = userCommand.trim().split("\\s+", 2)[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			commandString = "";
		}
		return commandString;
	}
}