package blindvirologist.agent;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;


/**
 * Ágenst kivédő vakcina osztály, egy ágens típus.
 * Akire szórják, védelmet nyer a más virológusok által szóródó ágensektől
 */
public class ProtectAgent extends Agent
{
    /**
     * Konstruktor
     * Beállítja a privát adattagjainak értékét
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public ProtectAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
    }

    /**
     * A kivédő vakcina kifejti a hatását a virológusra
     * @param _virologist a virológus, amin a hatását kifejti
     */
    @Override
    public void affect(Virologist _virologist) throws AgentBlockedException
    {
        throw new AgentBlockedException( "Vírus blokkolva" );
    }

    @Override
    public void remove(Virologist _virologist) {
        return;
    }

    @Override
    public String toString() {
        return "ProtectAgent{duration="+duration+", usable="+usable+"}";
    }
}
