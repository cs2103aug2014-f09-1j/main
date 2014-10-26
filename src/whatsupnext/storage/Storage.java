package whatsupnext.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;

import whatsupnext.structure.Task;

import static org.junit.Assert.*;


public class Storage {
	
	private static Storage storageSingleton;
	private String FILE_NAME;
	private final static String EXTENSION = ".txt";
	private final boolean SUCCESS = true;
	private final boolean FAILURE = false;
	public final static String DELIMITER = "%#";	
	private final static int NUMBER_OF_VERSIONS = 3; 
	
	private LinkedList<File> fileVersions;
	private static File currentFile; 
	private static int currentFileVersionNumber;
	
	/**
	 * Tries to initialize the Storage singleton with the file name
	 * @param fileName
	 * @return
	 * 		True if successful or False otherwise
	 */
	public static boolean tryInitialize(String fileName) {
		if (storageSingleton == null) {
			storageSingleton = new Storage(fileName);
			return true;
		}
		return false;
	}
	
	public static Storage getInstance() {
		if (storageSingleton == null) {
			storageSingleton = new Storage("tasks");
		}
		return storageSingleton;
	}
	
	private Storage(String fileName) {
		FILE_NAME = fileName;
		currentFileVersionNumber = 0;		
		File textFile = new File(FILE_NAME + EXTENSION);
		if (!textFile.exists()) {
			try {
				textFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileVersions = new LinkedList<File>();
		fileVersions.add(textFile);
		currentFile = textFile;
		
	}
	
	/**
	 * Calls the writeTasksToFile function if the ArrayList is valid. Returns true if writing was 
	 * successful, and false otherwise.
	 * @param tasks
	 * @return
	 * @throws IOException
	 */
	public boolean inputTasks(ArrayList<Task> tasks) throws IOException {
		if (isValidInput(tasks)) {			
			writeTasksToFile(tasks);
			return SUCCESS;
		} else if (tasks.size() == 0) {
			clearFile();
			return SUCCESS;
		} else {
			return FAILURE;
		}		
	}	
	
	/**
	 * Writes the tasks to the file, one on each line, separating tokens in each line with "%#".
	 * @param tasks
	 * @throws IOException
	 */
	private void writeTasksToFile(ArrayList<Task> tasks) throws IOException {
		setNewVersion();	
		BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
		for (int x = 0; x < tasks.size(); x++) {
			Task taskToBeWritten = tasks.get(x);
			writer.write(taskToString(taskToBeWritten));
			writer.newLine();
			writer.flush();
		}		
		writer.close();
	}
	
	/**
	 * Checks if the ArrayList of tasks is valid, namely that the tasks field is not empty.
	 * @param tasks
	 * @return
	 */
	private boolean isValidInput(ArrayList<Task> tasks) {
		if (tasks == null || tasks.size() == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Calls the readFromFile function, and returns the ArrayList of tasks read from the file if it is not empty.
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Task> readTasks() throws IOException {
		ArrayList<Task> arrayOfTasks = readFromFile();
		if (arrayOfTasks.size() > 0) {
			return arrayOfTasks;
		}
		else {
			return new ArrayList<Task>();
		}
	}
	
	/**
	 * Reads the contents of the file, converts each line to a task, and returns all of them as 
	 * an ArrayList of tasks.
	 * @return
	 * @throws IOException
	 */
	private ArrayList<Task> readFromFile() throws IOException {
		Scanner reader = new Scanner(currentFile);
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		while (reader.hasNextLine()) {
			String taskInString = reader.nextLine();
			tasks.add(stringToTask(taskInString));	
		}
		
		reader.close();
		return tasks;
	}
	
	/**
	 * Converts String to Task, by creating a new one with fields that have been read from the string. This helps when 
	 * reading each line of the file as a String.
	 * @param taskInString
	 * @return
	 */
	public Task stringToTask(String taskInString) {
		Scanner extractFromString = new Scanner(taskInString);
		extractFromString.useDelimiter(DELIMITER);
		
		String taskID = extractFromString.next();
		String description = extractFromString.next();
		String startTime = extractFromString.next();
		String endTime = extractFromString.next();
		String booleanString = extractFromString.next();
		
		assertTrue(booleanString.equals("true") || booleanString.equals("false"));		
		boolean isDone = Boolean.parseBoolean(booleanString);
		
		Task taskFromString = new Task();
		taskFromString.setTaskID(taskID);
		taskFromString.setDescription(description);
		taskFromString.setStartTime(startTime);
		taskFromString.setEndTime(endTime);
		taskFromString.setDone(isDone);
		
		extractFromString.close();
		
		return taskFromString;
	}
		
	public String taskToString(Task task) {
		assertNotNull(task.getTaskID());
		assertNotNull(task.getDescription());
		assertNotNull(task.getStartTime());
		assertNotNull(task.getEndTime());
		assertNotNull(task.getDone());
		return task.getTaskID() + DELIMITER + task.getDescription() + DELIMITER + 
				task.getStartTime() + DELIMITER + task.getEndTime() + 
				DELIMITER + task.getDone() + DELIMITER;
	}
	
	public void clearFile() throws IOException {
		BufferedWriter clearingWriter = new BufferedWriter(new FileWriter(currentFile, false));
		clearingWriter.close();
	}
	
	private void setNewVersion() throws IOException {
		if (currentFileVersionNumber < fileVersions.size() - 1) {
			deleteLaterVersions();
		}
		if (fileVersions.size() < NUMBER_OF_VERSIONS) {
			addNewVersion();
		}
		else if (fileVersions.size() == NUMBER_OF_VERSIONS) {
			deleteAndAddNewVersion();
		}
	}
	
	private void addNewVersion() throws IOException {
		File newNameFile = new File(FILE_NAME + (fileVersions.size() - 1) + EXTENSION);
		transferData(currentFile.getName(), newNameFile.getName());
		fileVersions.remove(currentFileVersionNumber);
		
		fileVersions.add(newNameFile);
		currentFile = new File(FILE_NAME + EXTENSION);
		fileVersions.add(currentFile);
		currentFileVersionNumber = currentFileVersionNumber + 1;
	}
	
	private void deleteAndAddNewVersion() throws IOException {
		File oldFile = fileVersions.remove();
		oldFile.delete();
		for (int x = 0; x < fileVersions.size(); x++) {
			File f = fileVersions.get(x);
			transferData(f.getName(), FILE_NAME + x + EXTENSION);
		}
		File newNameFile = new File(FILE_NAME + 0 + EXTENSION);
		fileVersions.addFirst(newNameFile);
	}
	

	private void deleteLaterVersions() throws IOException{
		for (int x = currentFileVersionNumber + 1; x < fileVersions.size(); x++) {
			File f = fileVersions.remove(x);
			f.delete();			
		}
	}
	
	public boolean goToPreviousVersion() {
		if (currentFileVersionNumber > 0) {
			currentFile = fileVersions.get(currentFileVersionNumber - 1);
			currentFileVersionNumber = currentFileVersionNumber - 1;
			return SUCCESS;
		}
		else {
			return FAILURE;
		}
	}	
	
	public boolean goToNextVersion() {
		if (currentFileVersionNumber < fileVersions.size() - 1) {
			currentFile = fileVersions.get(currentFileVersionNumber + 1);
			currentFileVersionNumber = currentFileVersionNumber + 1;
			return SUCCESS;
		}
		else {
			return FAILURE;
		}
	}
	
	public void deleteFileVersions() throws IOException{
		for (int x = 0; x < fileVersions.size(); x++) {
			File toBeDeleted = fileVersions.get(x);
			System.out.println(toBeDeleted.getName() + " inside delete");
			if (!toBeDeleted.getName().equals(currentFile.getName())) {
				System.out.println(toBeDeleted.getName() + " is deleted");
				fileVersions.remove(x);
				toBeDeleted.delete();
			}			
		}
		currentFileVersionNumber = 0;
		File newNameFile = new File(FILE_NAME + EXTENSION);
		currentFile.renameTo(newNameFile);
		
	}
	
	private void transferData(String fromFile, String toFile) throws IOException{
		Scanner sc = new Scanner(new File(fromFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(toFile));
		while (sc.hasNextLine()) {
			bw.write(sc.nextLine());
			bw.newLine();
		}
		bw.flush();
		bw.close();
		sc.close();
	}
}
