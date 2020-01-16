package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.ForgetPasswordController;

public class ForgetPasswordListener implements ActionListener  {
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(ForgetPasswordListener.class.getName());
		logger.setLevel(Level.ALL);
	}
	
	ForgetPasswordController acct = ForgetPasswordController.getInstance();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Check Account")) {
			logger.info("Checking account");
			if (acct.checkAccount()) {
				
				// send email and add temporary password
				acct.destroy();
				acct.toLogIn();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Account Not Exist", "Failed To Find User",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getActionCommand().equals("Cancel")) {
			logger.info("Cancel Checking");

			acct.destroy();
			acct.toLogIn();

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}