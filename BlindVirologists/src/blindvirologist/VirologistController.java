package blindvirologist;

import java.util.ArrayList;

// az ellenséges virológusokat irányító controller
public class VirologistController implements INextTurn
{
	//attribútumok
	ArrayList<Virologist> ControlledVirologists;
	
	//konstruktor
	public VirologistController()
	{
		this.ControlledVirologists=new ArrayList<Virologist>();
	}
	
	//metódusok
	
	/**
	 * 
	 * @param NewVirologist
	 */
	public void AddVirologist(Virologist NewVirologist)
	{
		this.ControlledVirologists.add(NewVirologist);
	}

	@Override
	public void NextTurn() 
	{
		for(Virologist v : ControlledVirologists)
		{
			//TODO lépések generálása
		}
	}
}
