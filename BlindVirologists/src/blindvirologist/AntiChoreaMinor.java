package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a vitust�nc ellen v�d� vakcin�t megval�s�t� oszt�ly
public class AntiChoreaMinor extends Vaccine
{
	//konstruktor
	public AntiChoreaMinor(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
	
	//met�dusok
	
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
