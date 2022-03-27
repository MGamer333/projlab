package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a vitustánc ellen védõ vakcinát megvalósító osztály
public class AntiChoreaMinor extends Vaccine
{
	//konstruktor
	public AntiChoreaMinor(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
	
	//metódusok
	
	@Override
	public void Inject(Virologist newHost) throws VirusBlockedException, GloveException
	{
		for(Equipment e : newHost.GetEquipment())
		{
			try 
			{
				e.Effect();
			} catch (VirusBlockedException v)
			{
				throw v;
			}
			catch(GloveException g)
			{
				throw g;
			}
		}
		AntiChoreaMinor newVaccine = new AntiChoreaMinor(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVaccine(newVaccine);
	}
	
	@Override
	public String GetType()
	{
		return "ChoreaMinor";
	}
	
	@Override
	public void ThrownBack(Virologist newHost)
	{
		AntiChoreaMinor newVaccine = new AntiChoreaMinor(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVaccine(newVaccine);
	}


}
