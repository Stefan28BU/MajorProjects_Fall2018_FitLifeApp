package edu.baylor.ecs.FitLifeApp;

/*
 * File:			Account.java
 * Description:		Holds account details
 */
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
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
	private int fileID;

	@XmlElementWrapper(name = "workouts")
	@XmlElement(name = "workout")
	private List<String> workouts;

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

	Account(int fID) {
		this();
		this.fileID = fID;
	}
	/*
	 * Account(String uName, String pWord, String email) { this(); this.uName =
	 * uName; this.pWord = pWord; this.email = email; }
	 */

	@Override
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
}
