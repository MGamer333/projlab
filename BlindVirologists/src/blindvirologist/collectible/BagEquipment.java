package blindvirologist.collectible;

import blindvirologist.Virologist;

public class BagEquipment extends Equipment
{
    private int     increase;
    private boolean done = false;

    /**
     * Konstruktor
     * @param _count a növelés mértéke
     */
    public BagEquipment( int _count )
    {
        this.increase = _count;
    }

    /**
     * Megnöveli a virológus anyaghordó méretét
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
     * Leveszi a felszerelés hatását a virológusról
     * @param _virologist a viselő virológus
     */
    @Override
    public void removeEffect( Virologist _virologist )
    {
        _virologist.setAminoacidCount( _virologist.getAminoacidCount() - this.increase );
        _virologist.setNucleotidCount( _virologist.getNucleotidCount() - this.increase );
    }

    /**
     * Ennek a felszerelésnek nincs ilyen haszna
     * @param _virologist virológus
     */
    @Override
    public void handleEffect(Virologist _virologist)
    {
        return;
    }
}
