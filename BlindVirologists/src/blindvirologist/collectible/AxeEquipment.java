package blindvirologist.collectible;
    import blindvirologist.Virologist;


/**
 * Balta osztály, egy eszköz.
 * A medvevírussal fertőzött virológusra használható, annak fertőzését
 * megszűnteti, azonnal megöli a virológust
 */
public class AxeEquipment extends Equipment
{
    /**
     * usable: Azt határozza meg hogy a balta használható-e
     */
    private boolean usable;


    /**
     * Konstruktor
     * Beállítja a privát adattagjainak értékét
     */
    public AxeEquipment()
    {
        this.usable = true;
    }

    /**
     * A baltának nincs akcióbeli hatása
     * @param _virologist a viselő virológus
     * @throws Exception
     */
    @Override
    public void handleEffect(Virologist _virologist) throws Exception
    {
        // TODO
    }

    /**
     * A balta felvételekor nem történik hatás
     * @param _virologist a viselő virológus
     */
    @Override
    public void appendEffect(Virologist _virologist) {
        return;
    }

    /**
     * A balta elvételekor nem történik hatás
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect(Virologist _virologist) {
        return;
    }

    /**
     * Balta használata egy virológusra.
     * A virológus akire elsütik a baltát, azonnal meghal
     */
    public boolean use( Virologist _virologist )
    {
        if ( this.usable )
        {
            this.usable = false;
            _virologist.die();

            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "AxeEquipment{" +
                "usable=" + usable +
                '}';
    }
}
