package blindvirologist;

//a kódot megvalósító osztály
public class Code extends Collectible
{
	//attribútumok
	private int MaxDuration;
	private String Type;
	

	//konstruktor
	public Code(String Name, int MaxDuration, String Type) 
	{
		super(Name);
		this.MaxDuration=MaxDuration;
		this.Type=Type;
	}
	
	//metódusok

	@Override
	public String GetType()
	{
		return Type;
	}
	
	/**
	 * A kód alapján egy ágens létrehozása
	 * @param virus true, ha vírus, false, ha vakcina
	 * @return a kapott ágens
	 * @throws Exception hiba/ismeretlen ágens típus esetén
	 */
	Agent CreateAgent(boolean virus) throws Exception
	{
		switch(Type)
		{
		case("ChoreaMinor"):
			if(virus)
				return new ChoreaMinor(MaxDuration, MaxDuration, null);
				return new AntiChoreaMinor(MaxDuration, MaxDuration, null);
		case("Forget"):
			if(virus)
				return new Forget(MaxDuration, MaxDuration, null);
				return new AntiForget(MaxDuration, MaxDuration, null);
		case("Stun"):
			if(virus)
				return new Stun(MaxDuration, MaxDuration, null);
				return new AntiStun(MaxDuration, MaxDuration, null);
		default:
			throw new Exception("Unknown agent type in code: "+ this.GetName());
		}
		//return null; ha nem szeretné
	}

}
