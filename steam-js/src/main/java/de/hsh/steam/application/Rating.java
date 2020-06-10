package de.hsh.steam.application;

import java.io.Serializable;

public class Rating implements Serializable{

	private static final long serialVersionUID = -7806234457596021877L;
	
	private Score score;
	private String remark;

	private User ratingUser;
	private Series ratedSeries;
	
	public Rating(Score score, String remark, User ratingUser, Series ratedSeries) {
		super();
		this.score = score;
		this.remark = remark;
		this.ratingUser = ratingUser;
		this.ratedSeries = ratedSeries;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getRatingUser() {
		return ratingUser;
	}

	public void setRatingUser(User ofUser) {
		this.ratingUser = ofUser;
	}

	public Series getRatedSeries() {
		return ratedSeries;
	}

	public void setRatedSeries(Series ofSeries) {
		this.ratedSeries = ofSeries;
	}

}
