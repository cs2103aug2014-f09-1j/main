//@author A0126730M
package whatsupnext.ui;

import whatsupnext.logic.Logic;

public abstract class GUIAbstract {
	
	protected static Logic logic;
	
	public abstract void reset();
	public abstract void showWindows();
	
	public static Logic getLogic() {
		return logic;
	}
}
