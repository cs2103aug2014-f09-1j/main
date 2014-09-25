/*
 *  This is the Parser class for WhatsUpNext
 */
package Parser;

import Structure.Operation.OPCODE;
import java.util.StringTokenizer;

public class Parser {

	private final String[] ALIASES_ADD = {"add", "a"};
	private final String[] ALIASES_VIEW = {"view", "v", "list", "ls", "l"};
	private final String[] ALIASES_UPDATE = {"edit", "e", "modify", "m"};
	private final String[] ALIASES_DELETE = {"delete", "del", "d"};
	private final String[] ALIASES_HELP = {"help", "h", "?"};
	private final String[] ALIASES_EXIT = {"exit", "e", "quit", "q"};
	
	private static final int INDEX_FOR_TASK = 0;
	private static final int INDEX_FOR_TASKID = 0;
	private static final int INDEX_FOR_STARTTIME = 1;
	private static final int INDEX_FOR_ENDTIME = 2;
	
	private String input;
	private OPCODE opcode;
	private String[] operand;
	
	public Parser(String input){
		this.input = input;
		this.setOpcode(null);
		this.operand = null;
	}
	
	public void parseInput(){
		StringTokenizer st = new StringTokenizer(input);
		if(st.hasMoreTokens()){
			String operation = st.nextToken();
			this.setOpcode(determineOperation(operation));
		} else {
			this.setOpcode(OPCODE.INVALID);
		}
	}
	
	public void parseOperand(OPCODE opcode, String operand){
		
		switch (opcode) {
		case ADD:
			break;
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

	public String getOpcode() {
		return opcode.toString();
	}

	public void setOpcode(OPCODE opcode) {
		this.opcode = opcode;
	}

	
}