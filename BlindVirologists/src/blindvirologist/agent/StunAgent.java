package blindvirologist.agent;

import blindvirologist.Virologist;

public class StunAgent extends Agent
{
    /**
     * Konstruktor
     * @param _duration
     * @param _usable
     */
    public StunAgent( int _duration, int _usable )
    {
        super( _duration, _usable );
    }

    /**
     * Hatáskifejtő metódus az ágensnek
     * @param _virologist a virológus, amin a hatását kifejti
     */
    @Override
    public void affect( Virologist _virologist )
    {
        _virologist.setStunned( this.duration > 0 );
    }
}
