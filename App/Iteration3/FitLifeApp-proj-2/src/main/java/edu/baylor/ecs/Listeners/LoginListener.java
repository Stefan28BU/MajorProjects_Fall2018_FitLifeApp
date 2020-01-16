package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.LogInController;

//Listener used for buttons in LogIn window
//maybe this can be removed to another class
//can this just bubble up?
public class LoginListener implements ActionListener  {
	LogInController login = LogInController.getInstance();

	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(LoginListener.class.getName());
		logger.setLevel(Level.ALL);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Sign In")) {

			logger.info("Signing In");

			if (login.validate()) {
				login.setAcct(login.getAcct());
				login.destroyWindow();
				login.toHome();
			} else {
				JFrame loginError = new JFrame();

				JOptionPane.showMessageDialog(loginError, "Incorrect Username/Password", "Failed Login",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand().equals("Create Account")) {
			logger.info("Creating Account");

			login.destroyWindow();
			login.toAcctCreation();
		} else if (e.getActionCommand().equals("Forgot Password")) {
			logger.info("Forgot Password");

			login.destroyWindow();
			login.toForgetPassword();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
