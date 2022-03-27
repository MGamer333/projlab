package blindvirologist;

import java.util.ArrayList;

public class City 
{
	//attribútumok
	private ArrayList<Field> Fields;
	
	//metódusok
	/**
	 * Visszaadja a paraméterként megadott ID-jû mezõvel
	 * @param fieldName
	 * @return null ha nem találta meg
	 */
	public Field GetField(String fieldID) //nem dob kivételt, úgy is lehetne
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
	 * Hozzáadja a paraméterként kapott új mezõt a város listájához
	 * @param newField
	 * @throws Exception ha már van az adott ID-vel rendelkezõ mezõ a listában
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
