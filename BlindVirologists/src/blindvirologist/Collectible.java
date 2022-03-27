package blindvirologist;

//az összegyûjthetõ dolgokat megvalósító absztakt osztály
public abstract class Collectible 
{
	//attribútumok
	private String Name;
	
	//konstruktor
	public Collectible(String Name)
	{
		this.Name=Name;
	}
	
	//metódusok
	
	/**
	 * Az összegyûjthetõ dolog nevével visszatérõ függvény
	 * @return
	 */
	public String GetName()
	{
		return Name;
	}
	
	/**
	 * Az összegyûjthetõ dolog típusával visszatérõ osztály
	 * @return
	 */
	public abstract String GetType();

}
