package GameObjects;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import CSV.LoginMenu;
import Game.MainGame;
import Game.Ressource;
import Game.SharedRessources;
import Game.SharedRessources.RessourceType;
import Renderer.Renderer;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * 
 * Alien Test class
 *
 */
public class AlienTest {
	public static String Window_Name		= "Asteroids";
	public static final Renderer render = new Renderer(Window_Name);
	Alien alien1;
	Alien alien2;

	
	@Before
	public void testSetUp(){
		MainGame game = new MainGame();
		render.mainGame = game;	
		
	}
		
	
	@Test
	public void testAI() {
	
		
		SharedRessources ressources	= new SharedRessources();
		Ressource[] textures = new Ressource[]
				{
				new Ressource("Alien","./resources/textures/Alien.png", RessourceType.Texture ),
			
				}
				;
		
				ressources.LoadRessources(textures);
		
				alien1= new Alien("Alien1");
				alien2= new Alien("Alien2");
				
		assertTrue(alien1 == null);
		//assertEquals();
		// String with number login
		
	}

}
