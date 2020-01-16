package edu.baylor.ecs.FitLifeApp;

public class Meal extends LogItem{
	private Integer id = null;
	private Integer calories = null;
	private Integer carbs = null;
	private Integer fat = null;
	private Integer hydration = null;
	private String name = null;
	private Integer protein = null;
	
	public Meal() {
		
	}

	public Meal(Integer logid, Integer carbs, Integer fat, Integer hydration, String name, Integer protein) {
		setId(logid);
		setCarbs(carbs);
		setFat(fat);
		setHydration(hydration);
		setName(name);
		setProtein(protein);
		
		/*calculate calories*/
		setCalories((fat) * 9 + (carbs * 4) + (protein * 4));
		
	}
	
	public Meal(Integer carbs, Integer fat, Integer hydration, String name, Integer protein) {
		setCarbs(carbs);
		setFat(fat);
		setHydration(hydration);
		setName(name);
		setProtein(protein);
		
		/*calculate calories*/
		setCalories((fat) * 9 + (carbs * 4) + (protein * 4));
	}
	
	public Integer getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public Integer getCalories() {
		return calories;
	}

	private void setCalories(int calories) {
		this.calories = calories;
	}

	public Integer getCarbs() {
		return carbs;
	}

	private void setCarbs(int carbs) {
		this.carbs = carbs;
	}

	public Integer getFat() {
		return fat;
	}

	private void setFat(int fat) {
		this.fat = fat;
	}

	public Integer getHydration() {
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

	public Integer getProtein() {
		return protein;
	}

	private void setProtein(Integer protein) {
		this.protein = protein;
	}
}
