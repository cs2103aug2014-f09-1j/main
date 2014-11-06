//@author A0092165E
package whatsupnext.parser.extractor;


/**
 * This class contains repeatedly used functions in Extractors
 *
 */
public final class Utility {

	private Utility(){
		
	}

	/**
	 * Count the total number of words in a string
	 * @param userCommand
	*/
	public static int countWords (String input) {
		String trim = input.trim();
		if (trim.isEmpty()) {
			return 0;
		}
		// Separate string around 1 or more spaces
		return trim.split("\\s+").length;
	}
	
	/**
	 * Removes the first word of a string
	 * @param userCommand
	 * @return
	 */
	public static String removeFirstWord(String userCommand) {
		String commandString;
		try {
			commandString = userCommand.trim().split("\\s+", 2)[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			commandString = "";
		}
		return commandString;
	}
	
	
	
	/**
	 * Returns the first word of an input string
	 * @param userCommand
	 * @return
	 */
    public static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
    
    
	/**
	 * This method is to recover task description containing keyword
	 * @param details
	 * @return
	 */
	public static String recoverDetails(String keyword, String[] details) {
		// TODO Auto-generated method stub
		int detailsSize = details.length;
		String recoveredDetail = "";
		for (int i=0; i<detailsSize-1; i++) {
			recoveredDetail += " " + keyword + " " + details[i]; 
		}
		return removeFirstWord(recoveredDetail);	
	}
	
    
}

