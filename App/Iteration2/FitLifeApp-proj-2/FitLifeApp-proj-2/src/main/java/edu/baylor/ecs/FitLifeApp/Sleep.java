package edu.baylor.ecs.FitLifeApp;

public class Sleep extends LogItem{
	private int id;
	private int duration;
	private int rating;
	private int startTime;
	
	public Sleep(int duration, int rating, int startTime) {
		setId(LogItem.count++);
		setDuration(duration);
		setRating(rating);
		setStartTime(startTime);
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

	public int getRating() {
		return rating;
	}

	private void setRating(int rating) {
		this.rating = rating;
	}

	public int getStartTime() {
		return startTime;
	}

	private void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	
}
