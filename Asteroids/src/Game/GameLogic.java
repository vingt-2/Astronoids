package Game;

import java.util.ArrayList;
import java.util.Random;

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

	public static ArrayList<Laser> lasers;

	public static boolean lostLife = false;

	public static ArrayList<PowerUp> buffs = new ArrayList<PowerUp>();

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

			Random rand = new Random();

			lasers = player.secondEffect.GetLaserArray();

			ArrayList<Asteroid> rocks = asteroidField.asteroidList;

			for (int i = 0; i < rocks.size(); i++) 
			{

				if(!(buffs.size() <= i)){

					if (player.rigidBody.isColliding(buffs.get(i))){

						if(buffs.get(i).type.equals("Life")){
							player.lives++;
						}

						else if(buffs.get(i).type.equals("Shield")){
							immunity = true;
							immunityTimer = System.currentTimeMillis();
							player.objectRenderer.SetTexture("shielded_ship");

						}
						buffs.get(i).Delete();
						buffs.remove(i);
					}
				}
				if (!player.isDeleted) {
					// System.out.println(rocks[i].terminator);

					if (!(rocks.size() <= i)) 
					{

						if (player.rigidBody.isColliding(rocks.get(i))
								&& !immunity) {
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



						if ((System.currentTimeMillis() - immunityTimer) > 3000
								&& immunity) {

							immunity = false;
							player.objectRenderer.SetTexture("rocket_ship");
						}
						for (int j = 0; j < lasers.size(); j++) 
						{
							if (j<lasers.size() && i < rocks.size()) 
							{
								if (lasers.get(j).rigidBody.isColliding(rocks.get(i)))
								{
									SoundEffect.ASTEROIDBREAK.play();
									int pwrGen = rand.nextInt(40);
									if( pwrGen==2) {
										System.out.println("LIFE");
										PowerUp buff1 = new PowerUp(rocks.get(i).transform.position, "Life");
										buffs.add(buff1);
										buff1.Update();

									}
									if( pwrGen == 3) 
									{
										System.out.println("SHIELD");
										PowerUp buff2 = new PowerUp(rocks.get(i).transform.position, "Shield");
										buffs.add(buff2);
										buff2.Update();

									}

									rocks.get(i).isBroken = true;

									HUD.points += 10;

									asteroidField.fieldSize--;
									lasers.get(j).Delete();
									lasers.remove(j);
								}
							}
						}
					}
				}

				if (!(asteroidField.GetAsteroidArray().size() <= i))
					rocks.get(i).terminator = false;

				if (asteroidField.fieldSize == 0) 
				{
					GameWin = true;
				}
			}
			
			CheckForEndGame();
			
		}
		
		
		asteroidField.Update();
		hud.Update();
		if (!player.isDeleted) 
		{
			player.Update();
			alien.Update();
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
	
}


