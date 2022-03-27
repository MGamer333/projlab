package blindvirologist;

//a k�dot megval�s�t� oszt�ly
public class Code extends Collectible
{
	//attrib�tumok
	private int MaxDuration;
	private String Type;
	

	//konstruktor
	public Code(String Name, int MaxDuration, String Type) 
	{
		super(Name);
		this.MaxDuration=MaxDuration;
		this.Type=Type;
	}
	
	//met�dusok

	@Override
	public String GetType()
	{
		return Type;
	}
	
	/**
	 * A k�d alapj�n egy �gens l�trehoz�sa
	 * @param virus true, ha v�rus, false, ha vakcina
	 * @return a kapott �gens
	 * @throws Exception hiba/ismeretlen �gens t�pus eset�n
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
		//return null; ha nem szeretn�
	}

}
