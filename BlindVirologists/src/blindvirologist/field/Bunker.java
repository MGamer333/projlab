package blindvirologist.field;
    import blindvirologist.collectible.Equipment;

    import java.util.ArrayList;
    import java.util.List;


/**
 * Óvóhely mező
 * Ezen a mezőn vehet fel védőfelszereléseket
 * a rajt álló virológus
 */
public class Bunker extends Field
{
    private List<Equipment>	equipmentList;

    /**
     * Konstruktor
     */
    public Bunker()
    {
        this.equipmentList = new ArrayList<>();
    }

    /**
     * Hozzáadja a mezőhöz az eldobott felszerelést
     * @param _equipment felszerelés
     */
    public void addEquipment( Equipment _equipment )
    {
        this.equipmentList.add( _equipment );
    }

    /**
     * Visszaadja a mezőn levő összes eldobott felszerelést
     * @return a mezőn lévő felszerelések
     */
    public List<Equipment> getEquipmentList()
    {
        return this.equipmentList;
    }

    /**
     * Leveszi a mezőről a felszerelést
     * @param _equipment felszerelés
     */
    public void removeEquipment( Equipment _equipment )
    {
        this.equipmentList.remove( _equipment );
    }
}
