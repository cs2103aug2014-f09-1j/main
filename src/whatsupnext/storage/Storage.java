package whatsupnext.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import whatsupnext.structure.Task;
import static org.junit.Assert.assertNotNull;


public class Storage {
	
	private static Storage storageSingleton;
	private String FILE_NAME;
	private final static String EXTENSION = ".txt";
	private final boolean SUCCESS = true;
	private final boolean FAILURE = false;	 
		
	private static ArrayList<ArrayList<Task>> arrayOfVersions;
	private static int currentVersionNumber;
	
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
		File textFile = new File(FILE_NAME + EXTENSION);
		if (!textFile.exists()) {
			try {
				textFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		arrayOfVersions = new ArrayList<ArrayList<Task>>();
		currentVersionNumber = 0;
		
		try {
			arrayOfVersions.add(readTasks());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
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
			setNewVersion(tasks);
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
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME + EXTENSION));
		
		for (int x = 0; x < tasks.size(); x++) {
			Task taskToBeWritten = tasks.get(x);
			writer.write(taskToJSONString(taskToBeWritten));
			writer.newLine();
			writer.flush();
		}		
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public String taskToJSONString(Task task) {
		assertNotNull(task.getTaskID());
		assertNotNull(task.getDescription());
		assertNotNull(task.getStartTime());
		assertNotNull(task.getEndTime());
		assertNotNull(task.getDone());
		
		JSONArray taskDetailsArray = new JSONArray();
		
		taskDetailsArray.add(task.getTaskID());
		taskDetailsArray.add(task.getDescription());
		taskDetailsArray.add(task.getStartTime());
		taskDetailsArray.add(task.getEndTime());
		taskDetailsArray.add(task.getDone());
		
		return taskDetailsArray.toJSONString();
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
		Scanner reader = new Scanner(new File(FILE_NAME + EXTENSION));
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		try {
		
		    while (reader.hasNextLine()) {
		    	JSONParser parser=new JSONParser();
			    Object parsedJSON = parser.parse(reader.nextLine());
			    JSONArray arr = (JSONArray) parsedJSON;
			    
			    String taskID = (String) arr.get(0);
		    	String description = (String) arr.get(1);
		    	String startTime = (String) arr.get(2);
		    	String endTime = (String) arr.get(3);
		    	boolean isDone = (boolean) arr.get(4);
		    	
		    	Task taskFromJSON = new Task();
		    	taskFromJSON.setTaskID(taskID);
		    	taskFromJSON.setDescription(description);
		    	taskFromJSON.setStartTime(startTime);
		    	taskFromJSON.setEndTime(endTime);
		    	taskFromJSON.setDone(isDone);
	
		    	tasks.add(taskFromJSON);
		    }
		}
		catch (ParseException p) {
			p.printStackTrace();
		}
		reader.close();
		return tasks;
	}
	
	public void clearFile() throws IOException {
		BufferedWriter clearingWriter = new BufferedWriter(new FileWriter(FILE_NAME + EXTENSION, false));
		
		clearingWriter.close();
	}
	
	private void setNewVersion(ArrayList<Task> tasks) throws IOException {
		if (currentVersionNumber < arrayOfVersions.size() - 1) {
			deleteLaterVersions();
		}
		currentVersionNumber++;			
		arrayOfVersions.add(tasks);
	}	
	
	private void deleteLaterVersions() throws IOException {
		for (int x = 0; x < arrayOfVersions.size() - currentVersionNumber - 1; x++) {
			arrayOfVersions.remove(currentVersionNumber + 1);
		}
	}
	
	public boolean goToPreviousVersion() {
		try {
			if (arrayOfVersions.size() > 1 && currentVersionNumber > 0) {
				writeTasksToFile(arrayOfVersions.get(currentVersionNumber - 1));
				currentVersionNumber = currentVersionNumber - 1;
				return SUCCESS;
			}
			else {
				return FAILURE;
			}
		}
		catch (IOException e) {
			return FAILURE;
		}
	}	
	
	public boolean goToNextVersion() {
		try {
			if (currentVersionNumber < arrayOfVersions.size() - 1) {
				writeTasksToFile(arrayOfVersions.get(currentVersionNumber + 1));
				currentVersionNumber = currentVersionNumber + 1;
				return SUCCESS;
			}
			else {
				return FAILURE;
			}
		}
		catch (IOException e) {
			return FAILURE;
		}
	}	
	
	public void deleteFileVersions() throws IOException {
		currentVersionNumber = 0;
		arrayOfVersions = new ArrayList<ArrayList<Task>>();
		arrayOfVersions.add(readTasks());
	} 
}
