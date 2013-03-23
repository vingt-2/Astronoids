package Game;

import java.util.HashMap;

public class SharedRessources 
{

	HashMap<String,Integer> textures;
	
	public enum RessourceType
	{
		Texture(0);
		
		public int type;
		
		RessourceType(int type)
		{
			this.type = type;
		}
	}
	
	public SharedRessources()
	{
		textures = new HashMap<String,Integer>();
	}
	
	private boolean LoadTexture(String name,String filePath)
	{
		int newTexture = MainGame.render.CreateTexture(filePath);
		if(newTexture != 0)
		{
			textures.put(name,new Integer(newTexture));
			return true;
		}
		return false;
	}
	
	public boolean LoadRessources(Ressource[] ressources)
	{
		boolean allGood = true;
		
		for(int i=0; i<ressources.length; i++)
		{
			if( ressources[i].type == RessourceType.Texture )
			{
				allGood = LoadTexture(ressources[i].name,ressources[i].filePath);
			}
		}
		return allGood;
	}
	
	public int GetTexture(String name)
	{
		int textureID = textures.get(name);
		return textureID;
	}
	
}
