package blindvirologist;

import java.util.ArrayList;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

public class Sack extends Equipment
{
	//attrib�tumok
	private ArrayList<Material> Materials;	
	private static int maxcapacity = 3;

	//konstruktor
	public Sack(String Name) 
	{
		super(Name);
		Materials = new ArrayList<Material>();
	}
	
	//met�dusok

	/**
	 * Nincs hat�sa
	 */
	@Override
	public void Effect() throws VirusBlockedException, GloveException 
	{
	}

	@Override
	public String GetType() 
	{
		return "Sack";
	}
	
	/**
	 * Belerak annyi, a param�terk�nt kapott alapanyagot a zs�kba, amennyit param�terk�nt kapott
	 * Ami nem f�r bele, azt m�r nem rakja el
	 * @param newMaterial a zs�kba ker�l� �j alapanyag
	 * @param number a zs�kba ker�l� alapanyagok sz�ma
	 */
	public void AddMaterial(Material newMaterial, int number)
	{
		for(int i=0; i<number; i++)
		{
			if(Materials.size()==maxcapacity) break;
			Materials.add(newMaterial);
		}
	}
	
	
	/**
	 * Elt�vol�t egy, a param�terk�nt megadott alapanyagot a Materials attrib�tumb�l. Ha ezt a m�veletet nem tudja megcsin�lni, kiv�telt dob.
	 * @param oldMaterial elt�vol�tand� alapanyag
	 * @throws Exception
	 */
	public void RemoveMaterial(Material oldMaterial) throws Exception
	{
		boolean containedIt = Materials.remove(oldMaterial);
		if(!containedIt) throw new Exception();
	}
	
	/**
	 * Visszat�r egy, a param�terk�nt megadott nev� alapanyaggal a Materials list�b�l. Ha nem tal�lja, null �rt�kkel t�r vissza.
	 * @param MaterialName a keresend� alapanyag neve
	 * @return
	 */
	public Material GetMaterial(String MaterialName)
	{
		Material containedMaterial=null;
		for(Material m : Materials)
		{
			if(m.GetName().equals(MaterialName))
			{
				containedMaterial=m;
				break;
			}
		}
		return containedMaterial;
	}
	
	/**
	 * Visszat�r a zs�kban tal�lhat� alapanyagok list�j�val
	 * @return
	 */
	public ArrayList<Material> GetMaterials()
	{
		return Materials;
	}
	
	/**
	 * Visszat�r a zs�k m�ret�vel
	 * @return
	 */
	public int GetMaxCapacity()
	{
		return maxcapacity;
	}
}
