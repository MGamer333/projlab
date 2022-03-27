package blindvirologist;

import customexceptions.GloveException;
import customexceptions.VirusBlockedException;

//a v�d�felszerel�seket megval�s�t� absztakt oszt�ly
public abstract class Equipment extends Collectible
{

	//konstruktor
	public Equipment(String Name) 
	{
		super(Name);
	}
	
	//met�dusok
	
	/**
	 * A felszerel�s m�k�d�s�t megval�s�t� f�ggv�ny
	 */
	public abstract void Effect() throws VirusBlockedException, GloveException;

}
