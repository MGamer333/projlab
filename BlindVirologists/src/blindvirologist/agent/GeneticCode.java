package blindvirologist.agent;

/**
 * Genetikai kódokat megvalósító osztály
 * Egy ágenshez tartozó genetikai kódot valósít meg.
 * Laboratóriumok falán található meg, onnan tanulható, és így szórható
 * lesz az ágens
 */
public abstract class GeneticCode
{
    /**
     * nucleotidCost: Az ágens készítéséhez szükséges nukleotid költség
     */
    private int nucleotidCost;

    /**
     * aminoacidCost: Az ágens készítéséhez szükséges aminósav költség
     */
    private int aminoacidCost;

    /**
     * TODO
     */
    public static int DefaultNucleotidCost = 1;

    /**
     * TODO
     */
    public static int DefaultAminoacidCost = 1;


    /**
     * Konstruktor
     * A privát adattagok értékeit állítja be
     * @param _nucleotid az ágens létrehozásához szükséges nukleotid mennyiség
     * @param _aminoacid az ágens létrehozásához szükséges aminósav mennyiség
     */
    public GeneticCode( int _nucleotid, int _aminoacid )
    {
        this.nucleotidCost = _nucleotid;
        this.aminoacidCost = _aminoacid;
    }

    /**
     * Ágenst létrehozó metódus. Elkészíti a kódhoz tartozó ágenst
     * @return a létrehozott ágens
     */
    public abstract Agent createAgent();

    /**
     * Az ágens létrehozásához szükséges nukleotid mennyiség getter
     * @return nukleotid mennyiség
     */
    public int getNucleotidCost()
    {
        return this.nucleotidCost;
    }

    /**
     * Az ágens létrehozásához szükséges aminósav mennyiség getter
     * @return aminósav mennyiség
     */
    public int getAminoacidCost()
    {
        return this.aminoacidCost;
    }


    public abstract String toString();
}
