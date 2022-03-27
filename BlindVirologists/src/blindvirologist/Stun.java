package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

public class Stun extends Virus
{

	//konstruktor
	public Stun(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
	
	//metódusok
	
	/**
	 * Lebénítja a virológusát 
	 */
	@Override
	public void AffectHost()
	{
		this.GetHost().Stun();
	}
	
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
		Stun newVirus = new Stun(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVirus(newVirus);
	}
	
	@Override
	public String GetType()
	{
		return "Stun";
	}
	
	@Override
	public void ThrownBack(Virologist newHost)
	{
		Stun newVirus = new Stun(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVirus(newVirus);
	}

}
