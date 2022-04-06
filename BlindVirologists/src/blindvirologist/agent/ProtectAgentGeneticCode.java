package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Ágensektől megvédő vakcina osztály
 */
public class ProtectAgentGeneticCode extends GeneticCode
{
    public static int duration = 4;

    /**
     * Konstruktor
     * @param _nucleotid
     * @param _aminoacid
     */
    public ProtectAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Új ágenst létrehozó metódus
     * @return
     */
    @Override
    public Agent createAgent()
    {
        return new ProtectAgent( ProtectAgentGeneticCode.duration, Virologist.usableAfterCreation);
    }
}
