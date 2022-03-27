package blindvirologist;

import java.util.ArrayList;

//a játékot vezénylõ osztály
//TODO
public class Game 
{
	//attribútumok
	private ArrayList<Code> Codes;
	private ArrayList<Virologist> Virologists;
	private VirologistController controller;
	private City Map;
	
	//konstruktor
	public Game()
	{
		Codes = new ArrayList<Code>();
		Virologists=new ArrayList<Virologist>();
		Map = new City();
	}
	
	//metódusok
	

	
	public void NextTurn() //TODO játék nyerésének ellenõrzése ötlet: NextTurnList módosítása
	{
		controller.NextTurn();
		for(Virologist v : Virologists)
		{
			v.NextTurn();
			if(v.GetCodes().size()==Codes.size()) //ha valaki nyert
				EndGame(v);
		}
	}
	
	public void StartGame()
	{
		//TODO
	}
	
	public void EndGame(Virologist winner)
	{
		//TODO
		System.out.println("Vége a játéknak");
	}
	
	/**
	 * Hozzáad egy küdot a játék nyilvántartásához
	 * @param newCode
	 */
	public void AddCode(Code newCode)
	{
		Codes.add(newCode);
	}
	
	/**
	 * Kiszed egy kódot a játék nyilvántartásából
	 * @param oldCode
	 */
	public void RemoveCode(Code oldCode)
	{
		Codes.remove(oldCode);
	}
	

}
