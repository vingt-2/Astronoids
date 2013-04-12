package Game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import sun.applet.Main;


import CSV.Highscore;
import CSV.LoginMenu;
import CSV.Statistics;
import GameComponents.RigidBody.ForceMode;
import GameComponents.Transform;
import GameObjects.Alien;
import GameObjects.Asteroid;
import GameObjects.AsteroidField;
import GameObjects.GameChar;
import GameObjects.HUD;
import GameObjects.HudObject;
import GameObjects.Laser;
import GameObjects.Life;
import GameObjects.ParticleEffects;
import GameObjects.Player;
import GameObjects.PickUp;
import GameObjects.RapidFire;
import GameObjects.Shield;
import Helpers.SoundEffect;
import Maths.Vector2;
import java.util.Random;

public class GameLogic
{


	public Player player;
	public Alien alien;
	public Alien alien2;
	public HUD hud;


	public boolean immunity;
	public long immunityTimer;
	public int counter = 0;
	public static boolean playerOne = false;
	public boolean playerTwo = false;
	public ArrayList<ParticleEffects> shrapnel = new ArrayList<ParticleEffects>();

	public static boolean lostLife = false;
	public int stageCounter = 1;
	public boolean mediumDifficulty = false;
	public boolean hardDifficulty = false;
	int mediumSpeed = 0;
	int mediumLives = 0;
	int hardSpeed = 0;
	int hardLives = 0;

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
		player.transform.size = new Vector2(3,3);
		player.rigidBody.frictionCoefficient = 0.03f;

		
		hud = new HUD();
		if(Menu.isMedium) mediumDifficulty =true;
		if(Menu.isHard) hardDifficulty = true;
		System.out.println(mediumDifficulty + " " + hardDifficulty);
		if(mediumDifficulty){
			mediumSpeed =50;
			mediumLives =1;
		}
		if(hardDifficulty){
			mediumSpeed = 70;
			mediumLives = 3;
		}


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
		else if(playerOne){
			if(MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				player.Delete();
				for (Asteroid currentAsteroid : asteroidField.asteroidList){
					currentAsteroid.Delete();
				}
				for (GameChar object : hud.otherInfos){
					object.Delete();
				}
				MainGame.enterKeyPressed = true;
				hud.otherInfos.clear();
				HUD.points = 0;
				playerOne = false;
				playerTwo = true;
				GameOver = false;
			}
		}
		UpdateSceneObjects();
	}

	private void ExecuteLogic()
	{

		Random rand = new Random();
		int randPosX;
		int randPosY;
		int randSign;
		//Random rand = new Random();
		randPosX = rand.nextInt(1000)-750;
		randPosY = rand.nextInt(1000)-500;
		// Generated random Sign
		randSign = rand.nextBoolean() ? -1 : 1;


		lasers = player.shooter.GetLaserArray();
		

		if(asteroidField.fieldSize < 21){
			if( alien == null){

				alien= new Alien("Alien1");
				alien.transform.size=new Vector2(2,2);
				alien.rigidBody.frictionCoefficient= 0.05f;
				alien.rigidBody.SetPosition(new Vector2(randPosX*randSign, randPosY* randSign ));
				System.out.println(asteroidField.fieldSize);

			}
			else{
				alien.Update();
			}
			if (asteroidField.fieldSize < 15){

				if(alien2 == null){

					alien2= new Alien("Alien2");
					alien2.transform.size=new Vector2(2,2);
					alien2.rigidBody.frictionCoefficient= 0.05f;

					alien2.rigidBody.SetPosition(new Vector2(randPosX*randSign, randPosY* randSign ));
				}
				else
					alien2.Update();}
		}

		lasers = player.shooter.GetLaserArray();

		/*
		 * Computations on each asteroids 
		 */

		for (Asteroid currentAsteroid : asteroidField.asteroidList) 
		{
			Transform temporaryTransform = currentAsteroid.transform;
			ParticleEffects collateral = new ParticleEffects(temporaryTransform, 30);
			collateral.isShrapnel =true;
			shrapnel.add(collateral);
			
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
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SoundEffect.GAMEOVER.play();
					} 
					else 
					{
						immunity = true;
						immunityTimer = System.currentTimeMillis();
						//System.out.println("TRUE");
						player.isShieldOn = true;
						lostLife = true;

					}
				}

				// NO IDEA WHAT THIS IS DOING, damien ?
				//I sets the player immune for 3 seconds after hitting and asteroid
				if ((System.currentTimeMillis() - immunityTimer) > 3000
						&& immunity) {

					immunity = false;
				}

				// Check for player lasers collision

				// ASTEROID LASER/COLISION

				for (int j = 0; j < lasers.size(); j++) 
				{
					if (j<lasers.size()) 
					{
						if (lasers.get(j).rigidBody.isColliding(currentAsteroid))
						{
						collateral.TurnOn();
							
							currentAsteroid.lives--;
							if(currentAsteroid.lives ==0){
								currentAsteroid.isBroken = true;
								HUD.points += 10;
								SoundEffect.ASTEROIDBREAK.play();
								
							}
							

							lasers.get(j).Delete();
							lasers.remove(j);
							
								

								if(rand.nextInt(40) == 1){

									PickUpList.add(new Shield(currentAsteroid.transform.position));
								}
								if(rand.nextInt(40) == 2){
									PickUpList.add(new RapidFire(currentAsteroid.transform.position));
								}
								if(rand.nextInt(40) == 3){
									PickUpList.add(new Life(currentAsteroid.transform.position));
								}							
							
						}			
					}
				}
			}
		}


			if (asteroidField.asteroidList.size() == 0) 
			{
				stageCounter++;
				GameWin = true;
			}


			for(int j =0; j<PickUpList.size(); j++){
				if(!(PickUpList.get(j).isDeleted) && !(player.isDeleted)){
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
				HudObject gameFailed = new HudObject(new Vector2(0,0));
				gameFailed.permanent = true;
				gameFailed.objectRenderer.SetTexture("game_over");
				gameFailed.transform.size = new Vector2(50, 13);
				gameFailed.transform.position = new Vector2(0, -9);
				gameFailed.rigidBody.frictionCoefficient = 0.1f;
				hud.otherInfos.add(gameFailed);

				GameOver = true;
			}

			else if (GameWin) 
			{
				if(stageCounter ==2){
					stage2();
				}
				else if(stageCounter == 3){
					stage3();
				}
				else if(stageCounter == 4){
					stage4();
				}
				else if(stageCounter == 5){
					stage5();
				}
				else{
					HudObject gameWin = new HudObject(new Vector2(0,0));

					gameWin.permanent = true;
					gameWin.objectRenderer.SetTexture("Win");
					gameWin.transform.size = new Vector2(40, 8);
					gameWin.transform.position = new Vector2(0, 0);
					gameWin.rigidBody.frictionCoefficient = 0.1f;
					hud.otherInfos.add(gameWin);

					GameOver = true;
				}
			}

			if(GameOver && !playerOne && !playerTwo)
				try {
					Statistics.updateStats(MainGame.currentUser, HUD.points, 0);
					Highscore.addScore(MainGame.currentUser.getUsername(), HUD.points);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

		private void UpdateSceneObjects()
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
			hud.Update();
			for(int k =0; k<shrapnel.size(); k++){
				if(!shrapnel.get(k).isDeleted){
					shrapnel.get(k).Update();
					if(shrapnel.get(k).TimeToDie()){
						shrapnel.get(k).TurnOff();
						//shrapnel.get(k).Delete();
						//shrapnel.remove(k);
					}
				}
			}
			


		}

		private void stage2(){
			for(int j =0; j<PickUpList.size(); j++){

				PickUpList.get(j).Delete();
			}


			PickUpList.clear();
			HudObject stage2 = new HudObject(new Vector2(0,0));
			stage2.objectRenderer.SetTexture("Stage2");
			stage2.transform.size = new Vector2(40,15);
			hud.otherInfos.add(stage2);	


			player.rigidBody.SetPosition(new Vector2(0,0));
			asteroidField = new AsteroidField(14,5);
			asteroidField.speed = 25+hardSpeed+mediumSpeed;
			asteroidField.numberOfLives = 1+hardLives+mediumLives;

			asteroidField.GenerateField();
			GameWin = false;

		}


		private void stage3(){
			for(int j =0; j<PickUpList.size(); j++){

				PickUpList.get(j).Delete();
				//PickUpList.remove(j);

			}

			PickUpList.clear();
			
			HudObject stage3 = new HudObject(new Vector2(0,0));
			stage3.objectRenderer.SetTexture("Stage3");
			stage3.transform.size = new Vector2(40,15);
			hud.otherInfos.add(stage3);	

			player.rigidBody.SetPosition(new Vector2(0,0));
			asteroidField = new AsteroidField(17,5);
			asteroidField.speed = 50+hardSpeed+mediumSpeed;
			asteroidField.numberOfLives = 2+hardLives+mediumLives;
			asteroidField.GenerateField();
			GameWin = false;

		}


		private void stage4(){
			for(int j =0; j<PickUpList.size(); j++){

				PickUpList.get(j).Delete();
			}

			PickUpList.clear();
			HudObject stage4 = new HudObject(new Vector2(0,0));
			stage4.objectRenderer.SetTexture("Stage4");
			stage4.transform.size = new Vector2(40,15);
			hud.otherInfos.add(stage4);	

			player.rigidBody.SetPosition(new Vector2(0,0));
			asteroidField = new AsteroidField(19,5);
			asteroidField.speed = 100+hardSpeed+mediumSpeed;
			asteroidField.numberOfLives = 2+hardLives+mediumLives;
			asteroidField.GenerateField();
			GameWin = false;

		}

		private void stage5(){

			for(int j =0; j<PickUpList.size(); j++){
				System.out.println("Delete");
				PickUpList.get(j).Delete();
				//PickUpList.remove(j);

			}
			PickUpList.clear();
			HudObject stage5 = new HudObject(new Vector2(0,0));
			stage5.objectRenderer.SetTexture("Stage5");
			stage5.transform.size = new Vector2(40,15);
			hud.otherInfos.add(stage5);	

			player.rigidBody.SetPosition(new Vector2(0,0));
			asteroidField = new AsteroidField(21,5);
			asteroidField.numberOfLives = 3+hardLives+mediumLives;
			asteroidField.speed = 150 +hardSpeed+mediumSpeed;
			asteroidField.GenerateField();
			GameWin = false;

		}

	}

