package blindvirologist;

import java.util.ArrayList;

//az alap mez�t megval�s�t� oszt�ly
public class PlainField extends Field
{
	//konstruktor
	public PlainField(String ID, int NumberOfSides) 
	{
		super(ID, NumberOfSides);
	}
	
	//met�dusok

	/**
	 * Kiv�telt dob, mivel nincs �sszegy�jtend� dolog a mez�n.
	 */
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception 
	{
		throw new Exception("Nincs semmi gy�jtenival� a mez�n");
	}

	/**
	 * Kiv�telt dob, mivel incs �sszegy�jtend� dolog a mez�n.
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
