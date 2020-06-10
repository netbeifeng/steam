package de.hsh.steam.main;

import java.util.ArrayList;
import de.hsh.steam.application.Genre;
import de.hsh.steam.application.Steamservices;
import de.hsh.steam.application.Score;
import de.hsh.steam.application.Series;
import de.hsh.steam.application.Streamingprovider;
import de.hsh.steam.persistence.SerializedSeriesRepository;

public class SteamTest {
	
	public static void initData() {
		Steamservices facade = Steamservices.singleton();
		
		// init users
		facade.newUser("daisy", "123");
		facade.newUser("donald", "abc");
		facade.newUser("goofy", "???");
		
		// init series
		String username = "daisy";
		facade.addOrModifySeries("Dark", 3, Genre.Drama, Streamingprovider.Netflix, username, Score.good, "strange");	
		facade.addOrModifySeries("Breaking Bad", 7, Genre.Drama, Streamingprovider.Netflix, username, Score.very_good, "interesting");	
		facade.addOrModifySeries("Game Of Thrones", 50, Genre.ScienceFiction, Streamingprovider.Skye, username, Score.mediocre, "no  comment");		
		facade.addOrModifySeries("Narcos", 2, Genre.Thriller, Streamingprovider.Skye, username, Score.mediocre, "no  comment");		
		
//		username = "donald";
		facade.addOrModifySeries("Dark", 3, Genre.ScienceFiction, Streamingprovider.Netflix, username, Score.mediocre, "strange");	
		facade.addOrModifySeries("Stranger Things", 4, Genre.ScienceFiction, Streamingprovider.AmazonPrime, username, Score.very_good, "interesting");	
		
	}
	
	public static void userstory() {
		Steamservices facade = Steamservices.singleton();
		SerializedSeriesRepository repository = SerializedSeriesRepository.singleton();
		
		String username = "daisy";
		facade.login(username, "123");
		facade.addOrModifySeries("Casa del Papel", 4, Genre.Drama, Streamingprovider.Netflix, username, Score.good, "no  comment");		
		//ändere bereits vorhandene Serie
                facade.addOrModifySeries("Narcos", 3, Genre.Comedy, Streamingprovider.AmazonPrime, username, Score.good, "strange");	

		
		System.out.println("*********** print all series for user "  + username +":");
		repository.printAllSeries(username);
		
		ArrayList<Series>searchResult;
		System.out.println("[SEARCH RESULT]");
		searchResult = facade.search(username, Genre.Drama, Streamingprovider.Netflix, Score.good);
		//searchResult = facade.search(username, null, Streamingprovider.AmazonPrime, null);
		for (Series s: searchResult)
			System.out.println(s + "  score:" + facade.getRating(s.getTitle(), username).getScore());
		
		//-------------------------
//		System.out.println();
//		username = "donald";
//		facade.addOrModifySeries("Casa del Papel", 4, Genre.Drama, Streamingprovider.Netflix, username, Score.mediocre, "no  comment");		
//		facade.addOrModifySeries("Fleabag", 4, Genre.Drama, Streamingprovider.AmazonPrime, username, Score.good, "no  comment");	
//		System.out.println("*********** print all series for user "  + username +":");
//		repository.printAllSeries(username);
//		
//		System.out.println("[SEARCH RESULT]");
//		searchResult = facade.search("donald", Genre.Drama, Streamingprovider.Netflix, Score.mediocre);
//		for (Series s: searchResult)
//			System.out.println(s + "  score:" + facade.getRating(s.getTitle(), username).getScore());	
//		System.out.println("[TITLE SEARCH RESULT]");
//		searchResult = facade.getAllSeriesWithTitle("ing");
//		//searchResult = facade.search(username, null, Streamingprovider.AmazonPrime, null);
//		for (Series s: searchResult)
//			System.out.println(s);

	}
	public static void main(String[] args) {
		// zunächst: lösche ggf die Datei allSeriesData.ser
		Boolean create = true;   // setze auf true um einen Anfangsbestand von Benutzern und Serien zu erzeugen
		if (create) 
			initData();
		userstory();	
		
		SerializedSeriesRepository repository = SerializedSeriesRepository.singleton();
		repository.dumpRepository();
	}
}
