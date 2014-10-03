package Storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Structure.Task;


public class Storage {
	
	private static final String FILE_NAME = "tasks.txt";
	private final boolean SUCCESS = true;
	private final boolean FAILURE = false;
	
	private static int numberOfTasks = 0;
	
	public Storage() {
		
	}
	
	public boolean inputTasks(ArrayList<Task> tasks) throws IOException {
		if (isValidInput(tasks)) {
			incrementTaskNumber(tasks.size());
			writeTasksToFile(tasks);
			return SUCCESS;
		}
		else {
			return FAILURE;
		}		
	}	
	
	private void writeTasksToFile(ArrayList<Task> tasks) throws IOException {		
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
		for (int x = 0; x < tasks.size(); x++) {
			Task taskToBeWritten = tasks.get(x);
			writer.write(taskToBeWritten.getDescription() + "," + taskToBeWritten.getStartTime() + "," + taskToBeWritten.getEndTime());
			writer.newLine();
		}		
		writer.close();
	}
	
	private boolean isValidInput(ArrayList<Task> tasks) {
		if (tasks == null || tasks.size() == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public ArrayList<Task> readTasks() throws IOException {
		ArrayList<Task> arrayOfTasks = readFromFile();
		if (arrayOfTasks.size() > 0) {
			return arrayOfTasks;
		}
		else {
			return null;
		}
	}
	
	private ArrayList<Task> readFromFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));		
		ArrayList<Task> tasks = new ArrayList<Task>(numberOfTasks);
		for (int x = 0; x < numberOfTasks; x++) {			
			String taskInString = reader.readLine();
			tasks.add(x, StringToTask(taskInString));			
		}
		reader.close();
		return tasks;
	}
	
	public Task StringToTask(String taskInString) {
		Scanner extractFromString = new Scanner(taskInString);
		extractFromString.useDelimiter(",");
		
		String description = extractFromString.next();
		String startTime = extractFromString.next();
		String endTime = extractFromString.next();
		
		Task taskFromString = new Task();
		taskFromString.setDescription(description);
		taskFromString.setStartTime(startTime);
		taskFromString.setEndTime(endTime);
		
		extractFromString.close();
		
		return taskFromString;
	}
	
	private void incrementTaskNumber(int increase) {
		numberOfTasks = numberOfTasks + increase;
	}
	
	private void decrementTaskNumber() {
		numberOfTasks = numberOfTasks - 1;
	}
}
