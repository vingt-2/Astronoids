package GameObjects;

import Game.MainGame;
import Maths.Vector2;

public class Life extends PickUp {

	public Life(Vector2 pos) {
		super(pos);
		objectRenderer.SetTexture("Life");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void OnPickUp() {
		// TODO Auto-generated method stub
		MainGame.gameLogic.player.lives++;
	}

}
