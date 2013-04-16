package GameObjects;


import java.util.ArrayList;


import Game.MainGame;
import GameComponents.Transform;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;

/**
 * Handles the generation and destruction of lasers in accordance to a general boolean flag isTurnedOn
 *
 * @author Damien Doucet-Girard
 * @author Chi-Wing Sit
 * @author Vincent Petrella
 */
public class Shoot extends GameObject
{
	/**
	 * Declaring and initializing booleans and other various class variables
	 * lasers is our array of Laser objects at the core of our shoot class
	 */
	public float deltaTime = MainGame.render.deltaTime;

	public Transform transform;
	public int i = 0;
	
	private ArrayList<Laser> lasers;	
	public boolean isTurnedOn = false;
	public static int counter = 0;
	public long watchmen;
	public boolean isAlien1 = false;
	public boolean isAlien2 = false;

	Transform shootTrans;

	public Shoot(Transform transform)
	{

		shootTrans = transform;
		transform = this.transform;
		lasers = new ArrayList<Laser>();
	}

	public void Update(){
		/**
		 * verifies time to avoid over iteration of update loop
		 * also verifies if isTurnedOn isTurnedOn
		 * ( effect triggered by the keyListener when shoot key is pressed)
		 */
		if(System.currentTimeMillis()-watchmen>deltaTime)
		{
			
		if(isTurnedOn && counter == 0)
		{


			//temp fix for Texture rendering in middle of screen
			Laser laser1 = new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) Math.PI/2);
			//alien lasers differ in mass and lifetime
			if(isAlien1) {
				laser1.rigidBody.mass = 0.001f;
				laser1.lifeTime = 500;
			}
			if(isAlien2) {
				laser1.rigidBody.mass = 100f;
				laser1.lifeTime = 500;
			}
			//creates lasers to add to arrayList
			lasers.add(laser1);
			int l1Index = lasers.indexOf(laser1);
			lasers.get(l1Index).objectRenderer.shape= Shape.Square;
			
			lasers.get(l1Index).objectRenderer.SetTexture("redLaser");
			
			lasers.get(l1Index).transform.size= new Vector2(1.3f,1.3f);




	

				counter++;
			}

		/**
		 * Destroys lasers when they have reached the end of their lifetime
		 */
			for (int j = 0; j<lasers.size(); j++)
			{
	

				if (j<lasers.size())

				{
					if( lasers.get(j).TimeToDie() )
					{
						lasers.get(j).Delete();
						lasers.remove(j);
						TurnOff();
					}
					else lasers.get(j).Update();
				}


			}
		}
		watchmen = System.currentTimeMillis();
		//i++;


	}

	/**
	 * Turns on the shoot effect so laser objects can be created
	 */
	public void TurnOn()
	{
		if( !isTurnedOn )
		{
			isTurnedOn = true;
		}
	}

	/**
	 * Turns off shoot effect
	 */
	public void TurnOff()
	{
		if( isTurnedOn )
		{
			isTurnedOn = false;
		}
	}

	/**
	 * 
	 * returns array of lasers
	 */
	public ArrayList<Laser> GetLaserArray() {
		// TODO Auto-generated method stub
		return lasers;
	}


}
