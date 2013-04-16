package GameObjects;

import java.util.ArrayList;

import java.util.Random;

import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;
import GameObjects.Asteroid;

/**
 * @author Vincent Petrella
 * @author Damien Doucet-Girard
 * 
 * Asteroid field generator object. Generates the field of asteroids according to
 * give parameters
 *
 */

public class AsteroidField extends GameObject 
{
	
	public int fieldSize;
	public ArrayList<Asteroid> asteroidList;
	int randPosX;
	int randPosY;
	int randSign;
	public int speed;
	public int numberOfLives = 1;
	int directionCounter = 1;
	
	
	
	public AsteroidField(int nbAsteroids, int speed)
	{
		asteroidList = new ArrayList<Asteroid>() ;
		fieldSize = nbAsteroids;
	}
	
	/**
	 * generates an asteroid field (single call)
	 */
	public void GenerateField()
	{
		ArrayList<Asteroid> generatedField = new ArrayList<Asteroid>();
		while(generatedField.size() < fieldSize)
		{
			Random rand = new Random();
			
			randPosX = rand.nextInt(1500)-750;
			randPosY = rand.nextInt(1000)-500;
			
			// Generated random Sign
			randSign = rand.nextBoolean() ? -1 : 1;
			
			//Generate random position
			if (randPosX<150 && randPosX>-190) randPosX = 200+rand.nextInt(100)*randSign;
			if (randPosY<150 && randPosY>-190) randPosY = 200+rand.nextInt(100)*randSign;
			
			//Generate random size
			int randSize =rand.nextInt(12)%4+3;
		
			//generate asteroid according to random variables
			Asteroid newAsteroid = new Asteroid(new Vector2((float) randPosX, (float) randPosY));
			newAsteroid.transform.size=(new Vector2( randSize, randSize));
			newAsteroid.lives = numberOfLives;
			newAsteroid.Update();
			
			//if the asteroid is generated and doesnt overlap another one, apply 
			//speed and roation according to difficulty (speed). Otherwise delete the asteroid
			//to avoid overlapping
			if(!AsteroidCollidingWithField(newAsteroid, generatedField))
			{
				int x = rand.nextInt(200)-100;
				int y = rand.nextInt(200)-100;
				if (x>0) x += speed;
				else x -= speed;
				if(y>0) y+= speed;
				else y -=speed;
				newAsteroid.rigidBody.PushForce(new Vector2(x,y),ForceMode.Force);
				newAsteroid.rigidBody.PushTorque(rand.nextInt(10)%4-2, ForceMode.Force);
				generatedField.add(newAsteroid);
			}
			else
			{
				newAsteroid.Delete();
			}
			
		}
		asteroidList = generatedField;
	}
	
	/**
	 * iterates through asteroid array to make sure  anewly generated asteroid does not collide with anoter
	 * @param asteroidChecked
	 * @param field
	 * @return
	 */
	public static boolean AsteroidCollidingWithField(Asteroid asteroidChecked, ArrayList<Asteroid> field)
	{
		boolean isColliding = false;
		for (Asteroid currentAsteroid : field)
		{
			if(asteroidChecked.rigidBody.isColliding(currentAsteroid))
			{
				isColliding = true;
			}
		}
		return isColliding;
	}

	/**
	 * Updates the asteroid field
	 */
	public void Update()
	{
		for(int astIndex = 0; astIndex < asteroidList.size() ; astIndex++)
		{
			//update all asteroids in list
			Asteroid currentAsteroid = asteroidList.get(astIndex);
			currentAsteroid.Update();
			
			//if asteroid is destroyed, generate multiple smaller asteroids 
			//at the location of impact
			if(currentAsteroid.isBroken)
			{
				Random rand = new Random();
				float size = currentAsteroid.transform.size.x / 2;
				
				//if the asteroid is too small, create no more asteroids on impact
				if(size > 1)
				{
					//generate a number of smaller asteroids according to 
					//the size of the destroyed asteroid over 2
					for(int i = 0; i<size/2; i++)
					{
						fieldSize ++ ;
												
						Vector2 spawnSpot = (new Vector2 (rand.nextInt(10)-5,rand.nextInt(10)-5).Normalized().Scaled(20));
		
						
						Vector2 newPost = Vector2.Add(currentAsteroid.transform.position,spawnSpot);
						
						//apply forces to new asteroids according to speed and difficulty
						Asteroid newAst = new Asteroid(newPost);
						if (newAst.transform.size.x>0.5f) newAst.lives = numberOfLives;
						int x = rand.nextInt(400)-200;
						int y = rand.nextInt(400)-200;
						if (x>0) x += speed;
						else x -= speed;
						if(y>0) y+= speed;
						else y -=speed;
						newAst.rigidBody.PushForce(new Vector2(x,y),ForceMode.Force);
						newAst.rigidBody.PushTorque(rand.nextInt(10)%4-2, ForceMode.Force);
						newAst.transform.size=(new Vector2( currentAsteroid.transform.size.x/2, currentAsteroid.transform.size.y/2 ));
						asteroidList.add(newAst);
					}
				}
				
				asteroidList.remove(astIndex);
				currentAsteroid.Delete();

			}
		}
		
	}
	
	/**
	 * return the arrayList of asteroids
	 * @return
	 */
	public ArrayList<Asteroid> GetAsteroidArray(){
		
		return asteroidList;
	}
	
	/**
	 * deletes a given asteroid in the field
	 * @param field
	 * @param i
	 */
	public static void Die( Asteroid[] field, int i){
		
		field[i].Delete();
	}

	/**
	 * adds an asteroid to the field
	 * @param asteroid
	 */
	public void addToField(Asteroid asteroid)
	{
		
		asteroidList.add(asteroid);
		
	}
	
}
