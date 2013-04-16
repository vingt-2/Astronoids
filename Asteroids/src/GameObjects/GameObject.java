package GameObjects;

/**
 *	The simplest form of Game entities within the game.
 * <p>	Every game entity should inherit from it </p>
 * 
 * @author Vincent Petrella
 */

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
