package blindvirologist.agent;
	import blindvirologist.Virologist;
	import customexceptions.AgentBlockedException;


/**
 * Az ágenseket megvalósító absztrakt osztály
 * A vírusok és vakcinák ?sosztálya
 */
public abstract class Agent
{
	private static int 	usableDecreaseValue = 1;
	protected int		duration;
	private int 		usable;
	private GeneticCode code;

	/**
	 * Konstruktor
	 * @param _duration az ágens hatásának ideje
	 */
	public Agent( int _duration, int _usable )
	{
		this.duration	= _duration;
		this.usable		= _usable;
	}

	/**
	 * Az ágens kifejti a hatását a paraméterként kapott virológusra
	 * @param _virologist a virológus, amin a hatását kifejti
	 */
	public abstract void affect( Virologist _virologist ) throws AgentBlockedException;

	/**
	 * Az ágens felhasználhatósági idejét csökkenti
	 * @return az ágens felhasználható-e
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
	 * Az ágens hatásának idejét csökkenti
	 * @return az ágens felhasználható-e
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
