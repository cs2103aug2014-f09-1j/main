//@author A0126730M
package whatsupnext.ui;

import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice.WindowTranslucency;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class WhatsUpNextMain {
	
	static boolean isPerPixelTranslucencySupported;
	
	
	/**
	 * Launch the application.
	 * Sets window visibility and size.
	 */
	public static void main(String[] args) {
		// Determine what the GraphicsDevice can support.
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		isPerPixelTranslucencySupported = 
				gd.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT);

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			JFrame.setDefaultLookAndFeelDecorated(true);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAbstract gui = new GUIOneWindow();
					gui.showWindows();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
