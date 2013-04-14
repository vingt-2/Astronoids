package CSV;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Tests LoginMenu.java
 * @author Chi-Wing Sit
 *
 */
public class LoginMenuTest {

	/**
	 * Clean up users.csv before test 
	 */
	@Before
	public void testSetup(){
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("resources/CSV/users.csv"));
			writer.writeAll(new ArrayList());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test the login() method
	 */
	@Test
	public void testLogin() {
		try {
			// Add some users to test
			LoginMenu.addUser("Chi-Wing");
			LoginMenu.addUser("Rami123");
			LoginMenu.addUser("Haibo Zeng");
			
			// Name with hyphen login
			assertTrue("Chi-Wing login", LoginMenu.login("Chi-Wing"));
			// String with number login
			assertTrue("Rami123 login", LoginMenu.login("Rami123"));
			// Case sensitivity test
			assertTrue("RAmI123 not login", !LoginMenu.login("RAmI123"));
			// Space test
			assertTrue("Haibo Zend login", LoginMenu.login("Haibo Zeng"));
			// Not a registered user test
			assertTrue("Damien not login", !LoginMenu.login("Damien"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test the addUser() method
	 */
	@Test
	public void testAddUser() {

		CSVReader reader;
		try {
			LoginMenu.addUser("Chi-Wing");
			LoginMenu.addUser("Rami123");
			LoginMenu.addUser("Haibo Zeng");
			
			reader = new CSVReader(new FileReader("resources/CSV/users.csv"));
					
			// Test 1st user
	        String[] nextLine = reader.readNext();
			assertEquals("Chi-Wing", nextLine[0]);
	        assertEquals("0", nextLine[1]);
	        assertEquals("0", nextLine[2]);
	        assertEquals("0", nextLine[3]);
	        assertEquals("0", nextLine[4]);
	        
	        // Test 2nd user
	        nextLine = reader.readNext();
			assertEquals("Rami123", nextLine[0]);
	        assertEquals("0", nextLine[1]);
	        assertEquals("0", nextLine[2]);
	        assertEquals("0", nextLine[3]);
	        assertEquals("0", nextLine[4]);
	        
	        // Test 3rd user
	        nextLine = reader.readNext();
			assertEquals("Haibo Zeng", nextLine[0]);
	        assertEquals("0", nextLine[1]);
	        assertEquals("0", nextLine[2]);
	        assertEquals("0", nextLine[3]);
	        assertEquals("0", nextLine[4]);
	        
	        reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
