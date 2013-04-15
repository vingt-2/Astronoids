package GameObjects;

import java.util.ArrayList;

import Game.GameLogic;
import Game.MainGame;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Maths.Vector2;
import Renderer.Renderer;

/**
 * Controls and updates objects over the main game that act as information displays, such as 
* Life, score, GameOver and You Win. 
 *  
 * 
 * @author Vincent Petrella
 * @author Damien Doucet-Girard
 *
 */
public class HUD extends GameChar {

	public static boolean changingStage = false;
	//hudOBjects is only used for player lives
	public ArrayList<GameChar> hudObjects;
	//otherINfos is used for all other information overlayed
	public ArrayList<HudObject> otherInfos;
	int counter = 0;
	boolean correct = false;
	public static int points = 0;

	String inputTest = "";

	public HUD() 
	{
		hudObjects = new ArrayList<GameChar>();
		otherInfos = new ArrayList<HudObject>();
		objectRenderer.SetTexture("Empty");
	}

	public void Update() {

		super.Update();

		//displaying and updating lives as the player gains and loses them
		//Lives are displayed as gameChar with the ship's texture
		for (int i = 0; i < MainGame.gameLogic.player.lives; i++) 
		{
			//initializes lives at the beginning of the game, and adds one when 
			//life is gained i.e. hudObjects.size() increases
			if (hudObjects.size() <= i) {
				hudObjects.add(new LifeDisplay(new Vector2((280 + i * 40), 300)));
				hudObjects.get(i).transform.size = (new Vector2(2.5f, 2.5f));

			
				hudObjects.get(i).Update();
			}
			
			
			if (GameLogic.lostLife) {
				hudObjects.get(i).Delete();
				hudObjects.remove(i);
				GameLogic.lostLife = false;
			}

		}
		
		//when player is deleted, all lives are removed
		if (MainGame.gameLogic.player.isDeleted)
		{
			for (int i = 0; i < hudObjects.size(); i++) 
			{
				hudObjects.get(i).Delete();
			}
		}
		//points are displayed here through the text Renderer
		MainGame.render.DrawText(inputTest + points, new Vector2(270, 230),
				Color.White, 1f);
		
		
		for(GameChar infos : otherInfos)
		{
			infos.Update();
			
			
		}

	}
	
	
	
	
}
