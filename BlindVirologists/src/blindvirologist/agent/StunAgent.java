package blindvirologist.agent;
    import blindvirologist.Virologist;


/**
 * Bénító vírus osztály, egy ágens típus.
 * A virológus akire szórják, lebénult állapotba kerül, addig nem tud mozogni amíg hatása tart
 */
public class StunAgent extends Agent
{
    /**
     * Konstruktor
     * Beállítja a privát adattagjainak értékét
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public StunAgent( int _duration, int _usable )
    {
        super( _duration, _usable );
    }

    /**
     * A bénító vírus kifejti hatását a virológusra
     * @param _virologist a virológus, amin a hatását kifejti
     */
    @Override
    public void affect( Virologist _virologist )
    {
        _virologist.setStunned( this.duration > 0 );
    }

    @Override
    public void remove(Virologist _virologist) {
        return;
    }

    @Override
    public String toString() {
        return "StunAgent{duration="+duration+", usable="+usable+"}";
    }
}
