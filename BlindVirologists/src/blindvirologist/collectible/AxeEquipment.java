package blindvirologist.collectible;

import blindvirologist.Virologist;

/**
 * Balta eszköz osztály
 */
public class AxeEquipment extends Equipment
{
    private boolean usable;

    public AxeEquipment()
    {
        this.usable = true;
    }

    @Override
    public void handleEffect(Virologist _virologist) throws Exception
    {
        return;
    }

    @Override
    public void appendEffect(Virologist _virologist) {
        return;
    }

    @Override
    public void removeEffect(Virologist _virologist) {
        return;
    }

    /**
     * Balta használat
     */
    public void use( Virologist _virologist )
    {
        if ( this.usable )
        {
            this.usable = false;
            _virologist.die();
        }
    }
}
