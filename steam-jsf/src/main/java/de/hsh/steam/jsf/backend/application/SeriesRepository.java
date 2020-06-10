package de.hsh.steam.jsf.backend.application;

import java.io.Serializable;
import java.util.ArrayList;

// das SeriesDirectory ist eine Abstraktion der Datenbank
// es hält alle Daten vor und bietet Funktionen zum Anlegen, Ändern und Suchen von Objekten 
public abstract class SeriesRepository implements Serializable {

	private static final long serialVersionUID = -4283784255267828850L;

	// Liste aller Serien und User
	protected ArrayList<Series> allSeries = new ArrayList<Series>() ;
	protected ArrayList<User> allUsers = new ArrayList<User>();

	// werden in Unterklasse Serializer implementiert
	abstract protected void saveData();
	abstract protected void readData();
	
	public void registerUser(User u) {
		this.allUsers.add(u);
		//System.out.println("[registerUser] "  + u.getUsername() + " muber of registered users "  + this.allUsers.size() );
	}



	public User getUserObject(String username) {
		for (User u : allUsers){
			if (u.getUsername().equals(username) ) {
				return u;
			}
		}
		System.out.println("----- user not found: " + username +    "-----");
		return null;
	}
	
	// gibt das Series object (das aus dem Directory (falls vorhanden oder das neue Objekt 
	public Series addOrModifySeries(Series s) {
		for (Series old : allSeries) {
			if ( old.getTitle().equals(s.getTitle()) ) {
				// Serie gibt es schon im Directory		
				old.setGenre(s.getGenre());
				old.setNumberOfSeasons(s.getNumberOfSeasons());
				old.setStreamedBy(s.getStreamedBy());
				return old;
			}
		}
		allSeries.add(s);
		return s;
	}

	public ArrayList<Series> getAllSeries() {
		return allSeries;
	}

	public ArrayList<Series> getAllSeriesOfUser(String username) {
		ArrayList<Series> allSeriesOfUser = new ArrayList<Series>();
		for (Series s : allSeries) {
			if (s.isSeenBy(username))
				allSeriesOfUser.add(s);
		}
		return allSeriesOfUser;
	}
	
	public ArrayList<Series> getAllSerieWithTitle(String title) {
		ArrayList<Series> allSeriesWithTitle = new ArrayList<Series>();
		for (Series s : allSeries) {
			if (s.getTitle().contains(title))
				allSeriesWithTitle.add(s);
		}
		return allSeriesWithTitle;
	}

	public ArrayList<Series> searchSeries(String username, Genre genre, Streamingprovider streamedBy, Score score) {
		ArrayList<Series> searchResult = new ArrayList<Series>();
		if (username !="")
			for (Series s : allSeries) {
				if (isSearchCriteriaFulfilled(s, username, genre, streamedBy, score)) {
					searchResult.add(s);
				}
		}
		return searchResult;
	}
	private Boolean isSearchCriteriaFulfilled(Series s, String username, Genre genre, Streamingprovider streamedBy, Score score) {
		if (username != null && !s.isSeenBy(username) )
			return false;
		if (genre != null && s.getGenre() != genre)
			return false;
		if (streamedBy != null && s.getStreamedBy() != streamedBy)
			return false;
		if (score != null) {
			User u = this.getUserObject(username);
			if (u!= null ) {
				Rating r = u.ratingOf(s);
				if (r != null && r.getScore() != score) {
					return false;
				}
			}
		}	
		return true;
	}
	
	public Series getSeriesObjectFromName(String seriesname) {
		for (Series s : allSeries){
			if (s.getTitle().equals(seriesname) ) {
				return s;
			}
		}
		return null;
	}


	
	
	
	// ------------ zum Testen: -------------------
	public void dumpRepository() {
		System.out.println();
		System.out.println("######### data dump ############");
		for (User u: this.allUsers)
			System.out.println(u);
		for (Series s: this.allSeries) {
			System.out.println(s);
		}
	}
	public void printAllSeries(String username) {
		User u = this.getUserObject(username);
		for (Series s: this.allSeries) {
			if (s.isSeenBy(username))
				System.out.println(s + " score:" + u.ratingOf(s).getScore());
				;
		}
	}
	
}













