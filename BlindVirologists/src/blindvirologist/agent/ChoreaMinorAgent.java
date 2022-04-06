package blindvirologist.agent;

import blindvirologist.Virologist;
import customexceptions.AgentBlockedException;

public class ChoreaMinorAgent extends Agent
{
    /**
     * Konstruktor
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public ChoreaMinorAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
    }

    /**
     * Ágens akcióját kifejtő metódus
     * @param _virologist a virológus, amin a hatását kifejti
     * @throws Exception
     */
    @Override
    public void affect( Virologist _virologist ) throws AgentBlockedException
    {
        int random = (int)((Math.random() * (_virologist.getLocation().getNeighbors().size() - 1)) + 0);
        _virologist.move( _virologist.getLocation().getNeighbors().get( random ) );
    }
}
