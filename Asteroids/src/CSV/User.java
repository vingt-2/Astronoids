package CSV;

/**
 * User class
 * @author Chi-Wing Sit
 *
 */
public class User {
	
	private String username;
	private int bestScore;
	private int nbGamesPlayed;
	private int avgScore;
	private int playTime;
	
	public User(){
		
	};
	
	/**
	 * Constructs a new User
	 * @param username 
	 * @param bestScore User's best score
	 * @param nbGamesPlayed User's number of games played
	 * @param avgScore User's average score
	 * @param playtime User's total playtime
	 */
	public User(String username, int bestScore, int nbGamesPlayed, int avgScore, int playtime){
		this.username = username;
		this.bestScore = bestScore;
		this.nbGamesPlayed = nbGamesPlayed;
		this.avgScore = avgScore;
		this.playTime = playtime;
	}
	
	/**
	 * Gets the User username
	 * @return User's username
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * Sets the User username
	 * @param username The username to be set
	 */
	public void setUsername(String username){
		this.username = username;
	}

	/**
	 * Gets the User best score
	 * @return User's best score
	 */
	public int getBestScore(){
		return bestScore;
	}
	
	/**
	 * Sets the User best score
	 * @param bestScore The score to be set
	 */
	public void setBestScore(int bestScore){
		this.bestScore = bestScore;
	}
	
	/**
	 * Gets the number of games played
	 * @return The number of games played
	 */
	public int getNbGamesPlayed(){
		return nbGamesPlayed;
	}
	
	/**
	 * Sets the number of games played
	 * @param nbGamesPlayed Number of games played
	 */
	public void setNbGamesPlayed(int nbGamesPlayed){
		this.nbGamesPlayed = nbGamesPlayed;
	}
	
	/**
	 * Gets the average score
	 * @return The User's average score
	 */
	public int getAvgScore(){
		return avgScore;
	}
	
	/**
	 * Sets the average score
	 * @param avgScore The User's average score
	 */
	public void setAvgScore(int avgScore){
		this.avgScore = avgScore;
	}
	
	/**
	 * Gets the total play time
	 * @return the User's total play time
	 */
	public int getPlayTime(){
		return playTime;
	}
	
	/**
	 * Sets the total play time
	 * @param playTime The User's play time
	 */
	public void setPlayTime(int playTime){
		this.playTime = playTime;
	}
	
}
