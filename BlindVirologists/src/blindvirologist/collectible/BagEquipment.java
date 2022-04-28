package blindvirologist.collectible;
    import blindvirologist.Virologist;


/**
 * Zsák osztály, egy eszköz.
 * Viselőjének anyaggyűjtő képessége megnő
 */
public class BagEquipment extends Equipment
{
    /**
     * increase: Ennyivel nő a viselő virológus anyaggyűjtő mértéke
     */
    private int     increase;

    /**
     * done: Biztosítja, hogy a zsák hatása csak egyszer kerüljön a virológusra
     */
    private boolean done = false;

    /**
     * DefaultBagSize: zsák alapértelmezett mérete
     */
    public static int DefaultBagSize    = 3;


    /**
     * Konstruktor
     * Beállítja a privát adattagok értékeit
     * @param _count a növelés mértéke
     */
    public BagEquipment( int _count )
    {
        this.increase = _count;
    }

    /**
     * Megnöveli a virológus anyaghordó méretét, a tárgy felvételekor
     * @param _virologist a viselő virológus
     */
    @Override
    public void appendEffect( Virologist _virologist )
    {
        if ( !done )
        {
            _virologist.setAminoacidCount( _virologist.getAminoacidCount() + this.increase );
            _virologist.setNucleotidCount(_virologist.getNucleotidCount() + this.increase );

            done = !done;
        }
    }

    /**
     * Leveszi a zsák hatását a virológusról, a tárgy eldobásakor
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect( Virologist _virologist )
    {
        _virologist.setAminoacidCount( _virologist.getAminoacidCount() - this.increase );
        _virologist.setNucleotidCount( _virologist.getNucleotidCount() - this.increase );

        done = false;
    }

    /**
     * A zsáknak nincs akcióbeli hatása
     * @param _virologist virológus
     */
    @Override
    public void handleEffect(Virologist _virologist)
    {
        return;
    }

    @Override
    public String toString() {
        return "BagEquipment{" +
                "increase=" + increase +
                ", done=" + done +
                '}';
    }
}
