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
			
			Vector2 charFrontInWorldCoordinates = transform.LocalDirectionToWorld(new Vector2(0,1)).Normalized();
			//MainGame.debug.DrawLine(transform.position,charFrontInWorldCoordinates,100,Color.Blue);
			
		}
		
		private void AlienAI()
		{
			Vector2 forward= transform.LocalDirectionToWorld(new Vector2(0,1)).Normalized();
			Vector2 direction = Vector2.Add(MainGame.player.transform.position, transform.position.negate());
			Vector2 xDir= MainGame.player.transform.LocalDirectionToWorld(new Vector2(1,0)).Normalized();
			int distanceThreshold=30;
			//double k= Math.sqrt(Math.pow((MainGame.player.transform.position.x - transform.position.x),2)+Math.pow((MainGame.player.transform.position.y - transform.position.y),2));
			float dotProduct= Vector2.Dot(xDir, direction);
		
			
			if (direction.GetLength() > distanceThreshold){
				rigidBody.PushForce(Vector2.Scale(100, direction.Normalized()),ForceMode.Impulse);
			
			if (Vector2.Dot(forward, direction) < 0.90f){
	
				rigidBody.PushTorque((float)(sign(dotProduct)*13), ForceMode.Impulse);
			
				if (Vector2.Dot(forward, direction)> 0.80f){
					
					rigidBody.PushTorque((float)(sign(dotProduct)*(7*(1-Vector2.Dot(forward, direction))/(0.25))), ForceMode.Impulse);
					
				}
			}
			}
			
		}
			
		public double sign(double sign){

			if (sign < 0){
				sign= 1;
			}
			else {
				sign= - 1;
			}
			return sign;
		}
		
	
}