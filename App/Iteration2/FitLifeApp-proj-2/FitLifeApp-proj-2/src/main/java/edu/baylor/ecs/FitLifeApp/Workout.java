package edu.baylor.ecs.FitLifeApp;

public class Workout extends LogItem {
	private int duration;
	private String name;
	private String type;
	private int userWeight;
	private int workoutWeights;
	private int id;
	
	
	
	public Workout(int duration, String name, String type, int userWeight, int workoutWeights) {
		setId(LogItem.count++);
		setDuration(duration);
		setName(name);
		setType(type);
		setUserWeight(userWeight);
		setWorkoutWeights(workoutWeights);
	}
	
	
	public int getId() {
		return id;
	}


	private void setId(int id) {
		this.id = id;
	}


	public int getDuration() {
		return duration;
	}



	private void setDuration(int duration) {
		this.duration = duration;
	}



	public String getName() {
		return name;
	}



	private void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return type;
	}



	private void setType(String type) {
		this.type = type;
	}



	public int getUserWeight() {
		return userWeight;
	}



	private void setUserWeight(int userWeight) {
		this.userWeight = userWeight;
	}



	public int getWorkoutWeights() {
		return workoutWeights;
	}



	private void setWorkoutWeights(int workoutWeights) {
		this.workoutWeights = workoutWeights;
	}
	
	
	
	
}
