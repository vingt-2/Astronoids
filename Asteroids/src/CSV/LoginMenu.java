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


public class LoginMenu {

	private static final String USERS_FILE="resources/CSV/users.csv";
	public static boolean login = false;

	public static boolean available = false;
	public static User player;
	

	public static boolean login(String username) throws IOException{

		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List usersList = csv.parse(strat, reader);
		reader = new CSVReader(new FileReader(USERS_FILE));
		List userWrite = reader.readAll();

		for(Object object : usersList){
			User user = (User) object;
			if(username.equals(user.getUsername())){
				login = true;
				System.out.println("true");
				player = user;
				MainGame.currentUser = user;
				break;
			}
			else System.out.println("false");
		}

		reader.close();
		return login;

	}

	public static void addUser(String username) throws IOException{		
		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List usersList = csv.parse(strat, reader);
		reader = new CSVReader(new FileReader(USERS_FILE));
		List writeUser = reader.readAll();

		available = true;

		for(Object object : usersList){
			User user = (User) object;
			if(username.equals(user.getUsername())){
				System.out.println("Username is already taken");
				available = false;
				break;
			}
		}
		reader.close();

		if(available){
			CSVWriter writer = new CSVWriter(new FileWriter(USERS_FILE));
			writeUser.add(new String[] {username, "0", "0", "0", "0"});
			writer.writeAll(writeUser);
			System.out.println("User has been added");
			writer.close();
		}

	}
}

