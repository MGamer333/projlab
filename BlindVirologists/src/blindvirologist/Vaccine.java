package blindvirologist;

//a vakcin�kat megval�s�t� absztakt oszt�ly
public abstract class Vaccine extends Agent
{
	
	//konstruktor
	
	public Vaccine(int MaxDuration, int LeftDuration, Virologist Host) 
	{
		super(MaxDuration, LeftDuration, Host);
		// TODO Auto-generated constructor stub
	}

	//met�dusok
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
