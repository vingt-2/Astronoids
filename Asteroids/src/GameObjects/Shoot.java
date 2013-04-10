package GameObjects;

import Game.MainGame;
import GameComponents.Transform;
import GameComponents.ObjectRenderer.Shape;
import GameComponents.RigidBody.ForceMode;
import Maths.Vector2;



public class Shoot extends GameObject{


	public float deltaTime = MainGame.render.deltaTime;



	public Transform transform;
	public int i = 0;
	private Laser[] lasers;	
	public boolean isTurnedOn = false;

	public long watchmen;
	

	Transform shootTrans;


	public Shoot(Transform transform){

		shootTrans = transform;
		transform = this.transform;
		lasers = new Laser[100];
	}


	public void Update(){

		
		if(System.currentTimeMillis()-watchmen>deltaTime){

		if(isTurnedOn){

			//temp fix for Texture rendering in middle of screen
			lasers[i%100] = new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) Math.PI/2);
			lasers[i%100].objectRenderer.shape= Shape.Square;
			if(shootTrans.equals(MainGame.player.transform))
				lasers[i%100].objectRenderer.SetTexture("redLaser");
			else
				lasers[i%100].objectRenderer.SetTexture("greenLaser");
			lasers[i%100].transform.size= new Vector2(0.75f,0.75f);
			if( true){
				i++;
				lasers[i%100] = new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) Math.PI/4);
				lasers[i%100].objectRenderer.shape= Shape.Square;
				lasers[i%100].objectRenderer.SetTexture("redLaser");
				lasers[i%100].transform.size= new Vector2(0.75f,0.75f);
				i++;
				lasers[i%100] = new Laser(new Vector2 (1000000f,10000000f), shootTrans, (float) (3*Math.PI/4) );
				lasers[i%100].objectRenderer.shape= Shape.Square;
				lasers[i%100].objectRenderer.SetTexture("redLaser");
				lasers[i%100].transform.size= new Vector2(0.75f,0.75f);
			}
		}

		for (int j = 0; j<lasers.length; j++){

			if (lasers[j] != null){

				if( lasers[j].TimeToDie() )
				{
					lasers[j].Delete();
					lasers[j] = null;
					TurnOff();
				}

				else lasers[j].Update();
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

	public Laser[] GetLaserArray(){

		return lasers;
	}
}


