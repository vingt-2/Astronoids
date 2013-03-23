package GameObjects;

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
