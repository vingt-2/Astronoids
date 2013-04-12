package GameObjects;

import java.util.Random;

import Game.GameLogic;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import GameComponents.Transform;
import Maths.Vector2;

public class ParticleEffects extends GameObject
{
	public boolean isTurnedOn = false;
	public Transform transform;
	public boolean isShrapnel = false;
	Vector2 particlePos ;
	private Particles[] particleArray;
	long creationTime;
	long lifeTime = 700;

	public ParticleEffects(Transform transform,int maxParticles)
	{
		this.transform = transform;
		creationTime = System.currentTimeMillis();
		particleArray = new Particles[maxParticles];
	}

	public void Update()
	{
		Random ran = new Random();
		Vector2 back = transform.LocalDirectionToWorld(new Vector2(0,-1)).Normalized();
		int shipLength = 15;
		int offset = ran.nextInt(10)-5;

		if(isShrapnel){
			particlePos = transform.LocalPositionToWorld(new Vector2(0+offset,0+offset));
		}
		else{
			particlePos = transform.LocalPositionToWorld(new Vector2(0,-shipLength));
		}
		for(int i = 0; i < particleArray.length; i++)
		{
			if( particleArray[i] == null )
			{
				if(ran.nextInt(1000) % 217 == 0 || isShrapnel )
				{
					if( isTurnedOn )
					{	

						particleArray[i] = new Particles(ran.nextInt(2000), particlePos);

						if(isShrapnel){
							particleArray[i].objectRenderer.SetTexture("Shrapnel");
							particleArray[i].rigidBody.frictionCoefficient = 0.01f;
							particleArray[i].rigidBody.PushForce( new Vector2( (float)(Math.cos(System.currentTimeMillis()))*100,(float)Math.sin(System.currentTimeMillis())*100),ForceMode.Force);
							particleArray[i].transform.size = new Vector2(0.5f,0.5f);
							particleArray[i].rigidBody.PushTorque((ran.nextInt(45) -10), ForceMode.Impulse);
							particleArray[i].objectRenderer.opacity = 0.0f;
						}
						else{
							particleArray[i].objectRenderer.SetTexture("smoke");
							particleArray[i].rigidBody.frictionCoefficient = 0.01f;
							particleArray[i].rigidBody.PushForce(new Vector2((ran.nextInt(20))*15*back.x,(ran.nextInt(20))*50*back.y),ForceMode.Impulse);
							particleArray[i].transform.size = new Vector2((1+ran.nextInt(2)) - 0.075f*ran.nextInt(50));
							particleArray[i].rigidBody.PushTorque((ran.nextInt(20) -10) * 10, ForceMode.Impulse);
							particleArray[i].objectRenderer.opacity = 0.8f;
						}

					}
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

	public boolean TimeToDie()
	{
		long currentTime = System.currentTimeMillis();

		if(currentTime - creationTime > lifeTime)
		{
			return true;
		}

		return false;
	}

	@Override
	public void Delete(){
		for(int i = 0; i < particleArray.length; i++)
		{
			if( particleArray[i] != null )
			{
				particleArray[i].Delete();
			}
		}
	}
}
