package blindvirologist;

import java.util.ArrayList;

//a laboratóriumot megvalósító osztály
public class Laboratory extends Field
{
	//attribútumok
	private Code HiddenCode;
	
	//konstruktor
	public Laboratory(String ID, int NumberOfSides) 
	{
		super(ID, NumberOfSides);
	}
	
	//metódusok

	/**
	 * A mezõn található kódot adja vissza egy (1 elemû) listában
	 */
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception 
	{
		ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
		collectibles.add(HiddenCode);
		return collectibles;
	}

	/**
	 * A mezõn található kódot adja vissza
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
