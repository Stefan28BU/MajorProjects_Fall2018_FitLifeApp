package edu.baylor.ecs.FitLifeApp;

import java.util.ArrayList;
import java.util.List;

public class Meal extends LogItem {
	private int id;
	private int calories;
	private int carbs;
	private String entre;
	private int fat;
	private int hydration;
	private String name;
	private int protein;
	private List<String> sides;
	
	

	public Meal(int carbs, String entre, int fat, int hydration, String name, int protein, String ...sides) {
		setId(LogItem.count++);
		setCarbs(carbs);
		setEntre(entre);
		setFat(fat);
		setHydration(hydration);
		setName(name);
		setProtein(protein);
		this.sides = new ArrayList<String>();
		
		List<String> s = new ArrayList<String>();
		for(String x : sides) {
			s.add(x);
		}
		
		setSides(s);
		
		/*calculate calories*/
		setCalories((fat) * 9 + (carbs * 4) + (protein * 4));
		
	}
	
	
	public int getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getCalories() {
		return calories;
	}


	private void setCalories(int calories) {
		this.calories = calories;
	}


	public int getCarbs() {
		return carbs;
	}


	private void setCarbs(int carbs) {
		this.carbs = carbs;
	}


	public String getEntre() {
		return entre;
	}


	private void setEntre(String entre) {
		this.entre = entre;
	}


	public int getFat() {
		return fat;
	}


	private void setFat(int fat) {
		this.fat = fat;
	}


	public int getHydration() {
		return hydration;
	}


	private void setHydration(int hydration) {
		this.hydration = hydration;
	}


	public String getName() {
		return name;
	}


	private void setName(String name) {
		this.name = name;
	}


	public int getProtein() {
		return protein;
	}


	private void setProtein(int protein) {
		this.protein = protein;
	}


	public List<String> getSides() {
		return sides;
	}


	private void setSides(List<String> sides) {
		this.sides = sides;
	}

	
	
}
