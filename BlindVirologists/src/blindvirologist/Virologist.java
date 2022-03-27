package blindvirologist;

import java.util.ArrayList;

import customexceptions.GloveException;
import customexceptions.InventoryFullException;
import customexceptions.VirusBlockedException;

public class Virologist implements INextTurn
{
	//adattagok felvétele
	private int ActionPoints, MaxActionPoints;
	//MaxActionPoints lehetne statikus
	private boolean IsStunned;
	private String Name;
	private Field Location;
	private ArrayList<Material> Materials;
	//az ágensek megjelenése
	private Agent CreatedAgent;
	private ArrayList<Agent> AffectingViruses;
	private ArrayList<Agent> AffectingVaccines;
	private ArrayList<Code> Codes;
	private ArrayList<Equipment> WornEquipment;
	private ArrayList<Sack> Sacks;
	//a felszerelés korlátozottságát statikus változókként vettem fel
	private static int maxEquipment = 3;
	private static int maxMaterials = 6;
	
	
	//konstruktorok
	/**
	 * A Virológus konstruktora. 
	 * @param name Virológus neve
	 * @param Location Virológus kiindulási helye
	 */
	public Virologist(String name, Field Location) //kell neki konstruktorba egy hely is
	{
		this.Name=name;
		//ebben az esetben 3 akciópont van, itt lehet megváltoztatni
		ActionPoints=3;
		MaxActionPoints=3;
		IsStunned=false;
		this.Location=Location;
		AffectingViruses=new ArrayList<Agent>();
		AffectingVaccines=new ArrayList<Agent>();
		Codes=new ArrayList<Code>();
		WornEquipment=new ArrayList<Equipment>();
		Sacks=new ArrayList<Sack>();
		Materials = new ArrayList<Material>();
	}
	
	//függvények
	/**
	 * Visszatér a virológus jelenlegi akciópontjaival.
	 * @return ActionPoints
	 */
	public int GetActionPoints() 
	{
		return ActionPoints;
	}
	
