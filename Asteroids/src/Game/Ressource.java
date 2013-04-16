package Game;

import Game.SharedRessources.RessourceType;

/**
 * Implements Resources type
 * 
 * @author Vincent Petrella
 * 
 * </p>
 * 		Defines what a ressource is:
 * 			<ul><li>It has a name,</li>
 * 			<li>a filepath,</li>
 * 			<li>and a type.</li> </ul>
 * 
 */
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