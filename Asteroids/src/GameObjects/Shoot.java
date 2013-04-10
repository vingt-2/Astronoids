package GameObjects;

import java.util.ArrayList;

import Game.MainGame;
import GameComponents.Transform;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;



public class Shoot extends GameObject{


	public float deltaTime = MainGame.render.deltaTime;



	public Transform transform;
	public int i = 0;
	private ArrayList<Laser> lasers;	
	public boolean isTurnedOn = false;
	public static int counter = 0;
	public long watchmen;
	

	Transform shootTrans;


	public Shoot(Transform transform){

		shootTrans = transform;
		transform = this.transform;
		lasers = new ArrayList<Laser>();
	}


	public void Update(){

		
		if(System.currentTimeMillis()-watchmen>deltaTime){
			
		if(isTurnedOn && counter == 0){
			
			//temp fix for Texture rendering in middle of screen
			Laser laser1 = new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) Math.PI/2);
			lasers.add(laser1);
			int l1Index = lasers.indexOf(laser1);
			lasers.get(l1Index).objectRenderer.shape= Shape.Square;
			if(shootTrans.equals(MainGame.player.transform))
				lasers.get(l1Index).objectRenderer.SetTexture("redLaser");
			else
				lasers.get(l1Index).objectRenderer.SetTexture("greenLaser");
			lasers.get(l1Index).transform.size= new Vector2(0.75f,0.75f);
			
//				i++;
//				lasers.add( new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) Math.PI/4));
//				lasers.get(i).objectRenderer.shape= Shape.Square;
//				lasers.get(i).objectRenderer.SetTexture("redLaser");
//				lasers.get(i).transform.size= new Vector2(0.75f,0.75f);
//				i++;
//				lasers.add(new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) (3*Math.PI/4) ));
//				lasers.get(i).objectRenderer.shape= Shape.Square;
//				lasers.get(i).objectRenderer.SetTexture("redLaser");
//				lasers.get(i).transform.size= new Vector2(0.75f,0.75f);
//			
			counter++;
		}

		for (int j = 0; j<lasers.size(); j++){

			if (j<lasers.size()){

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


	public ArrayList<Laser> GetLaserArray() {
		// TODO Auto-generated method stub
		return lasers;
	}
	
}


