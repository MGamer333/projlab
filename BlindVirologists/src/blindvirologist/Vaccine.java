package blindvirologist;

//a vakcinákat megvalósító absztakt osztály
public abstract class Vaccine extends Agent
{
	
	//konstruktor
	
	public Vaccine(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
		// TODO Auto-generated constructor stub
	}

	//metódusok
	@Override
	public String GetType()
	{
		return "Vaccine";
	}
	
	@Override
	public void AffectHost()
	{
	}
}
