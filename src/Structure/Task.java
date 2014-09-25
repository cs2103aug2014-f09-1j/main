package Structure;

import Structure.Operation.OPCODE;

public class Task {

	private static final int OPERAND_SIZE = 4;
	private static final int INDEX_FOR_DESCRIPTION = 0;
	private static final int INDEX_FOR_STARTTIME = 1;
	private static final int INDEX_FOR_ENDTIME = 2;
	private static final int INDEX_FOR_DELETETYPE = 3;
	
	private OPCODE opcode;
	private String[] operand;

	public Task(){
		setOpcode(null);
		operand = new String[OPERAND_SIZE];
	}

	public OPCODE getOpcode() {
		return opcode;
	}

	public void setOpcode(OPCODE opcode) {
		this.opcode = opcode;
	}
	
	public String getDescription(){
		return operand[INDEX_FOR_DESCRIPTION];
	}
	
	public void setDescription(String description){
		operand[INDEX_FOR_DESCRIPTION] = description;
	}
	
	public String getStartTime(){
		return operand[INDEX_FOR_STARTTIME];
	}
	
	public void setStartTime(String startTime){
		operand[INDEX_FOR_STARTTIME] = startTime;
	}
	
	public String getEndTime(){
		return operand[INDEX_FOR_ENDTIME];
	}
	
	public void setEndTime(String endTime){
		operand[INDEX_FOR_ENDTIME] = endTime;
	}
	
	public String getDeleteType(){
		return operand[INDEX_FOR_DELETETYPE];
	}
	
	public void setDeleteType(String deleteType){
		operand[INDEX_FOR_DELETETYPE] = deleteType;
	}
	
	
	
}
