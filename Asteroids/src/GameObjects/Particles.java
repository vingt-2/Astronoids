package GameObjects;

import Game.MainGame;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

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
		if(currentTime - creationTime > lifeTime - 0.2*lifeTime)
		{
			objectRenderer.opacity -= 0.01f;
			transform.size = transform.size.Scaled(objectRenderer.opacity);
		}

		Vector2 toPlayer = Vector2.Add(MainGame.player.transform.position,transform.position.negate());

		//MainGame.debug.DrawLine(transform.position,toPlayer,toPlayer.GetLength());

		if(toPlayer.GetLength() < 200)
		{
			objectRenderer.opacity = toPlayer.GetLength()/200;
			if(toPlayer.GetLength() < 100)
			{
				rigidBody.PushForce(toPlayer.Normalized().Scaled(-30*MainGame.player.rigidBody.velocity.GetLength()), ForceMode.Impulse);
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
