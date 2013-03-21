package CSV;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;


public class LoginMenu {
	
	private static final String USERS_FILE="resources/CSV/users.csv";
	
	public static void login(String username, String password) throws IOException{

		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "password", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List usersList = csv.parse(strat, reader);

		for(Object object : usersList){
			User user = (User) object;
			if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
				System.out.println("true");
				break;
			}
			else System.out.println("false");
		}

		reader.close();

	}

	public static void addUser(String username, String password) throws IOException{		
		ColumnPositionMappingStrategy<User> strat = new ColumnPositionMappingStrategy<User>();
		strat.setType(User.class);
		strat.setColumnMapping(new String[] {"username", "password", "bestScore",
				"nbGamesPlayed", "avgScore", "playTime"});
		CsvToBean csv = new CsvToBean();
		CSVReader reader = new CSVReader(new FileReader(USERS_FILE));
		List usersList = csv.parse(strat, reader);

		boolean available = true;

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
			usersList.add(new User(username, password, 0, 0, 0, 0));
			writer.writeAll(usersList);
			System.out.println("User has been added");
			writer.close();
		}

	}
}
