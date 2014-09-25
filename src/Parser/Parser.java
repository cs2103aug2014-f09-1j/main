/*
 *  This is the Parser class for WhatsUpNext
 */
package Parser;

import Structure.Operation.OPCODE;
import Structure.Task;
import java.util.StringTokenizer;

public class Parser {

	private final String[] ALIASES_ADD = {"add", "a"};
	private final String[] ALIASES_VIEW = {"view", "v", "list", "ls", "l"};
	private final String[] ALIASES_UPDATE = {"edit", "e", "modify", "m"};
	private final String[] ALIASES_DELETE = {"delete", "del", "d"};
	private final String[] ALIASES_HELP = {"help", "h", "?"};
	private final String[] ALIASES_EXIT = {"exit", "e", "quit", "q"};
	
	private String input;
	private Task task;
	private int taskID;
	
	public Parser(String input){
		this.input = input;
		this.setTask(new Task());
	}
	
	public Task parseInput(){
		StringTokenizer st = new StringTokenizer(input);
		if(st.hasMoreTokens()){
			String operation = st.nextToken();
			task.setOpcode(determineOperation(operation));
			parseTask();
		} else {
			task.setOpcode(OPCODE.INVALID);
		}
		return task;
	}
	
	public void parseTask(){
		Extractor ex = new Extractor(task, input);
		switch (task.getOpcode()) {
		case ADD:
			ex.extractorAdd();
			break;
		case DELETE:
			ex.extractorDelete();
			break;
		case UPDATE:
			ex.extractorUpdate();
			taskID = ex.gettaskID();
		case EXIT:
			System.exit(0);
		default:
			//throw an error if the command is not recognized
			throw new Error("Unrecognized command type");
		}
	}

	private OPCODE determineOperation(String operation) {
		
		if (determineIsAddOperation(operation)) {
			return OPCODE.ADD;
		} else if (determineIsViewOperation(operation)) {
			return OPCODE.VIEW;
		} else if (determineIsUpdateOperation(operation)) {
			return OPCODE.UPDATE;
		} else if (determineIsDeleteOperation(operation)) {
			return OPCODE.DELETE;
		} else if (determineIsHelpOperation(operation)) {
			return OPCODE.HELP;
		} else if (determineIsExitOperation(operation)) {
			return OPCODE.EXIT;
		} else {
			return OPCODE.INVALID;
		}
	}

	private boolean determineIsAddOperation(String operation) {
		for(String alias : ALIASES_ADD ){
			if(operation.equalsIgnoreCase(alias)){
				return true;
			}
		}
		return false;
	}
	
	private boolean determineIsViewOperation(String operation) {
		for(String alias : ALIASES_VIEW ){
			if(operation.equalsIgnoreCase(alias)){
				return true;
			}
		}
		return false;
	}
	
	private boolean determineIsUpdateOperation(String operation) {
		for(String alias : ALIASES_UPDATE ){
			if(operation.equalsIgnoreCase(alias)){
				return true;
			}
		}
		return false;
	}
	
	private boolean determineIsDeleteOperation(String operation) {
		for(String alias : ALIASES_DELETE ){
			if(operation.equalsIgnoreCase(alias)){
				return true;
			}
		}
		return false;
	}

	private boolean determineIsHelpOperation(String operation) {
		for(String alias : ALIASES_HELP ){
			if(operation.equalsIgnoreCase(alias)){
				return true;
			}
		}
		return false;
	}
	
	private boolean determineIsExitOperation(String operation) {
		for(String alias : ALIASES_EXIT ){
			if(operation.equalsIgnoreCase(alias)){
				return true;
			}
		}
		return false;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
}