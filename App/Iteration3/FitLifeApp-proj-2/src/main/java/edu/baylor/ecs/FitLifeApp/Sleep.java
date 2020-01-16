package edu.baylor.ecs.FitLifeApp;

import java.sql.Time;

public class Sleep extends LogItem{
	private Integer id;
	private Double duration;
	private Integer rating;
	private Time startTime;

	public Sleep() {

	}

	public Sleep(Double duration, Integer rating, Time startTime) {
		setDuration(duration);
		setRating(rating);
		setStartTime(startTime);
	}

	public Sleep(Integer logid, Double duration, Integer rating, Time startTime) {
		setId(logid);
		setDuration(duration);
		setRating(rating);
		setStartTime(startTime);
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public Double getDuration() {
		return duration;
	}

	private void setDuration(Double duration) {
		this.duration = duration;
	}

	public Integer getRating() {
		return rating;
	}

	private void setRating(Integer rating) {
		this.rating = rating;
	}

	public Time getStartTime() {
		return startTime;
	}

	private void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
}
