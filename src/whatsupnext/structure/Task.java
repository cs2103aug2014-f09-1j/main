package whatsupnext.structure;

import whatsupnext.structure.DELETETYPE;
import whatsupnext.structure.OPCODE;
import whatsupnext.structure.UPDATETYPE;
import whatsupnext.structure.VIEWTYPE;

public class Task {

	private static final int OPERAND_SIZE = 4;
	private static final int INDEX_FOR_TASK_ID = 0;
	private static final int INDEX_FOR_DESCRIPTION = 1;
	private static final int INDEX_FOR_START_TIME = 2;
	private static final int INDEX_FOR_END_TIME = 3;
	
	private OPCODE opcode;
	private ADDTYPE addType;
	private VIEWTYPE viewType;
	private UPDATETYPE updateType;
	private DELETETYPE deleteType;
	private String[] operand;

	public Task() {
		setOpcode(null);
		setAddType(null);
		setViewType(null);
		setUpdateType(null);
		setDeleteType(null);
		operand = new String[OPERAND_SIZE];
	}

	public OPCODE getOpCode() {
		return opcode;
	}

	public void setOpcode(OPCODE opcode) {
		this.opcode = opcode;
	}
	
	public ADDTYPE getAddType() {
		return addType;
	}
	
	public void setAddType(ADDTYPE addType) {
		this.addType = addType;
	}
	
	public VIEWTYPE getViewType() {
		return viewType;
	}
	
	public void setViewType(VIEWTYPE viewType) {
		this.viewType = viewType;
	}
	
	public UPDATETYPE getUpdateType() {
		return updateType;
	}
	
	public void setUpdateType(UPDATETYPE updateType) {
		this.updateType = updateType;
	}
	
	public DELETETYPE getDeleteType() {
		return deleteType;
	}
	
	public void setDeleteType(DELETETYPE deleteType) {
		this.deleteType = deleteType;
	}
	
	public String getTaskID() {
		return operand[INDEX_FOR_TASK_ID];
	}
	
	public void setTaskID(String taskID) {
		operand[INDEX_FOR_TASK_ID] = taskID;
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

}
