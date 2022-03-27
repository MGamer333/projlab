package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//bénítás elleni vakcinát megvalósító osztály
public class AntiStun extends Vaccine
{

	//konstruktor
	public AntiStun(int MaxDuration, int LeftDuration, Virologist Host) 
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
		AntiStun newVaccine = new AntiStun(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVaccine(newVaccine);
	}
	
	@Override
	public String GetType()
	{
		return "Stun";
	}
	
	@Override
	public void ThrownBack(Virologist newHost)
	{
		AntiStun newVaccine = new AntiStun(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVaccine(newVaccine);
	}

}
