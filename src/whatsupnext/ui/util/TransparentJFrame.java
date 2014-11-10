//@author A0126730M-unused

/*
 * This class was only used in the unused GUIMultipleWindows
 */
package whatsupnext.ui.util;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;

public class TransparentJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private LineBorder border = new LineBorder(new Color(204, 224, 250, 150), 2);

	public TransparentJFrame() {
		super();
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		this.getRootPane().setBorder(border);
	}
}