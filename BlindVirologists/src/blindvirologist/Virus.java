package blindvirologist;

//a vírusokat megvalósító absztakt osztály
public abstract class Virus extends Agent
{

	//konstruktor
	public Virus(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
	}
}
