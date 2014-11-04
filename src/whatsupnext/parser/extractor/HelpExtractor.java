package whatsupnext.parser.extractor;

import java.util.ArrayList;
import java.util.Arrays;

import whatsupnext.structure.Help;
import whatsupnext.structure.Task;

public class HelpExtractor {
	private final String MESSAGE_INVALID_ARGUMENT = "Invalid Argument.";
	
	public void extract(Task task, String input){
		if(input.isEmpty()) {
			task.setHelpMessage(Help.DEFAULT_HELP_MESSAGE);
		} else {
			String argument= Utility.getFirstWord(input);
			switch (argument.toLowerCase()) {	
				case "add":
					task.setHelpMessage(Help.ADD_HELP_MESSAGE);
					break;
				case "view":
					task.setHelpMessage(Help.VIEW_HELP_MESSAGE);
					break;
				case "update":
					task.setHelpMessage(Help.UPDATE_HELP_MESSAGE);
					break;
				case "delete":
					task.setHelpMessage(Help.DELETE_HELP_MESSAGE);
					break;
				case "search":
					task.setHelpMessage(Help.SEARCH_HELP_MESSAGE);
					break;
				case "done":
					task.setHelpMessage(Help.DONE_HELP_MESSAGE);
					break;
				case "undo":
					task.setHelpMessage(Help.UNDO_HELP_MESSAGE);
					break;
				case "redo":
					task.setHelpMessage(Help.REDO_HELP_MESSAGE);
					break;
				case "exit":
					task.setHelpMessage(Help.EXIT_HELP_MESSAGE);
					break;
				default:
					throw new IllegalArgumentException(MESSAGE_INVALID_ARGUMENT);
			}
		}
	}
}
