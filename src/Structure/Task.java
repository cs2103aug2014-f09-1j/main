package Structure;

import Structure.OPCODE;
import Structure.VIEWTYPE;
import Structure.UPDATETYPE;
import Structure.DELETETYPE;

public class Task {

	private static final int OPERAND_SIZE = 6;
	private static final int INDEX_FOR_DESCRIPTION = 0;
	private static final int INDEX_FOR_START_TIME = 1;
	private static final int INDEX_FOR_END_TIME = 2;
/*	private static final int INDEX_FOR_DELETE_TYPE = 3;
	private static final int INDEX_FOR_UPDATE_TYPE = 4;
	private static final int INDEX_FOR_VIEW_TYPE = 5;*/
	
	private OPCODE opcode;
	private VIEWTYPE viewtype;
	private UPDATETYPE updatetype;
	private DELETETYPE deletetype;
	private String[] operand;

	public Task() {
		setOpcode(null);
		operand = new String[OPERAND_SIZE];
	}

	public OPCODE getOpCode() {
		return opcode;
	}

	public void setOpcode(OPCODE opcode) {
		this.opcode = opcode;
	}
	
	public String getDescription() {
		return operand[INDEX_FOR_DESCRIPTION];
	}
	
	public void setDescription(String description) {
		operand[INDEX_FOR_DESCRIPTION] = description;
	}
	
	public String getStartTime() {
		return operand[INDEX_FOR_START_TIME];
	}
	
	public void setStartTime(String startTime) {
		operand[INDEX_FOR_START_TIME] = startTime;
	}
	
	public String getEndTime() {
		return operand[INDEX_FOR_END_TIME];
	}
	
	public void setEndTime(String endTime) {
		operand[INDEX_FOR_END_TIME] = endTime;
	}
	
	
	
	
	public DELETETYPE getDeleteType() {
	//	return operand[INDEX_FOR_DELETE_TYPE];
		return deletetype;
	}
	
	public UPDATETYPE getUpdateType() {
	//	return operand[INDEX_FOR_UPDATE_TYPE];
		return updatetype;
	}
	
	public VIEWTYPE getViewType() {
	//	return operand[INDEX_FOR_VIEW_TYPE];
		return viewtype;
	}
	
	
	public void setDeleteType(DELETETYPE newdeletetype) {
	//	operand[INDEX_FOR_DELETE_TYPE] = deleteType;
		deletetype = newdeletetype;
	}
	
	public void setUpdateType(UPDATETYPE newupdatetype) {
	//	operand[INDEX_FOR_UPDATE_TYPE] = updateType;
		updatetype = newupdatetype;
	}
	
	public void setViewType(VIEWTYPE newviewtype) {
	//	operand[INDEX_FOR_VIEW_TYPE] = viewType;
	    viewtype = newviewtype;
	}
}
