package storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Storage {
	
	private static final String FILE_NAME = "tasks.txt";
	private final boolean SUCCESS = true;
	private final boolean FAILURE = false;
	
	private static int numberOfTasks = 0;
	
	public boolean inputTask(String[] input) throws IOException {
		if (isValidInput(input)) {
			incrementTaskNumber();
			writeTaskToFile(input);
			return SUCCESS;
		}
		else {
			return FAILURE;
		}		
	}
	
	private void writeTaskToFile(String[] taskDetails) throws IOException {		
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
		for (int x = 0; x < taskDetails.length; x++) {
			writer.write(taskDetails[x] + " ");
		}
		writer.newLine();
		writer.close();
	}
	
	private boolean isValidInput(String[] input) {
		if (input == null || input.length == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public String[] readTasks() throws IOException {
		String[] arrayOfTasks = readFromFile();
		if (arrayOfTasks.length > 0) {
			return arrayOfTasks;
		}
		else {
			return null;
		}
	}
	
	private String[] readFromFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));		
		String[] tasks = new String[numberOfTasks];
		for (int x = 0; x < numberOfTasks; x++) {			
			tasks[x] = reader.readLine();;
		}
		reader.close();
		return tasks;
	}
	
	private void incrementTaskNumber() {
		numberOfTasks = numberOfTasks + 1;
	}
	
	private void decrementTaskNumber() {
		numberOfTasks = numberOfTasks - 1;
	}
}
