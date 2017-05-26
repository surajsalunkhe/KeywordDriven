package config;

import javax.swing.JOptionPane;

public class AlertWindow {

	public static void infoBox(String infoMessage,String titleBar){
		JOptionPane.showMessageDialog(null, infoMessage, "infoBox : "+titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

}
