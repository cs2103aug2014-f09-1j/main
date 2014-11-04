package whatsupnext.ui;

import java.awt.Color;
import javax.swing.JFrame;

public class TranslucentJFrame extends JFrame {
	public TranslucentJFrame() {
		super("GradientTranslucentWindow");

		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
	}
}