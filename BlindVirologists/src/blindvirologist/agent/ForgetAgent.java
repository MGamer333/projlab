package blindvirologist.agent;

import blindvirologist.Virologist;
import customexceptions.AgentBlockedException;
import skeleton.Logger;

import java.util.ArrayList;

public class ForgetAgent extends Agent
{
    /**
     * Konstruktor
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
     *
     * @param _virologist a virológus, amin a hatását kifejti
     * @return
     */
    @Override
    public void affect( Virologist _virologist ) throws AgentBlockedException
    {
        _virologist.setLearntCodes( new ArrayList<>() );
    }
}
