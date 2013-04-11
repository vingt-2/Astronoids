package GameObjects;
import java.awt.event.KeyEvent;

import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Maths.Vector2;


public class Alien extends GameChar{
	float distanceThreshold = 55;
	public Shoot secondEffect = new Shoot(transform);
	long lastTime = 0;
	final static long effectTimeThreshold = 1000; // wait 200ms to toggle effect

		public Alien()
		{
			super();
			objectRenderer.shape= Shape.Square;
			objectRenderer.SetTexture("Alien");
			
		}
		
		public void Update()
		{
			super.Update();
			
			
			// Alien Stuff
			AlienAI();

			
		}
		
		private void AlienAI()
		{
			Vector2 forward= transform.LocalDirectionToWorld(new Vector2(0,1)).Normalized();
			
			Vector2 direction = Vector2.Add(MainGame.gameLogic.player.transform.position, transform.position.negate());
			
			Vector2 xDir= MainGame.gameLogic.player.transform.LocalDirectionToWorld(new Vector2(-1,0));
			int distanceThreshold=30;

			float rotationSign = (float) sign(Vector2.Dot(xDir, direction.Normalized()));
			
			
			float alignment = Vector2.Dot(forward, direction.Normalized());
		
			
			if ( alignment < 0.90f)
			{
				if(alignment < 0.70f)
				{
					rigidBody.PushTorque(rotationSign*10*(1-alignment),ForceMode.Impulse);
					MainGame.debug.DrawLine(transform.position, direction, direction.GetLength(),Color.Blue);	
				}
				else
				{
					rigidBody.PushTorque(rotationSign*10* ((1-alignment)), ForceMode.Impulse);
					
					
					if(direction.GetLength() > distanceThreshold)
					{
						rigidBody.PushForce(Vector2.Scale(100,forward), ForceMode.Impulse);
					}
					
					MainGame.debug.DrawLine(transform.position, direction, direction.GetLength(),Color.Red);
				}
			}
			else
			{
				if(direction.GetLength() > distanceThreshold)
				{
					rigidBody.PushForce(Vector2.Scale(200*alignment,forward), ForceMode.Impulse);
				}
				MainGame.debug.DrawLine(transform.position, direction, direction.GetLength(),Color.Green);
				
			}		
		}
			
		public float sign(float sign)
		{

			if(sign > 0)
				return 1;
			else 
				return -1;
			
		}
		
	
}