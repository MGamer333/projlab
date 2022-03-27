package blindvirologist;

import java.util.ArrayList;

//az óvóhelyet megvalósító osztály
public class Bunker extends Field
{
	public Bunker(String ID, int NumberOfSides, Equipment StartingEquipment) 
	{
		super(ID, NumberOfSides);
		this.AddEquipment(StartingEquipment);
	}

	//konstruktor
	
	//metódusok
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception
	{
		throw new Exception();
	}

	@Override
	public Collectible GetCollectible(String Name) throws Exception
	{
		throw new Exception();
	}

	@Override
	public String GetType() 
	{
		return "Bunker";
	}
	
	

}
