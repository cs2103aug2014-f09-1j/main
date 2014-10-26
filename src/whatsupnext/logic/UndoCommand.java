package whatsupnext.logic;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

public class UndoCommand extends Command{

	private final String MESSAGE_UNDO = "The execution was canceled.";
	private final String MESSAGE_UNDO_FIAL = "Cannot execute undo command.";
	
	public UndoCommand(Task task) {
		super(task);
	}

	public String executeCommand() {
		storage = Storage.getInstance();
		if (storage.goToPreviousVersion()) {
			Logic.readTasksIntoInternalList();
			LogicUtilities.setupAvailableIDs();
			
			return MESSAGE_UNDO;
		} else {
			return MESSAGE_UNDO_FIAL;
		}
	}
	

}