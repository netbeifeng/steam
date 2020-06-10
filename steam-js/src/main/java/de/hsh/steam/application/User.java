package de.hsh.steam.application;

import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable{

	private static final long serialVersionUID = -3545765962123273389L;
	
	// username ist eindeutig
	// username und pwd können nicht geändert werden
	private String username;
	private String password;
	private ArrayList<Rating> myRatings = new ArrayList<Rating>();
	
	public User(String name, String password) {
		this.username = name;
		this.password = password;
	}
	
	public Boolean login(String username, String password) {
		return this.username == username && this.password == password;
	}
	
	// setzt neues Rating oder modifiziert vorhandenes Rating
	public void rate(Series series, Score score, String remark) {
		for (Rating r: myRatings) {
			if ( r.getRatedSeries().equals(series) ) {
				r.setScore(score);
				r.setRemark(remark);
                                return;
			}
		}
		Rating r = new Rating(score, remark, this, series);
		myRatings.add(r);
	}
	
	
	public Rating ratingOf(Series s) {
		for (Rating r: myRatings) {
			if (r.getRatedSeries() == s)
				return r;
		}
		return null;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String toString() {
		return this.username + " " + this.password;
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		User u = (User) o;
		return this.username == u.username;
	}
}
