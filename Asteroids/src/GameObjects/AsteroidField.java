package GameObjects;

import java.util.ArrayList;
import java.util.Random;

import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;
import GameObjects.Asteroid;

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
			

			if (randPosX<150 && randPosX>-190) randPosX = 200+rand.nextInt(100)*randSign;
			if (randPosY<150 && randPosY>-190) randPosY = 200+rand.nextInt(100)*randSign;
			
			int randSize =rand.nextInt(12)%4+3;
		
			Asteroid newAsteroid = new Asteroid(new Vector2((float) randPosX, (float) randPosY));
			newAsteroid.transform.size=(new Vector2( randSize, randSize));
			newAsteroid.lives = numberOfLives;
			newAsteroid.Update();
			
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

	public void Update()
	{
		for(int astIndex = 0; astIndex < asteroidList.size() ; astIndex++)
		{
			Asteroid currentAsteroid = asteroidList.get(astIndex);
			currentAsteroid.Update();
			
			if(currentAsteroid.isBroken)
			{
				Random rand = new Random();
				float size = currentAsteroid.transform.size.x / 2;
				
				if(size > 1)
				{
					for(int i = 0; i<size/2; i++)
					{
						fieldSize ++ ;
						
						int SPAWN_RADIUS = 20;
						
						Vector2 spawnSpot = (new Vector2 (rand.nextInt(10)-5,rand.nextInt(10)-5).Normalized().Scaled(20));
		
						
						Vector2 newPost = Vector2.Add(currentAsteroid.transform.position,spawnSpot);
						
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
	
	public ArrayList<Asteroid> GetAsteroidArray(){
		
		return asteroidList;
	}
	
	public static void Die( Asteroid[] field, int i){
		
		field[i].Delete();
	}

	public void addToField(Asteroid asteroid)
	{
		
		asteroidList.add(asteroid);
		
	}
	
}
