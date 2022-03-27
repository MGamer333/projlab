package blindvirologist;

//az �sszegy�jthet� dolgokat megval�s�t� absztakt oszt�ly
public abstract class Collectible 
{
	//attrib�tumok
	private String Name;
	
	//konstruktor
	public Collectible(String Name)
	{
		this.Name=Name;
	}
	
	//met�dusok
	
	/**
	 * Az �sszegy�jthet� dolog nev�vel visszat�r� f�ggv�ny
	 * @return
	 */
	public String GetName()
	{
		return Name;
	}
	
	/**
	 * Az �sszegy�jthet� dolog t�pus�val visszat�r� oszt�ly
	 * @return
	 */
	public abstract String GetType();

}
