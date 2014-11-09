//@author A0126730M
package whatsupnext.structure.enums;

public class Types {
	
	public enum ADDTYPE {
		FLOATING, DEADLINE, TIMEFRAME
	}
	
	public enum DELETETYPE {
		ALL, ID, DATE, DEADLINE, TIMEFRAME, DONE
	}
	
	public enum UPDATETYPE {
		DESCRIPTION, DEADLINE, TIMEFRAME 
	}
	
	public enum VIEWTYPE {
		ALL, NEXT, DATE, TIMEFRAME, UNDONE, FLOATING, OVERDUE
	}
	
	public enum FREETYPE {
		DATE, TIMEFRAME,
	}
}
