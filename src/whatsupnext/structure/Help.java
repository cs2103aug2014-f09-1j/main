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
	
	public static final String ADD_HELP_MESSAGE = "add" + INDENT + "Add a task by with description only or"
			+ NEWLINE + INDENT + "append it with a deadline or time period."
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "add [task]"
			+ NEWLINE + "add [task] by [end_time]" 
			+ NEWLINE + "add [task] from [start_time] to [end_time]";
	
	public static final String VIEW_HELP_MESSAGE = "view" + INDENT + "Display the upcoming task or the tasks"
			+ NEWLINE + INDENT + "for the day. A time frame can be" 
			+ NEWLINE + INDENT + "specified too."
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "view all"
			+ NEWLINE + "view next"
			+ NEWLINE + "view [day|date]"
			+ NEWLINE + "view from [start_time] to [end_time]";
	
	public static final String UPDATE_HELP_MESSAGE =  "update" + INDENT + "Update the description, deadline, start"
			+ NEWLINE + INDENT + "or end time of a task."
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "update [task_id] [new_description]"
			+ NEWLINE + "update [task_id] by [end_time]"
			+ NEWLINE + "update [task_id] from [start_time] to [end_time]";
	
	public static final String DELETE_HELP_MESSAGE = "Delete by id, date, deadline, or time frame."
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "delete [task_id]"
			+ NEWLINE + "delete deadline"
			+ NEWLINE + "delete [date]"
			+ NEWLINE + "delete from [start_time] to [end_time]";
	
	public static final String DONE_HELP_MESSAGE = "Label a task as done."
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "done [task_id]";
	
	public static final String SEARCH_HELP_MESSAGE = "search" + INDENT + "Search for a task by its keyword."
	+ NEWLINE + "Formats supported:" 
	+ NEWLINE + "search [keywords]";
	
	public static final String UNDO_HELP_MESSAGE = "undo" + INDENT + "Undo most recent add/update/delete.";
	
	public static final String REDO_HELP_MESSAGE = "redo" + INDENT + "Redo the most recent undo.";
	
	public static final String EXIT_HELP_MESSAGE = "exit" + INDENT + "Close WhatsUpNext.";
	
}
