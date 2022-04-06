package blindvirologist.field;
	import blindvirologist.Virologist;
	import skeleton.Logger;

	import java.util.ArrayList;
	import java.util.List;


/**
 * A játéktér mez?it megvalósító alapértelmezett mez? ?sosztály
 */
public class Field
{
	private List<Field>		neighbors;
	private Virologist 		currentVirologist;

	/**
	 * Konstruktor
	 */
	public Field()
	{
		this.currentVirologist 	= null;
		this.neighbors 			= new ArrayList<>();
	}

	/**
	 * Megadja a paraméterben kapott mez?röl, hogy az szomszédja-e
	 * @param _field a kérdezett mez?
	 * @return szomszédja-e
	 */
	public boolean isNeighbor( Field _field )
	{
		Logger.log(">", "Field", "Field ellenorzi szomszedos mezo-e");
		for( Field neighbor : this.neighbors )
		{
			if ( neighbor == _field )
			{
				Logger.log(">", "Field", "A mezo szomszedos");
				return true;
			}
		}

		Logger.log(">", "Field", "A mezo nem szomszedos");
		Logger.log("<", "Field", "");
		return false;
	}

	/**
	 * Leveszi a mez?r?l a rajta álló virológust
	 * @return a mez?n álló virológus
	 */
	public Virologist removeVirologist()
	{
		Logger.log(">", "Field", "Virologus levetele a mezorol");
		Virologist tmp = this.currentVirologist;
		this.currentVirologist = null;
		Logger.log("<", "Virologist", "");

		return tmp;
	}

	/**
	 * Hozzáadja a mez?höz a paraméterben kapott virológust
	 * @param _virologist a virológus
	 */
	public void addVirologist( Virologist _virologist )
	{
		Logger.log(">", "Virologist", "Virologus hozzaadasa a mezohoz");
		this.currentVirologist = _virologist;
		Logger.log("<", "Virologist", "");
	}

	/**
	 * Hozzáadja a paraméterben kapott mez?t a szomszédjai listához
	 * @param _neighbor szomszédos mez?
	 */
	public void addNeighbor( Field _neighbor )
	{
		this.neighbors.add( _neighbor );
	}

	/**
	 * Szomszédos mez?knek a gettere
	 * @return szomszédos mez?k
	 */
	public List<Field> getNeighbors()
	{
		return neighbors;
	}
}
