package blindvirologist.agent;
	import blindvirologist.Virologist;
	import customexceptions.AgentBlockedException;


/**
 * Az �genseket megval�s�t� absztrakt oszt�ly
 * A v�rusok �s vakcin�k ?soszt�lya
 */
public abstract class Agent
{
	private static int 	usableDecreaseValue = 1;
	protected int		duration;
	private int 		usable;
	private GeneticCode code;

	/**
	 * Konstruktor
	 * @param _duration az �gens hat�s�nak ideje
	 */
	public Agent( int _duration, int _usable )
	{
		this.duration	= _duration;
		this.usable		= _usable;
	}

	/**
	 * Az �gens kifejti a hat�s�t a param�terk�nt kapott virol�gusra
	 * @param _virologist a virol�gus, amin a hat�s�t kifejti
	 */
	public abstract void affect( Virologist _virologist ) throws AgentBlockedException;

	/**
	 * Az �gens felhaszn�lhat�s�gi idej�t cs�kkenti
	 * @return az �gens felhaszn�lhat�-e
	 */
	public boolean decreaseUsableTime()
	{
		if ( this.usable != -1 )
		{
			return (usable -= Agent.usableDecreaseValue) < 1;
		}

		return true;
	}

	/**
	 * Az �gens hat�s�nak idej�t cs�kkenti
	 * @return az �gens felhaszn�lhat�-e
	 */
	public boolean decreaseDuration()
	{
		if ( this.duration != -1 )
		{
			return (duration -= Agent.usableDecreaseValue) < 1;
		}

		return true;
	}
}
