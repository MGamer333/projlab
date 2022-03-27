package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a felejt�s v�rus elleni vakcin�t megval�s�t� oszt�ly
public class AntiForget extends Vaccine
{

	//konstruktor
	public AntiForget(int MaxDuration, int LeftDuration, Virologist Host) 
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
		AntiForget newVaccine = new AntiForget(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVaccine(newVaccine);
	}
	
	@Override
	public String GetType()
	{
		return "Forget";
	}
	
	@Override
	public void ThrownBack(Virologist newHost)
	{
		AntiForget newVaccine = new AntiForget(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVaccine(newVaccine);
	}

}
