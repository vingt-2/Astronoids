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

/** Read and modifies the highscore.csv file
 * 
 * @author Chi-Wing Sit
 * 
 * */

public class Highscore {
	/** Location of highscore.csv*/
	private static final String HIGHSCORES_FILE="resources/CSV/highscores.csv";

	/** Player username*/
	private String username;
	/** Player score*/
	private int score;

	/** Top ten scores*/
	public static List<String[]> highscores;

	/** Public Highscore constructor*/
	public Highscore(){

	}

	/** Public Highscore constructor
	 * 
	 * @param username Player username
	 * @param score Player score
	 * */
	public Highscore(String username, int score){
		this.username = username;
		this.score = score;
	}

	/**Gets the username
	 * @return HighScore username*
	 * /
	public String getUsername(){
		return username;
	}

	/**
	 * Sets the username
	 * @param username The username to be set
	 *
	 * */
	public void setUsername(String username){
		this.username = username;
	}

	/**
	 * Gets the score
	 * @return HighScore score
	 * */
	public int getScore(){
		return score;
	}

	/**
	 * Sets the score
	 * @param score score to be set
	 */
	public void setScore(int score){
		this.score =  score;
	}

	/**Check if the score is in the highscores
	 * @param username username to be added
	 * @param score score to be checked
	 * */
	public static void addScore(String username, int score) throws IOException{
		// Set type to Highscore
		ColumnPositionMappingStrategy<Highscore> strat = new ColumnPositionMappingStrategy<Highscore>();
		strat.setColumnMapping(new String[] {"username","score"});
		CsvToBean csv = new CsvToBean();
		strat.setType(Highscore.class);
		strat.setColumnMapping(new String[] {"username", "score"});
		
		// Read the csv file
		CSVReader reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		List highscoresList = csv.parse(strat, reader);
		
		// Read the csv file to as a string array
		reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		List<String[]> writeHighscores = reader.readAll();
		
		// Check if the score is higher than of the highscore
		for(Object object : highscoresList){
			Highscore highscore = (Highscore) object;
			// if score is higher than highscore
			if(highscore.getScore() < score){
				int index = highscoresList.indexOf(object);
				// Add score to highscore list
				writeHighscores.add(index, new String[] {username, ""+score});
				// Remove the last score
				writeHighscores.remove(10);

				// Write highscore list in csv file
				CSVWriter writer = new CSVWriter(new FileWriter(HIGHSCORES_FILE));
				writer.writeAll(writeHighscores);
				// Close writer
				writer.close();
				
				break;
			}
		}

		reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		highscores = reader.readAll();
		// Close reader
		reader.close();
	}

	/**
	 * Return all the highscores
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void getHighscores() throws FileNotFoundException, IOException{

		CSVReader reader = new CSVReader(new FileReader(HIGHSCORES_FILE));
		highscores = reader.readAll();
		reader.close();

	}

}

