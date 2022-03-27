package blindvirologist;

import java.util.ArrayList;

public class Storage extends Field
{
	//attrib�tumok
	private ArrayList<Collectible> Materials; //V�LTOZTAT�S

	//konstruktor
	public Storage(String ID, int NumberOfSides) 
	{
		super(ID, NumberOfSides);
	}
	
	//met�dusok

	/**
	 * Visszaadja a mez�n l�v� alapanyagok list�j�t.
	 */
	@Override
	public ArrayList<Collectible> GetCollectibles() throws Exception 
	{
		return Materials; //nem tud �soszt�lyra castolni valami�rt -> Virol�gusban is lehet majd v�ltoztatni kell
	}

	@Override
	public Collectible GetCollectible(String Name) throws Exception 
	{
		Material foundMaterial = null;
		for(Collectible c : Materials)
		{
			if(c.GetName().equals(Name))
			{
				foundMaterial=(Material)c; //r�er�ltetett konverzi�
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
