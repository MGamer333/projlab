package blindvirologist;

import customexceptions.AgentDiedException;
import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//az �genseket megval�s�t� absztakt oszt�ly
public abstract class Agent implements INextTurn
{
	//attrib�tumok
	private int MaxDuration, LeftDuration;
	private Virologist Host;
	
	//konstruktor
	public Agent(int MaxDuration, int LeftDuration, Virologist Host)
	{
		this.MaxDuration=MaxDuration;
		this.LeftDuration=LeftDuration;
		this.Host=Host;
	}
	
	//met�dusok
	/**
	 * Ha a Host �rt�ke nem null, akkor megh�vja az AffectHost met�dust.
	 * Cs�kkenti a LeftDuration �rt�k�t 1-gyel.
	 */
	public void live()
	{
		if(!Host.equals(null))
		{
			AffectHost();
		}
		LeftDuration--;
	}
	
	/**
	 * Kiv�telt dob a megh�v�nak
	 * @throws AgentDiedException
	 */
	/*
	public void die() throws AgentDiedException //nincs r� sz�ks�g
	{
		throw new AgentDiedException("The agent died");
	}*/
	
	/**
	 * Az �gens hat�s�t val�s�tja meg a virol�guson
	 */
	public abstract void AffectHost();
	
	/**
	 * Az �genst beadja a param�terk�nt kapott virol�gusnak
	 */
	public abstract void Inject(Virologist newHost) throws VirusBlockedException, GloveException;
	
	/**
	 * Az �gens h�tral�v� idej�vel t�r vissza.
	 * @return
	 */
	public int GetLeftDuration()
	{
		return LeftDuration;
	}
	
	/**
	 * Az �gens t�pus�val t�r vissza.
	 * @return
	 */
	public abstract String GetType();
	
	/**
	 * az �gens maxim�lis id�tartam�val t�r vissza.
	 * @return
	 */
	public int GetMaxDuration()
	{
		return MaxDuration;
	}
	
	@Override
	public void NextTurn() //throws AgentDiedException
	{
		if(LeftDuration>0) live();
		//else die();
	}
	
	/**
	 * Az �gens gazdatest�vel visszat�r� f�ggv�ny
	 * @return Virologist
	 */
	public Virologist GetHost()
	{
		return Host;
	}
	
	/**
	 * Keszty�vel visszaver�skor kell megh�vni, felszerel�st nem vizsg�lva adja be a c�lpontnak az �genst
	 * @param newHost
	 */
	public abstract void ThrownBack(Virologist newHost);

}
