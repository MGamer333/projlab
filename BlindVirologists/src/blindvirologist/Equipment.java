package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a védõfelszereléseket megvalósító absztakt osztály
public abstract class Equipment extends Collectible
{

	//konstruktor
	public Equipment(String Name) 
	{
		super(Name);
	}
	
	//metódusok
	
	/**
	 * A felszerelés mûködését megvalósító függvény
	 */
	public abstract void Effect() throws VirusBlockedException, GloveException;

}
