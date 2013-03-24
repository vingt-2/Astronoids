package CSV;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;


public class Statistics {
	
	private static final String USERS_FILE="resources/CSV/users.csv";

	public void updateStats(User player, int score, int gameTime) throws IOException{
		
		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "password", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		CSVReader reader = new CSVReader(new FileReader("USERS_FILE"));
		List usersList = csv.parse(strat, reader);
		
		int index = usersList.indexOf(player);
		
		if(player.getBestScore() < score)
			player.setBestScore(score);
		player.setPlayTime(player.getPlayTime() + gameTime);	
		player.setAvgScore((player.getAvgScore()*player.getNbGamesPlayed()+score)/(player.getNbGamesPlayed()+1));
		player.setNbGamesPlayed(player.getNbGamesPlayed()+1);
		reader.close();
		
		usersList.set(index, player);
		
		CSVWriter writer = new CSVWriter(new FileWriter(USERS_FILE));
		writer.writeAll(usersList);
		writer.close();
		
	}
}
