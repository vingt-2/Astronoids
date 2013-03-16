package GameComponents;

import java.util.ArrayList;
import GameObjects.GameChar;
import Maths.*;
import Renderer.Renderer;

public class RigidBody 
{
	public static final float deltaTime = 1/Renderer.RERFRESH_RATE;
	
	public float mass = 0.0001f;
	
	private GameChar parent;
	private ArrayList<Vector2> forcesList;
	private Vector2 previousPosition = Vector2.zero;
	
	
	public RigidBody(GameChar parent)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Vector2>();
	}

	public RigidBody(GameChar parent, float mass)
	{
		this.parent = parent;
		this.forcesList = new ArrayList<Vector2>();
		this.mass = mass;
	}
	
	public void UpdateState()
	{	
		Transform transform = parent.transform;
		Vector2 sumForces = this.GetSumForces();
		Vector2 temp = transform.position;
		
		
		//Newton integration P = P + dP + acceleration * dt^2
		transform.position.x += (transform.position.x-previousPosition.x) + sumForces.x/mass *deltaTime*deltaTime;
		transform.position.y += (transform.position.y-previousPosition.y) + sumForces.y/mass *deltaTime*deltaTime;
		
		previousPosition = temp;
	}
	
	public void pushForce(Vector2 force)
	{
		boolean isThere = false;
		for(int i=0; i<forcesList.size();i++)
		{
			if(forcesList.get(i).x==force.x && forcesList.get(i).y==force.y) isThere=true;
		}
		if(!isThere) forcesList.add(force);
	}
	
	public void removeForce(Vector2 force)
	{
		for(int i=0; i<forcesList.size();i++)
		{
			if(forcesList.get(i).x==force.x && forcesList.get(i).y==force.y) forcesList.remove(i);
		}
	}
	
	// LETS THINK ABOUT FRICTION WOULDYA ?
	
	private Vector2 GetSumForces()
	{
		Vector2 sumForces = new Vector2(0,0);
		for(int i=0;i<forcesList.size();i++)
		{
			sumForces.x += forcesList.get(i).x;
			sumForces.y += forcesList.get(i).y;
		}
		
		return sumForces;
	}

}
