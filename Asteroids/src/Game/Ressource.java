package Game;

import Game.SharedRessources.RessourceType;

public class Ressource
{
	public RessourceType type;
	public String name;
	public String filePath;
	
	public Ressource(String name, String filePath,RessourceType type)
	{
		this.type = type;
		this.name = name;
		this.filePath = filePath;
	}
	
}