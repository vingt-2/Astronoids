package Game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import CSV.Highscore;
import CSV.Statistics;
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
import GameObjects.PickUp;
import GameObjects.Player;
import GameObjects.RapidFire;
import GameObjects.Shield;
import Helpers.Color;
import Helpers.SoundEffect;
import Maths.Vector2;

/**
 * Controls all the game logic
 *
 * @author Damien Doucet-Girard
 * @author Chi-Wing Sit
 */
public class GameLogic
{
	/**
	 * Declares all game logic objects
	 * inializes secondary objects(that don't need to be included in the constructor
	 * 
	 */
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


	public  ArrayList<Laser> lasers = new ArrayList<Laser>();
	public  ArrayList<Laser> alienLaser1 = new ArrayList<Laser>();
	public  ArrayList<Laser> alienLaser2 = new ArrayList<Laser>();
	public  ArrayList<PickUp> PickUpList = new ArrayList<PickUp>();
	public AsteroidField asteroidField;

	// GAME LOGIC STATES !
	public boolean GameFail = false;
	public boolean GameWin 	= false;

	public int numberOfAsteroidsDestroyed = 0;

	public static boolean GameOver = false;

	/**
	 * GameLogic constructor
	 * initializes player, HUD, difficulty and asterodiField
	 */
	public GameLogic()
	{
		player = new Player();
		player.transform.size = new Vector2(3,3);
		player.rigidBody.frictionCoefficient = 0.03f;

		hud = new HUD();
		if(Menu.isMedium) mediumDifficulty =true;
		else mediumDifficulty = false;
		if(Menu.isHard) hardDifficulty = true;
		else hardDifficulty = false;

		asteroidField = new AsteroidField(12,5);
		asteroidField.GenerateField();

	}

	/**
	 * Update the game logic
	 */
	public void UpdateLogic() 
	{

		if(!GameOver)
		{
			ExecuteLogic();
			CheckForEndGame();
		}
		// if player one turn ends
		else if(MainGame.playerOne){
			if(MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				clearGameScreen();
				MainGame.enterKeyPressed = true;
				MainGame.scoreOne = HUD.points;
				HUD.points = 0;
				MainGame.playerTwo = true;
				MainGame.playerOne = false;
				GameOver = false;
			}
		}
		// if player two turn ends
		else if(MainGame.playerTwo){
			clearGameScreen();
			String winner;

			MainGame.scoreTwo = HUD.points;

			// Displays the winner
			if(MainGame.scoreOne == MainGame.scoreTwo)
				winner = "Tie!";
			else if(MainGame.scoreOne < MainGame.scoreTwo)
				winner = "Player 2 Wins";
			else
				winner = "Player 1 Wins";
			MainGame.render.DrawText(winner,Vector2.zero(),Color.White,1f);
			if(MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				clearGameScreen();
				// Resets points counter to 0
				HUD.points = 0;

				// Returns to menu
				MainGame.inMenu = true;
				MainGame.inStartMenu = true;
			}
		}
		// Waits for user to press enter key to return to menu
		else{
			if(MainGame.controls.isPressed(KeyEvent.VK_ENTER)){
				clearGameScreen();
				HUD.points = 0;
				MainGame.inMenu = true;
				Menu.inGameMenu = true;
				Menu.initGameMenu();
				Menu.inLevelMenu = false;
			}
		}
		/**
		 * updates all objects on the scene
		 * 
		 */
		UpdateSceneObjects();
	}

