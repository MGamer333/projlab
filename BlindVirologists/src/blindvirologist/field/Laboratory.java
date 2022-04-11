package blindvirologist.field;

import blindvirologist.Virologist;
import blindvirologist.agent.BearVirusAgent;
import blindvirologist.agent.GeneticCode;
import customexceptions.GloveException;

/**
 * Laboratórium mező
 * Ezen a mezőn tapogathat le genetikai kódot
 * a rajt álló virológus
 */
public class Laboratory extends Field
{
    private GeneticCode code;
    private boolean affected;

    /**
     * Konstruktor
     * @param _code a laboratóriumban található genetikai kód
     */
    public Laboratory( GeneticCode _code )
    {
        this.code = _code;
        this.affected = false;
    }


    public void setAffected(boolean affected)
    {
        this.affected = affected;
    }

    public boolean isAffected()
    {
        return affected;
    }

    /**
     * A mezőn lévő genetikai kódot letapogató metódus
     * @return genetikai kód
     */
    public GeneticCode getGeneticCode()
    {
        return this.code;
    }

    @Override
    public void action(Virologist _virologist)
    {
        if ( this.affected )
        {
            try
            {
                _virologist.addAgent( new BearVirusAgent( -1, -1 ));
            }
            catch ( GloveException ex )
            {
                // TODO
            }
        }
    }
}
