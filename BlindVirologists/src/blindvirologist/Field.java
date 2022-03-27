package blindvirologist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//a mezõk oldalszámát megvalósító enum
enum SideNumber //TODO ellenõrzés
{
	THREE(3),
	FOUR(4),
	SIX(6);
	
	private int sideIndex;
	//private static Map<Integer, SideNumber> map = new HashMap<Integer, SideNumber>();
	
	private SideNumber(int n)
	{
		this.sideIndex=n;
	}
	
	
	public int value()
	{
		return sideIndex;
	}
	
	// https://stackoverflow.com/questions/11047756/getting-enum-associated-with-int-value
	public static SideNumber getSideNumber(int number)
	{
		for(SideNumber s : SideNumber.values())
		{
			if(s.sideIndex==number) return s;
		}
		throw new IllegalArgumentException("Not valid side number");
	}
	
}

//a mezõt megvalósító absztrakt osztály
public abstract class Field 
{
	//adattagok felvétele
	private String ID;
	private ArrayList<Field> Neighbors;
	private SideNumber NumberOfSides;
	private ArrayList<Equipment> EquipmentList;
	private ArrayList<Virologist> Virologists;
	
	//konstruktor
	public Field(String ID, int NumberOfSides)
	{
		this.ID=ID;
		Neighbors=new ArrayList<Field>();
		this.NumberOfSides=SideNumber.getSideNumber(NumberOfSides);
		EquipmentList=new ArrayList<Equipment>();
		Virologists = new ArrayList<Virologist>();
	}
	
	//metódusok
	/**
	 * Visszaadja a mezõn található összegyûjthetõ dolgok listáját.
	 * @return ArrayList<Collectible>
	 */
	abstract public ArrayList<Collectible> GetCollectibles() throws Exception;
	
	/**
	 * Összegyûjti a mezõn található, paraméterben megadott nevû dolgot.
	 * @param Name Az összegyûjthetõ dolog neve
	 * @return Collectible
	 */
	abstract public Collectible GetCollectible(String Name) throws Exception;
	
	/**
	 * Visszatér a mezõ egyedi azonosítójával.
	 * @return ID
	 */
	public String GetID()
	{
		return ID;
	}
	
	/**
	 * Megvizsgálja, hogy a paraméterként kapott mezõ szomszédja-e.
	 * @param UnknownField
	 * @return true ha szomszédja
	 */
	public boolean IsNeighbor(Field UnknownField)
	{
		boolean neighbor=false;
		for(Field f : Neighbors)
		{
			if(UnknownField==f)
			{
				neighbor=true;
				break;
			}
		}
		return neighbor;
	}
	
	/**
	 * Hozzáadja a paraméterként kapott mezõt a szomszédainak listájához
	 * @param NewNeighbor
	 * @throws Exception ha túllépné a lehetséges szomszédok számát
	 */
	public void AddNeighbor(Field NewNeighbor) throws Exception
	{
		//ha túl sok lenne a szomszéd, kivételt dob
		if(this.NumberOfSides.value()<=(Neighbors.size())) throw new Exception(); //ezt tesztelni kell
		Neighbors.add(NewNeighbor);
	}
	
	/**
	 * Visszaadja a mezõ szomszédainak listáját.
	 * @return
	 */
	public ArrayList<Field> GetNeighbors()
	{
		return Neighbors;
	}
	
	/**
	 * Elhelyez egy új védõfelszerelést a mezõn
	 * @param NewEquipment
	 */
	public void AddEquipment(Equipment NewEquipment)
	{
		EquipmentList.add(NewEquipment);
	}
	
	/**
	 * Elhelyez egy új virológust a mezõn
	 * @param NewVirologist
	 */
	public void AddVirologist(Virologist NewVirologist)
	{
		Virologists.add(NewVirologist);
	}
	
	/**
	 * Eltávolítja a paraméterként átadott virológust a mezõrõl.
	 * @param OldVirologist a mezõn található, eltávalítandó virológus
	 * @throws Exception kivételt dob, ha nem találja
	 */
	public void RemoveVirologist(Virologist OldVirologist) throws Exception
	{
		boolean hasElement = Virologists.remove(OldVirologist); //a remove(Object) egy boolean visszatérési értékkel jelzi, hogy tartalmazta-e a lista eredetileg az elemet
		if(!hasElement) throw new Exception(); //ha a remove(Object) hamis értékkel tért vissza, akkor nem tartalmazta
	}
	
	/**
	 * Visszatér a mezõ oldalszámával
	 * @return NumberOfSides
	 */
	public int GetNumberOfSides()
	{
		return NumberOfSides.value();
	}
	
	/**
	 * Visszaadja a mezõn elhelyezett védõfelszerelések listáját
	 * @return EquipmentList
	 */
	public ArrayList<Equipment> GetEquipmentList()
	{
		return EquipmentList;
	}
	
	public Equipment GetEquipment(String ContainedEquipment) throws Exception//változtatás, eredetileg Equipment volt a paraméter
	{
		for(Equipment e : EquipmentList)
		{
			//TODO
			//if(e.)
		}
		
		return null;
	}
	
	/**
	 * Visszatér a mezõn lévõ, paraméterben megadott nevû virológussal.
	 * @param VirologistName a keresendõ virológus neve
	 * @return a névhez tartozó virológus
	 * @throws Exception ha nem találja a virológust
	 */
	public Virologist GetVirologist(String VirologistName) throws Exception
	{
		Virologist tmp = null;
		for(Virologist v : Virologists)
		{
			if(v.GetName().equals(VirologistName))
			{
				tmp=v;
				break;
			}
		}
		if(tmp==null) throw new Exception();
		return tmp;
	}
	
	/**
	 * Visszaadja a mezõn található virológusok listáját.
	 * @return
	 */
	public ArrayList<Virologist> GetVirologists()
	{
		return Virologists;
	}
	
	/**
	 * Megvizsgálja, hogy a mezõn található-e a paraméterben átadott névvel rendelkezõ Virológus
	 * @param VirologistName a keresendõ virológus neve
	 * @return true ha megtalálta
	 */
	public boolean HasVirologist(String VirologistName)
	{
		boolean hasVirologist = false;
		for(Virologist v : Virologists)
		{
			if(v.GetName().equals(VirologistName))
			{
				hasVirologist = true;
				break;
			}
		}
		return hasVirologist;
	}
	
	/**
	 * Visszatér a mezõ típusával.
	 * @return
	 */
	public abstract String GetType();

}
