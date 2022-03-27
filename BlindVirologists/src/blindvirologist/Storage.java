package blindvirologist;

import java.util.ArrayList;

public class Storage extends Field
{
	//attribútumok
	private ArrayList<Collectible> Materials; //VÁLTOZTATÁS

	//konstruktor
	public Storage(String ID, int NumberOfSides) 
	{
		super(ID, NumberOfSides);
	}
	
	//metódusok

	/**
	 * Visszaadja a mezõn lévõ alapanyagok listáját.
	 */
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception 
	{
		return Materials; //nem tud õsosztályra castolni valamiért -> Virológusban is lehet majd változtatni kell
	}

	@Override
	public Collectible GetCollectible(String Name) throws Exception 
	{
		Material foundMaterial = null;
		for(Collectible c : Materials)
		{
			if(c.GetName().equals(Name))
			{
				foundMaterial=(Material)c; //ráerõltetett konverzió
				break;
			}
		}
		return foundMaterial;
	}

	@Override
	public String GetType() 
	{
		return "Storage";
	}

}
