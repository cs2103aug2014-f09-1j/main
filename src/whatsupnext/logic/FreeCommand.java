package whatsupnext.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import whatsupnext.structure.enums.Types.FREETYPE;
import whatsupnext.structure.util.Task;

public class FreeCommand extends Command {

	private ArrayList<Task> list = LogicUtilities.list;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<String> output = new ArrayList<String>();
	private FREETYPE freeType;
	private String feedback;
	private String MESSAGE_NOTFOUND = "No free time slots are found.";

	private int duration = Integer.parseInt(description);
	private long startDate;
	private long endDate = Long.parseLong(endTime) / 10000;
	
	public FreeCommand(Task task) {
		super(task);
		freeType = task.getFreeType();
	}

	public String executeCommand() {
		getTasks();
		
		switch (freeType) {
		case DATE:
			freeDate(endDate);
			break;
		case TIMEFRAME:
			startDate = Long.parseLong(startTime) / 10000;
			freeTimeFrame(startDate, endDate);
			break;
		default:
			break;
		}
		
		output.clear();
		return feedback;
	}
	
	private void freeDate (long date) {
		findOnDate(date);
		if (output.isEmpty()) {
			feedback = MESSAGE_NOTFOUND;
		} else {
			feedback = "Available time slots:\n" + LogicUtilities.formatArrayAsString(output);
		}		
	}
	
	private void freeTimeFrame (long startDate, long endDate) {
		String date = Long.toString(startDate);
		String end = Long.toString(endDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		while (!date.equalsIgnoreCase(end)) {
			long day = Long.parseLong(date);
			findOnDate(day);
			c.add(Calendar.DATE, 1);
			date = sdf.format(c.getTime());
		}
		
		long day = Long.parseLong(date);
		findOnDate(day);
		
		if (output.isEmpty()) {
			feedback = MESSAGE_NOTFOUND;
		} else {
			feedback = "Available time slots:\n" + LogicUtilities.formatArrayAsString(output);
		}
	}
	
	private void findOnDate (long date) {
		int[] timeSlot = getTimeSlots(date);
		int i=0, j;
		
		while (i<16) {
			int length = 0;
			j=i;
			
			while ((j<16)&&(timeSlot[j]==1)) {
				length++;
				j++;
			}
			
			if (length >= duration) {
				String answer = getTime(date,i,j);
				output.add(answer);
			}
			
			i=j+1;
		}
	}
	
	private int[] getTimeSlots(long date) {
		int[] timeSlot = new int[16];		
			
		for (int i = 0; i <= 15; i++) {
			timeSlot[i] = 1;
			long headtime = date * 10000 + (i+6) * 100;
			long tailtime = date * 10000 + (i+6) * 100 + 59;
			
			Iterator<Task> taskIterator = tasks.iterator();
			while (taskIterator.hasNext()) {
				Task task = taskIterator.next();
				long stime = Long.parseLong(task.getStartTime());
				long etime = Long.parseLong(task.getEndTime());
				
				if ((headtime >= stime) && (tailtime <= etime)) {
					timeSlot[i] = 0;
				}
		    }	
		}
		
		return timeSlot;
	}
	
	private String getTime (long date, int i, int j) {
		String headtime = Long.toString(date * 10000 + (i+6) * 100);
		String tailtime = Long.toString(date * 10000 + (j+6) * 100);
		String time = LogicUtilities.getFormattedTime(headtime) + " - " + LogicUtilities.getFormattedTime(tailtime);		
		return time;
	}
	
	private void getTasks() {
		Iterator<Task> taskIterator = list.iterator();
		
		while (taskIterator.hasNext()) {
			Task task = taskIterator.next();
			if (!task.getEndTime().isEmpty() &&	!task.getStartTime().isEmpty()
					&& (task.getDone() == false)) {
				tasks.add(task);
			}
		}
	}
}
