package Structure;

import Structure.OPCODE;

public class Task {

	private static final int OPERAND_SIZE = 6;
	private static final int INDEX_FOR_DESCRIPTION = 0;
	private static final int INDEX_FOR_START_TIME = 1;
	private static final int INDEX_FOR_END_TIME = 2;
	private static final int INDEX_FOR_DELETE_TYPE = 3;
	private static final int INDEX_FOR_UPDATE_TYPE = 4;
	private static final int INDEX_FOR_VIEW_TYPE = 5;
	
	private OPCODE opcode;
	private String[] operand;

	public Task(){
		setOpcode(null);
		operand = new String[OPERAND_SIZE];
	}

	public OPCODE getOpCode() {
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
		return operand[INDEX_FOR_START_TIME];
	}
	
	public void setStartTime(String startTime){
		operand[INDEX_FOR_START_TIME] = startTime;
	}
	
	public String getEndTime(){
		return operand[INDEX_FOR_END_TIME];
	}
	
	public void setEndTime(String endTime){
		operand[INDEX_FOR_END_TIME] = endTime;
	}
	
	public String getDeleteType(){
		return operand[INDEX_FOR_DELETE_TYPE];
	}
	
	public String getUpdateType(){
		return operand[INDEX_FOR_UPDATE_TYPE];
	}
	
	public String getViewType(){
		return operand[INDEX_FOR_VIEW_TYPE];
	}
	
	
	public void setDeleteType(String deleteType){
		operand[INDEX_FOR_DELETE_TYPE] = deleteType;
	}
	
	public void setUpdateType(String updateType){
		operand[INDEX_FOR_UPDATE_TYPE] = updateType;
	}
	
	public void setViewType(String viewType){
		operand[INDEX_FOR_VIEW_TYPE] = viewType;
	}
}
