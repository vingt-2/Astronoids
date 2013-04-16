package GameObjects;


import Game.MainGame;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

/**
 * Particle Game Object
 * 
 *	<p>Particle extends just float into the air until it's time for them to die.
 *	They also are affected by the player movement so that the smoke will spreads out when the player
 *	moves into it.
 * </p>
 * 
 * @author Vincent Petrella
 */

public class Particles extends GameChar
{
	long creationTime;
	long lifeTime;
	

	public Particles(int lifetime,Vector2 position)
	{
		super(position);
		lifeTime = lifetime;

		creationTime = System.currentTimeMillis();
	}

	public void Update()
	{  
		super.Update();
		long currentTime = System.currentTimeMillis();
		long timeLived = currentTime - creationTime;
		
		objectRenderer.opacity = 1f - (float) timeLived/lifeTime;
		
		Vector2 toPlayer = Vector2.Add(MainGame.gameLogic.player.transform.position,transform.position.negate());

		//MainGame.debug.DrawLine(transform.position,toPlayer,toPlayer.GetLength());

		if(toPlayer.GetLength() < 100)
		{
			rigidBody.PushForce(toPlayer.Normalized().Scaled(-30*MainGame.gameLogic.player.rigidBody.velocity.GetLength()), ForceMode.Impulse);

			if(toPlayer.GetLength() < 50)
			{
				objectRenderer.opacity *= toPlayer.GetLength()/50;
			}
		}
	}

	public boolean TimeToDie()
	{
		long currentTime = System.currentTimeMillis();

		if(currentTime - creationTime > lifeTime)
		{
			return true;
		}

		return false;
	}

}
