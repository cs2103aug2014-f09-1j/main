//@author A0111773L
package whatsupnext.junit.parser;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import whatsupnext.parser.api.Parser;
import whatsupnext.parser.extractor.ParseDate;
import whatsupnext.structure.enums.OPCODE;
import whatsupnext.structure.enums.Types.ADDTYPE;
import whatsupnext.structure.enums.Types.DELETETYPE;
import whatsupnext.structure.enums.Types.UPDATETYPE;
import whatsupnext.structure.enums.Types.VIEWTYPE;
import whatsupnext.structure.util.Task;

public class ParserTest {
	
	@Test
	/* This is a test case for the 'add timeframe' partition */
	public void testParserAddTimeframe() {
		String input = "add dine with Amy from 7 am tml to 9 AM tomorrow";	
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE",OPCODE.ADD,task.getOpCode());
        assertEquals("addType",ADDTYPE.TIMEFRAME,task.getAddType());
		assertEquals("description", "dine with Amy", task.getDescription());
		assertEquals("startTime", getTomorrowDate()+"0700", task.getStartTime());
		assertEquals("endTime", getTomorrowDate()+"0900", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'add deadline' partition */
	public void testParserAddDeadline() {
		String input = "add submit report by 10 PM tml";	
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("addType",ADDTYPE.DEADLINE,task.getAddType());
		assertEquals("description", "submit report", task.getDescription());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", getTomorrowDate()+"2200", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'add floating' partition */
	public void testParserAddFloating() {
		String input = "add submit report";	
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.ADD, task.getOpCode());
        assertEquals("addType",ADDTYPE.FLOATING,task.getAddType());
		assertEquals("description", "submit report", task.getDescription());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", "", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'add empty' partition */
	public void testParserAddInvalidDesc() {
		String MESSAGE_INVALID_DESCRIPTION = "'add' must have a valid description";
		String input = "add";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'add invalid start_time' partition */
	public void testParserAddInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'add' must have a valid start time";
		String input = "add invalid from 23/09/14 to tml";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'add invalid end_time' partition */
	public void testParserAddInvalidEndtime() {
		String MESSAGE_INVALID_END_TIME = "'add' must have a valid end time";
		String input = "add invalid from tml to 23/09/14";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'add invalid start_end_time' partition */
	public void testParserAddInvalidStartEndTime() {
		String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
		String input = "add invalid from tml to today";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'update timeframe' partition */
	public void testParserUpdateTimeframe() {
		String input = "update 19 from tml 9 am to tml 12 pm";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.UPDATE, task.getOpCode());
		assertEquals("updateType", UPDATETYPE.TIMEFRAME, task.getUpdateType());
        assertEquals("taskID", "19", task.getTaskID());
		assertEquals("description", "", task.getDescription());
		assertEquals("startTime", getTomorrowDate()+"0900", task.getStartTime());
		assertEquals("endTime", getTomorrowDate()+"1200", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'update deadline' partition */
	public void testParserUpdateDeadline() {
		String input = "update 19 by 18:00 tml";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("updateType", UPDATETYPE.DEADLINE, task.getUpdateType());
        assertEquals("taskID", "19", task.getTaskID());
		assertEquals("description", "", task.getDescription());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", getTomorrowDate()+"1800", task.getEndTime());	
	}
	
	@Test
	/* This is a test case for the 'update floating' partition */
	public void testParserUpdateDescription() {
		String input = "update 19 new descripitions1234";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.UPDATE, task.getOpCode());
        assertEquals("updateType", UPDATETYPE.DESCRIPTION, task.getUpdateType());
        assertEquals("taskID", "19", task.getTaskID());
		assertEquals("description", "new descripitions1234", task.getDescription());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", "", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'update invalid ID' partition */
	public void testParserUpdateInvalidID() {
		String MESSAGE_INVALID_TASKID = "'update' must have a valid Task ID";
		String input = "update -1";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_TASKID, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'update empty' partition */
	public void testParserUpdateInvalidDesc() {
		String MESSAGE_INVALID_DESCRIPTION = "'update' must have a valid description";
		String input = "update 19";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'update invalid start_time' partition */
	public void testParserUpdateInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'update' must have a valid start time";
		String input = "update 19 d from 23/09/14 to tml";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'update invalid end_time' partition */
	public void testParserUpdateInvalidEndtime() {
		String MESSAGE_INVALID_END_TIME = "'update' must have a valid end time";
		String input = "update 19 by 23/09/14";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'update invalid start_end_time' partition */
	public void testParserUpdateInvalidStartEndTime() {
		String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
		String input = "update 19 from tml to today";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'view undone' partition */
	public void testParserViewUndone() {
		String input = "view";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.VIEW, task.getOpCode());
		assertEquals("viewType", VIEWTYPE.UNDONE, task.getViewType());
	}
	
	@Test
	/* This is a test case for the 'view all' partition */
	public void testParserViewAll() {
		String input = "view all";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.VIEW, task.getOpCode());
		assertEquals("viewType", VIEWTYPE.ALL, task.getViewType());
	}
	
	@Test
	/* This is a test case for the 'view next' partition */
	public void testParserViewNext() {
		String input = "view next";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.VIEW, task.getOpCode());
		assertEquals("viewType", VIEWTYPE.NEXT, task.getViewType());
	}
	
	@Test
	/* This is a test case for the 'view timeframe' partition */
	public void testParserViewTimeframe() {
		String input = "v from 1900 29092014 to 2030 29092014";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.VIEW, task.getOpCode());
        assertEquals("viewType", VIEWTYPE.TIMEFRAME, task.getViewType());
		assertEquals("description", "", task.getDescription());
		assertEquals("startTime", "201409291900", task.getStartTime());
		assertEquals("endTime", "201409292030", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'view date' partition */
	public void testParserViewDate() {
		String input = "view 1900 29092014";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.VIEW, task.getOpCode());
        assertEquals("viewType", VIEWTYPE.DATE, task.getViewType());
        assertEquals("description", "", task.getDescription());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", "201409291900", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'view invalid start_time' partition */
	public void testParserViewInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'view' must have an valid start time";
		String input = "view from 32/09/14 to tml";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'view invalid end_time' partition */
	public void testParserViewInvalidEndtime() {
		String MESSAGE_INVALID_END_TIME = "'view' must have an valid end time";
		String input = "view from today to 32/09/14";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'view invalid start_end_time' partition */
	public void testParserViewInvalidStartEndTime() {
		String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
		String input = "view from tml to today";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'view invalid date' partition */
	public void testParserViewInvalidDate() {
		String MESSAGE_INVALID_DATE = "'view' must have an valid date";
		String input = "view 32/09/14";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_DATE, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'delete id' partition */
	public void testParserDeleteID() {
		String input = "delete 19";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.DELETE, task.getOpCode());
        assertEquals("deleteType", DELETETYPE.ID, task.getDeleteType());
        assertEquals("taskID", "19", task.getTaskID());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", "", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'delete deadline' partition */
	public void testParserDeleteTimeDeadline() {
		String input = "delete deadline";
		Parser parser = new Parser(input);
		ParseDate parseDate = new ParseDate();
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.DELETE, task.getOpCode());
        assertEquals("deleteType", DELETETYPE.DEADLINE, task.getDeleteType());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", parseDate.getCurrentTime(), task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'delete date' partition */
	public void testParserDeleteDate() {
		String input = "d 2030 29092014";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.DELETE, task.getOpCode());
        assertEquals("deleteType", DELETETYPE.DATE, task.getDeleteType());
		assertEquals("startTime", "", task.getStartTime());
		assertEquals("endTime", "201409292030", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'delete timeframe' partition */
	public void testParserDeleteTimeframe() {
		String input = "d from 1900 29092014 To 2030 29092014";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.DELETE, task.getOpCode());
        assertEquals("deleteType", DELETETYPE.TIMEFRAME, task.getDeleteType());
		assertEquals("startTime", "201409291900", task.getStartTime());
		assertEquals("endTime", "201409292030", task.getEndTime());
	}
	
	@Test
	/* This is a test case for the 'delete empty' partition */
	public void testParserDeleteInvalid() {
		String MESSAGE_INVALID_ARGUMENT = "'delete' must have an argument";
		String input = "delete";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_ARGUMENT, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'delete invalid start_time' partition */
	public void testParserDeleteInvalidStartTime() {
		String MESSAGE_INVALID_START_TIME = "'delete' must have an valid start time";
		String input = "delete from 32/09/14 to tml";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'delete invalid end_time' partition */
	public void testParserDeleteInvalidEndtime() {
		String MESSAGE_INVALID_END_TIME = "'delete' must have an valid end time";
		String input = "delete from today to 32/09/14";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'delete invalid start_end_time' partition */
	public void testParserDeleteInvalidStartEndTime() {
		String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time"; 
		String input = "delete from tml to today";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_START_END_TIME, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'delete invalid date' partition */
	public void testParserdeleteInvalidDate() {
		String MESSAGE_INVALID_TASKID_OR_DATE = "'delete' must have a valid Task ID or Date";
		String input = "delete 32/09/14";	
		Parser parser = new Parser(input);
		try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
			assertEquals(MESSAGE_INVALID_TASKID_OR_DATE, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'search keyword' partition */
	public void testParserSearch() {
		String input = "search for this keyword";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.SEARCH, task.getOpCode());
        assertEquals("OPCODE", "for this keyword", task.getSearchKeyword());
	}
	
	@Test
	/* This is a test case for the 'search empty' partition */
	public void testParserSearchEmpty() {
		String MESSAGE_INVALID_DESCRIPTION = "'Search' must have valid keywords";
		String input = "search";
		Parser parser = new Parser(input);      
        try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
	        assertEquals("OPCODE", MESSAGE_INVALID_DESCRIPTION, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'done ID' partition */
	public void testParserDone() {
		String input = "done 13";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.DONE, task.getOpCode());
        assertEquals("ID", "13", task.getTaskID());
	}
	
	@Test
	/* This is a test case for the 'done empty' partition */
	public void testParserDoneEmpty() {
		String MESSAGE_INVALID_TASKID = "'Done' must have a valid ID";
		String input = "done";
		Parser parser = new Parser(input);      
        try {
			parser.parseInput();
		} catch (IllegalArgumentException e) {
	        assertEquals("OPCODE", MESSAGE_INVALID_TASKID, e.getMessage());
		}
	}
	
	@Test
	/* This is a test case for the 'undo' partition */
	public void testParserUndo() {
		String input = "undo";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.UNDO, task.getOpCode());
	}
	
	@Test
	/* This is a test case for the 'redo' partition */
	public void testParserRedo() {
		String input = "redo";
		Parser parser = new Parser(input);
		Task task = parser.parseInput();
        assertEquals("OPCODE", OPCODE.REDO, task.getOpCode());
	}
	
	private String getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String twoDigitMonth = "";
		String twoDigitDayOfMonth = ""; 
        if (month < 10) {
        	twoDigitMonth = "0" + month;
 		} else {
 			twoDigitMonth = "" + month;
 		}
 		if (dayOfMonth < 10) {
 			twoDigitDayOfMonth = "0" + dayOfMonth;
 		} else {
 			twoDigitDayOfMonth = "" + dayOfMonth;
 		}
         
		return year + twoDigitMonth + twoDigitDayOfMonth;
	}
	
	
}