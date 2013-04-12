

package GameObjects;


import java.awt.event.KeyEvent;
import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.SoundEffect;
import Maths.Vector2;


public class Player extends GameChar 
{

	ParticleEffects effect = new ParticleEffects(transform,2000);
	public Shoot shooter = new Shoot(transform);
	long lastShootTime = System.currentTimeMillis();
	long lastPlayerTime = System.currentTimeMillis();
	long time = System.currentTimeMillis();
	public int lives;
	public boolean isShieldOn;
	public boolean rapidFireOn;
	private GameChar shield;
	private long birthTime;
	private long birthTime2;
	public long shootTimeThreshold = 200; // wait 200ms between two shots.


	public Player()
	{
		super();
		objectRenderer.shape= Shape.Square;
		objectRenderer.SetTexture("rocket_ship");
		this.lives = 3;
	}

	
	public void Update()
	{
		super.Update();

		time = System.currentTimeMillis();

		// Player Stuff
		effect.Update();
		shieldOn();
		shooter.Update();
		rapidFireOn();
		PlayerControls();

	}


	private void PlayerControls()
	{
		if(MainGame.controls.isPressed(KeyEvent.VK_RIGHT))
		{
			rigidBody.PushTorque(15,ForceMode.Impulse);
		}
		if(MainGame.controls.isPressed(KeyEvent.VK_LEFT))
		{
			rigidBody.PushTorque(-15,ForceMode.Impulse);
		}
		if(MainGame.controls.isPressed(KeyEvent.VK_UP))
		{


			if( time - lastPlayerTime > 200)
			{
				SoundEffect.AFTERBURN.play();
				lastPlayerTime = time;
			}


			Vector2 objectFrontInWorldCoordinates = transform.LocalDirectionToWorld(new Vector2(0,1));
			rigidBody.PushForce(Vector2.Scale(500, objectFrontInWorldCoordinates),ForceMode.Impulse);
			effect.TurnOn();
		}
		else
		{
			effect.TurnOff();
		}


		if(MainGame.controls.isPressed(KeyEvent.VK_X))
		{
			shooter.TurnOff();
			if( time - lastShootTime >  shootTimeThreshold)
			{
				SoundEffect.SHOOT.play();


				if(!shooter.isTurnedOn)
				{
					shooter.TurnOn();
				}
				Shoot.counter = 0 ;
				lastShootTime = time;
			}
		}

	}


	public void shieldOn(){


		if(isShieldOn){
			if (shield == null){
				birthTime = System.currentTimeMillis();
				shield = new GameChar();
				shield.objectRenderer.SetTexture("Shield");
				shield.transform = this.transform;


			}




			else{
				shield.objectRenderer.opacity = (float)((Math.cos(System.currentTimeMillis()/100)+1)/2);
			}


			if(System.currentTimeMillis()-birthTime>3000){
				isShieldOn = false;
				shield.Delete();
				shield = null;


			}


		}
		else{
			if (shield != null){
				shield.Delete();
			}
		}
	}


	public void rapidFireOn(){


		if(rapidFireOn){
			birthTime2 = System.currentTimeMillis();
			shootTimeThreshold = 125;
			rapidFireOn = false;
		}


		if(System.currentTimeMillis()-birthTime2 > 3000){
			shootTimeThreshold = 200;
		}


	}
	
	@Override
	public void Delete()
	{
		super.Delete();
		effect.Delete();
	}


}

