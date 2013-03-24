package GameObjects;
import java.awt.event.KeyEvent;

import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Maths.Vector2;


public class Alien extends GameChar{
	float distanceThreshold = 15;

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
			MainGame.debug.DrawLine(transform.position,charFrontInWorldCoordinates,100,Color.Blue);
		}
		
		private void AlienAI()
		{
			
			Vector2 distance = Vector2.Add(transform.position, MainGame.player.transform.position.negate());
		
			if(distance.GetLength() > distanceThreshold)
			{
				rigidBody.PushForce(Vector2.Scale(1000, distance.negate().Normalized()),ForceMode.Impulse); 
				
			}
			
			if (MainGame.controls.isPressed(KeyEvent.VK_RIGHT)){
				rigidBody.PushTorque(10, ForceMode.Impulse);
			}
			
			if (MainGame.controls.isPressed(KeyEvent.VK_LEFT)){
				rigidBody.PushTorque(-10, ForceMode.Impulse);
			}
			
		}
			
}