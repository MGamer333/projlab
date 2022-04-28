package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Vitustánc vírus osztály genetikai kódja, egy genetikai kód
 */
public class ChoreaMinorAgentGeneticCode extends GeneticCode
{
    /**
     * duration: Statikus változó, a szórt ágens hatásának idejét határozza meg
     */
    public static int duration = 3;


    /**
     * Konstruktor
     * Beállítja a privát adattagok értékeit
     * @param _nucleotid nukleotid költség
     * @param _aminoacid aminósav költség
     */
    public ChoreaMinorAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehozza a vítustánc vírust
     * @return az ágens
     */
    @Override
    public Agent createAgent()
    {
        return new ChoreaMinorAgent( ChoreaMinorAgentGeneticCode.duration, Virologist.usableAfterCreation);
    }

    @Override
    public String toString() {
        return "ChoreaMinorAgent";
    }
}
