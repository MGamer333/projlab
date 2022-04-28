package blindvirologist.agent;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;


/**
 * Vítustánc osztály, egy ágens típus.
 * A vítustánccal fertőzött virológus kontrollálatlanul kezd el a pályán lépkedni
 */
public class ChoreaMinorAgent extends Agent
{
    /**
     * Konstruktor
     * Beállítja a privát adattagok értékeit
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public ChoreaMinorAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
    }

    /**
     * A vítustánc vírus kifejti a hatását a virológusra
     * @param _virologist a virológus, amin a hatását kifejti
     * @throws Exception ha a virológus kivédi a szórást
     */
    @Override
    public void affect( Virologist _virologist ) throws AgentBlockedException
    {
        _virologist.setChoreaAffected(true);
    }

    /**
     * Leveszi a vítustánc hatását a virológusról
     * @param _virologist a virológus
     */
    @Override
    public void remove(Virologist _virologist)
    {
        _virologist.setChoreaAffected(false);
    }

    @Override
    public String toString() {
        return "ChoreaMinorAgent{duration="+duration+", usable="+usable+"}";
    }
}
