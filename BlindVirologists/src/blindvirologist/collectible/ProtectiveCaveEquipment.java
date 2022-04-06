package blindvirologist.collectible;
    import blindvirologist.Virologist;
    import customexceptions.AgentBlockedException;


/**
 * Védőköpeny felszerelés
 */
public class ProtectiveCaveEquipment extends Equipment
{
    /**
     * Ennek nincs hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void appendEffect( Virologist _virologist )
    {
        return;
    }

    /**
     * Ennek nincs hatása
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect( Virologist _virologist )
    {
        return;
    }

    /**
     * Ágens szórás közben van hatása a virológusra
     * @param _virologist a viselő virológus
     */
    @Override
    public void handleEffect( Virologist _virologist ) throws AgentBlockedException
    {
        int random = (int)((Math.random() * (1000 - 1)) + 0);
        if ( random <= 823 ) throw new AgentBlockedException( "Védőköpeny" );
    }
}
