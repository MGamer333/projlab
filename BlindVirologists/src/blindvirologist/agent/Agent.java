package blindvirologist.agent;
	import blindvirologist.Virologist;
	import customexceptions.AgentBlockedException;


/**
 * Az ágenseket megvalósító absztrakt osztály.
 * A vírusok és vakcinák megvalósításához szükséges osztály
 * Egy ágenst reprezentál amit készíteni és szórni lehet.
 */
public abstract class Agent
{
	/**
	 * usableDecreaseValue: statikus változó, azt határozza meg, hogy körönként hány egységgel
	 * csökkenjen az ágens használati, és hatási ideje
	 */
	private static int 	usableDecreaseValue = 1;

	/**
	 * duration: az ágens hatásának ideje, addig érvényesül a hatása amíg ez nullára nem csökken
	 */
	protected int		duration;

	/**
	 * usable: az ágens elkészítése utáni felhasználhatósági ideje, addig szórható az ágens,
	 * amíg ez az érték nullára nem csökken
	 */
	protected int 		usable;

	/**
	 * code: az ágenshez tartozó genetikai kód, az ágens készítéséhez szükséges kód,
	 * ami laborok falára van felvésve, és onnan tanulható
	 */
	private GeneticCode code;


	/**
	 * Konstruktor
	 * Beállítja a privát változók értékeit
	 * @param _duration az ágens hatásának ideje
	 */
	public Agent( int _duration, int _usable )
	{
		this.duration	= _duration;
		this.usable		= _usable;
	}

	/**
	 * Az ágens kifejti a hatását a paraméterként kapott virológusra
	 * Egy ágens szórását valósítja meg
	 * @param _virologist a virológus, amin a hatását kifejti
	 * @throws AgentBlockedException amikor a virológus akire az ágenst szórják,
	 * kivédi a szórást
	 */
	public abstract void affect( Virologist _virologist ) throws AgentBlockedException;

	/**
	 * Az ágens miután lejárt az ideje le kell vennie a hatását a virológusról
	 * @param _virologist a virológus
	 */
	public abstract void remove( Virologist _virologist);

	/**
	 * Az ágens felhasználhatósági idejét csökkenti és megadja,
	 * hogy az felhasználható-e még
	 * Minden játékkör végén csökkenteni kell a felhasználhatósági id?t
	 * @return az ágens felhasználható-e
	 */
	public boolean decreaseUsableTime()
	{
		this.usable--;

		return this.usable > 0;
	}

	/**
	 * Az ágens virológusra kifejtett hatásának idejét csökkenti és megadja,
	 * hogy az hat-e még
	 * Minden játékkör végén csökkenteni kell a felhasználhatósági id?t
	 * @return az ágens felhasználható-e
	 */
	public boolean decreaseDuration()
	{
		this.duration--;

		return this.duration > 0;
	}

	@Override
	public abstract String toString();
}
