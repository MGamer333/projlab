package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Bénító ágens genetikai kódja
 */
public class StunAgentGeneticCode extends GeneticCode
{
    public static int duration = 4;

    /**
     * Konstruktor
     * @param _nucleotid
     * @param _aminoacid
     */
    public StunAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehoz egy ágenst
     * @return ágens
     */
    @Override
    public Agent createAgent()
    {
        return new StunAgent( StunAgentGeneticCode.duration, Virologist.usableAfterCreation );
    }
}
