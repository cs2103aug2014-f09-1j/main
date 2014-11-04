package whatsupnext.structure;

public class Help {
	
	private static final String INDENT = "\t";
	private static final String NEWLINE = "\n";
	
	public static final String DEFAULT_HELP_MESSAGE = "Supported Commands:"
			+ NEWLINE + "add" + INDENT + "Add a task by with description only"
			+ NEWLINE + INDENT + "or append it with a deadline or time"
			+ NEWLINE + INDENT + "period."
			+ NEWLINE + "view" + INDENT + "Display the upcoming task or the"
			+ NEWLINE + INDENT + "tasks for the day. A time frame can" 
			+ NEWLINE + INDENT + "be specified too."
			+ NEWLINE + "update" + INDENT + "Update the description, deadline,"
			+ NEWLINE + INDENT + "start or end time of a task."
			+ NEWLINE + "delete" + INDENT + "Delete by id, date, deadline, or"
			+ NEWLINE + INDENT + "time frame."
			+ NEWLINE + "search" + INDENT + "Search for a task by its keyword."
			+ NEWLINE + "done" + INDENT + "Label a task as done."
			+ NEWLINE + "undo" + INDENT + "Undo most recent add/update/delete."
			+ NEWLINE + "redo" + INDENT + "Redo the most recent undo."
			+ NEWLINE + "exit" + INDENT + "Close WhatsUpNext."
			+ NEWLINE
			+ NEWLINE + "Type \"help <command>\" to find out more."
			+ NEWLINE + "Type \"help time\" to see time/date formats.";
	
	public static final String ADD_HELP_MESSAGE = "add" + INDENT + "Add a task by with description only"
			+ NEWLINE + INDENT + "or append it with a deadline or time"
			+ NEWLINE + INDENT + "period."
			+ NEWLINE
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "add [task]"
			+ NEWLINE + "add [task] by [end_time]" 
			+ NEWLINE + "add [task] from [start_time] to [end_time]"
			+ NEWLINE
			+ NEWLINE + "Aliases supported:"
			+ NEWLINE + "\'add\', \'a\'";
	
	public static final String VIEW_HELP_MESSAGE = "view" + INDENT + "Display the upcoming task or the"
			+ NEWLINE + INDENT + "tasks for the day. A time frame can" 
			+ NEWLINE + INDENT + "be specified too."
			+ NEWLINE
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "view all"
			+ NEWLINE + "view next"
			+ NEWLINE + "view [day|date]"
			+ NEWLINE + "view from [start_time] to [end_time]"
			+ NEWLINE
			+ NEWLINE + "Aliases supported:"
			+ NEWLINE + "\'view\', \'v\', \'list\', \'ls\', \'l\'";
	
	public static final String UPDATE_HELP_MESSAGE =  "update" + INDENT + "Update the description, deadline,"
			+ NEWLINE + INDENT + "start or end time of a task."
			+ NEWLINE
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "update [task_id] [new_description]"
			+ NEWLINE + "update [task_id] by [end_time]"
			+ NEWLINE + "update [task_id] from [start_time] to [end_time]"
			+ NEWLINE
			+ NEWLINE + "Aliases supported:"
			+ NEWLINE + "\'update\', \'u\', \'edit\', \'e\', \'modify\', \'m\'";
			
	public static final String DELETE_HELP_MESSAGE = "delete" + INDENT + "Delete by id, date, deadline, or"
			+ NEWLINE + INDENT + "time frame."
			+ NEWLINE
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "delete [task_id]"
			+ NEWLINE + "delete deadline"
			+ NEWLINE + "delete [date]"
			+ NEWLINE + "delete from [start_time] to [end_time]"
			+ NEWLINE
			+ NEWLINE + "Aliases supported:"
			+ NEWLINE + "\'delete\', \'del\', \'d\'";

	public static final String SEARCH_HELP_MESSAGE = "search" + INDENT + "Search for a task by its keyword."
			+ NEWLINE
			+ NEWLINE + "Formats supported:" 
			+ NEWLINE + "search [keywords]"
			+ NEWLINE
			+ NEWLINE + "Aliases supported:"
			+ NEWLINE + "\'search\', \'s\', \'find\', \'f\'";
	
	public static final String DONE_HELP_MESSAGE = "done" + INDENT + "Label a task as done."
			+ NEWLINE
			+ NEWLINE + "Formats supported:"
			+ NEWLINE + "done [task_id]";
	
	public static final String UNDO_HELP_MESSAGE = "undo" + INDENT + "Undo most recent add/update/delete.";
	
	public static final String REDO_HELP_MESSAGE = "redo" + INDENT + "Redo the most recent undo.";
	
	public static final String EXIT_HELP_MESSAGE = "exit" + INDENT + "Close WhatsUpNext.";
	
	public static final String TIME_HELP_MESSAGE = "Time formats supported:"
			+ NEWLINE + "24-hour clock" + INDENT + "\'09:00\', \'2359\'" 
			+ NEWLINE + "12-hour clock" + INDENT + "\'9:00 am, \'11 pm"
			+ NEWLINE
			+ NEWLINE + "Date formats supported:"
			+ NEWLINE + "DayMonthYear" + INDENT + "\'021114\', \'31102014\'"
			+ NEWLINE + "Day/Month/Year" + INDENT + "\'02/11/14\', \'31/10/2014\'"
			+ NEWLINE + "Day-Month-Year" + INDENT + "\'02-11-14\', \'31-10-2014\'"
			+ NEWLINE
			+ NEWLINE + "Special formats supported:"
			+ NEWLINE + "Day of Week" + INDENT + "\'monday\', \'mon\'"
			+ NEWLINE + "Today date" + INDENT + INDENT + "\'today\'"
			+ NEWLINE + "Tomorrow date" + INDENT + "\'tomorrow\', \'tml\'"
			+ NEWLINE + "Current Time" + INDENT + "\'now\'"
			+ NEWLINE
			+ NEWLINE + "Valid examples:"
			+ NEWLINE + "\'0000 010114\', \'31/12/2014 23:59\'"
			+ NEWLINE + "\'7:30 am sunday\', \'tml 12 pm\'"
			+ NEWLINE + "\'0930 10-11-14\', \'11:30 am today\'"
			+ NEWLINE +  "\'now\'"
			+ NEWLINE
			+ NEWLINE + "Things to note:"
			+ NEWLINE + "When day of week is specified, it is referring to the upcoming day of week."
			+ NEWLINE + "When only time is specified, it will be assigned with today date."
			+ NEWLINE + "When only date is specified, it will be assigned either 0000 for start time or 2359 for end time.";
	
}
