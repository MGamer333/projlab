package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

public class Glove extends Equipment
{

	//konstruktor
	public Glove(String Name) 
	{
		super(Name);
	}
	
	
	//metódusok
	
	/**
	 * @throws GloveException
	 */
	@Override
	public void Effect() throws GloveException
	{
		throw new GloveException("");
	}

	@Override
	public String GetType() 
	{
		return "Glove";
	}

}
