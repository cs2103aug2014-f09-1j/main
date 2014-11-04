package whatsupnext.structure;

public class Help {
	
	private static final String INDENT = "\t";
	private static final String NEWLINE = "\n";
	
	public static final String DEFAULT_HELP_MESSAGE = "Type \"help <command>\" to find out more."
			+ NEWLINE + "Supported Commands:"
			+ NEWLINE + "add" + INDENT + "Add a task by with description only or"
			+ NEWLINE + INDENT + "append it with a deadline or time period."
			+ NEWLINE + "view" + INDENT + "Display the upcoming task or the tasks"
			+ NEWLINE + INDENT + "for the day. A time frame can be" 
			+ NEWLINE + INDENT + "specified too."
			+ NEWLINE + "update" + INDENT + "Update the description, deadline, start"
			+ NEWLINE + INDENT + "or end time of a task."
			+ NEWLINE + "delete" + INDENT + "Delete by id, date, deadline, or time"
			+ NEWLINE + INDENT + "frame."
			+ NEWLINE + "search" + INDENT + "Search for a task by its keyword."
			+ NEWLINE + "done" + INDENT + "Label a task as done."
			+ NEWLINE + "undo" + INDENT + "Undo most recent add/update/delete."
			+ NEWLINE + "redo" + INDENT + "Redo the most recent undo."
			+ NEWLINE + "exit" + INDENT + "Close WhatsUpNext.";

	public static final String ADD_HELP_MESSAGE_BRIEF = "Add a task by specifying the task description only or" + "\n" 
			+ " a specific deadline or a time period."; 
	
	public static final String ADD_HELP_MESSAGE_DETAILED = "Add a task by specifying the task description only or" + "\n"
			+ " a specific deadline or a time period." + "\n"
			+ "Formats supported:" + "\n"
			+ "	add [task]" + "\n"
			+ "	add [task] by [end_time]" + "\n" 
			+ "	add [task] from [start_time] to [end_time]";
	
	public static final String VIEW_HELP_MESSAGE_BRIEF = "Displays the upcoming task or the tasks for the day." + "\n"
			+ "A time frame can be specified to manage your schedule better within that period.";
	
	public static final String VIEW_HELP_MESSAGE_DETAILED = "Displays the upcoming task or the tasks for the day." + "\n"
			+ "A time frame can be specified to manage your schedule better within that period."  + "\n"
			+ "Formats supported:" + "\n"
			+ "	view all" + "\n"
			+ "	view next" + "\n"
			+ "	view [day|date]" + "\n"
			+ "	view from [start_time] to [end_time]";
	
	public static final String UPDATE_HELP_MESSAGE_BRIEF = "Update the description, deadline, start, or end time of a task.";
	
	public static final String UPDATE_HELP_MESSAGE_DETAILED =  "Update the description, deadline, start, or end time of a task." + "\n"
			+ "Formats supported:" + "\n"
			+ "	update [task_id] [new_description]" + "\n"
			+ "	update [task_id] by [end_time]" + "\n"
			+ "	update [task_id] from [start_time] to [end_time]";
	
	public static final String DELETE_HELP_MESSAGE_BRIEF = "Delete by id, date, deadline, or time frame.";
	
	public static final String DELETE_HELP_MESSAGE_DETAILED = "Delete by id, date, deadline, or time frame." + "\n"
			+ "Formats supported:" + "\n"
			+ "	delete [task_id]" + "\n"
			+ "	delete deadline" + "\n"
			+ "	delete [date]" + "\n"
			+ "	delete from [start_time] to [end_time]";
	
	public static final String DONE_HELP_MESSAGE_BRIEF = "Label a task as done.";
	
	public static final String DONE_HELP_MESSAGE_DETAILED = "Label a task as done." + "\n"
			+ "Formats supported:" + "\n"
			+ " done [task_id]";
	
	public static final String SEARCH_HELP_MESSAGE = "Formats supported:" + "\n" + "	search [keywords]";
	
	public static final String UNDO_HELP_MESSAGE = "Undo works for a maximum of two times.";
	
	public static final String REDO_HELP_MESSAGE = "Redo works for a maximum of " + "\n"
			+ "the number of consecutive undo commands, ";
	
	public static final String EXIT_HELP_MESSAGE = "Exit WhatsUpNext.";
	
}
