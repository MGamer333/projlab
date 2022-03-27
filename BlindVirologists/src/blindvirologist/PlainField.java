package blindvirologist;

import java.util.ArrayList;

//az alap mezõt megvalósító osztály
public class PlainField extends Field
{
	//konstruktor
	public PlainField(String ID, int NumberOfSides) 
	{
		super(ID, NumberOfSides);
	}
	
	//metódusok

	/**
	 * Kivételt dob, mivel nincs összegyûjtendõ dolog a mezõn.
	 */
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception 
	{
		throw new Exception("Nincs semmi gyûjtenivaló a mezõn");
	}

	/**
	 * Kivételt dob, mivel incs összegyûjtendõ dolog a mezõn.
	 */
	@Override
	public Collectible GetCollectible(String Name) throws Exception 
	{
		throw new Exception("Nincs itt semmi");
	}

	@Override
	public String GetType() 
	{
		return "Plain";
	}

}
