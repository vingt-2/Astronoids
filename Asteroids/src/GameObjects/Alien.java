package GameObjects;
import Game.MainGame;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;


public class Alien extends GameChar{
	float distanceThreshold = 10;

		public Alien()
		{
			super();
		}
		
		public void Update()
		{
			super.Update();
			
			// Player Stuff
			AlienAI();
		}
		
		private void AlienAI()
		{
			
			Vector2 distance = Vector2.Add(transform.position, MainGame.player.transform.position.negate());
			
		
			if(distance.GetLength() > distanceThreshold)
			{
				rigidBody.PushForce(Vector2.Scale(1000, distance.negate().Normalized()),ForceMode.Impulse);
				rigidBody.PushTorque(3,ForceMode.Impulse);
			
			}
			
			
			
		}
			
}