package blindvirologist.collectible;
    import blindvirologist.Virologist;
    import customexceptions.GloveException;
    import customexceptions.ItemBrokeException;


/**
 * Védőkesztyű osztály, egy eszköz.
 * A viselőjére szórt ágenseket visszaszórja arra a virológusra aki azt szórta
 */
public class GloveEquipment extends Equipment
{
    /**
     * usableCount: kesztyű hatásának száma, hányszor használható még a kesztyű
     */
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
     * A védőkesztyű akcióbeli hatása: visszaszórja az ágenst arra a virológusra aki szórta
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
     * A védőkesztyűnek nincs felvételkori hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void appendEffect(Virologist _virologist)
    {
        return;
    }

    /**
     * A védőkesztyűnek nincs eldobáskori hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect(Virologist _virologist)
    {
        return;
    }

    @Override
    public String toString() {
        return "GloveEquipment{" +
                "usableCount=" + usableCount +
                '}';
    }
}
