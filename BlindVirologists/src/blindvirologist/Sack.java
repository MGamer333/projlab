package blindvirologist;

import java.util.ArrayList;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

public class Sack extends Equipment
{
	//attribútumok
	private ArrayList<Material> Materials;	
	private static int maxcapacity = 3;

	//konstruktor
	public Sack(String Name) 
	{
		super(Name);
		Materials = new ArrayList<Material>();
	}
	
	//metódusok

	/**
	 * Nincs hatása
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
	 * Belerak annyi, a paraméterként kapott alapanyagot a zsákba, amennyit paraméterként kapott
	 * Ami nem fér bele, azt már nem rakja el
	 * @param newMaterial a zsákba kerülõ új alapanyag
	 * @param number a zsákba kerülõ alapanyagok száma
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
	 * Eltávolít egy, a paraméterként megadott alapanyagot a Materials attribútumból. Ha ezt a mûveletet nem tudja megcsinálni, kivételt dob.
	 * @param oldMaterial eltávolítandó alapanyag
	 * @throws Exception
	 */
	public void RemoveMaterial(Material oldMaterial) throws Exception
	{
		boolean containedIt = Materials.remove(oldMaterial);
		if(!containedIt) throw new Exception();
	}
	
	/**
	 * Visszatér egy, a paraméterként megadott nevû alapanyaggal a Materials listából. Ha nem találja, null értékkel tér vissza.
	 * @param MaterialName a keresendõ alapanyag neve
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
	 * Visszatér a zsákban található alapanyagok listájával
	 * @return
	 */
	public ArrayList<Material> GetMaterials()
	{
		return Materials;
	}
	
	/**
	 * Visszatér a zsák méretével
	 * @return
	 */
	public int GetMaxCapacity()
	{
		return maxcapacity;
	}
}
