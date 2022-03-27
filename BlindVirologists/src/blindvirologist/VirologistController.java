package blindvirologist;

import java.util.ArrayList;

// az ellens�ges virol�gusokat ir�ny�t� controller
public class VirologistController implements INextTurn
{
	//attrib�tumok
	ArrayList<Virologist> ControlledVirologists;
	
	//konstruktor
	public VirologistController()
	{
		this.ControlledVirologists=new ArrayList<Virologist>();
	}
	
	//met�dusok
	
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
			//TODO l�p�sek gener�l�sa
		}
	}
}
