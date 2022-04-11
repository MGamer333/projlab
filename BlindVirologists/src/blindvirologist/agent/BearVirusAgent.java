package blindvirologist.agent;

import blindvirologist.Virologist;
import customexceptions.AgentBlockedException;

public class BearVirusAgent extends Agent
{
    /**
     * Konstruktor
     * @param _duration ágens hatási ideje
     * @param _usable ágens meddig felhasználható
     */
    public BearVirusAgent( int _duration, int _usable )
    {
        super( _duration, _usable);
    }

    @Override
    public void affect(Virologist _virologist) throws AgentBlockedException {

    }
}
