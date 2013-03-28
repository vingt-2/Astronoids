package GameComponents;


import java.util.ArrayList;

import GameComponents.ObjectRenderer.Shape;
import GameObjects.GameChar;
import Helpers.Debug;
import Maths.*;
import Renderer.Renderer;

public class RigidBody 
{
	public static final float deltaTime= 1f/(float)Renderer.RERFRESH_RATE;
	
	//	currently 1 unit = 1 pixel on the original window size.
	
	public float mass = 1f;
	public float frictionCoefficient = 0.05f;		// A force: pixel/s^2
	public CollisionShape collider = CollisionShape.Square;
	
	// Accessible and modifiable Physics States.
	public Vector2 acceleration = Vector2.zero();	// A sum of forces : pixel/s^2
	public Vector2 velocity = Vector2.zero();		// pixel/s
	public float angularVelocity = 0;				// rad/s 	
	public float angularAcceleration = 0;
	
	
	public static Debug debug = new Debug();
	private GameChar parent;
	private ArrayList<Forces> forcesList;
	private ArrayList<Torque> torqueList;
	private Vector2 previousPosition;
	private float previousRotation = 0;
	
	public RigidBody(GameChar parent)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Forces>();
		this.torqueList = new ArrayList<Torque>();

		previousPosition = parent.transform.position;
		
	}

	public RigidBody(GameChar parent, float mass)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Forces>();
		this.torqueList = new ArrayList<Torque>();
		previousPosition = parent.transform.position;
		this.mass = mass;
		
	}
	
	/**
	 * Calls all Updates in the right order
	 */
	
	public void Update()
	{
		UpdateVelocity();
		UpdateAcceleration();
		UpdateTransform();
	}
	
	/**
	 * Calculate Acceleration dP^2 and angular Acceleration (around "depth" axis) dw^2
	 */
	
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
	
	/**
	 * Calculate velocity dP and angular velocity dw of the RigidBody 
	 */
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
	
	/**
	 * Update the Transform associated to the RigidBody
	 * According to sir Newton's laws of physics.
	 */
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
	
	/**
	 * Applies a force to the rigidBody.
	 * A force is a Vector2, currently in Pixel/s^2
	 * 
	 * 
	 * @param force
	 * @param mode
	 */
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
	
	/**
	 * Applies a torque to the RigidBody around the "depth" axis 
	 * 
	 * A torque force is in Rad/s^2.
	 * 
	 * @param torqueValue
	 * @param mode
	 */
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
	
	/**
	 * Removes a force from the force list.
	 * 
	 * @param force
	 */
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
	
	/**
	 * Removes torque a force from the torque forces list.
	 * 
	 * @param torque
	 */
	
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
	
	/**
	 * Sets Position keeping rigidBody's velocity
	 * 
	 * @param position
	 */
	
	public void SetPosition(Vector2 position)
	{
		Transform transform = parent.transform;
		transform.position = position;
		// Dont loose your dP!
		previousPosition = Vector2.Add(position,velocity.negate());
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
	
	
		
		
	
	
	public enum CollisionShape
	{
		Square (
				new Vector2[]
					{
						new Vector2(-10f,-10f), new Vector2(10f,-10f),
						new Vector2(10f,10f), new Vector2(-10f,10f) 
					}
		),		
		
		Triangle (
				
				new Vector2[] {new Vector2(-10f, -10f), new Vector2(10f, -10f), new Vector2(0,10)}
						
				),
				
		HexAngle(
				
				new Vector2[] {new Vector2(-10,-4),new Vector2(1,-10),new Vector2(10,-10),new Vector2(10,4),new Vector2(-1,10),new Vector2(-10,10)}
				);
		
		
		
		
		public Vector2[] vertices;
		CollisionShape(Vector2[] vertices)
		{
			this.vertices = vertices;
		}
		
		
		
	
	}
	
public boolean isColliding(GameChar char1, GameChar char2){
		
		float rotation = char1.transform.rotation;
		
		Vector2[] box1 = makeBox(char1);
		Vector2[] box2 = makeBox(char2);
		
		for (int i =0; i<box1.length; i++){
			//System.out.println(box1[i].x);
			//System.out.println(box1[i].y);
			//System.out.println(box2[i].x);
			//System.out.println(box2[i].y);
		}
		
		for(int i= 0; i<4; i++){
			for (int j = 0; j<4;j++){
				if(i!=j)
						if(intersects(box1[i],box1[(i+1)%4],box2[j],box2[(j+1)%4])) return true;
					
				}
		}
		

		for (int i =0; i<box1.length; i++){
			//System.out.println(box1[i].x);
			//System.out.println(box1[i].y);
			//System.out.println(box2[i].x);
			//System.out.println(box2[i].y);
		
		debug.DrawRay(box1[i],box1[(i+1)%4]);
		debug.DrawRay(box2[i],box2[(i+1)%4]);
		}
		return false;
		
	}
	
	public boolean intersects(Vector2 a1, Vector2 a2, Vector2 b1, Vector2 b2){
		
		return java.awt.geom.Line2D.linesIntersect((double)a1.x,(double)a1.y,(double)a2.x,(double)a2.y,(double)b1.x,(double)b1.y,(double)b2.x,(double)b2.y);
		
	}
	
	public boolean isColliding(){
		
		CollisionShape shape = parent.rigidBody.collider;
		
		if (shape.equals(Shape.Square)){
			Vector2[] box = makeBox(parent);
		}
		
		
		
		return false;
	}

	public Vector2[] makeBox(GameChar char1){
		
		Vector2 position1 = char1.transform.position;
		Vector2 size1 = char1.transform.size;
		float r = char1.transform.rotation;
		
		Vector2[] box = {new Vector2(position1.x+(-10)*size1.x*(float)Math.sin(r)-(-10)*(float)Math.cos(r)*size1.y, position1.y+(-10)*size1.x*(float)Math.cos(r)+(-10)*(float)Math.sin(r)*size1.y),
				new Vector2(position1.x+(10)*size1.x*(float)Math.sin(r)-(-10)*(float)Math.cos(r)*size1.y, position1.y+(10)*size1.x*(float)Math.cos(r)+(-10)*(float)Math.sin(r)*size1.y),
				new Vector2(position1.x+(10)*size1.x*(float)Math.sin(r)-(10)*(float)Math.cos(r)*size1.y, position1.y+(10)*size1.x*(float)Math.cos(r)+(10)*(float)Math.sin(r)*size1.y),
				new Vector2(position1.x+(-10)*size1.x*(float)Math.sin(r)-(10)*(float)Math.cos(r)*size1.y, position1.y+(-10)*size1.x*(float)Math.cos(r)+(10)*(float)Math.sin(r)*size1.y)}
				;
		
		return box;
		
		
		
	}
	
	
	

}
