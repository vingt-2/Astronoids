package GameComponents;

import java.util.ArrayList;
import GameObjects.GameChar;
import Helpers.Color;
import Helpers.Debug;
import Maths.*;
import Renderer.Renderer;

public class RigidBody 
{
	public static final float deltaTime= 1f/(float)Renderer.RERFRESH_RATE;
	
	public float mass = 1f;
	public float frictionCoefficient = 0.1f;
	public Vector2 velocity = Vector2.zero(); 
	public Vector2 acceleration = Vector2.zero();
	
	private GameChar parent;
	private ArrayList<Forces> forcesList;
	private Vector2 previousPosition = Vector2.zero();
	
	public RigidBody(GameChar parent)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Forces>();
	}

	public RigidBody(GameChar parent, float mass)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Forces>();
		this.mass = mass;
	}
	
	public void Update()
	{
		UpdateVelocity();
		UpdateAcceleration();
		UpdateTransform();
	}
	
	private void UpdateAcceleration()
	{
		Vector2 newAcceleration = new Vector2(0,0);
		
		for(int i=0;i<forcesList.size();i++)
		{
			newAcceleration.x += forcesList.get(i).force.x / mass;
			newAcceleration.y += forcesList.get(i).force.y / mass;
			
			if(forcesList.get(i).mode == ForceMode.Impulse)
			{
				RemoveForce(forcesList.get(i));
			}
		}
		acceleration = newAcceleration;
	}
	
	private void UpdateVelocity()
	{
		Vector2 currentPosition = new Vector2(parent.transform.position);
		Vector2 newVelocity = Vector2.Add(currentPosition,previousPosition.negate());
		previousPosition = currentPosition;
		
		velocity = newVelocity;
	}
	
	private void UpdateTransform()
	{	
		Transform transform = parent.transform;
		
		Vector2 friction;

		
		friction = Vector2.Scale(velocity, -frictionCoefficient);
		
		//Newton integration P = P + dP + acceleration * dt^2
		transform.position.x += velocity.x + (acceleration.x *deltaTime*deltaTime) + friction.x;
		transform.position.y += velocity.y + (acceleration.y *deltaTime*deltaTime) + friction.y;
		
	}
	
	public void PushForce(Vector2 force,ForceMode mode)
	{
		boolean isThere = false;
		Forces newForce = new Forces(force,mode);
		for(int i=0; i<forcesList.size();i++)
		{
			if(forcesList.get(i).force.x==force.x && forcesList.get(i).force.y==force.y) isThere=true;
		}
		
		if(!isThere) 
		{
			forcesList.add(newForce);
		}
	}
	
	public void RemoveForce(Forces force)
	{
		for(int i=0; i<forcesList.size();i++)
		{
			if(forcesList.get(i).force.x==force.force.x && forcesList.get(i).force.y==force.force.y)
			{
				forcesList.remove(i);
			}
		}
	}
	
	public void DrawAcceleration(Debug debug,Color color)
	{
		debug.DrawRay(parent.transform.position,Vector2.Add(parent.transform.position,acceleration),color);
	}
	
	public enum ForceMode
	{
		Impulse(0),
		Force(0);
		
		int mode;
		
		ForceMode(int mode)
		{
			this.mode = mode;
		}
	}

	private class Forces
	{
		public Vector2 force;
		public ForceMode mode;
		
		public Forces(Vector2 force,ForceMode mode)
		{
			this.force = force;
			this.mode = mode;
		}
	}

}
