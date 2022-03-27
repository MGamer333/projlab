package blindvirologist;

import java.util.ArrayList;

import customexceptions.GloveException;
import customexceptions.InventoryFullException;
import customexceptions.VirusBlockedException;

public class Virologist implements INextTurn
{
	//adattagok felv�tele
	private int ActionPoints, MaxActionPoints;
	//MaxActionPoints lehetne statikus
	private boolean IsStunned;
	private String Name;
	private Field Location;
	private ArrayList<Material> Materials;
	//az �gensek megjelen�se
	private Agent CreatedAgent;
	private ArrayList<Agent> AffectingViruses;
	private ArrayList<Agent> AffectingVaccines;
	private ArrayList<Code> Codes;
	private ArrayList<Equipment> WornEquipment;
	private ArrayList<Sack> Sacks;
	//a felszerel�s korl�tozotts�g�t statikus v�ltoz�kk�nt vettem fel
	private static int maxEquipment = 3;
	private static int maxMaterials = 6;
	
	
	//konstruktorok
	/**
	 * A Virol�gus konstruktora. 
	 * @param name Virol�gus neve
	 * @param Location Virol�gus kiindul�si helye
	 */
	public Virologist(String name, Field Location) //kell neki konstruktorba egy hely is
	{
		this.Name=name;
		//ebben az esetben 3 akci�pont van, itt lehet megv�ltoztatni
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
	
	//f�ggv�nyek
	/**
	 * Visszat�r a virol�gus jelenlegi akci�pontjaival.
	 * @return ActionPoints
	 */
	public int GetActionPoints() 
	{
		return ActionPoints;
	}
	
	/**
	 * A Virol�gus mozg�s�t megval�s�t� f�ggv�ny. 
	 * @param NewLocation Az l�p�s c�lpontja
	 * @throws Ha a Virol�gus nem rendelkezik akci�ponttal
	 */
	public void Move(Field NewLocation) throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
		else if(!this.Location.equals(NewLocation))
		{
			
			 //a szomsz�doss�g megvizsg�l�sa
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
	 * A Virol�gus k�dot tapogat le
	 * @throws Ha a Virol�gus nem rendelkezik akci�ponttal
	 */
	public void TouchCode() throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
		else
		{
			
			if(Location.GetType()!="Laboratory") throw new Exception();
			else
			{
				Code NewCode = (Code)Location.GetCollectible("");
				if (NewCode==null) throw new Exception(); //biztons�g kedv��rt egy null oszt�ly vizsg�lat
				else 
				{
					if(!Codes.contains(NewCode)) Codes.add(NewCode); //csak egyszer t�roljuk el ugyanazt a k�dot
				}
			}
			
		}
	}
	
	/**
	 * L�trehoz egy �j �genst a k�d alapj�n
	 * @param FromCode a felhaszn�lt k�d
	 * @param virus v�rus eset�n true az �rt�ke, egy�bk�nt false
	 * @throws Ha a Virol�gusnak nincs akci�pontja vagy nem ismeri a k�dot
	 */
	public void CreateAgent(Code FromCode, boolean virus) throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs r� akci�pont");
		if(!Codes.contains(FromCode)) throw new Exception("Nem ismert a k�d"); //biztons�g kedv��rt
		
		//TODO
		//https://stackoverflow.com/questions/5235401/split-string-into-array-of-character-strings
		String[] materialnames =FromCode.GetName().split("(?<=.)");
		ArrayList<Material> requiredMaterials = new ArrayList<Material>();
		
		for(String s : materialnames)
		{
			Material tmp = this.GetMaterial(s);
			if(tmp==null) //nincsenek meg a kell� alapanyagok
			{
				for(Material r : requiredMaterials)
				{
					Materials.add(r); //vissza kell adni az alapanyagokat
				}
				throw new Exception("Nincs el�g alapanyag");
			}
			else
			{
				this.RemoveMaterial(tmp);
				requiredMaterials.add(tmp);
			}
		}
		//csak akkor nem dob kiv�telt, ha siker�lt
		//a GC megoldja a felszabad�t�st
		
		CreatedAgent = FromCode.CreateAgent(virus); //ekkor l�trehozzuk az �genst
		ActionPoints--; //akci�pont csak siker eset�n von�dik le
		
	}
	
