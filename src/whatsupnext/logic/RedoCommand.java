package whatsupnext.logic;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

public class RedoCommand extends Command{

	private final String MESSAGE_REDO = "The execution was re executed.";
	private final String MESSAGE_REDO_FAIL = "Cannot execute redo command.";
	
	public RedoCommand(Task task) {
		super(task);
	}

	public String executeCommand() {
		storage = Storage.getInstance();
		if (storage.goToNextVersion()) {
			LogicUtilities.clearList();
			Logic.readTasksIntoInternalList();
			LogicUtilities.setupAvailableIDs();
			return MESSAGE_REDO;
		} else {
			return MESSAGE_REDO_FAIL;
		}
	}
}
