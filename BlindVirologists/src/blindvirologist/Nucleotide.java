package blindvirologist;

//a nukleotidokat megvalósító osztály
public class Nucleotide extends Material
{

	//konstruktor
	public Nucleotide(String Name) 
	{
		super(Name);
	}

	//metódusok
	
	
	@Override
	public String GetType() 
	{
		return "Nucleotide";
	}

}
