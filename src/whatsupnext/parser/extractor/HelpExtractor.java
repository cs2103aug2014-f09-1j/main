package whatsupnext.parser.extractor;

import java.util.ArrayList;
import java.util.Arrays;

import whatsupnext.structure.Help;
import whatsupnext.structure.Task;

public class HelpExtractor {
	private final String MESSAGE_INVALID_ARGUMENT = "Invalid Argument.";
	
	private final ArrayList<String> ALIASES_VERBOSE = new ArrayList<String>(Arrays.asList("verbose", "v"));
	
	public void extract(Task task, String input){
		if(input.isEmpty()) {
			task.setHelpMessage(Help.DEFAULT_HELP_MESSAGE);
		} else {
			String argument= Utility.getFirstWord(input);
			input = Utility.removeFirstWord(input);
			String option = Utility.getFirstWord(input);
			switch (argument.toLowerCase()) {	
				case "add":
					if(ALIASES_VERBOSE.contains(option.toLowerCase())) {
						task.setHelpMessage(Help.ADD_HELP_MESSAGE_DETAILED);
					} else {
						task.setHelpMessage(Help.ADD_HELP_MESSAGE_BRIEF);
					}
					break;
				case "view":
					if(ALIASES_VERBOSE.contains(option.toLowerCase())) {
						task.setHelpMessage(Help.VIEW_HELP_MESSAGE_DETAILED);
					} else {
						task.setHelpMessage(Help.VIEW_HELP_MESSAGE_BRIEF);
					}
					break;
				case "update":
					if(ALIASES_VERBOSE.contains(option.toLowerCase())) {
						task.setHelpMessage(Help.UPDATE_HELP_MESSAGE_DETAILED);
					} else {
						task.setHelpMessage(Help.UPDATE_HELP_MESSAGE_BRIEF);
					}
					break;
				case "delete":
					if(ALIASES_VERBOSE.contains(option.toLowerCase())) {
						task.setHelpMessage(Help.DELETE_HELP_MESSAGE_DETAILED);
					} else {
						task.setHelpMessage(Help.DELETE_HELP_MESSAGE_BRIEF);
					}
					break;
				case "search":
					task.setHelpMessage(Help.SEARCH_HELP_MESSAGE);
					break;
				case "done":
					if(ALIASES_VERBOSE.contains(option.toLowerCase())) {
						task.setHelpMessage(Help.DONE_HELP_MESSAGE_DETAILED);
					} else {
						task.setHelpMessage(Help.DONE_HELP_MESSAGE_BRIEF);
					}
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
