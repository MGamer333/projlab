package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

public class Forget extends Virus
{

	//konstruktor
	public Forget(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
	
	//metódusok

	/**
	 * Elfelejteti a virológussal az összes ismert kódját.
	 */
	@Override
	public void AffectHost() 
	{
		this.GetHost().ForgetCodes();
	}
	
	/**
	 * 
	 */
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
		Forget newVirus = new Forget(1, 1, newHost); //a Forget vírus nem marad sokáig
		newHost.AddAffectingVirus(newVirus);
	}
	
	@Override
	public String GetType()
	{
		return "Forget";
	}
	
	@Override
	public void ThrownBack(Virologist newHost)
	{
		Forget newVirus = new Forget(1, 1, newHost); //a Forget vírus nem marad sokáig
		newHost.AddAffectingVirus(newVirus);
	}
}
