package blindvirologist;

import java.util.Random;

import customexceptions.VirusBlockedException;

//a v�d�k�penyt megval�s�t� oszt�ly
public class Cloak extends Equipment
{

	public Cloak(String Name) 
	{
		super(Name);
	}

	/**
	 * 82.3%-os es�llyel VirusBlockedExceptiont dob.
	 * @throws VirusBlockedException
	 */
	@Override
	public void Effect() throws VirusBlockedException
	{
		Random rand = new Random();
		int defense=rand.nextInt(1000);
		if(defense<=823) throw new VirusBlockedException("A felken�s lev�dve");
	}

	@Override
	public String GetType() 
	{
		return "Cloak";
	}

}
