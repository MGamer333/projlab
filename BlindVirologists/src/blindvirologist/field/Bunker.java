package blindvirologist.field;
    import blindvirologist.collectible.Equipment;
    import java.util.ArrayList;
    import java.util.List;


/**
 * Óvóhely mező, egy mező típus.
 * Ezen a mezőn vehet fel védőfelszereléseket a rajt álló virológus
 */
public class Bunker extends Field
{
    /**
     * equipmentList: A mezőn levő eszközök
     */
    private Equipment	equipment;

    /**
     * Konstruktor
     * Beállítja a privát adattafok értékeit
     */
    public Bunker()
    {
    }

    /**
     * Hozzáadja a mezőhöz a paraméterben kapott felszerelést
     * @param _equipment felszerelés
     */
    public boolean addEquipment( Equipment _equipment )
    {
        this.equipment = _equipment;
        return true;
    }

    /**
     * Visszaadja a mezőn levő összes eldobott felszerelést
     * @return a mezőn lévő felszerelések
     */
    public Equipment getEquipment()
    {
        return this.equipment;
    }

    /**
     * Leveszi a mezőről a paraméterben megadott felszerelést
     */
    public void removeEquipment()
    {
        this.equipment = null;
    }
}
