package blindvirologist.collectible;
    import blindvirologist.Virologist;


/**
 * Felszerelések absztrakt ősosztálya
 */
public abstract class Equipment
{
    /**
     * Hozzáadja a védőfelszerelés hatását a virológushoz
     * @param _virologist a viselő virológus
     */
    public abstract void appendEffect( Virologist _virologist );

    /**
     * Elveszi a védőfelszerelés hatását a virológustól
     * @param _virologist a viselő virológus
     */
    public abstract void removeEffect( Virologist _virologist );

    /**
     * Akcióbeli hatása a felszerelésnek
     * @param _virologist a viselő virológus
     * @throws Exception kivétel
     */
    public abstract void handleEffect( Virologist _virologist ) throws Exception;
}
