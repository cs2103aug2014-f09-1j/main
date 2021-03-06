//@author A0111773L
package whatsupnext.parser.extractor;

import whatsupnext.structure.util.Task;

public interface Extractor {
	
	/**
	 * Returns a string feedback on the status of extraction
	 * @param task A Task object to put in the extracted information
	 * @param input The string to perform extraction
	 * @return 
	 */
	void extract(Task task, String input);
	
}
