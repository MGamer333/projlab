package blindvirologist.agent;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;
    import skeleton.Logger;
    import java.util.ArrayList;


/**
 * Felejtés vírus osztály, egy ágens típus.
 * A virológus akire szórják, elfelejti az összes megtanult genetikai kódot
 */
public class ForgetAgent extends Agent
{
    /**
     * Konstruktor
     * Beállítja a privát adattagjainak értékét
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public ForgetAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
        Logger.log( ">", "ForgetAgent", "Agens letrehozasa" );
        Logger.log( "<", "ForgetAgent", "" );
    }

    /**
     * A felejtés vírus kifejti hatását a virológusra
     * @param _virologist a virológus, amin a hatását kifejti
     * @throws AgentBlockedException ha a virológus kivédi a szórást
     */
    @Override
    public void affect( Virologist _virologist ) throws AgentBlockedException
    {
        _virologist.getLearntCodes().clear();
    }

    @Override
    public void remove(Virologist _virologist) {
        return;
    }

    @Override
    public String toString() {
        return "ForgetAgent{duration="+duration+", usable="+usable+"}";
    }
}
