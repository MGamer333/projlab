package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Vitustánc vírus osztály genetikai kódja
 */
public class ChoreaMinorAgentGeneticCode extends GeneticCode
{
    public static int duration = 3;

    /**
     * Konstruktor
     * @param _nucleotid
     * @param _aminoacid
     */
    public ChoreaMinorAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehozza az ágenst
     * @return az ágens
     */
    @Override
    public Agent createAgent()
    {
        return new ChoreaMinorAgent( ChoreaMinorAgentGeneticCode.duration, Virologist.usableAfterCreation);
    }
}
