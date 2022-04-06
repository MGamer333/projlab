package blindvirologist.agent;
    import blindvirologist.Virologist;
    import skeleton.Logger;

/**
 * Elfelejtő vírus
 */
public class ForgetAgentGeneticCode extends GeneticCode
{
    public static int duration = 1;

    /**
     * Konstruktor
     * @param _nucleotid
     * @param _aminoacid
     */
    public ForgetAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehoz egy elfelejtő ágenst
     * @return egy új ágens
     */
    @Override
    public Agent createAgent()
    {
        Logger.log( ">", "ForgetAgentGeneticCode", "Agens letrehozasa genetikai kodbol" );

        ForgetAgent agent = new ForgetAgent( ForgetAgentGeneticCode.duration, Virologist.usableAfterCreation );

        Logger.log( "<", "ForgetAgentGeneticCode", "" );

        return agent;
    }
}
