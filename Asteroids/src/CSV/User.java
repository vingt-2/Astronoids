package CSV;

import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

public class User {
	
	private String username;
	private String password;
	private int bestScore;
	private int nbGamesPlayed;
	private int avgScore;
	private int playTime;
	
	public User(){
		
	};
	
	public User(String username, String password, int bestScore, int nbGamesPlayed, int avgScore, int playtime){
		this.username = username;
		this.password = password;
		this.bestScore = bestScore;
		this.nbGamesPlayed = nbGamesPlayed;
		this.avgScore = avgScore;
		this.playTime = playtime;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public int getBestScore(){
		return bestScore;
	}
	
	public void setBestScore(int bestScore){
		this.bestScore = bestScore;
	}
	
	public int getNbGamesPlayed(){
		return nbGamesPlayed;
	}
	
	public void setNbGamesPlayed(int nbGamesPlayed){
		this.nbGamesPlayed = nbGamesPlayed;
	}
	
	public int getAvgScore(){
		return avgScore;
	}
	
	public void setAvgScore(int avgScore){
		this.avgScore = avgScore;
	}
	
	public int getPlayTime(){
		return playTime;
	}
	
	public void setPlayTime(int playTime){
		this.playTime = playTime;
	}
	
}
