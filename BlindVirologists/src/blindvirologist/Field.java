package blindvirologist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//a mez�k oldalsz�m�t megval�s�t� enum
enum SideNumber //TODO ellen�rz�s
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

//a mez�t megval�s�t� absztrakt oszt�ly
public abstract class Field 
{
	//adattagok felv�tele
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
	
	//met�dusok
	/**
	 * Visszaadja a mez�n tal�lhat� �sszegy�jthet� dolgok list�j�t.
	 * @return ArrayList<Collectible>
	 */
	abstract public ArrayList<Collectible> GetCollectibles() throws Exception;
	
	/**
	 * �sszegy�jti a mez�n tal�lhat�, param�terben megadott nev� dolgot.
	 * @param Name Az �sszegy�jthet� dolog neve
	 * @return Collectible
	 */
	abstract public Collectible GetCollectible(String Name) throws Exception;
	
	/**
	 * Visszat�r a mez� egyedi azonos�t�j�val.
	 * @return ID
	 */
	public String GetID()
	{
		return ID;
	}
	
	/**
	 * Megvizsg�lja, hogy a param�terk�nt kapott mez� szomsz�dja-e.
	 * @param UnknownField
	 * @return true ha szomsz�dja
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
	 * Hozz�adja a param�terk�nt kapott mez�t a szomsz�dainak list�j�hoz
	 * @param NewNeighbor
	 * @throws Exception ha t�ll�pn� a lehets�ges szomsz�dok sz�m�t
	 */
	public void AddNeighbor(Field NewNeighbor) throws Exception
	{
		//ha t�l sok lenne a szomsz�d, kiv�telt dob
		if(this.NumberOfSides.value()<=(Neighbors.size())) throw new Exception(); //ezt tesztelni kell
		Neighbors.add(NewNeighbor);
	}
	
	/**
	 * Visszaadja a mez� szomsz�dainak list�j�t.
	 * @return
	 */
	public ArrayList<Field> GetNeighbors()
	{
		return Neighbors;
	}
	
	/**
	 * Elhelyez egy �j v�d�felszerel�st a mez�n
	 * @param NewEquipment
	 */
	public void AddEquipment(Equipment NewEquipment)
	{
		EquipmentList.add(NewEquipment);
	}
	
	/**
	 * Elhelyez egy �j virol�gust a mez�n
	 * @param NewVirologist
	 */
	public void AddVirologist(Virologist NewVirologist)
	{
		Virologists.add(NewVirologist);
	}
	
	/**
	 * Elt�vol�tja a param�terk�nt �tadott virol�gust a mez�r�l.
	 * @param OldVirologist a mez�n tal�lhat�, elt�val�tand� virol�gus
	 * @throws Exception kiv�telt dob, ha nem tal�lja
	 */
	public void RemoveVirologist(Virologist OldVirologist) throws Exception
	{
		boolean hasElement = Virologists.remove(OldVirologist); //a remove(Object) egy boolean visszat�r�si �rt�kkel jelzi, hogy tartalmazta-e a lista eredetileg az elemet
		if(!hasElement) throw new Exception(); //ha a remove(Object) hamis �rt�kkel t�rt vissza, akkor nem tartalmazta
	}
	
	/**
	 * Visszat�r a mez� oldalsz�m�val
	 * @return NumberOfSides
	 */
	public int GetNumberOfSides()
	{
		return NumberOfSides.value();
	}
	
	/**
	 * Visszaadja a mez�n elhelyezett v�d�felszerel�sek list�j�t
	 * @return EquipmentList
	 */
	public ArrayList<Equipment> GetEquipmentList()
	{
		return EquipmentList;
	}
	
	public Equipment GetEquipment(String ContainedEquipment) throws Exception//v�ltoztat�s, eredetileg Equipment volt a param�ter
	{
		for(Equipment e : EquipmentList)
		{
			//TODO
			//if(e.)
		}
		
		return null;
	}
	
	/**
	 * Visszat�r a mez�n l�v�, param�terben megadott nev� virol�gussal.
	 * @param VirologistName a keresend� virol�gus neve
	 * @return a n�vhez tartoz� virol�gus
	 * @throws Exception ha nem tal�lja a virol�gust
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
	 * Visszaadja a mez�n tal�lhat� virol�gusok list�j�t.
	 * @return
	 */
	public ArrayList<Virologist> GetVirologists()
	{
		return Virologists;
	}
	
	/**
	 * Megvizsg�lja, hogy a mez�n tal�lhat�-e a param�terben �tadott n�vvel rendelkez� Virol�gus
	 * @param VirologistName a keresend� virol�gus neve
	 * @return true ha megtal�lta
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
	 * Visszat�r a mez� t�pus�val.
	 * @return
	 */
	public abstract String GetType();

}
