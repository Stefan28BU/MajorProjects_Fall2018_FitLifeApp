/*
 * Yufan Xu
 * labSwing
 */


package edu.baylor.ecs.FitLifeApp;

public class Main {
	public static void main(String[]args)  {

		final WindowManager m1 = new WindowManager();
		//toLogIn is called in the constructor
		//Not sure if that is a good style, probably not
		/*
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				m1.toLogIn();
			}
		});*/
	}
}
