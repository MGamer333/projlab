package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a védőfelszereléseket megvalósító absztakt osztály
public abstract class Equipment extends Collectible
{

	//konstruktor
	public Equipment(String Name) 
	{
		super(Name);
	}
	
	//metódusok
	
	/**
	 * A felszerelés működését megvalósító függvény
	 */
	public abstract void Effect() throws VirusBlockedException, GloveException;

}
