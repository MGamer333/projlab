package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Ágensektől megvédő vakcina osztály genetikai kódja, egy genetikai kód
 */
public class ProtectAgentGeneticCode extends GeneticCode
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
    public ProtectAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehozza az ágensektől megvédő vakcinát
     * @return az ágens
     */
    @Override
    public Agent createAgent()
    {
        return new ProtectAgent( ProtectAgentGeneticCode.duration, Virologist.usableAfterCreation);
    }

    @Override
    public String toString() {
        return "ProtectAgent";
    }
}
