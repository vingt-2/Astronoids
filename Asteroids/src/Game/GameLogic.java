package Game;

import java.util.ArrayList;
import java.util.Random;

import GameComponents.RigidBody.ForceMode;
import GameObjects.Asteroid;
import GameObjects.GameChar;
import GameObjects.HUD;
import GameObjects.Laser;
import GameObjects.PowerUp;
import Helpers.Color;
import Maths.Vector2;
import Renderer.Renderer;

public class GameLogic {

	public boolean immunity;
	public long immunityTimer;
	public int counter = 0;
	public static boolean winChecker = false;
	Random rand = new Random();
	public static boolean breakChecker = false;
	public static Vector2 brokenAsteroid;
	public static float brokenSize;
	public static ArrayList<Asteroid> rocks;
	public static ArrayList<Laser> lasers;
	public static boolean lostLife = false;
	public static ArrayList<PowerUp> buffs = new ArrayList<PowerUp>();
	
	public void UpdateLogic() {

		rocks = MainGame.fieldGenerator.GetAsteroidArray();
		lasers = MainGame.player.secondEffect.GetLaserArray();
		
		
		for (int i = 0; i < rocks.size(); i++) {
			// System.out.println(System.currentTimeMillis() - immunityTimer);
			// System.out.println(MainGame.player.lives);
			if(!(buffs.size() <= i)){
				
				if (MainGame.player.rigidBody.isColliding(buffs.get(i))){
					
					if(buffs.get(i).type.equals("Life")){
							MainGame.player.lives++;
					}
					
					else if(buffs.get(i).type.equals("Shield")){
						immunity = true;
						immunityTimer = System.currentTimeMillis();
						MainGame.player.objectRenderer.SetTexture("shielded_ship");
						
					}
					buffs.get(i).Delete();
					buffs.remove(i);
				}
			}
			if (!MainGame.player.isDeleted) {
				// System.out.println(rocks[i].terminator);
				
				if (!(rocks.size() <= i)) {
					
					if (MainGame.player.rigidBody.isColliding(rocks.get(i))
							&& !immunity) {
						MainGame.player.lives--;

						if (MainGame.player.lives == 0) {
							MainGame.player.Delete();
						} else {
							immunity = true;
							immunityTimer = System.currentTimeMillis();
							MainGame.player.objectRenderer.SetTexture("shielded_ship");
							lostLife = true;
						}

					}
					
					

					if ((System.currentTimeMillis() - immunityTimer) > 3000
							&& immunity) {

						immunity = false;
						MainGame.player.objectRenderer
								.SetTexture("rocket_ship");
					}
					for (int j = 0; j < lasers.size(); j++) {
						if (j<lasers.size() && i < rocks.size()) {
							
							if (lasers.get(j).rigidBody.isColliding(rocks.get(i))) {
								
								int pwrGen = rand.nextInt(40);
								if( pwrGen==2) {
									System.out.println("LIFE");
									PowerUp buff1 = new PowerUp(rocks.get(i).transform.position, "Life");
									buffs.add(buff1);
									buff1.Update();
									
								}
								if( pwrGen == 3) {
									System.out.println("SHIELD");
									PowerUp buff2 = new PowerUp(rocks.get(i).transform.position, "Shield");
									buffs.add(buff2);
									buff2.Update();
									
								}
								
								if (i < rocks.size()) {

									for (int k = 0; k < (rocks.get(i).transform.size.x) / 2; k++) {

										float size = rocks.get(i).transform.size.x / 2;

										if (size > 1) {

											MainGame.fieldGenerator.number++;
											breakChecker = true;
											brokenAsteroid = rocks.get(i).transform.position;
											brokenSize = size;
											

										} else
											breakChecker = false;

									}

									rocks.get(i).Delete();
									rocks.remove(i);
									HUD.points += 10;

								}
								
								MainGame.fieldGenerator.number--;
								lasers.get(j).Delete();
								lasers.remove(j);
								System.out.println(buffs.size());
								
							}
						}
					}
				}
				
				
				

				if (!(MainGame.fieldGenerator.GetAsteroidArray().size() <= i))
					rocks.get(i).terminator = false;

				if (MainGame.fieldGenerator.number == 0) {
					winChecker = true;

				}
			}
		}

		MainGame.fieldGenerator.Update();
		MainGame.hud.Update();
		// Update object_1 transform, physics, rendering etc...
		if (!MainGame.player.isDeleted) {
			MainGame.player.Update();
			MainGame.alien.Update();

		} else {
			
			if (counter == 0){
			
				MainGame.GameOver = new GameChar();
				counter++;
			}
			MainGame.GameOver.objectRenderer.SetTexture("game_over");
			MainGame.GameOver.transform.size = new Vector2(50, 13);
			MainGame.GameOver.transform.position = new Vector2(0, -9);
			MainGame.GameOver.rigidBody.frictionCoefficient = 0.1f;
			MainGame.GameOver.Update();
		}

		if (winChecker) {

			if (counter == 0) {
				MainGame.Win = new GameChar();
				counter++;
			}
			MainGame.Win.objectRenderer.SetTexture("Win");
			MainGame.Win.transform.size = new Vector2(40, 8);
			MainGame.Win.transform.position = new Vector2(0, 0);
			MainGame.Win.rigidBody.frictionCoefficient = 0.1f;
			MainGame.Win.Update();

		}

	}
}
