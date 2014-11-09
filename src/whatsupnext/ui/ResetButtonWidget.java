package whatsupnext.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ResetButtonWidget {

	JPanel widgetPanel;
	JButton resetButton;
	GUIAbstract linkedGui;
	
	
	public ResetButtonWidget(GUIAbstract gui) {
		linkedGui = gui;
		initializeResetButtonPanel();
		setComponentNames();
	}
	
	public JPanel getWidgetPanel() {
    	return widgetPanel;
    }
	
	private void setComponentNames() {
		widgetPanel.setName("resetButtonWidgetPanel");
		resetButton.setName("resetButton");
	}

	private void initializeResetButtonPanel() {
		widgetPanel = new JPanel();
		widgetPanel.setBackground(new Color(204, 224, 250));
		
		GridBagLayout gbl_widgetPanel = new GridBagLayout();
		gbl_widgetPanel.columnWeights = new double[]{1.0};
		gbl_widgetPanel.rowWeights = new double[]{1.0};
		widgetPanel.setLayout(gbl_widgetPanel);
		
		initializeResetButton();
	}

	private void initializeResetButton() {
		Image img = Toolkit.getDefaultToolkit().getImage(GUIOneWindow.class.getResource("/whatsupnext/ui/resetIcon.png"));
		resetButton = new JButton(new ImageIcon(img));
		resetButton.setToolTipText("Reset to default size");
		resetButton.setBackground(new Color(255, 117, 56));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickReset();
			}
		});
		
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.fill = GridBagConstraints.BOTH;
		gbc_resetButton.anchor = GridBagConstraints.CENTER;
		gbc_resetButton.insets = new Insets(0, 0, 0, 0);
		gbc_resetButton.gridx = 0;
		gbc_resetButton.gridy = 0;
		
		widgetPanel.add(resetButton, gbc_resetButton);
	}
	
	private void clickReset() {
		linkedGui.reset();
	}
	
}
