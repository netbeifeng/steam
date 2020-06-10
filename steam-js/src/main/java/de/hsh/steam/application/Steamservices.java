package de.hsh.steam.application;

import java.util.ArrayList;
import java.util.regex.*;
import de.hsh.steam.persistence.SerializedSeriesRepository;

public class Steamservices {
	SerializedSeriesRepository repository = SerializedSeriesRepository.singleton();

	private static Steamservices exemplar = null;
	public static Steamservices singleton() {
		if (exemplar == null) {
			exemplar = new Steamservices();
		}
		return exemplar;
	}

	// gibt false zurück, falls es username schon gibt
	public Boolean newUser(String username, String pwd){
		this.readAllData();
		if (repository.getUserObject(username) == null) {
			User u = new User(username, pwd);
			repository.registerUser(u);
			this.writeAllData();
			return true;
		}
		else
			return false;
	}

	public Boolean validRegisterData(String username,String pwd){
		String patternUsername = "[a-zA-Z0-9_-]{6,12}";
		Pattern u = Pattern.compile(patternUsername);
		String patternPwd = "[a-zA-Z0-9_-]{6,12}";
		Pattern p = Pattern.compile(patternPwd);

		Matcher mu = u.matcher(username);
		Matcher mp = p.matcher(pwd);
		return (mu.matches() && mp.matches());
	}

	public Boolean login(String username, String pwd){
		this.readAllData();
		User u = repository.getUserObject(username);
		if (u!= null)
			return u.getPassword().equals(pwd);
		return false;
	}

	public void addOrModifySeries(String title, int numberOfSeasons, Genre genre, Streamingprovider streamedBy, String username, Score score, String remark) {
		this.readAllData();
		Series s = new Series(title, numberOfSeasons, genre, streamedBy);
		Series series = repository.addOrModifySeries(s);
		User u = repository.getUserObject(username);
		if (u != null) {
			series.putOnWatchListOfUser(u);
			u.rate(series, score, remark);
		}
		this.writeAllData();
	}

	public Rating getRating(String seriesname, String username) {
		User u = repository.getUserObject(username);
		Series s = repository.getSeriesObjectFromName(seriesname);
		return u.ratingOf(s);
	}

	public ArrayList<Series> getAllSeries() {
		return repository.getAllSeries();
	}

	public ArrayList<Series> getAllSeriesWithTitle(String title) {
		return repository.getAllSerieWithTitle(title);
	}

	public ArrayList<Series> getAllSeriesOfUser(String username) {
		return repository.getAllSeriesOfUser(username);
	}

	// UND-Verknüpfung aller Suchkriterien
	// ein Suchkriterium, dass nicht berücksichtigt werden soll muss auf null gesetzt werden
	// wichtig: der Username muss gesetzt sein, wenn man nach scores sucht
	public ArrayList<Series> search(String username, Genre genre, Streamingprovider streamingService, Score score) {
		return repository.searchSeries(username, genre, streamingService, score);
	}

	private void writeAllData() {
		repository.saveData();
	}

	private void readAllData() {
		repository.readData();
	}

}
