

package GameObjects;


import java.awt.event.KeyEvent;
import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.SoundEffect;
import Maths.Vector2;

/** 
 * Player Class, handles all player properties, behaviors and effects
 * @author Vincent Petrella 
 * @author Damien Doucet-Girard
 * @author Chi Wing Sit
 */
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
	/**
	 * determines the amount of time between shots
	 */
	public long shootTimeThreshold = 200; 

	/**
	 * COnstructor instantiate player ship texture and number of lives
	 */
	public Player()
	{
		super();
		objectRenderer.shape= Shape.Square;
		objectRenderer.SetTexture("rocket_ship");
		this.lives = 3;
	}

	/**
	 * Updates render, particle effects, shield, Shoot object, rapidFire and controls
	 */
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

	/**
	 * Dictates behavior according to key inputs. 
	 * Pattern currently is up, down, left, right for respective directions
	 * and x to shoot
	 */
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
		
		//up pushes the ship forward and activates afterburn particle effects simultaneously
		if(MainGame.controls.isPressed(KeyEvent.VK_UP))
		{


			if( time - lastPlayerTime > 200)
			{
				SoundEffect.AFTERBURN.play();
				lastPlayerTime = time;
			}

			//player impulse determined by rigidbody
			//so all predetermined physical properties apply to its movement
			Vector2 objectFrontInWorldCoordinates = transform.LocalDirectionToWorld(new Vector2(0,1));
			rigidBody.PushForce(Vector2.Scale(800, objectFrontInWorldCoordinates),ForceMode.Impulse);
			effect.TurnOn();
		}
		else
		{
			effect.TurnOff();
		}

		//shoot activates the player's Shoot object effect, allowing for
		//the creation of laser objects in the shoot class. 
		
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

	/**
	 * creates and renders a shield when the isShieldOn flag is toggled
	 */
	public void shieldOn(){


		if(isShieldOn){
			if (shield == null){
				birthTime = System.currentTimeMillis();
				shield = new GameChar();
				shield.objectRenderer.SetTexture("Shield");
				shield.transform = this.transform;


			}




			else{
				//Allows the shield's opacity to oscillate, giving a cool glowing animation 
				shield.objectRenderer.opacity = (float)((Math.cos(System.currentTimeMillis()/100)+1)/2);
			}

			//Shield Is deleted after 3 seconds
			if(System.currentTimeMillis()-birthTime>3000){
				isShieldOn = false;
				shield.Delete();
				shield = null;


			}


		}
		else{
			//if our shield is not on, but the object is not null, it must be deleted
			if (shield != null){
				shield.Delete();
			}
		}
	}


	/**
	 * Changes the ShootTimeThreshold when the rapidFireOn flag is triggered
	 */
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
	
	/**
	 * Deletes the ship and its particle trail
	 */
	@Override
	public void Delete()
	{
		super.Delete();
		effect.Delete();
	}


}

