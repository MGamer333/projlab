package blindvirologist;

import customexceptions.AgentDiedException;
import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//az ágenseket megvalósító absztakt osztály
public abstract class Agent implements INextTurn
{
	//attribútumok
	private int MaxDuration, LeftDuration;
	private Virologist Host;
	
	//konstruktor
	public Agent(int MaxDuration, int LeftDuration, Virologist Host)
	{
		this.MaxDuration=MaxDuration;
		this.LeftDuration=LeftDuration;
		this.Host=Host;
	}
	
	//metódusok
	/**
	 * Ha a Host értéke nem null, akkor meghívja az AffectHost metódust.
	 * Csökkenti a LeftDuration értékét 1-gyel.
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
	 * Kivételt dob a meghívónak
	 * @throws AgentDiedException
	 */
	/*
	public void die() throws AgentDiedException //nincs rá szükség
	{
		throw new AgentDiedException("The agent died");
	}*/
	
	/**
	 * Az ágens hatását valósítja meg a virológuson
	 */
	public abstract void AffectHost();
	
	/**
	 * Az ágenst beadja a paraméterként kapott virológusnak
	 */
	public abstract void Inject(Virologist newHost) throws VirusBlockedException, GloveException;
	
	/**
	 * Az ágens hátralévõ idejével tér vissza.
	 * @return
	 */
	public int GetLeftDuration()
	{
		return LeftDuration;
	}
	
	/**
	 * Az ágens típusával tér vissza.
	 * @return
	 */
	public abstract String GetType();
	
	/**
	 * az ágens maximális idõtartamával tér vissza.
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
	 * Az ágens gazdatestével visszatérõ függvény
	 * @return Virologist
	 */
	public Virologist GetHost()
	{
		return Host;
	}
	
	/**
	 * Kesztyûvel visszaveréskor kell meghívni, felszerelést nem vizsgálva adja be a célpontnak az ágenst
	 * @param newHost
	 */
	public abstract void ThrownBack(Virologist newHost);

}
