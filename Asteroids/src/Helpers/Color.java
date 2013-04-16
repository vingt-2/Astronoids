package Helpers;

/**
 * 
 *	Simple enough, that's some Color enum.
 * @author Vincent Petrella
 */

public enum Color 
{
	Red(1f,0,0),
	Green(0,1f,0),
	Blue(0,0,1f),
	White(1f,1f,1f);
	
	Color(float r, float g, float b)
	{
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
	public float r,g,b;
}
