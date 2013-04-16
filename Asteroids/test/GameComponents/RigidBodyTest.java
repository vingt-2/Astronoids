package GameComponents;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GameObjects.GameChar;
import GameComponents.*;
import Maths.Vector2;

public class RigidBodyTest {

	GameChar mainChar;
	GameChar secondaryChar;
	
	//creating two gameChars to be checked for collision.
	//initializing all their spatial properties to 0
	@Before
	
	public void instantiateChars(){
		
	mainChar = new GameChar();
	secondaryChar = new GameChar();
	mainChar.rigidBody.SetPosition(new Vector2(0,0));
	mainChar.transform.size = new Vector2(1,1);
	mainChar.transform.rotation = 0f;
	secondaryChar.rigidBody.SetPosition(new Vector2(0,0));
	secondaryChar.transform.size = new Vector2(1,1);
	secondaryChar.transform.rotation = 0f;
	
	

	}
	
	/**
	 * testing vertexFormula method which creates a vertex of the collision Shape based on 
	 *and two coordinates x and y
	 */
	@Test
	public void testVertexFormula() {
		
		instantiateChars();
		
		assertEquals(new Vector2(-10f,10f).x,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).x 
				,0f);
		assertEquals(new Vector2(-10f,10f).y ,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).y
				,0f);
		
		//testing if vertexFormula works when the shape rotates
		mainChar.transform.rotation = (float)Math.PI;
		
		assertEquals(new Vector2(10f,-10f).x,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).x 
				,0.00001f);
		assertEquals(new Vector2(10f,-10f).y ,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).y
				,0.00001f);
		
	
		//tests if vertexFormula works when the shape moves in position
		mainChar.transform.position =(new Vector2(5,5));
		
		assertEquals(new Vector2(15f,-5f).x,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).x 
				,0.00001f);
		assertEquals(new Vector2(15f,-5f).y ,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).y
				,0.00001f);
		
		//tests if vertexFormula works when teh shape changes size
		mainChar.transform.size = (new Vector2(5,5));
		
		assertEquals(new Vector2(55f,-45f).x,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).x 
				,0.00001f);
		assertEquals(new Vector2(55f,-45f).y ,mainChar.rigidBody.vertexFormula(mainChar, 10, 10).y
				,0.00001f);
		
		
		
	}
	
	/**
	 * tests the Makebox function which applies the vertexFormula to 4 predetermined coordinates
	 * to make a basic square collision box
	 */
	@Test
	public void testMakeBox(){
		
		instantiateChars();
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[0].x, 6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[0].y, -6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[1].x, 6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[1].y, 6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[2].x, -6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[2].y, 6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[3].x, -6f, 0f);
		assertEquals(mainChar.rigidBody.makeBox(mainChar)[3].y, -6f, 0f);
			
	}
	
	/**
	 * tests the MakeShip function which applies the vertexFormula to 6 predetermined coordinates
	 * to make a ship collision box
	 */
	@Test
	public void testMakeShip(){
		
		instantiateChars();
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[0].x, 10f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[0].y, -8f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[1].x, 6f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[1].y, -1f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[2].x, 3f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[2].y, 9f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[3].x, -3f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[3].y, 9f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[4].x, -6f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[4].y, -1f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[5].x, -10f, 0f);
		assertEquals(mainChar.rigidBody.makeShip(mainChar)[5].y, -8f, 0f);
			
	}
	
	/**
	 * tests the MakeAsteroidfunction which applies the vertexFormula to 6 predetermined coordinates
	 * to make a Asteroid collision box
	 */
	@Test
	public void testMakeAsteroid(){
		
		instantiateChars();
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[0].x, 10f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[0].y, -3f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[1].x, 4f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[1].y, 10f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[2].x, -2f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[2].y, 10f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[3].x, -5f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[3].y, 4f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[4].x, -9f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[4].y, -2f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[5].x, -2f, 0f);
		assertEquals(mainChar.rigidBody.makeAsteroid(mainChar)[5].y, -11f, 0f);
			
	}

	
	/**
	 * Integrating all previously tested functions into the testing of IsColliding.
	 * creates collision shapes and tests if the different shape segments intersect
	 * 
	 */
	@Test
	public void testIsColliding(){
		
		instantiateChars();
		
		mainChar.rigidBody.makeShip(mainChar);
		secondaryChar.rigidBody.makeAsteroid(secondaryChar);
		
		assertTrue(mainChar.rigidBody.isColliding(secondaryChar));
		
		/**
		 * checks if method is false when ship moves away and shapes no longer intersect
		 */
		mainChar.rigidBody.SetPosition(new Vector2(100,100));
		mainChar.rigidBody.makeShip(mainChar);
		
		assertFalse(mainChar.rigidBody.isColliding(secondaryChar));
		
		/**
		 * checks if method is true when asteroid is sized up and intersects the ship
		 * 
		 */
		secondaryChar.transform.size = (new Vector2(16,16));
		secondaryChar.rigidBody.makeAsteroid(secondaryChar);
		
		assertTrue(mainChar.rigidBody.isColliding(secondaryChar));
		
		/**
		 *  check if method is true with roation
		 *   
		 */
		
		mainChar.transform.rotation = 9f;
		
		assertTrue(mainChar.rigidBody.isColliding(secondaryChar));
	}
}
