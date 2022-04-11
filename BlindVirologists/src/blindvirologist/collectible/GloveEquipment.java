package blindvirologist.collectible;
    import blindvirologist.Virologist;
    import customexceptions.GloveException;
    import customexceptions.ItemBrokeException;


/**
 * Védőkesztyű felszerelés
 */
public class GloveEquipment extends Equipment
{
    private int usableCount;

    /**
     * Konstruktor
     * Beállítja a kesztyű használhatóságának számát
     */
    public GloveEquipment()
    {
        this.usableCount = 3;
    }

    /**
     * Visszadobja a kenőre az ágenst
     * @param _virologist a viselő virológus
     * @throws Exception kivétel
     */
    @Override
    public void handleEffect(Virologist _virologist) throws GloveException, ItemBrokeException
    {
        if ( this.usableCount > 0 )
        {
            this.usableCount--;
            throw new GloveException( "Védőkesztyű" );
        }
    }

    /**
     * Nincs hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void appendEffect(Virologist _virologist)
    {
        return;
    }

    /**
     * Nincs hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect(Virologist _virologist)
    {
        return;
    }
}
