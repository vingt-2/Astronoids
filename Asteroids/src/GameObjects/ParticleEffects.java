package GameObjects;

import java.util.Random;

import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import GameComponents.Transform;
import Maths.Vector2;

public class ParticleEffects extends GameObject
{
	public boolean isTurnedOn = false;
	public Transform transform;

	private Particles[] particleArray;

	public ParticleEffects(Transform transform,int particlesNumber)
	{
		this.transform = transform;

		particleArray = new Particles[particlesNumber];
	}

	public void Update()
	{
		Random ran = new Random();
		Vector2 back = transform.LocalDirectionToWorld(new Vector2(0,-1)).Normalized();
		for(int i = 0; i < particleArray.length; i++)
		{
			if(particleArray[i] == null)
			{
				if(isTurnedOn)
				{	
					particleArray[i] = new Particles(ran.nextInt(1500)+1000,new Vector2(transform.position.x,transform.position.y));
					particleArray[i].objectRenderer.shape= Shape.Square;
					particleArray[i].objectRenderer.SetTexture("./resources/textures/rocket_ship.png");
					particleArray[i].rigidBody.frictionCoefficient = 0.01f;
					particleArray[i].rigidBody.PushForce(new Vector2((ran.nextInt(20))*300*back.x,(ran.nextInt(20))*1000*back.y),ForceMode.Impulse);
					particleArray[i].rigidBody.PushTorque((ran.nextInt(20) -10) * 10, ForceMode.Impulse);
				}
			}
			else
			{
				if( particleArray[i].TimeToDie() )
				{
					particleArray[i].Delete();
					particleArray[i] = null;
				}
				else
				{
					particleArray[i].Update();
				}
			}
		}
	}
	
	public void TurnOn()
	{
		if( !isTurnedOn )
		{
			isTurnedOn = true;
		}
	}
	
	public void TurnOff()
	{
		if( isTurnedOn )
		{
			isTurnedOn = false;
		}
	}
	
	
}
