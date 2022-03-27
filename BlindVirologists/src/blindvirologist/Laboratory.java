package blindvirologist;

import java.util.ArrayList;

//a laborat�riumot megval�s�t� oszt�ly
public class Laboratory extends Field
{
	//attrib�tumok
	private Code HiddenCode;
	
	//konstruktor
	public Laboratory(String ID, int NumberOfSides) 
	{
		super(ID, NumberOfSides);
	}
	
	//met�dusok

	/**
	 * A mez�n tal�lhat� k�dot adja vissza egy (1 elem�) list�ban
	 */
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception 
	{
		ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
		collectibles.add(HiddenCode);
		return collectibles;
	}

	/**
	 * A mez�n tal�lhat� k�dot adja vissza
	 */
	@Override
	public Collectible GetCollectible(String Name) throws Exception 
	{
		return HiddenCode;
	}

	@Override
	public String GetType() 
	{
		return "Laboratory";
	}
}
