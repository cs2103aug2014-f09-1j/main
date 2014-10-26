/*
 * This is the Logic class.
 */
package whatsupnext.logic;

import java.io.IOException;
import java.util.Iterator;

import whatsupnext.structure.Task;
import whatsupnext.storage.Storage;

public class Logic {
	
	public Logic() {
		Storage.tryInitialize("tasks");
		readTasksIntoInternalList();
		LogicUtilities.setupAvailableIDs();
	}
	
	public Logic(String fileName) {
		Storage.tryInitialize(fileName);
		readTasksIntoInternalList();
		LogicUtilities.setupAvailableIDs();
	}
	
	public String executeTask(Task task) {
		Command userCommand;
		
		switch (task.getOpCode()) {
			case ADD:
				userCommand = new AddCommand(task);
				break;
			case DELETE:
				userCommand = new DeleteCommand(task);
				break;
			case UPDATE:
				userCommand = new UpdateCommand(task);
				break;
			case VIEW:
				userCommand = new ViewCommand(task);
				break;
			case DONE:
				userCommand = new DoneCommand(task);
				break;
			case SEARCH:
				userCommand = new SearchCommand(task);
				break;
			case UNDO:
				userCommand = new UndoCommand(task);
				break;
			default:
				return "Unable to execute the command";
		}
		
		return userCommand.executeCommand();
	}
	
	public static void readTasksIntoInternalList() {
		Storage storage = Storage.getInstance();
		try {
			Iterator<Task> readIterator = storage.readTasks().iterator();
			while (readIterator.hasNext()) {
				Task readTask = readIterator.next();
				LogicUtilities.list.add(readTask);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearRevisionFiles() {
		Storage storage = Storage.getInstance();
		try {
			storage.deleteFileVersions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
