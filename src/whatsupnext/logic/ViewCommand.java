package whatsupnext.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import whatsupnext.structure.Task;
import whatsupnext.structure.TaskComparators.TaskDefaultComparator;
import whatsupnext.structure.Types.VIEWTYPE;

public class ViewCommand extends Command {

	private final String MESSAGE_NOTFOUND = "No tasks are found.";
	private VIEWTYPE viewType;
	private ArrayList<Task> list = LogicUtilities.getTaskList();
	private ArrayList<String> output = LogicUtilities.getOutputList();
	
	public ViewCommand(Task task) {
		super(task);
		viewType = task.getViewType();
	}
	
	public String executeCommand() {	
		switch (viewType) {
			case ALL:
				viewAll();
				break;
			case NEXT:
				viewNext();
				break;
			case DATE:
				viewDate();
				break;
			case TIMEFRAME:
				viewTimeFrame();
				break;
			default:
				break;
		}
		
		String feedbackView = Logic.formatArrayAsString(output);
		output.clear();
		
		return feedbackView;
	}
	
	/*
	 * Four types of VIEW functions.
	 */
	private void viewTimeFrame() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && !Logic.endsBeforeDeadline(task, startTime) && Logic.endsBeforeDeadline(task, endTime)) {
				String taskInfo = Logic.getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
	}

	private void viewDate() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() && Logic.endsOnGivenDate(task, endTime)) {
				String taskInfo = Logic.getFormattedOutput(task);
				output.add(taskInfo);
			}
		}
	}
	
	private void viewNext() {
		Task currentTask;
		long nearestEndTime = 999999999999L;
		ArrayList<Task> sortedList = new ArrayList<Task>(list);
		Collections.sort(sortedList, new TaskDefaultComparator());
		Iterator<Task> taskIterator = sortedList.iterator();
		
		while (taskIterator.hasNext()) {
			currentTask = taskIterator.next();
			if (!currentTask.getEndTime().isEmpty() && !Logic.endsBeforeDeadline(currentTask, endTime)) {
				if (output.isEmpty()) {
					nearestEndTime = Long.parseLong(currentTask.getEndTime());
					String taskInfo = Logic.getFormattedOutput(currentTask);;
					output.add(taskInfo);
				} else {
					if (Long.parseLong(currentTask.getEndTime()) < nearestEndTime) {
						output.clear();
						nearestEndTime = Long.parseLong(currentTask.getEndTime());
						String taskInfo = Logic.getFormattedOutput(currentTask); 
						output.add(taskInfo);
					} else if (Long.parseLong(currentTask.getEndTime()) == nearestEndTime) {
						String taskInfo = Logic.getFormattedOutput(currentTask);
						output.add(taskInfo);
					}
				}
			}				
		}
		
		if (output.isEmpty()) {
			output.add(MESSAGE_NOTFOUND);
		}
	}
		
	private void viewAll() {			
		Iterator<Task> taskIterator = list.iterator();
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			String taskInfo = Logic.getFormattedOutput(task);
			output.add(taskInfo);
		}
	}	
}
