package blindvirologist.field;
    import blindvirologist.Virologist;
    import blindvirologist.agent.BearVirusAgent;
    import blindvirologist.agent.GeneticCode;
    import blindvirologist.collectible.Equipment;
    import customexceptions.GloveException;
    import skeleton.Logger;


/**
 * Laboratórium mező, egy mező típus.
 * Ezen a mezőn tapogathat le genetikai kódot a rajt álló virológus
 */
public class Laboratory extends Field
{
    /**
     * code: A laboratóriumban levő genetikai kód
     */
    private GeneticCode code;

    /**
     * affected: megadja, hogy a laboratórium fertőzött-e medvevírussal
     */
    private boolean affected;


    /**
     * Konstruktor
     * Beállítja a privát adattagok értékeit
     * @param _code a laboratóriumban található genetikai kód
     */
    public Laboratory( GeneticCode _code )
    {
        this.code = _code;
        this.affected = false;
    }

    /**
     * Laboratórium fertőzöttséget beállító setter
     * @param affected
     */
    public boolean setAffected(boolean affected)
    {
        this.affected = affected;
        return true;
    }

    /**
     * Megadja hogy a laboratórium fertőzött-e
     * @return laboratórium fertőzöttsége
     */
    public boolean isAffected()
    {
        return affected;
    }

    /**
     * A mezőn lévő genetikai kódot letapogató metódus
     * A virológus meg tudja tanulni a mezőn lévő genetikai kódot
     * @return genetikai kód
     */
    public GeneticCode getGeneticCode()
    {
        return this.code;
    }

    /**
     * TODO
     * @param code
     */
    public boolean setGeneticCode(GeneticCode code)
    {
        this.code = code;
        return true;
    }

    /**
     * Mezőrelépés akció.
     * Ha a labor fertőzött a medvevírussal, akkor az rászóródik a virológusra
     * @param _virologist a mezőre lépő virológus
     */
    @Override
    public void action(Virologist _virologist)
    {

    }

    /**
     * TODO
     * MEDVEVÍRUS
     * @param _virologist a virológus
     */
    @Override
    public void addVirologist( Virologist _virologist )
    {
        this.currentVirologist = _virologist;

        if ( this.affected )
        {
            try
            {
                _virologist.addAgent( new BearVirusAgent(-1, -1) );
            }
            catch (GloveException e) {}
            catch (Exception ex) {}
        }
    }
}
