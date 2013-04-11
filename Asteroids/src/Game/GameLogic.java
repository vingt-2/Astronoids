package Game;

import java.util.ArrayList;
import java.util.Random;

import GameComponents.RigidBody.ForceMode;
import GameObjects.Alien;
import GameObjects.Asteroid;
import GameObjects.AsteroidField;
import GameObjects.GameChar;
import GameObjects.HUD;
import GameObjects.Laser;
import GameObjects.Life;
import GameObjects.Player;
import GameObjects.PickUp;
import GameObjects.RapidFire;
import GameObjects.Shield;
import Helpers.SoundEffect;
import Maths.Vector2;

public class GameLogic
{

	public Player player;
	public Alien alien;
	public HUD hud;

	public boolean immunity;
	public long immunityTimer;
	public int counter = 0;

	public static boolean lostLife = false;


	public  ArrayList<Laser> lasers = new ArrayList<Laser>();
	public  ArrayList<PickUp> PickUpList = new ArrayList<PickUp>();
	public AsteroidField asteroidField;



	// GAME LOGIC STATES !
	public boolean GameFail = false;
	public boolean GameWin 	= false;
	public boolean GameOver = false;


	public GameLogic()
	{
		player = new Player();
		player.transform.size = new Vector2(4,4);
		player.rigidBody.frictionCoefficient = 0.05f;

		alien = new Alien();
		alien.transform.size=new Vector2(2,2);
		alien.rigidBody.frictionCoefficient= 0.05f;

		hud = new HUD();

		asteroidField = new AsteroidField(12,5);
		asteroidField.GenerateField();
	}

	public void UpdateLogic() 
	{

		if(!GameOver)
		{
			ExecuteLogic();

			CheckForEndGame();

		}

		UpdateObjectVectors();

	}



	private void ExecuteLogic()
	{

		Random rand = new Random();

		lasers = player.shooter.GetLaserArray();

		
		for (Asteroid currentAsteroid : asteroidField.asteroidList) 
		{
			Vector2 resultingForce = Vector2.zero();
			for(Asteroid secondAsteroid : asteroidField.asteroidList)
			{
				if(currentAsteroid != secondAsteroid && currentAsteroid.rigidBody.isColliding(secondAsteroid))
				{
					float deltaTime = MainGame.render.deltaTime;
					Vector2 secondToCurrent = Vector2.Add(currentAsteroid.transform.position,secondAsteroid.transform.position.negate());
					
					resultingForce = Vector2.Add(resultingForce,(Vector2.Scale(-Vector2.Dot(secondToCurrent.Normalized(),currentAsteroid.rigidBody.velocity) / (deltaTime*deltaTime) ,secondToCurrent.Normalized())));
				}
				
			}
			
			currentAsteroid.rigidBody.PushForce(resultingForce, ForceMode.Impulse);
			
			MainGame.debug.DrawLine(currentAsteroid.transform.position, resultingForce,resultingForce.GetLength());
			
			if (!player.isDeleted) 
			{
				if (player.rigidBody.isColliding(currentAsteroid) && !immunity)
				{
					player.lives--;

					if (player.lives == 0) 
					{
						SoundEffect.CRASH.play();
						GameFail = true;
						player.Delete();
					} 
					else 
					{
						immunity = true;
						immunityTimer = System.currentTimeMillis();
						System.out.println("TRUE");
						player.isShieldOn = true;
						lostLife = true;
						
					}

				}



				if ((System.currentTimeMillis() - immunityTimer) > 3000
						&& immunity) {

					immunity = false;
					
				}

				
				// Check for player lasers collision
				for (int j = 0; j < lasers.size(); j++) 
				{
					if (j<lasers.size()) 
					{
						if (lasers.get(j).rigidBody.isColliding(currentAsteroid))
						{
							
							currentAsteroid.isBroken = true;
							HUD.points += 10;
							SoundEffect.ASTEROIDBREAK.play();
						
							lasers.get(j).Delete();
							lasers.remove(j);
							
							if(rand.nextInt(10) == 1){
								PickUpList.add(new Shield(currentAsteroid.transform.position));
							}
							if(rand.nextInt(10) == 2){
								PickUpList.add(new RapidFire(currentAsteroid.transform.position));
							}
							if(rand.nextInt(10) == 3){
								PickUpList.add(new Life(currentAsteroid.transform.position));
							}
							
						}
					}
										
				}
			}


			if (asteroidField.asteroidList.size() == 0) 
			{
				GameWin = true;
			}
		}
		
		for(int j =0; j<PickUpList.size(); j++){
			if(!(PickUpList.get(j).isDeleted)){
				if(player.rigidBody.isColliding((PickUpList.get(j)))){
					PickUpList.get(j).OnPickUp();
					PickUpList.get(j).Delete();
					PickUpList.remove(j);
				}
			}
		}
	}

	private void CheckForEndGame()
	{
		if(GameFail)
		{

			GameChar gameFailed = new GameChar();

			gameFailed.objectRenderer.SetTexture("game_over");
			gameFailed.transform.size = new Vector2(50, 13);
			gameFailed.transform.position = new Vector2(0, -9);
			gameFailed.rigidBody.frictionCoefficient = 0.1f;
			hud.otherInfos.add(gameFailed);

			GameOver = true;
		}

		else if (GameWin) 
		{

			GameChar gameWin = new GameChar();

			gameWin.objectRenderer.SetTexture("Win");
			gameWin.transform.size = new Vector2(40, 8);
			gameWin.transform.position = new Vector2(0, 0);
			gameWin.rigidBody.frictionCoefficient = 0.1f;
			hud.otherInfos.add(gameWin);

			GameOver = true;
		}
	}

	private void UpdateObjectVectors()
	{	
		for(int j =0; j<PickUpList.size(); j++){
			if(!(PickUpList.get(j).isDeleted)){
				
				PickUpList.get(j).Update();
			
			}	
				
			}
		asteroidField.Update();
		hud.Update();
		if (!player.isDeleted) 
		{
			player.Update();
			alien.Update();
		} 
	}
}


