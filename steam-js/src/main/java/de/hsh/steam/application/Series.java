package de.hsh.steam.application;

import java.io.Serializable;
import java.util.ArrayList;

public class Series implements Serializable{
	
	private static final long serialVersionUID = 1130974711328366348L;
	
	private String title;
	private int numberOfSeasons;
	private Genre genre;
	private Streamingprovider streamedBy;
	private ArrayList<User> seenBy = new ArrayList<User>();
	
	public Series(String title, int numberOfSeasons, Genre genre, Streamingprovider streamedBy) {
		this.title = title;
		this.numberOfSeasons = numberOfSeasons;
		this.genre = genre;
		this.streamedBy = streamedBy;
	}

	public void putOnWatchListOfUser(User u) {
		if (!seenBy.contains(u)) {
			this.seenBy.add(u);				
		}
	}
	
	public Boolean isSeenBy(String username) {
		for (User u: seenBy) {
			if (u.getUsername().equals(username) )
				return true;
		}
		return false;
	}
	
	public ArrayList<User> getSeenBy() {
		return seenBy;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Genre getGenre() {
		return genre;
	}


	public void setGenre(Genre myGenre) {
		this.genre = myGenre;
	}


	public int getNumberOfSeasons() {
		return numberOfSeasons;
	}

	public void setNumberOfSeasons(int numberOfSeasons) {
		this.numberOfSeasons = numberOfSeasons;
	}
	
	public Streamingprovider getStreamedBy() {
		return streamedBy;
	}


	public void setStreamedBy(Streamingprovider streamedBy) {
		this.streamedBy = streamedBy;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		Series s = (Series) o;
		return this.title == s.title;
	}
	
	public String toString() {
		return this.title + " -  genre:" + this.genre  + "   - watched on :"  + this.streamedBy;
	}
	
	

}
