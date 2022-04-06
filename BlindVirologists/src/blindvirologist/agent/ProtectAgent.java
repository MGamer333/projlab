package blindvirologist.agent;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;


/**
 * Ágenst kivédő vakcina osztálya
 */
public class ProtectAgent extends Agent
{
    /**
     * Konstruktor
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public ProtectAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
    }

    /**
     * Hatását kifejtő metódus az ágensnek
     * @param _virologist a virológus, amin a hatását kifejti
     */
    @Override
    public void affect(Virologist _virologist) throws AgentBlockedException
    {
        throw new AgentBlockedException( "Vírus blokkolva" );
    }
}
