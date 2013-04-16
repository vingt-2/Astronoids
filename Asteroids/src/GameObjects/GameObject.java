/**
 * @author Vincent Petrella
 * 
 * GameObjects:
 * 		The simplest form of Game entities within the game.
 * 		Every game entity should inherit from it
 * 
 */

package GameObjects;

public class GameObject 
{
	public boolean isDeleted = false;
	

	public GameObject()
	{}
	
	public void Delete()
	{
		isDeleted = true;
	}

}