	/**
	 * R�keni a meglev� �genst a param�terk�nt megkapott nev� virol�gusra
	 * @param VirologistName az �ldozat neve
	 * @throws ha nincs akci�pont
	 * @throws ha nincs mit felkenni
	 */
	public void SmearAgent(String VirologistName) throws Exception
	{
		if(CreatedAgent==null) throw new Exception("Nincs l�trehozott �gens");
		if(ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
		if(!VirologistName.equals(this.Name)) //saj�t mag�nak odaadni k�nnyebb
		{
			if(!Location.HasVirologist(VirologistName)) throw new Exception("A virol�gus nem �rhet� el");

		}
		
		//ide akkor jutunk el, ha lehets�ges az �gens bead�sa
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
					break; //csak 1-et szed�nk ki
				}
			}
			//visszakapja az �genst
			CreatedAgent.ThrownBack(this);
		}
		CreatedAgent=null;
		ActionPoints--;
	}
	
	/**
	 * Visszaadja a virol�gusn�l l�v� �genst
	 * @return CreatedAgent
	 */
	public Agent GetCreatedAgent()
	{
		return CreatedAgent;
	}
	
	/**
	 * Hozz�adja a Virol�gushoz a param�terk�nt kapott v�rust
	 * @param NewVirus
	 */
	public void AddAffectingVirus(Virus NewVirus)
	{
		boolean resistant=false;
		for(Agent v : AffectingVaccines) //vakcin�k vizsg�lata
		{
			if(v.GetType().equals(NewVirus.GetType()))
			{
				resistant=true;
				break;
			}
		}
		if(!resistant) //ha nem ellen�ll� a szervezet, a v�rus hatni kezd
		{
			this.AffectingViruses.add(NewVirus);
		}
	}
	
	/**
	 * Lehet erre nem is lesz sz�ks�g m�r?
	 * @param OldVirus
	 */
	public void RemoveAffectingVirus(Virus OldVirus)
	{
		AffectingViruses.remove(OldVirus);
	}
	
	/**
	 * Hozz�adja a vakcin�k list�j�hoz a param�terben kapott vakcin�t
	 * @param NewVaccine
	 */
	public void AddAffectingVaccine(Vaccine NewVaccine)
	{
		AffectingVaccines.add(NewVaccine);
	}
	
	/**
	 * Erre lehet m�r nem lesz sz�ks�g
	 * @param OldVaccine
	 */
	public void RemoveAffectingVaccine(Vaccine OldVaccine)
	{
		
	}
	
	/**
	 * Visszat�r a virol�gus szervezet�t t�mad� v�rusokkal.
	 * @return AffectingViruses
	 */
	public ArrayList<Agent> GetAffectingViruses()
	{
		return AffectingViruses;
	}
	
	/**
	 * Visszat�r a virol�gus vakcin�inak list�j�val.
	 * @return AffectingVaccines
	 */
	public ArrayList<Agent> GetAffectingVaccines()
	{
		return AffectingVaccines;
	}
	
	/**
	 * Felveszi a f�ldr�l a v�d�felszerel�st
	 * @param WantedEquipment A v�lasztott v�d�felszerel�s neve
	 * @throws Exception Ha nincs el�g akci�pont
	 * @throws InventoryFullException ha t�ll�pi az �j v�d�felszerel�ssel a korl�tot
	 */
	public void CollectEquipment(String WantedEquipment) throws Exception, InventoryFullException
	{
		if(ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
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
			if((WornEquipment.size()+Sacks.size())>maxEquipment) throw new InventoryFullException("A felszerel�s megtelt");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Lerakja a param�terk�nt kapott felszerel�st a f�ldre, ha tudja
	 * @param DroppedEquipment
	 */
	public void DropEquipment(Equipment DroppedEquipment)
	{
		if(this.WornEquipment.remove(DroppedEquipment)) Location.AddEquipment(DroppedEquipment); //ha n�la van, lerakja a f�ldre
	}
	
	/**
	 * Felvesz a rakt�rb�l egy t�pus� alapanyagb�l a param�terk�nt megadott mennyis�get
	 * @param MaterialName
	 * @param quantity
	 */
	public void CollectMaterials(String MaterialName, int quantity) throws Exception
	{
		if(this.ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
		if(!this.Location.GetType().equals("Storage")) throw new Exception("Itt nincs alapanyag");
		for(int i=0; i<quantity; i++)
		{
			if(Materials.size()<=maxMaterials) //ha elf�r
			{
				Materials.add((Material)Location.GetCollectible(MaterialName)); //visszakonvert�ljuk
			}
			else //ha nem f�r el, a zs�kokban pr�b�lkozunk
			{
				for(Sack s : Sacks)
				{
					if(s.GetMaterials().size()<=s.GetMaxCapacity()) // ha belef�r belerakjuk
						s.AddMaterial((Material)Location.GetCollectible(MaterialName), 1);
					//ha nem f�r bele nem csin�lunk semmit
				}
			}
		}
		ActionPoints--;
	}
	
	/**
	 * Ellopja a param�terk�nt megadott nev� virol�gust�l a param�terk�nt megadott felszerel�st
	 * @param StolenEquipment az ellopand� v�d�felszerel�s
	 * @param VictimName a b�nult virol�gus neve
	 * @throws Exception ha a lop�s nem teljes�thet�
	 * @throws InventoryFullException ha az �j v�d�felszerel�ssel t�ll�pi a korl�tot
	 */
	public void StealEquipment(Equipment StolenEquipment, String VictimName) throws Exception
	{
		if(ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
		if(!Location.HasVirologist(VictimName)) throw new Exception("Nem �rhet� el a virol�gus");
		Virologist Victim = Location.GetVirologist(VictimName);
		if(!Victim.GetIsStunned())  throw new Exception("Nem b�nult a virol�gus");
		Victim.LoseEquipment(StolenEquipment);
		if(StolenEquipment.GetType().equals("Sack")) this.Sacks.add((Sack)StolenEquipment);
		else
			this.WornEquipment.add(StolenEquipment);
		ActionPoints--;
		if((this.WornEquipment.size()+this.Sacks.size())>maxEquipment) throw new InventoryFullException("Tele van a felszerel�s!");
	}
	
	/**
	 * Ellopja a param�terk�nt megadott felszerel�st a param�terk�nt megadott virol�gust�l
	 * @param StolenMaterial
	 * @param VictimName
	 * @throws Exception
	 */
	public void StealMaterial(Material StolenMaterial, String VictimName) throws Exception
	{
		if(this.ActionPoints<=0) throw new Exception("Nincs el�g akci�pont");
		if(!this.Location.HasVirologist(VictimName)) throw new Exception("Nem �rhet� el a virol�gus");
		Virologist Victim = this.Location.GetVirologist(VictimName);
		if(!Victim.GetIsStunned()) throw new Exception("Nem b�nult a virol�gus");
		Victim.RemoveMaterial(StolenMaterial);
		this.AddMaterial(StolenMaterial);
	}
	
	/**
	 * Elveszi a virol�gust�l a paramt�rk�nt �tadott felszerel�st
	 * @param LostEquipment
	 * @throws Exception ha nem rendelkezett vele
	 */
	public void LoseEquipment(Equipment LostEquipment) throws Exception
	{
		if(!this.WornEquipment.remove(LostEquipment)) throw new Exception("Nincs ilyen v�d�felszerel�sem");
	}
	
	/**
	 * Elveszi a virol�gust�l a param�terk�nt �tadott alapanyagot
	 * @param LostMaterial
	 * @throws Exception ha nem rendelkezett vele
	 */
	public void LoseMaterial(Material LostMaterial) throws Exception
	{
		if(!this.Materials.remove(LostMaterial)) throw new Exception("Nincs ilyen alapanyagom");
	}
	
	/**
	 * Visszaadja az els�, param�terk�nt kapott n�vvel megegyez� nev� alapanyagot.
	 * @param MaterialName
	 * @return null ha nincs n�la
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
	 * Elveszi a param�terk�nt megadott alapanyagot a virol�gust�l
	 * @param OldMaterial
	 * @throws Exception ha nem tal�lja
	 */
	public void RemoveMaterial(Material OldMaterial) throws Exception
	{
		if(!this.Materials.remove(OldMaterial)) throw new Exception("Nincs ilyen alapanyagom");
	}
	
	/**
	 * Hozz�adja a param�terk�nt kapott alapanyagot a virol�gus k�szlet�hez, ha elf�r.
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
	 * Visszat�r a virol�gus v�d�felszerel�seivel.
	 * @return WornEquipment
	 */
	public ArrayList<Equipment> GetEquipment()
	{
		return WornEquipment;
	}
	
	/**
	 * Visszat�r a virol�gus nev�vel
	 * @return Name
	 */
	public String GetName()
	{
		return Name;
	}
	
	/**
	 * A virol�gus elfelejti az �sszes ismert k�dj�t.
	 */
	public void ForgetCodes()
	{
		Codes.clear(); //lehetne szimpl�n egy new list-et h�vni ide, GC megoldja ak�rhogy
	}
	
	/**
	 * Leb�n�tja a virol�gust
	 */
	public void Stun()
	{
		this.IsStunned=true;
		this.ActionPoints=0;
	}
	
	/**
	 * Visszat�r a virol�gus b�nults�gi �llapot�val
	 * @return
	 */
	public boolean GetIsStunned()
	{
		return this.IsStunned;
	}
	
	/**
	 * A virol�gus poz�ci�j�val visszat�r� f�ggv�ny
	 * @return
	 */
	public Field GetLocation()
	{
		return Location;
	}
	
	/**
	 * A virol�gus �ltal ismert k�dok list�j�val t�r vissza
	 * @return
	 */
	public ArrayList<Code> GetCodes()
	{
		return Codes;
	}
	
	/**
	 * A virol�gus �ltal birtokolt alapanyagok list�j�val t�r vissza.
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
