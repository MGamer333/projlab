package blindvirologist;

//a v�rusokat megval�s�t� absztakt oszt�ly
public abstract class Virus extends Agent
{

	//konstruktor
	public Virus(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
}
