package blindvirologist;

//a nukleotidokat megval�s�t� oszt�ly
public class Nucleotide extends Material
{

	//konstruktor
	public Nucleotide(String Name) 
	{
		super(Name);
	}

	//met�dusok
	
	
	@Override
	public String GetType() 
	{
		return "Nucleotide";
	}

}
