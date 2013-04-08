package GameObjects;

import java.util.ArrayList;

import Game.GameLogic;
import Game.MainGame;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Maths.Vector2;
import Renderer.Renderer;

public class HUD extends GameChar {

	public ArrayList<LifeDisplay> hudObjects;
	int counter = 0;
	boolean correct = false;
	public static int points = 0;

	String inputTest = "";

	public HUD() {

		hudObjects = new ArrayList<LifeDisplay>();
		objectRenderer.SetTexture("Empty");

	}

	public void Update() {

		super.Update();

		for (int i = 0; i < MainGame.player.lives; i++) {
			if (hudObjects.size() <= i) {
				hudObjects
						.add(new LifeDisplay(new Vector2((300 + i * 40), 300)));
				hudObjects.get(i).transform.size = (new Vector2(2.5f, 2.5f));

				System.out.println("life");
				hudObjects.get(i).Update();
			}

			if (GameLogic.lostLife) {
				hudObjects.get(i).Delete();
				hudObjects.remove(i);
				GameLogic.lostLife = false;
			}

		}

		if (MainGame.player.isDeleted) {

			for (int i = 0; i < hudObjects.size(); i++) {
				hudObjects.get(i).Delete();
			}

		}

		MainGame.render.DrawText(inputTest + points, new Vector2(270, 230),
				Color.Blue, 1f);

	}

}
