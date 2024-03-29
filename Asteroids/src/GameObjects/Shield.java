package GameObjects;

import Game.GameLogic;

import Game.MainGame;
import Helpers.SoundEffect;
import Maths.Vector2;

/** 
 * Shield PickUp class, toggles isShieldOn flag in player on pickUp
 *  
 * @author Damien Doucet-Girard
 *
 */

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
		SoundEffect.SHIELD.play();
		
	}
	

	

}