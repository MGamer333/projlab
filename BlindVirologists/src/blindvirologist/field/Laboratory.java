package blindvirologist.field;

import blindvirologist.agent.GeneticCode;

/**
 * Laboratórium mező
 * Ezen a mezőn tapogathat le genetikai kódot
 * a rajt álló virológus
 */
public class Laboratory extends Field
{
    private GeneticCode code;

    /**
     * Konstruktor
     * @param _code a laboratóriumban található genetikai kód
     */
    public Laboratory( GeneticCode _code )
    {
        this.code = _code;
    }

    /**
     * A mezőn lévő genetikai kódot letapogató metódus
     * @return genetikai kód
     */
    public GeneticCode getGeneticCode()
    {
        return this.code;
    }
}
