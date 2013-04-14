package CSV;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Tests Highscore.java class
 * @author Chi-Wing Sit
 *
 */

public class HighscoreTest {

	/**
	 * Resets the highscores.csv file
	 */
	@Before
	public void resetHighScore(){
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("resources/CSV/highscores.csv"));
			ArrayList highscores = new ArrayList();
			for(int i = 0; i < 10; i++)
				highscores.add(new String[] {"ABC", "100"});
			writer.writeAll(highscores);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test the addscore method
	 */
	@Test
	public void testAddScore() {
		CSVReader reader;
		try {

	        // Chi-Wing should be 1st place
			Highscore.addScore("Chi-Wing", 200);
			reader = new CSVReader(new FileReader("resources/CSV/highscores.csv"));
	        String[] nextLine = reader.readNext();
			assertEquals("Chi-Wing", nextLine[0]);
	        assertEquals("200", nextLine[1]);
	        
	        nextLine = reader.readNext();
			assertEquals("ABC", nextLine[0]);
	        assertEquals("100", nextLine[1]);
	        
	        // Damien should now be 1st place
	        Highscore.addScore("Damien", 1000);
			reader = new CSVReader(new FileReader("resources/CSV/highscores.csv"));
			
			nextLine = reader.readNext();
			assertEquals("Damien", nextLine[0]);
	        assertEquals("1000", nextLine[1]);
			
	        nextLine = reader.readNext();
			assertEquals("Chi-Wing", nextLine[0]);
	        assertEquals("200", nextLine[1]);
	        
	        nextLine = reader.readNext();
			assertEquals("ABC", nextLine[0]);
	        assertEquals("100", nextLine[1]);
	        
	        // Vincent should be 2nd place
	        Highscore.addScore("Vincent", 500);
			reader = new CSVReader(new FileReader("resources/CSV/highscores.csv"));
			
			nextLine = reader.readNext();
			assertEquals("Damien", nextLine[0]);
	        assertEquals("1000", nextLine[1]);
			
	        nextLine = reader.readNext();
			assertEquals("Vincent", nextLine[0]);
	        assertEquals("500", nextLine[1]);
	        
	        nextLine = reader.readNext();
			assertEquals("Chi-Wing", nextLine[0]);
	        assertEquals("200", nextLine[1]);
	        
	        // Should stay the same as previously
	        Highscore.addScore("Vincent", 0);
			reader = new CSVReader(new FileReader("resources/CSV/highscores.csv"));
			
			nextLine = reader.readNext();
			assertEquals("Damien", nextLine[0]);
	        assertEquals("1000", nextLine[1]);
			
	        nextLine = reader.readNext();
			assertEquals("Vincent", nextLine[0]);
	        assertEquals("500", nextLine[1]);
	        
	        nextLine = reader.readNext();
			assertEquals("Chi-Wing", nextLine[0]);
	        assertEquals("200", nextLine[1]);
	        
	        
	        reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test the getHighscores() method
	 */
	@Test
	public void testGetHighscores() {
		try {
			
			Highscore.getHighscores();
			// All highscores should be ABC - 100
			for(String[] highscore : Highscore.highscores){
				assertEquals("ABC", highscore[0]);
				assertEquals("100", highscore[1]);
			}
			
			Highscore.addScore("Chi-Wing", 2000);
			Highscore.getHighscores();
			// Chi-Wing should now be 1st place with 2000 points
			assertEquals("Chi-Wing", Highscore.highscores.get(0)[0]);
			assertEquals("2000", Highscore.highscores.get(0)[1]);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
