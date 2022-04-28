package blindvirologist.collectible;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;


/**
 * Védőköpeny osztály, egy eszköz.
 * Egy bizonyos százalékban kivédi a viselőjére szórt ágenseket
 */
public class ProtectiveCaveEquipment extends Equipment
{
    /**
     * A védőköpenynek nincs felvételkori hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void appendEffect( Virologist _virologist )
    {
        return;
    }

    /**
     * A védőköpenynek nincs eldobáskori hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect( Virologist _virologist )
    {
        return;
    }

    /**
     * A védőköpeny akcióbeli hatása.
     * Ágens szórásakor kivéd(heti) a szórt ágenst, egy bizonyos (82.3) százalékkal
     * @param _virologist a viselő virológus
     */
    @Override
    public void handleEffect( Virologist _virologist ) throws AgentBlockedException
    {
        int random = (int)((Math.random() * (1000 - 1)) + 0);
        if ( random <= 823 ) throw new AgentBlockedException( "Védőköpeny" );
    }

    @Override
    public String toString() {
        return "ProtectiveCaveEquipment{}";
    }
}
