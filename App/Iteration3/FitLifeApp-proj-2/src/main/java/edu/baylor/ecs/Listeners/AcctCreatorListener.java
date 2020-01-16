package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.AcctCreator;

public class AcctCreatorListener implements ActionListener  {

	AcctCreator acct = AcctCreator.getInstance();

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Create Account")) {
			if (acct.createAcct()) {
				acct.destroy();
				acct.toLogIn();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Account Creation Failed", "Failed Creation",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand().equals("Cancel")) {
			acct.destroy();
			acct.toLogIn();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}