//@author A0126730M-reused
package whatsupnext.junit;

import java.awt.*;

public class GUITestUtils {
		
	public static Component getChildNamed(Component parent, String name) {
		if (name.equals(parent.getName())) {
			return parent;
		} else if (parent instanceof Container) {
			Component[] children = ((Container) parent).getComponents();
			
			for (int i = 0; i < children.length; i++) {
				Component child = getChildNamed(children[i], name);
				if (child != null) {
					return child;
				}
			}
		}
		
		return null;
	}
}
