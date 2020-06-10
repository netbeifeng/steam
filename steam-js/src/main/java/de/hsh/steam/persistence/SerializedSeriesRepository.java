package de.hsh.steam.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import de.hsh.steam.application.Series;
import de.hsh.steam.application.SeriesRepository;
import de.hsh.steam.application.User;

public class SerializedSeriesRepository extends SeriesRepository {


	private static final long serialVersionUID = -2939393922792471937L;
	
	private static SerializedSeriesRepository exemplar = null;
	
	public static SerializedSeriesRepository singleton() {
		if (exemplar == null) {
			exemplar = new SerializedSeriesRepository();
		}
		return exemplar;
	}
	public void saveData() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
//			System.out.println("[serialized before] " + allUsers.size() + " users -- " + allSeries.size() + " series");
			fos = new FileOutputStream("allSeriesData.ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this.allSeries);
			oos.writeObject(this.allUsers);
			//System.out.println("[serialized after] " + this.allUsers.size() + " users -- " + this.allSeries.size() + " series");
	
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void readData() {
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("allSeriesData.ser");
			ois = new ObjectInputStream(fis);
			this.allSeries = (ArrayList<Series>) ois.readObject();
			this.allUsers = (ArrayList<User>) ois.readObject();		
			//System.out.println("[deserialized] " + this.allUsers.size() + " users -- " + this.allSeries.size() + " series");
	
		} catch (IOException ex) {
			System.out.println("File does not exist!! ");
		} catch (ClassNotFoundException e) {
			System.out.println("File does not exist");
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (ois != null)
					ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
