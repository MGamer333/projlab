package blindvirologist.agent;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;


/**
 * Medvevírus osztály, egy ágens típus.
 * A medvevírussal fertőzött virológus minden útjába eső raktárat elpusztít,
 * és véletlenszerű mezőkre lép a pályán. Minden útjába eső virológust megfertőz
 * a medvevírussal
 */
public class BearVirusAgent extends Agent
{
    /**
     * Konstruktor
     * Beállítja a privát adattagok értékét
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public BearVirusAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
    }

    /**
     * A medvevírus kifejti a hatását a virológusra.
     * @param _virologist a virológus, amin a hatását kifejti
     * @throws AgentBlockedException ha a virológus kivédte a szórást
     */
    @Override
    public void affect(Virologist _virologist) throws AgentBlockedException
    {
        _virologist.setBearAffected(true);
    }

    @Override
    public void remove(Virologist _virologist)
    {
        return;
    }

    @Override
    public String toString() {
        return "BearVirus{}";
    }
}
