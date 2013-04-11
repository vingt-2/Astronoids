package Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import CSV.Highscore;
import CSV.LoginMenu;
import CSV.Statistics;
import GameComponents.RigidBody.ForceMode;
import GameObjects.Alien;
import GameObjects.Asteroid;
import GameObjects.AsteroidField;
import GameObjects.GameChar;
import GameObjects.HUD;
import GameObjects.Laser;
import GameObjects.Player;
import GameObjects.PowerUp;
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
	public  ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();
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

		UpdateSceneObjects();

	}



	private void ExecuteLogic()
	{

		Random rand = new Random();

		lasers = player.shooter.GetLaserArray();

		/*
		 * Computations on each asteroids 
		 */
		for (Asteroid currentAsteroid : asteroidField.asteroidList) 
		{

			// PLAYER COLLISION WITH ASTEROID
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
						player.objectRenderer.SetTexture("shielded_ship");
						lostLife = true;
					}

				}


				// NO IDEA WHAT THIS IS DOING, damien ?
				if ((System.currentTimeMillis() - immunityTimer) > 3000
						&& immunity) {

					immunity = false;
					player.objectRenderer.SetTexture("rocket_ship");
				}


				// ASTEROID LASER/COLISION
				for (int j = 0; j < lasers.size(); j++) 
				{
					if (j<lasers.size()) 
					{
						if (lasers.get(j).rigidBody.isColliding(currentAsteroid))
						{

							currentAsteroid.isBroken = true;
							HUD.points += 10;
							SoundEffect.ASTEROIDBREAK.play();


							int pwrGen = rand.nextInt(40);

							if( pwrGen==2) 
							{
								System.out.println("LIFE");
								PowerUp buff1 = new PowerUp(currentAsteroid.transform.position, "Life");
								powerUpList.add(buff1);
								buff1.Update();
							}
							if( pwrGen == 3) 
							{
								System.out.println("SHIELD");
								PowerUp buff2 = new PowerUp(currentAsteroid.transform.position, "Shield");
								powerUpList.add(buff2);
								buff2.Update();

							}

							lasers.get(j).Delete();
							lasers.remove(j);
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
		
		if(GameOver)
			try {
				Statistics.updateStats(MainGame.currentUser, HUD.points, 0);
				Highscore.addScore(MainGame.currentUser.getUsername(), HUD.points);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void UpdatePowerUps()
	{
		for (int i = 0; i<powerUpList.size();i++)
		{

			PowerUp currentPowerUp = powerUpList.get(i);

			currentPowerUp.Update();
			
			if (player.rigidBody.isColliding(currentPowerUp))
			{

				if(currentPowerUp.type.equals("Life"))
				{
					player.lives++;
				}

				else if(currentPowerUp.type.equals("Shield"))
				{
					immunity = true;
					immunityTimer = System.currentTimeMillis();
					player.objectRenderer.SetTexture("shielded_ship");

				}

				powerUpList.remove(i);
				currentPowerUp.Delete();
			}
		}
	}
	
	private void UpdateSceneObjects()
	{	
		asteroidField.Update();
		UpdatePowerUps();
		hud.Update();
		if (!player.isDeleted) 
		{
			player.Update();
			alien.Update();
		} 

	}
}


