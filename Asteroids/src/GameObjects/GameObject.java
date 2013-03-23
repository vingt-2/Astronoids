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
