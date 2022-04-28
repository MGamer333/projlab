package blindvirologist.agent;
    import blindvirologist.Virologist;
    import skeleton.Logger;

/**
 * Felejtés vírus osztály genetikai kódja, egy genetikai kód
 */
public class ForgetAgentGeneticCode extends GeneticCode
{
    /**
     * duration: Statikus változó, a szórt ágens hatásának idejét határozza meg
     */
    public static int duration = 1;

    /**
     * Konstruktor
     * Beállítja a privát adattagjainak értékét
     * @param _nucleotid nukleotid költség
     * @param _aminoacid aminósav költség
     */
    public ForgetAgentGeneticCode( int _nucleotid, int _aminoacid )
    {
        super( _nucleotid, _aminoacid );
    }

    /**
     * Létrehozza a felejtő vírust
     * @return az ágens
     */
    @Override
    public Agent createAgent()
    {
        Logger.log( ">", "ForgetAgentGeneticCode", "Agens letrehozasa genetikai kodbol" );

        ForgetAgent agent = new ForgetAgent( ForgetAgentGeneticCode.duration, Virologist.usableAfterCreation );

        Logger.log( "<", "ForgetAgentGeneticCode", "" );

        return agent;
    }

    @Override
    public String toString() {
        return "ForgetAgent";
    }
}
