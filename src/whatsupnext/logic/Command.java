//@author A0105720W
package whatsupnext.logic;

import whatsupnext.storage.Storage;
import whatsupnext.structure.Task;

public abstract class Command {
	
	protected String taskID;
	protected String description;
	protected String startTime;
	protected String endTime;
	
	protected Storage storage;
	
	public Command(Task info) {
		taskID = info.getTaskID();
		description = info.getDescription();
		startTime = info.getStartTime();
		endTime = info.getEndTime();
		
		storage = Storage.getInstance();
	}
	
	public abstract String executeCommand();
}
