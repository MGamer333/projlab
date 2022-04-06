package blindvirologist.agent;

/**
 * Ágens genetikai kódja
 */
public abstract class GeneticCode
{
    private int nucleotidCost;
    private int aminoacidCost;

    /**
     * Konstruktor
     * @param _nucleotid az ágens létrehozásához szükséges nukleotid mennyiség
     * @param _aminoacid az ágens létrehozásához szükséges aminósav mennyiség
     */
    public GeneticCode( int _nucleotid, int _aminoacid )
    {
        this.nucleotidCost = _nucleotid;
        this.aminoacidCost = _aminoacid;
    }

    /**
     * Létrehoz egy ágenst
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
}
