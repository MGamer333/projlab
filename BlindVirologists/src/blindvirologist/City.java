package blindvirologist;

import java.util.ArrayList;

public class City 
{
	//attrib�tumok
	private ArrayList<Field> Fields;
	
	//met�dusok
	/**
	 * Visszaadja a param�terk�nt megadott ID-j� mez�vel
	 * @param fieldName
	 * @return null ha nem tal�lta meg
	 */
	public Field GetField(String fieldID) //nem dob kiv�telt, �gy is lehetne
	{
		for(Field f : Fields)
		{
			if(f.GetID().equals(fieldID))
			{
				return f;
			}
		}
		return null;
	}
	
	/**
	 * Hozz�adja a param�terk�nt kapott �j mez�t a v�ros list�j�hoz
	 * @param newField
	 * @throws Exception ha m�r van az adott ID-vel rendelkez� mez� a list�ban
	 */
	public void Addfield(Field newField) throws Exception
	{
		for(Field f : Fields)
		{
			if(f.GetID().equals(newField.GetID()))
			{
				throw new Exception("This Field ID already exists");
			}
		}
		Fields.add(newField);
	}

}
