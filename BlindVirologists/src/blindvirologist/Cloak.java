package blindvirologist;

import java.util.Random;

import customexceptions.VirusBlockedException;

//a védõköpenyt megvalósító osztály
public class Cloak extends Equipment
{

	public Cloak(String Name) 
	{
		super(Name);
	}

	/**
	 * 82.3%-os eséllyel VirusBlockedExceptiont dob.
	 * @throws VirusBlockedException
	 */
	@Override
	public void Effect() throws VirusBlockedException
	{
		Random rand = new Random();
		int defense=rand.nextInt(1000);
		if(defense<=823) throw new VirusBlockedException("A felkenés levédve");
	}

	@Override
	public String GetType() 
	{
		return "Cloak";
	}

}
