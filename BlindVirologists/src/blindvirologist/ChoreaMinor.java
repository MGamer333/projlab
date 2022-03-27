package blindvirologist;

import java.util.ArrayList;
import java.util.Random;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a vitust�nc v�rust megval�s�t� oszt�ly
public class ChoreaMinor extends Virus
{

	//konstruktor
	public ChoreaMinor(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
	
	//met�dusok
	
	/**
	 * V�letlenszer� ir�nyba l�p, am�g a virol�gusnak van akci�pontja.
	*/
	@Override
	public void AffectHost() 
	{
		Virologist Host = this.GetHost();
		while(Host.GetActionPoints()>0)
		{
			ArrayList<Field> possibleSteps = Host.GetLocation().GetNeighbors();
			Random rand = new Random();
			int nextstepindex = rand.nextInt(possibleSteps.size());
			try {
				Host.Move(possibleSteps.get(nextstepindex));
			} catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
		}
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
		ChoreaMinor newVirus = new ChoreaMinor(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVirus(newVirus);
	}
	
	@Override
	public String GetType()
	{
		return "ChoreaMinor";
	}
	
	@Override
	public void ThrownBack(Virologist newHost)
	{
		ChoreaMinor newVirus = new ChoreaMinor(this.GetMaxDuration(), this.GetMaxDuration(), newHost);
		newHost.AddAffectingVirus(newVirus);
	}
}
