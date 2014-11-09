//@author A0092165E
package whatsupnext.parser.extractor;

import whatsupnext.structure.util.Task;

public class SearchExtractor implements Extractor{

	private final String MESSAGE_INVALID_DESCRIPTION = "'Search' must have valid keywords";
	
	public void extract(Task task, String input){
		String keywords = input;

		if (keywords.trim().equals("")){
			throw new IllegalArgumentException(MESSAGE_INVALID_DESCRIPTION); 
		} else {
			task.setSearchKeyword(keywords);
		}
			
	}
	
}
