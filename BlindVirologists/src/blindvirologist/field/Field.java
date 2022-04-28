package blindvirologist.field;
	import blindvirologist.Virologist;
	import blindvirologist.agent.GeneticCode;
	import blindvirologist.collectible.Equipment;
	import skeleton.Logger;
	import java.util.ArrayList;
	import java.util.List;


/**
 * A játéktér mezőit megvalósító osztály.
 * A mezőhöz tartozó funkciókat valósítja meg. Reprezentál egy általános típusú mezőt
 */
public class Field
{
	/**
	 * neighbors: A mező szomszédai
	 */
	private List<Field>		neighbors;

	/**
	 * currentVirologist: a mezőn álló virológus
	 */
	protected Virologist 		currentVirologist;

	/**
	 * Konstruktor
	 * Beállítja a privát adattagok értékeit
	 */
	public Field()
	{
		this.currentVirologist 	= null;
		this.neighbors 			= new ArrayList<>();
	}

	/**
	 * Megadja a paraméterben kapott mezőröl, hogy az szomszédja-e annak
	 * @param _field a kérdezett mező
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
	 * TODO
	 * @return
	 */
	public Virologist getCurrentVirologist() {
		return currentVirologist;
	}

	/**
	 * Leveszi a mezőről a rajta álló virológust
	 * @return a mezőn álló virológus
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
	 * Hozzáadja a mezőhöz a paraméterben kapott virológust
	 * @param _virologist a virológus
	 */
	public void addVirologist( Virologist _virologist )
	{
		Logger.log(">", "Virologist", "Virologus hozzaadasa a mezohoz");
		this.currentVirologist = _virologist;
		Logger.log("<", "Virologist", "");
	}

	/**
	 * Hozzáadja a paraméterben kapott mezőt a szomszédjai listához
	 * @param _neighbor a szomszédos mező
	 */
	public void addNeighbor( Field _neighbor )
	{
		if ( this.neighbors.contains( _neighbor ) ) return;

		this.neighbors.add( _neighbor );
		_neighbor.neighbors.add( this );
	}

	/**
	 * Szomszédos mezőknek a gettere
	 * @return szomszédos mezők
	 */
	public List<Field> getNeighbors()
	{
		return neighbors;
	}


	/**
	 * A mezőrelépéskor történő akció.
	 * Akkor fut le amikor egy virológus a mezőre lép
	 * @param _virologist
	 */
	public void action( Virologist _virologist )
	{
		// TODO
		return;
	};



	public boolean addEquipment( Equipment _equipment )
	{
		return false;
	}

	/**
	 * TODO
	 * @param code
	 */
	public boolean setGeneticCode(GeneticCode code)
	{
		return false;
	}

	/**
	 * TODO
	 * @param affected
	 * @return
	 */
	public boolean setAffected(boolean affected)
	{
		return false;
	}

	/**
	 * TODO
	 * @return
	 */
	public GeneticCode getGeneticCode()
	{
		return null;
	}

	public Equipment getEquipment()
	{
		return null;
	}

	public void removeEquipment()
	{
	}
}
