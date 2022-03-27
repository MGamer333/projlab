package blindvirologist;

import java.util.ArrayList;

//a j�t�kot vez�nyl� oszt�ly
//TODO
public class Game 
{
	//attrib�tumok
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
	
	//met�dusok
	

	
	public void NextTurn() //TODO j�t�k nyer�s�nek ellen�rz�se �tlet: NextTurnList m�dos�t�sa
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
		System.out.println("V�ge a j�t�knak");
	}
	
	/**
	 * Hozz�ad egy k�dot a j�t�k nyilv�ntart�s�hoz
	 * @param newCode
	 */
	public void AddCode(Code newCode)
	{
		Codes.add(newCode);
	}
	
	/**
	 * Kiszed egy k�dot a j�t�k nyilv�ntart�s�b�l
	 * @param oldCode
	 */
	public void RemoveCode(Code oldCode)
	{
		Codes.remove(oldCode);
	}
	

}
