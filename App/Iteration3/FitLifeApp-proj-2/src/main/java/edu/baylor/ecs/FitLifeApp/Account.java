package edu.baylor.ecs.FitLifeApp;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {
	

	/*
	 * c1 -> Base1 c2 -> Base2 c3 -> Trim1 c4 -> Trim2 I don't really know proper
	 * coloring terms Here because one of the requirements is being able to change
	 * colors
	 */
	@XmlJavaTypeAdapter(ColorAdapter.class)
	private Color colorBase1;
	@XmlJavaTypeAdapter(ColorAdapter.class)
	private Color colorBase2;
	@XmlJavaTypeAdapter(ColorAdapter.class)
	private Color colorTrim1;
	@XmlJavaTypeAdapter(ColorAdapter.class)
	private Color colorTrim2;

	/*
	 * //Account class doen't hold username and password so that it isn't marshalled
	 * into XML // private String uName; private String email; private String pWord;
	 */
	private static String uName = null;
	private int fileID;

	@XmlElementWrapper(name = "workouts")
	@XmlElement(name = "workout")
	private List<String> workouts;
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(Account.class.getName());
		logger.setLevel(Level.ALL);
	}
	
	public List<String> getWorkouts() {
		return this.workouts;
	}

	public void setWorkouts(List<String> src) {
		workouts = src;
	}

	public Color getColorBase1() {
		return colorBase1;
	}

	/*
	 * 
	 * public String getuName() { return uName; }
	 * 
	 * 
	 * 
	 * public void setuName(String uName) { this.uName = uName; }
	 * 
	 * 
	 * 
	 * public String getEmail() { return email; }
	 * 
	 * 
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * 
	 * 
	 * public String getpWord() { return pWord; }
	 * 
	 * 
	 * 
	 * public void setpWord(String pWord) { this.pWord = pWord; }
	 * 
	 */

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public void setColorBase1(Color colorBase1) {
		this.colorBase1 = colorBase1;
	}

	public void setColorBase2(Color colorBase2) {
		this.colorBase2 = colorBase2;
	}

	public void setColorTrim1(Color colorTrim1) {
		this.colorTrim1 = colorTrim1;
	}

	public void setColorTrim2(Color colorTrim2) {
		this.colorTrim2 = colorTrim2;
	}

	public Color getColorBase2() {
		return colorBase2;
	}

	public Color getColorTrim1() {
		return colorTrim1;
	}

	public Color getColorTrim2() {
		return colorTrim2;
	}
	
	public static String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		Account.uName = uName;
	}
	

	Account() {
		this.colorBase1 = new Color(72, 201, 176);
		this.colorBase2 = new Color(46, 64, 83);
		this.colorTrim1 = new Color(174, 214, 241);
		this.colorTrim2 = new Color(84, 153, 199);
		this.workouts = new ArrayList<String>();

		/*
		 * this.uName = null; this.pWord = null; this.email = null;
		 */
	}

	public Account(int fID) {
		this();
		this.fileID = fID;
	}
	/*
	 * Account(String uName, String pWord, String email) { this(); this.uName =
	 * uName; this.pWord = pWord; this.email = email; }
	 */

	public String toString() {
		String s = new String();
		s += Integer.toString(fileID) + "\n";
		s += "ColorB1: " + this.colorBase1.toString() + "\n";
		s += "ColorB2: " + this.colorBase2.toString() + "\n";
		s += "ColorT1: " + this.colorTrim1.toString() + "\n";
		s += "ColorT2: " + this.colorTrim2.toString() + "\n";

		s += "Workout Count: " + this.workouts.size() + "\n";
		for (int i = 0; i < this.workouts.size(); i++) {
			s += this.workouts.get(i) + "\n";
		}
		return s;
	}

	public void save() {
		BufferedWriter bw;
		try {
			File acct = new File("ACCT" + Integer.toString(this.fileID));
			// this would fail, but it can't hurt

			bw = new BufferedWriter(new FileWriter(acct));

			String marshed = null;
			StringWriter strWriter = new StringWriter();

			JAXBContext context = JAXBContext.newInstance(Account.class);
			Marshaller m = context.createMarshaller();
			m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(this, strWriter);
			// m.marshal(new Account(accountCount), System.out);
			marshed = AcctCipher.encrypt(strWriter.toString(), "UnGuEsSaBlEkEyke");
			bw.write(marshed);

			bw.close();

		} catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage(), e1);
		} catch (JAXBException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
