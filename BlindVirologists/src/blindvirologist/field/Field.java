package blindvirologist.field;
	import blindvirologist.Virologist;
	import skeleton.Logger;

	import java.util.ArrayList;
	import java.util.List;


/**
 * A j�t�kt�r mez?it megval�s�t� alap�rtelmezett mez? ?soszt�ly
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
	 * Megadja a param�terben kapott mez?r�l, hogy az szomsz�dja-e
	 * @param _field a k�rdezett mez?
	 * @return szomsz�dja-e
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
	 * Leveszi a mez?r?l a rajta �ll� virol�gust
	 * @return a mez?n �ll� virol�gus
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
	 * Hozz�adja a mez?h�z a param�terben kapott virol�gust
	 * @param _virologist a virol�gus
	 */
	public void addVirologist( Virologist _virologist )
	{
		Logger.log(">", "Virologist", "Virologus hozzaadasa a mezohoz");
		this.currentVirologist = _virologist;
		Logger.log("<", "Virologist", "");
	}

	/**
	 * Hozz�adja a param�terben kapott mez?t a szomsz�djai list�hoz
	 * @param _neighbor szomsz�dos mez?
	 */
	public void addNeighbor( Field _neighbor )
	{
		this.neighbors.add( _neighbor );
	}

	/**
	 * Szomsz�dos mez?knek a gettere
	 * @return szomsz�dos mez?k
	 */
	public List<Field> getNeighbors()
	{
		return neighbors;
	}
}
