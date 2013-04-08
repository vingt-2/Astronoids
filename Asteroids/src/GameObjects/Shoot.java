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
	
	public Shoot(Transform transform){
		
		transform = this.transform;
		lasers = new Laser[100];
	}
	
	
	public void Update(){
		
		if(System.currentTimeMillis()-watchmen>deltaTime){
		if(isTurnedOn){
			
			//temp fix for Texture rendering in middle of screen
			lasers[i%100] = new Laser(new Vector2 (1000000f,10000000f));
			lasers[i%100].objectRenderer.shape= Shape.Square;
			lasers[i%100].objectRenderer.SetTexture("Laser");
			lasers[i%100].transform.size= new Vector2(1,1);			
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