	/**
	 * A Virológus mozgását megvalósító függvény. 
	 * @param NewLocation Az lépés célpontja
	 * @throws Ha a Virológus nem rendelkezik akcióponttal
	 */
	public void Move(Field NewLocation) throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		else if(!this.Location.equals(NewLocation))
		{
			
			 //a szomszédosság megvizsgálása
			  if(!Location.IsNeighbor(NewLocation)) throw new Exception();
			  else
			  {
			  		Location.RemoveVirologist(this);
			  		NewLocation.AddVirologist(this);
			  		ActionPoints--;
			  }
		}
	}
	
	/**
	 * A Virológus kódot tapogat le
	 * @throws Ha a Virológus nem rendelkezik akcióponttal
	 */
	public void TouchCode() throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		else
		{
			
			if(Location.GetType()!="Laboratory") throw new Exception();
			else
			{
				Code NewCode = (Code)Location.GetCollectible("");
				if (NewCode==null) throw new Exception(); //biztonság kedvéért egy null osztály vizsgálat
				else 
				{
					if(!Codes.contains(NewCode)) Codes.add(NewCode); //csak egyszer tároljuk el ugyanazt a kódot
				}
			}
			
		}
	}
	
	/**
	 * Létrehoz egy új ágenst a kód alapján
	 * @param FromCode a felhasznált kód
	 * @param virus vírus esetén true az értéke, egyébként false
	 * @throws Ha a Virológusnak nincs akciópontja vagy nem ismeri a kódot
	 */
	public void CreateAgent(Code FromCode, boolean virus) throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs rá akciópont");
		if(!Codes.contains(FromCode)) throw new Exception("Nem ismert a kód"); //biztonság kedvéért
		
		//TODO
		//https://stackoverflow.com/questions/5235401/split-string-into-array-of-character-strings
		String[] materialnames =FromCode.GetName().split("(?<=.)");
		ArrayList<Material> requiredMaterials = new ArrayList<Material>();
		
		for(String s : materialnames)
		{
			Material tmp = this.GetMaterial(s);
			if(tmp==null) //nincsenek meg a kellõ alapanyagok
			{
				for(Material r : requiredMaterials)
				{
					Materials.add(r); //vissza kell adni az alapanyagokat
				}
				throw new Exception("Nincs elég alapanyag");
			}
			else
			{
				this.RemoveMaterial(tmp);
				requiredMaterials.add(tmp);
			}
		}
		//csak akkor nem dob kivételt, ha sikerült
		//a GC megoldja a felszabadítást
		
		CreatedAgent = FromCode.CreateAgent(virus); //ekkor létrehozzuk az ágenst
		ActionPoints--; //akciópont csak siker esetén vonódik le
		
	}
	
	/**
	 * Rákeni a meglevõ ágenst a paraméterként megkapott nevû virológusra
	 * @param VirologistName az áldozat neve
	 * @throws ha nincs akciópont
	 * @throws ha nincs mit felkenni
	 */
	public void SmearAgent(String VirologistName) throws Exception
	{
		if(CreatedAgent==null) throw new Exception("Nincs létrehozott ágens");
		if(ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		if(!VirologistName.equals(this.Name)) //saját magának odaadni könnyebb
		{
			if(!Location.HasVirologist(VirologistName)) throw new Exception("A virológus nem érhetõ el");

		}
		
		//ide akkor jutunk el, ha lehetséges az ágens beadása
		Virologist victim = Location.GetVirologist(VirologistName);
		try
		{
			CreatedAgent.Inject(victim);
		}
		catch(VirusBlockedException v)
		{
			
		}
		catch(GloveException g)
		{
			
			for(Equipment e : WornEquipment)
			{
				if(e.GetType().equals("Glove"))
				{
					WornEquipment.remove(e);
					break; //csak 1-et szedünk ki
				}
			}
			//visszakapja az ágenst
			CreatedAgent.ThrownBack(this);
		}
		CreatedAgent=null;
		ActionPoints--;
	}
	
	/**
	 * Visszaadja a virológusnál lévõ ágenst
	 * @return CreatedAgent
	 */
	public Agent GetCreatedAgent()
	{
		return CreatedAgent;
	}
	
	/**
	 * Hozzáadja a Virológushoz a paraméterként kapott vírust
	 * @param NewVirus
	 */
	public void AddAffectingVirus(Virus NewVirus)
	{
		boolean resistant=false;
		for(Agent v : AffectingVaccines) //vakcinák vizsgálata
		{
			if(v.GetType().equals(NewVirus.GetType()))
			{
				resistant=true;
				break;
			}
		}
		if(!resistant) //ha nem ellenálló a szervezet, a vírus hatni kezd
		{
			this.AffectingViruses.add(NewVirus);
		}
	}
	
	/**
	 * Lehet erre nem is lesz szükség már?
	 * @param OldVirus
	 */
	public void RemoveAffectingVirus(Virus OldVirus)
	{
		AffectingViruses.remove(OldVirus);
	}
	
	/**
	 * Hozzáadja a vakcinák listájához a paraméterben kapott vakcinát
	 * @param NewVaccine
	 */
	public void AddAffectingVaccine(Vaccine NewVaccine)
	{
		AffectingVaccines.add(NewVaccine);
	}
	
	/**
	 * Erre lehet már nem lesz szükség
	 * @param OldVaccine
	 */
	public void RemoveAffectingVaccine(Vaccine OldVaccine)
	{
		
	}
	
	/**
	 * Visszatér a virológus szervezetét támadó vírusokkal.
	 * @return AffectingViruses
	 */
	public ArrayList<Agent> GetAffectingViruses()
	{
		return AffectingViruses;
	}
	
	/**
	 * Visszatér a virológus vakcináinak listájával.
	 * @return AffectingVaccines
	 */
	public ArrayList<Agent> GetAffectingVaccines()
	{
		return AffectingVaccines;
	}
	
	/**
	 * Felveszi a földrõl a védõfelszerelést
	 * @param WantedEquipment A választott védõfelszerelés neve
	 * @throws Exception Ha nincs elég akciópont
	 * @throws InventoryFullException ha túllépi az új védõfelszereléssel a korlátot
	 */
	public void CollectEquipment(String WantedEquipment) throws Exception, InventoryFullException
	{
		if(ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		try
		{
			Equipment newEquipment = Location.GetEquipment(WantedEquipment);
			if(newEquipment.GetType().equals("Sack"))
			{
				this.Sacks.add((Sack)newEquipment);
			}
			else
			this.WornEquipment.add(newEquipment);
			ActionPoints--;
			if((WornEquipment.size()+Sacks.size())>maxEquipment) throw new InventoryFullException("A felszerelés megtelt");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Lerakja a paraméterként kapott felszerelést a földre, ha tudja
	 * @param DroppedEquipment
	 */
	public void DropEquipment(Equipment DroppedEquipment)
	{
		if(this.WornEquipment.remove(DroppedEquipment)) Location.AddEquipment(DroppedEquipment); //ha nála van, lerakja a földre
	}
	
	/**
	 * Felvesz a raktárból egy típusú alapanyagból a paraméterként megadott mennyiséget
	 * @param MaterialName
	 * @param quantity
	 */
	public void CollectMaterials(String MaterialName, int quantity) throws Exception
	{
		if(this.ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		if(!this.Location.GetType().equals("Storage")) throw new Exception("Itt nincs alapanyag");
		for(int i=0; i<quantity; i++)
		{
			if(Materials.size()<=maxMaterials) //ha elfér
			{
				Materials.add((Material)Location.GetCollectible(MaterialName)); //visszakonvertáljuk
			}
			else //ha nem fér el, a zsákokban próbálkozunk
			{
				for(Sack s : Sacks)
				{
					if(s.GetMaterials().size()<=s.GetMaxCapacity()) // ha belefér belerakjuk
						s.AddMaterial((Material)Location.GetCollectible(MaterialName), 1);
					//ha nem fér bele nem csinálunk semmit
				}
			}
		}
		ActionPoints--;
	}
	
	/**
	 * Ellopja a paraméterként megadott nevû virológustól a paraméterként megadott felszerelést
	 * @param StolenEquipment az ellopandó védõfelszerelés
	 * @param VictimName a bénult virológus neve
	 * @throws Exception ha a lopás nem teljesíthetõ
	 * @throws InventoryFullException ha az új védõfelszereléssel túllépi a korlátot
	 */
	public void StealEquipment(Equipment StolenEquipment, String VictimName) throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		if(!Location.HasVirologist(VictimName)) throw new Exception("Nem érhetõ el a virológus");
		Virologist Victim = Location.GetVirologist(VictimName);
		if(!Victim.GetIsStunned())  throw new Exception("Nem bénult a virológus");
		Victim.LoseEquipment(StolenEquipment);
		if(StolenEquipment.GetType().equals("Sack")) this.Sacks.add((Sack)StolenEquipment);
		else
			this.WornEquipment.add(StolenEquipment);
		ActionPoints--;
		if((this.WornEquipment.size()+this.Sacks.size())>maxEquipment) throw new InventoryFullException("Tele van a felszerelés!");
	}
	
	/**
	 * Ellopja a paraméterként megadott felszerelést a paraméterként megadott virológustól
	 * @param StolenMaterial
	 * @param VictimName
	 * @throws Exception
	 */
	public void StealMaterial(Material StolenMaterial, String VictimName) throws Exception
	{
		if(this.ActionPoints<=0) throw new Exception("Nincs elég akciópont");
		if(!this.Location.HasVirologist(VictimName)) throw new Exception("Nem érhetõ el a virológus");
		Virologist Victim = this.Location.GetVirologist(VictimName);
		if(!Victim.GetIsStunned()) throw new Exception("Nem bénult a virológus");
		Victim.RemoveMaterial(StolenMaterial);
		this.AddMaterial(StolenMaterial);
	}
	
	/**
	 * Elveszi a virológustól a paramtérként átadott felszerelést
	 * @param LostEquipment
	 * @throws Exception ha nem rendelkezett vele
	 */
	public void LoseEquipment(Equipment LostEquipment) throws Exception
	{
		if(!this.WornEquipment.remove(LostEquipment)) throw new Exception("Nincs ilyen védõfelszerelésem");
	}
	
	/**
	 * Elveszi a virológustól a paraméterként átadott alapanyagot
	 * @param LostMaterial
	 * @throws Exception ha nem rendelkezett vele
	 */
	public void LoseMaterial(Material LostMaterial) throws Exception
	{
		if(!this.Materials.remove(LostMaterial)) throw new Exception("Nincs ilyen alapanyagom");
	}
	
	/**
	 * Visszaadja az elsõ, paraméterként kapott névvel megegyezõ nevû alapanyagot.
	 * @param MaterialName
	 * @return null ha nincs nála
	 */
	public Material GetMaterial(String MaterialName)
	{
		Material foundmaterial=null;
		for(Material m : Materials)
		{
			if(m.GetName().equals(MaterialName))
			{
				foundmaterial=m;
				break;
			}
		}
		return foundmaterial;
	}
	
	/**
	 * Elveszi a paraméterként megadott alapanyagot a virológustól
	 * @param OldMaterial
	 * @throws Exception ha nem találja
	 */
	public void RemoveMaterial(Material OldMaterial) throws Exception
	{
		if(!this.Materials.remove(OldMaterial)) throw new Exception("Nincs ilyen alapanyagom");
	}
	
	/**
	 * Hozzáadja a paraméterként kapott alapanyagot a virológus készletéhez, ha elfér.
	 * @param NewMaterial
	 */
	public void AddMaterial(Material NewMaterial)
	{
		if(this.Materials.size()<this.maxMaterials) this.Materials.add(NewMaterial);
		else
		{
			for(Sack s : Sacks)
			{
				if(s.GetMaterials().size()<s.GetMaxCapacity())
					s.AddMaterial(NewMaterial, 1);
			}
		}
	}
	
	/**
	 * Visszatér a virológus védõfelszereléseivel.
	 * @return WornEquipment
	 */
	public ArrayList<Equipment> GetEquipment()
	{
		return WornEquipment;
	}
	
	/**
	 * Visszatér a virológus nevével
	 * @return Name
	 */
	public String GetName()
	{
		return Name;
	}
	
	/**
	 * A virológus elfelejti az összes ismert kódját.
	 */
	public void ForgetCodes()
	{
		Codes.clear(); //lehetne szimplán egy new list-et hívni ide, GC megoldja akárhogy
	}
	
	/**
	 * Lebénítja a virológust
	 */
	public void Stun()
	{
		this.IsStunned=true;
		this.ActionPoints=0;
	}
	
	/**
	 * Visszatér a virológus bénultsági állapotával
	 * @return
	 */
	public boolean GetIsStunned()
	{
		return this.IsStunned;
	}
	
	/**
	 * A virológus pozíciójával visszatérõ függvény
	 * @return
	 */
	public Field GetLocation()
	{
		return Location;
	}
	
	/**
	 * A virológus által ismert kódok listájával tér vissza
	 * @return
	 */
	public ArrayList<Code> GetCodes()
	{
		return Codes;
	}
	
	/**
	 * A virológus által birtokolt alapanyagok listájával tér vissza.
	 * @return
	 */
	public ArrayList<Material> GetMaterials()
	{
		return Materials;
	}
	
	public void NextTurn()
	{
		ActionPoints=MaxActionPoints;
		IsStunned=false;
		for(Agent a : AffectingViruses)
		{
			a.NextTurn();
			if(a.GetLeftDuration()==0) AffectingViruses.remove(a);
		}
		for(Agent a : AffectingVaccines)
		{
			a.NextTurn();
			if(a.GetLeftDuration()==0) AffectingVaccines.remove(a);
		}
		if(CreatedAgent!=null)
		{
			CreatedAgent.NextTurn();
			if(CreatedAgent.GetLeftDuration()==0) CreatedAgent=null;
		}
	}
}