	/**
	 * execute the game's general logic including collision, powerups, lives, etc.
	 * 
	 */
	private void ExecuteLogic()
	{

		/**
		 * initializing random variables to be used in the generation of the alien
		 */
		Random rand = new Random();
		int randPosX;
		int randPosY;
		int randSign;
		randPosX = rand.nextInt(1000)-750;
		randPosY = rand.nextInt(1000)-500;
		// Generated random Sign
		randSign = rand.nextBoolean() ? -1 : 1;


		lasers = player.shooter.GetLaserArray();
		
		/**
		 * Computations on each asteroids 
		 */

		for (Asteroid currentAsteroid : asteroidField.asteroidList) 
		{
			Transform temporaryTransform = currentAsteroid.transform;
			ParticleEffects collateral = new ParticleEffects(temporaryTransform, 30);
			collateral.isShrapnel =true;
			shrapnel.add(collateral);

			/**
			 *  PLAYER COLLISION WITH ASTEROID
			 */
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

				//Sets the player immune for 3 seconds after hitting and asteroid
				if ((System.currentTimeMillis() - immunityTimer) > 3000
						&& immunity) {

					immunity = false;
				}

				/**
				 *  ASTEROID LASER/COLISION
				 */

				for (int j = 0; j < lasers.size(); j++) 
				{
					if (j<lasers.size()) 
					{
						if (lasers.get(j).rigidBody.isColliding(currentAsteroid))
						{

							collateral.TurnOn();
							numberOfAsteroidsDestroyed ++;


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

						/**
						 * Player laser to alien collision
						 */
						if (alien != null) {
							if (!alien.isDeleted) {
								if(j<lasers.size()){
									if (lasers.get(j).rigidBody.isColliding(alien)) {
										alien.Delete();
										for(int i = 0; i< alien.alienCannon1.GetLaserArray().size();i++){
											alien.alienCannon1.GetLaserArray().get(i).Delete();
										}
										alien.alienCannon1.GetLaserArray().clear();

									}
								}
							}
						}
						if (alien2 != null) {
							if (!alien2.isDeleted) {
								if(j<lasers.size()){
									if (lasers.get(j).rigidBody.isColliding(alien2)) {
										alien2.Delete();
										for(int i = 0; i< alien2.alienCannon2.GetLaserArray().size();i++){

											alien2.alienCannon2.GetLaserArray().get(i).Delete();
										}
										alien2.alienCannon2.GetLaserArray().clear();
									}
								}
							}
						}
					}
				}
			}
		}

		/**
		 *  Generates an alien on specific stages and after 10 asteroids are destroyed
		 */
		if(numberOfAsteroidsDestroyed  > 10 &&(stageCounter ==2 ||stageCounter ==4||stageCounter ==5)){
			if( alien == null){

				alien= new Alien("Alien1");
				alien.transform.size=new Vector2(2,2);
				alien.rigidBody.frictionCoefficient= 0.05f;
				alien.rigidBody.SetPosition(new Vector2(randPosX*randSign, randPosY* randSign ));

			}
			else{
				if(!alien.isDeleted && !player.isDeleted)
					alien.Update();
			}
			
			/**
			 *  Generates an alien2 on specific stages and after 15 asteroids are destroyed
			 */
			if(numberOfAsteroidsDestroyed  > 15 &&(stageCounter == 3 ||stageCounter ==5 || stageCounter == 2)){

				if(alien2 == null){

					alien2= new Alien("Alien2");
					alien2.transform.size=new Vector2(2,2);
					alien2.rigidBody.frictionCoefficient= 0.05f;

					alien2.rigidBody.SetPosition(new Vector2(randPosX*randSign, randPosY* randSign ));

				}
				else
					if (!alien2.isDeleted && !player.isDeleted){
						alien2.Update();}
			}
		}

		lasers = player.shooter.GetLaserArray();

		/**
		 * Player collision with Alien's lasers
		 */
		if(alien!= null ){
			alienLaser1 = alien.alienCannon1.GetLaserArray();
			for(int i = 0; i <alienLaser1.size(); i++){
				if (!player.isDeleted) {
					if (player.rigidBody.isColliding(alienLaser1.get(i))&& !immunity) {
						
						player.lives--;

						if (player.lives == 0) {
							SoundEffect.CRASH.play();
							GameFail = true;
							player.Delete();
							
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
						if ((System.currentTimeMillis() - immunityTimer) > 3000
								&& immunity) {

							immunity = false;
						}


					}
				}
			}
		}

		/**
		 *  Collision detection with alien2
		 */
		if(alien2 != null){
			alienLaser2 = alien2.alienCannon2.GetLaserArray();
			for(int i = 0; i <alienLaser2.size(); i++){
				if(alienLaser2 != null){
					
					if (!player.isDeleted) {
						if (player.rigidBody.isColliding(alienLaser2.get(i))&& !immunity) {
							player.lives--;

							if (player.lives == 0) {
								SoundEffect.CRASH.play();
								GameFail = true;
								player.Delete();
								
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
							if ((System.currentTimeMillis() - immunityTimer) > 3000
									&& immunity) {

								immunity = false;
							}

						}
					}
				}
			}
		}


		



		if (asteroidField.asteroidList.size() == 0) 
		{
			stageCounter++;
			numberOfAsteroidsDestroyed = 0;
			GameWin = true;
		}

		/**
		 * Collision with power ups on map
		 * 
		 */

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


	/**
	 * Checks if the game has ended and modifies the according public boolean
	 */
	private void CheckForEndGame()
	{
		// Lost the game
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
		/**
		 *  Destroyed all the asteroids on the screen
		 */
		else if (GameWin) 
		{
			/**
			 * stage progression until end of game
			 */
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

		/**
		 *  if in one player mode update highscores and statistics
		 */
		if(GameOver && !MainGame.playerOne && !MainGame.playerTwo)
			try {
				Statistics.updateStats(MainGame.currentUser, HUD.points, 0);
				Highscore.addScore(MainGame.currentUser.getUsername(), HUD.points);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		


	}

	/**
	 * Updates the asteroidField and the HUD
	 */
	private void UpdateSceneObjects()
	{	
		if (!player.isDeleted) // if player not deleted updates player
		{
			player.Update();

		}
		
		hud.Update(); // Updates HUD
		
		//updates PickUps on field
		for(int j =0; j<PickUpList.size(); j++){
			if(!(PickUpList.get(j).isDeleted)){
				PickUpList.get(j).Update();
			}	
		}
	
		//updates particles effects when asteroids explode
		for(int k =0; k<shrapnel.size(); k++){
			if(!shrapnel.get(k).isDeleted && !player.isDeleted){
				shrapnel.get(k).Update();

			}
			if(shrapnel.get(k).TimeToDie()){
				shrapnel.get(k).TurnOff();
			}
		}
		
		asteroidField.Update(); // Updates asteroids
	}

	/**
	 * Removes all the GameChar from the screen
	 */
	private void clearGameScreen(){
		// Deletes player
		player.Delete();
		// Deletes asteroids particle effect
		for (ParticleEffects object : shrapnel){
			if(object != null)
				object.Delete();
		}
		
		// Deletes remaining asteroids
		for (Asteroid currentAsteroid : asteroidField.asteroidList){
			currentAsteroid.Delete();
		}
		
		// Deletes HUD info
		for (GameChar object : hud.otherInfos){
			object.Delete();
		}
		
		// Deletes pickups
		for (GameChar object : PickUpList){
			object.Delete();
		}
		
		// Deletes remaining lasers
		lasers = player.shooter.GetLaserArray();
		for (Laser object : lasers){
			if(object != null)
				object.Delete();
		}
		
		// Clears the arraylists
		asteroidField.asteroidList.clear();
		hud.otherInfos.clear();
		PickUpList.clear();
		
		// Deletes the aliens and their bullets if any
		if(alien != null)
			alien.Delete();
			if(alien.alienCannon1!=null){
				for (Laser object : alien.alienCannon1.GetLaserArray()){
					if(object != null)
						object.Delete();
				}
			}
		if(alien2 != null)
			alien2.Delete();
		if(alien2.alienCannon2!=null){
			for (Laser object : alien2.alienCannon2.GetLaserArray()){
				if(object != null)
					object.Delete();
			}
		}
	}


	/**
	 * Generates asteroids for stage 2
	 */
	private void stage2(){
		
		// Deletes the pickups
		for(int j =0; j<PickUpList.size(); j++){
			PickUpList.get(j).Delete();
		}

		// Resets player position
		player.rigidBody.SetPosition(new Vector2(0,0));
		asteroidField = new AsteroidField(14,5);
		
		// Level difficulty
		if(hardDifficulty){
			asteroidField.speed = 45;
			asteroidField.numberOfLives = 3;
		}
		else if(mediumDifficulty){
			asteroidField.speed = 30;
			asteroidField.numberOfLives = 1;
		}
		else{
			asteroidField.speed = 10;
			asteroidField.numberOfLives = 1;

		}

		PickUpList.clear();
		
		// Display stage 2
		HudObject stage2 = new HudObject(new Vector2(0,0));
		stage2.objectRenderer.SetTexture("Stage2");
		stage2.transform.size = new Vector2(40,15);
		hud.otherInfos.add(stage2);	

		// Generates new asteroid field
		player.rigidBody.SetPosition(new Vector2(0,0));
		asteroidField = new AsteroidField(14,5);

		asteroidField.GenerateField();
		GameWin = false;

	}

	/**
	 * Generates stage 3
	 */
	private void stage3(){
		// Removes previous pickups
		for(int j =0; j<PickUpList.size(); j++){
			PickUpList.get(j).Delete();
		}

		PickUpList.clear();

		// Display stage 3
		HudObject stage3 = new HudObject(new Vector2(0,0));
		stage3.objectRenderer.SetTexture("Stage3");
		stage3.transform.size = new Vector2(40,15);
		hud.otherInfos.add(stage3);	

		// Resets player position
		player.rigidBody.SetPosition(new Vector2(0,0));
		asteroidField = new AsteroidField(17,5);
		
		// Level difficulty
		if(hardDifficulty){
			asteroidField.speed = 65;
			asteroidField.numberOfLives = 3;
		}
		else if(mediumDifficulty){
			asteroidField.speed = 45;
			asteroidField.numberOfLives = 1;
		}
		else{
			asteroidField.speed = 20;
			asteroidField.numberOfLives = 1;

		}
		asteroidField.GenerateField();
		GameWin = false;
	}


	/**
	 * Generates stage 4
	 */
	private void stage4(){
		
		// Removes previous pickups
		for(int j =0; j<PickUpList.size(); j++){

			PickUpList.get(j).Delete();
		}

		PickUpList.clear();
		
		// Displays stage 4
		HudObject stage4 = new HudObject(new Vector2(0,0));
		stage4.objectRenderer.SetTexture("Stage4");
		stage4.transform.size = new Vector2(40,15);
		hud.otherInfos.add(stage4);	

		// Resets player position
		player.rigidBody.SetPosition(new Vector2(0,0));
		asteroidField = new AsteroidField(19,5);
		
		// Level Difficulty
		if(hardDifficulty){
			asteroidField.speed = 75;
			asteroidField.numberOfLives = 3;
		}
		else if(mediumDifficulty){
			asteroidField.speed = 50;
			asteroidField.numberOfLives = 1;
		}
		else{
			asteroidField.speed = 25;
			asteroidField.numberOfLives = 1;

		}
		asteroidField.GenerateField();
		GameWin = false;

	}

	/**
	 * Generates stage 5
	 */
	private void stage5(){
		// Removes previous pickups
		for(int j =0; j<PickUpList.size(); j++){
			PickUpList.get(j).Delete();
		}
		PickUpList.clear();

		// Displays stage 5
		HudObject stage5 = new HudObject(new Vector2(0,0));
		stage5.objectRenderer.SetTexture("Stage5");
		stage5.transform.size = new Vector2(40,15);
		hud.otherInfos.add(stage5);

		// Resets player position
		player.rigidBody.SetPosition(new Vector2(0,0));
		asteroidField = new AsteroidField(21,5);
		
		// Level difficulty
		if(hardDifficulty){
			asteroidField.speed = 90;
			asteroidField.numberOfLives = 3;
		}
		else if(mediumDifficulty){
			asteroidField.speed = 60;
			asteroidField.numberOfLives = 1;
		}
		else{
			asteroidField.speed = 30;
			asteroidField.numberOfLives = 1;

		}
		asteroidField.GenerateField();
		GameWin = false;
	}

}

