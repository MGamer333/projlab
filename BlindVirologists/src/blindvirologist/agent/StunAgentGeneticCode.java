package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Bénító vírus osztály genetikai kódja, egy genetikai kód
 */
public class StunAgentGeneticCode extends GeneticCode
{
    /**
     * duration: Statikus változó, a szórt ágens hatásának idejét határozza meg
     */
    public static int duration = 4;

    /**
     * Konstruktor
     * Beállítja a privát adattagjainak értékét
     * @param _nucleotid nukleotid költség
     * @param _aminoacid aminósav költség
     */
    public StunAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehoz egy bénító vírust
     * @return a bénító vírus
     */
    @Override
    public Agent createAgent()
    {
        return new StunAgent( StunAgentGeneticCode.duration, Virologist.usableAfterCreation );
    }

    @Override
    public String toString() {
        return "StunAgent";
    }
}
