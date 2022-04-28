package blindvirologist.collectible;
    import blindvirologist.Virologist;


/**
 * Felszerelés osztály.
 * Egy felszerelést reprezentál, annak hatásait valósítja meg
 */
public abstract class Equipment
{
    /**
     * Hozzáadja a védőfelszerelés hatását a virológushoz.
     * Amikor a virológus felveszi az eszközt akkor hívódik meg
     * @param _virologist a viselő virológus
     */
    public abstract void appendEffect( Virologist _virologist );

    /**
     * Elveszi a védőfelszerelés hatását a virológustól.
     * Amikor elvételre kerül a virológustól az eszköz, akkor hívódik meg
     * @param _virologist a viselő virológus
     */
    public abstract void removeEffect( Virologist _virologist );

    /**
     * Akcióbeli hatása a felszerelésnek.
     * Egyes felszereléseknek van akció közbeni hatása, annak hatását valósítja meg
     * @param _virologist a viselő virológus
     * @throws Exception ha kivédik az eszköz hatását
     */
    public abstract void handleEffect( Virologist _virologist ) throws Exception;

    /**
     * TODO
     * @param _virologist
     */
    public boolean use( Virologist _virologist ) {return false;};

    @Override
    public String toString() {
        return super.toString();
    }
}
