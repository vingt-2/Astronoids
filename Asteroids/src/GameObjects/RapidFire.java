package GameObjects;

import Game.MainGame;
import Helpers.SoundEffect;
import Maths.Vector2;
/** 
 * RapidFire PickUp class, toggles rapidFire flag in Player upon pickUp
 *  
 *  
 * @author Damien Doucet-Girard
 *
 */
public class RapidFire extends PickUp {

	public RapidFire(Vector2 pos) {
		super(pos);
		objectRenderer.SetTexture("RapidFire");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void OnPickUp() {
		// TODO Auto-generated method stub
		MainGame.gameLogic.player.rapidFireOn = true;
		SoundEffect.RAPIDFIRE.play();
	}

}
