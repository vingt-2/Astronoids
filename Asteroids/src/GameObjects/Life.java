package GameObjects;

import Game.MainGame;
import Helpers.SoundEffect;
import Maths.Vector2;

/** 
 * Life PickUp class, gives player an extra life upon pickUp 
 *  
 * @author Damien Doucet-Girard
 *
 */

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
		SoundEffect.NEWLIFE.play();
	}

}
