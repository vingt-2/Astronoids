package GameObjects;

import Game.GameLogic;
import Game.MainGame;
import Maths.Vector2;

public class Shield extends PickUp {
	
	public Shield(Vector2 pos) {
		super(pos);
		objectRenderer.SetTexture("Shield");
		
		// TODO Auto-generated constructor stub

		
	}

	@Override
	public void OnPickUp() {
		// TODO Auto-generated method stub
		MainGame.gameLogic.player.isShieldOn = true;
		
	}
	

	

}