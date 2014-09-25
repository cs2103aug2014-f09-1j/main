package Logic;

import Structure.Operation.OPCODE;

import java.util.ArrayList;

/*
 * This is the Logic class.
 */

public class Logic {
	
	public static ArrayList<String> list = new ArrayList<String>();
	public static ArrayList<String> output = new ArrayList<String>();
	
	public static String MESSAGE_ADDED = "Task successfully added";
	public static String MESSAGE_DELETED = "Task successfully deleted";
	public static String MESSAGE_UPDATED = "Task successfully updated";
	
	
	public static void main(String[] args) {
		 

	}
	
	public static void execute(OPCODE command) {		 
		switch (command) {
		case ADD:
			addTask();
			break;
		case DELETE:
			deleteTask();
			break;
		case VIEW:
			viewTask();
			break;
		case UPDATE:
			updateTask();
			break;
		}
	}
	
	public static void addTask() {
		
	}
	
	public static void viewTask() {
		
	}
	
	public static void updateTask() {
		
	}
	
	public static void deleteTask() {
		
	}
	
	public static void getOutput() {
		
	}
}



