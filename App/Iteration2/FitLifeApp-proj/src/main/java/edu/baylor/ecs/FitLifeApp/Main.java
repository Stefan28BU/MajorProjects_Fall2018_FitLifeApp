/*
 * Yufan Xu
 * labSwing
 */


package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class Main {
	public static void main(String[]args)  {

		final WindowManager m1 = new WindowManager();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				m1.toLogIn();
			}
		});
	}
}
