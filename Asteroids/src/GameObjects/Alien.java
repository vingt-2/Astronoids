package GameObjects;
import java.awt.event.KeyEvent;
import java.util.Random;
import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Helpers.SoundEffect;
import Maths.Vector2;


public class Alien extends GameChar{
	float distanceThreshold = 55;
	
	long lastTime = 0;
	long effectTimeThreshold = 1000; // wait 200ms to toggle effect
	String aiType;
	public Shoot alienCannon1;
	public Shoot alienCannon2;
	long lastShootTime = System.currentTimeMillis();
	public long shootTimeThreshold = 200; 
	
	long time = System.currentTimeMillis();
		public Alien(String aiType)
		{
			super();
			objectRenderer.shape = Shape.Square;
			transform.size = new Vector2(2,2);
			this.aiType=aiType;
			objectRenderer.shape= Shape.Square;
			if (aiType.equals("Alien1")){
			objectRenderer.SetTexture("Alien");
			alienCannon1 = new Shoot(transform);
			
			alienCannon1.isAlien1 =true;
			}
			if (aiType.equals("Alien2")){
				objectRenderer.SetTexture("Alien2");
				alienCannon2 = new Shoot(transform);
				
				alienCannon2.isAlien2 =true;
			}
			
			
		}
		
		public void Update()
		{
			super.Update();
			
			time = System.currentTimeMillis();
			
			// Alien Stuff
			if (aiType.equals("Alien1")){
				AlienAI();	
			}
			
			if (aiType.equals("Alien2")){
				AlienAI2();
				
			}

			
		}
		
		private void AlienAI()
		{
			Vector2 forward= transform.LocalDirectionToWorld(new Vector2(0,1)).Normalized();
			
			Vector2 direction = Vector2.Add(MainGame.gameLogic.player.transform.position, transform.position.negate());
			
			Vector2 xDir= MainGame.gameLogic.player.transform.LocalDirectionToWorld(new Vector2(-1,0));
			int distanceThreshold=30;

			float rotationSign = (float) sign(Vector2.Dot(xDir, direction.Normalized()));
			
			
			float alignment = Vector2.Dot(forward, direction.Normalized());
		
			if(alienCannon1 == null){
				alienCannon1 = new Shoot(this.transform);
				
				alienCannon1.isAlien1 =true;
				}
				else alienCannon1.Update();
			
			if ( alignment < 0.90f)
			{
				
				if (alienCannon1 != null){
					if( time - lastShootTime >  1000)
					{
						SoundEffect.SHOOT.play();


						if(!alienCannon1.isTurnedOn)
						{
							alienCannon1.TurnOn();


						}
						Shoot.counter = 0 ;
						lastShootTime = time;
					}
					else alienCannon1.TurnOff();
					
				}
				if(alignment < 0.70f)
				{
					rigidBody.PushTorque(rotationSign*10*(1-alignment),ForceMode.Impulse);
					//MainGame.debug.DrawLine(transform.position, direction, direction.GetLength(),Color.Blue);	
				}
				else
				{
					rigidBody.PushTorque(rotationSign*10* ((1-alignment)), ForceMode.Impulse);
					
					
					if(direction.GetLength() > distanceThreshold)
					{
						rigidBody.PushForce(Vector2.Scale(100,forward), ForceMode.Impulse);
					}
					
					//MainGame.debug.DrawLine(transform.position, direction, direction.GetLength(),Color.Red);
				}
			}
			else
			{
				if(direction.GetLength() > distanceThreshold)
				{
					rigidBody.PushForce(Vector2.Scale(200*alignment,forward), ForceMode.Impulse);
				}
				
				if(alienCannon1 != null && alienCannon1.isTurnedOn){
					alienCannon1.TurnOff();
				}
				//MainGame.debug.DrawLine(transform.position, direction, direction.GetLength(),Color.Green);
				
			}		
		}
		
private void AlienAI2(){
			
		if(alienCannon2 == null){
			alienCannon2 = new Shoot(this.transform);
			alienCannon2.isAlien2 =true;
			
			
			//System.out.println("Shoot created");
		}
		
		if( time - lastShootTime >  shootTimeThreshold)
		{
			SoundEffect.SHOOT.play();


			if(!alienCannon2.isTurnedOn)
			{
				alienCannon2.TurnOn();


			}
			Shoot.counter = 0 ;
			lastShootTime = time;
		}
		
		else alienCannon2.Update();
		
			Random rand = new Random();
			int randPosX;
			int randPosY;
			int randSign;
			//Random rand = new Random();
			randPosX = rand.nextInt(1000)-750;
			randPosY = rand.nextInt(1000)-500;
			// Generated random Sign
			randSign = rand.nextBoolean() ? -1 : 1;
			//rigidBody.SetPosition(new Vector2(randPosX*randSign, randPosY* randSign ));
			Vector2 direction = Vector2.Add(MainGame.gameLogic.player.transform.position, transform.position.negate());
			Vector2 testAI= Vector2.Add(MainGame.gameLogic.player.transform.position, transform.position.negate());
			rigidBody.PushForce(testAI, ForceMode.Impulse);
			rigidBody.PushTorque(randPosX/10, ForceMode.Impulse);
			//rigidBody.PushTorque(randPosY*randSign, ForceMode.Impulse);
			
			
		}
			
		public float sign(float sign)
		{

			if(sign > 0)
				return 1;
			else 
				return -1;
			
		}
		
	
}