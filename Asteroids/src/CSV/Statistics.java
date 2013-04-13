package CSV;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * Manages the User statistics
 * @author Chi-Wing Sit
 *
 */
public class Statistics {
	
	private static final String USERS_FILE="resources/CSV/users.csv";

	/**
	 * Update all the player statistics
	 * @param player Current player
	 * @param score Game score
	 * @param gameTime Game time
	 * @throws IOException
	 */
	public static void updateStats(User player, int score, int gameTime) throws IOException{
		
		// Read the users.csv file
		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List<User> usersList = csv.parse(strat, reader);
		reader = new CSVReader(new FileReader(USERS_FILE));
		List<String[]> writeStats = reader.readAll();
		
		// Updates the player stats
		if(player.getBestScore() < score)
			player.setBestScore(score);
		player.setPlayTime(player.getPlayTime() + gameTime);	
		player.setAvgScore((player.getAvgScore()*player.getNbGamesPlayed()+score)/(player.getNbGamesPlayed()+1));
		player.setNbGamesPlayed(player.getNbGamesPlayed()+1);
		int index = 0;
		
		// Finds the player
		for(Object object : usersList){
			User user = (User) object;
			if(user.getUsername().equals(player.getUsername())){
				index = usersList.indexOf(object);				
				break;
			}
		}

		// Writes the updated statistics
		writeStats.set(index, new String[] {player.getUsername(), ""+player.getBestScore(), ""+player.getNbGamesPlayed(),
				""+player.getAvgScore(),"" + player.getPlayTime()});
		
		// Closes reader
		reader.close();
		
		// Modifies the csv file
		CSVWriter writer = new CSVWriter(new FileWriter(USERS_FILE));
		writer.writeAll(writeStats);
		
		// Closes the reader
		writer.close();
		
	}
}
