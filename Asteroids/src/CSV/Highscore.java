package CSV;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;


public class Highscore {
	private static final String HIGHSCORES_FILE="resources/CSV/highscores.csv";

	private String username;
	private int score;

	public static List<String[]> highscores;

	public Highscore(){

	}

	public Highscore(String username, int score){
		this.username = username;
		this.score = score;
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public int getScore(){
		return score;
	}

	public void setScore(int score){
		this.score =  score;
	}

	public static void addScore(String username, int score) throws IOException{
		ColumnPositionMappingStrategy<Highscore> strat = new ColumnPositionMappingStrategy<Highscore>();
		strat.setColumnMapping(new String[] {"username","score"});
		CsvToBean csv = new CsvToBean();
		strat.setType(Highscore.class);
		strat.setColumnMapping(new String[] {"username", "score"});
		CSVReader reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		List highscoresList = csv.parse(strat, reader);
		reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		List<String[]> writeHighscores = reader.readAll();
		for(Object object : highscoresList){
			Highscore highscore = (Highscore) object;
			if(highscore.getScore() < score){
				int index = highscoresList.indexOf(object);
				writeHighscores.add(index, new String[] {username, ""+score});
				writeHighscores.remove(10);

				CSVWriter writer = new CSVWriter(new FileWriter(HIGHSCORES_FILE));
				writer.writeAll(writeHighscores);
				writer.close();
				System.out.println("Highscores have been changed");

				break;
			}
		}

		reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		highscores = reader.readAll();
		reader.close();
	}

	public static void getHighscores() throws FileNotFoundException, IOException{

		CSVReader reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		highscores = reader.readAll();
		reader.close();

	}

}

