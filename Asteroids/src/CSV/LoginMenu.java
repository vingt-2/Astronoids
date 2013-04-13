package CSV;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Game.MainGame;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * Manage the users.csv file
 * @author Chi-Wing Sit
 *
 */
public class LoginMenu {
	private static final String USERS_FILE="resources/CSV/users.csv";
	public static boolean login = false;

	public static boolean available = false;
	public static User player;
	
	/**
	 * Check if username exists
	 * @param username username entered by user
	 * @return true if username exists false otherwise
	 * @throws IOException
	 */
	public static boolean login(String username) throws IOException{

		// Set User type
		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		// Read CSV file
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List usersList = csv.parse(strat, reader);
		reader = new CSVReader(new FileReader(USERS_FILE));
		List userWrite = reader.readAll();

		// Check every users in users.csv
		for(Object object : usersList){
			User user = (User) object;
			// if user exists
			if(username.equals(user.getUsername())){
				login = true;
				player = user;
				MainGame.currentUser = user;
				break;
			}
		}

		// Close reader
		reader.close();
		return login;

	}

	/**
	 * Creates and adds a new user in the user.csv file
	 * @param username username to be created
	 * @throws IOException
	 */
	public static void addUser(String username) throws IOException{	
		// Set User type
		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		// Read user.csv
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List usersList = csv.parse(strat, reader);
		reader = new CSVReader(new FileReader(USERS_FILE));
		List writeUser = reader.readAll();

		available = true;

		// Check if username exists
		for(Object object : usersList){
			User user = (User) object;
			// if username already exists available = false
			if(username.equals(user.getUsername())){
				available = false;
				break;
			}
		}
		reader.close();

		// if username is available creates new user
		if(available){
			CSVWriter writer = new CSVWriter(new FileWriter(USERS_FILE));
			writeUser.add(new String[] {username, "0", "0", "0", "0"});
			writer.writeAll(writeUser);
			writer.close();
		}

	}
}

