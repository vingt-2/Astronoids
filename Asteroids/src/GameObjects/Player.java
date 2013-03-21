package GameObjects;

import java.awt.event.KeyEvent;
import java.util.Random;

import Game.MainGame;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

public class Player extends GameChar 
{
	public Player()
	{
		super();
	}
	
	public void Update()
	{
		super.Update();
		
		// Player Stuff
		PlayerControls();
	}
	
	private void PlayerControls()
	{
		if(MainGame.controls.isPressed(KeyEvent.VK_RIGHT))
		{
			rigidBody.PushTorque(10,ForceMode.Impulse);
		}
		if(MainGame.controls.isPressed(KeyEvent.VK_LEFT))
		{
			rigidBody.PushTorque(-10,ForceMode.Impulse);
		}
		if(MainGame.controls.isPressed(KeyEvent.VK_UP))
		{
			Vector2 objectFrontInWorldCoordinates = transform.LocalDirectionToWorld(new Vector2(0,1));
			rigidBody.PushForce(Vector2.Scale(1000, objectFrontInWorldCoordinates),ForceMode.Impulse);
		}
		
		if(MainGame.controls.isPressed(KeyEvent.VK_SPACE))
		{
			Random ran = new Random();
			rigidBody.PushForce(new Vector2((ran.nextInt(20)-10)*1000,(ran.nextInt(20)-10)*1000),ForceMode.Impulse);
			rigidBody.PushTorque((ran.nextInt(20) -10) * 10, ForceMode.Impulse);
		}
	}
	
}
