package CSV;

public class User {
	
	private String username;
	private int bestScore;
	private int nbGamesPlayed;
	private int avgScore;
	private int playTime;
	
	public User(){
		
	};
	
	public User(String username, int bestScore, int nbGamesPlayed, int avgScore, int playtime){
		this.username = username;
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
