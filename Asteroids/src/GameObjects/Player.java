package GameObjects;

import java.awt.event.KeyEvent;
import Game.MainGame;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Helpers.Color;
import Maths.Vector2;

public class Player extends GameChar 
{
	
	ParticleEffects effect = new ParticleEffects(transform,20);
	
	public Player()
	{
		super();
		objectRenderer.shape= Shape.Square;
		objectRenderer.SetTexture("./resources/textures/rocket_ship.png");
	}
	
	public void Update()
	{
		super.Update();
		
		// Player Stuff
		effect.Update();
		PlayerControls();
		
		
		Vector2 charFrontInWorldCoordinates = transform.LocalDirectionToWorld(new Vector2(0,1)).Normalized();
		MainGame.debug.DrawLine(transform.position,charFrontInWorldCoordinates,100,Color.Blue);
		
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
			if(!effect.isTurnedOn)
			{
				effect.TurnOn();
			}
			else				
			{
				effect.TurnOff();
			}
		}
	}
	
}
