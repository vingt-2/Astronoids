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
	
	//	currently 1 unit = 1 pixel on the original window size.
	
	public float mass = 1f;
	public float frictionCoefficient = 0.05f;		// A force: pixel/s^2
	
	// Accessible and modifiable Physics States.
	public Vector2 acceleration = Vector2.zero();	// A sum of forces : pixel/s^2
	public Vector2 velocity = Vector2.zero();		// pixel/s
	public float angularVelocity = 0;				// rad/s 	
	public float angularAcceleration = 0;
	
	private GameChar parent;
	private ArrayList<Forces> forcesList;
	private ArrayList<Torque> torqueList;
	private Vector2 previousPosition = Vector2.zero();
	private float previousRotation = 0;
	
	public RigidBody(GameChar parent)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Forces>();
		this.torqueList = new ArrayList<Torque>();
	}

	public RigidBody(GameChar parent, float mass)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Forces>();
		this.torqueList = new ArrayList<Torque>();
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
		
		float newAngularAcc = 0;
		
		for(int i=0;i<torqueList.size();i++)
		{
			newAngularAcc += torqueList.get(i).torqueVal / mass;
			if(torqueList.get(i).mode == ForceMode.Impulse)
			{
				RemoveTorque(torqueList.get(i));
			}
		}
		angularAcceleration = newAngularAcc;
		
	}
	
	private void UpdateVelocity()
	{
		Vector2 currentPosition = new Vector2(parent.transform.position);
		Vector2 newVelocity = Vector2.Add(currentPosition,previousPosition.negate());
		previousPosition = currentPosition;
		
		float currentRotation = parent.transform.rotation;
		angularVelocity = currentRotation - previousRotation;
		previousRotation = currentRotation;
		
		velocity = newVelocity;
	}
	
	private void UpdateTransform()
	{	
		Transform transform = parent.transform;
		
		Vector2 friction = Vector2.Scale(-frictionCoefficient,velocity);
		float angularFriction = -frictionCoefficient*angularVelocity;
		
		//Newton integration P = P + dP + acceleration * dt^2
		transform.position.x += velocity.x + (acceleration.x *deltaTime*deltaTime) + friction.x;
		transform.position.y += velocity.y + (acceleration.y *deltaTime*deltaTime) + friction.y;
		transform.rotation += angularVelocity + angularAcceleration*deltaTime*deltaTime + angularFriction ;
		
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
	
	public void PushTorque(float torqueValue,ForceMode mode)
	{
		boolean isThere = false;
		Torque newTorque = new Torque(torqueValue,mode);
		for(int i=0; i<torqueList.size();i++)
		{
			if(torqueList.get(i).torqueVal == torqueValue) isThere=true;
		}
		
		if(!isThere) 
		{
			torqueList.add(newTorque);
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
	
	public void RemoveTorque(Torque torque)
	{
		for(int i=0; i<torqueList.size();i++)
		{
			if(torqueList.get(i).torqueVal == torque.torqueVal)
			{
				torqueList.remove(i);
			}
		}
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
	
	private class Torque
	{
		public float torqueVal;
		public ForceMode mode;
		
		public Torque(float torqueVal,ForceMode mode)
		{
			this.torqueVal = torqueVal;
			this.mode = mode;
		}
	}

}
