package edu.baylor.ecs.FitLifeApp;

public class Workout extends LogItem{
	private Integer duration = null;
	private String name = null;
	private String type = null;
	private Double userWeight = null;
	private Double workoutWeights = null;
	private Integer id = null; 
	
	public Workout() {
		
	}
	
	public Workout(Integer logid, Integer duration, String name, String type, Double userWeight, Double workoutWeights) {
		setId(logid);
		setDuration(duration);
		setName(name);
		setType(type);
		setUserWeight(userWeight);
		setWorkoutWeights(workoutWeights);
	}
	
	public Workout(Integer duration, String name, String type, Double userWeight, Double workoutWeights) {
		setDuration(duration);
		setName(name);
		setType(type);
		setUserWeight(userWeight);
		setWorkoutWeights(workoutWeights);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(Double userWeight) {
		this.userWeight = userWeight;
	}

	public Double getWorkoutWeights() {
		return workoutWeights;
	}

	public void setWorkoutWeights(Double workoutWeights) {
		this.workoutWeights = workoutWeights;
	}
}
