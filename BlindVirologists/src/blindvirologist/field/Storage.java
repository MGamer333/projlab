package blindvirologist.field;
    import blindvirologist.Virologist;
    import skeleton.Logger;


/**
 * Raktár mező, egy mező típus.
 * Ezen a mezőn gyűjthet alapanyagot a rajta álló virológus
 */
public class Storage extends Field
{
    /**
     * aminoacidCount: A raktárban levő aminósav mennyiség
     */
    private int aminoacidCount;

    /**
     * nucleotidCount: A raktárban levő nukleotid mennyiség
     */
    private int nucleotidCount;


    /**
     * Konstruktor
     * Beállítja a privát adattagok értékeit
     * @param _aminoacid a mezőn lévő aminósavak
     * @param _nucleotid a mezőn lévő nukleotidok
     */
    public Storage( int _aminoacid, int _nucleotid )
    {
        this.aminoacidCount = _aminoacid;
        this.nucleotidCount = _nucleotid;
    }

    /**
     * Visszaad egy bizonyos mennyiségű aminósavat (maximum a mezőn lévő mennyiséget)
     * @return aminósav(ak)
     */
    public int getAminoacidCount()
    {
        // TODO
        Logger.log(">", "Storage", "Aminosav lekerese");
        Logger.log("<", "Storage", "");
        return aminoacidCount;
    }

    /**
     * Visszaad egy bizonyos mennyiségű nukleotidot (maximum a mezőn lévő mennyiséget)
     * @return nukleotid(ok)
     */
    public int getNucleotidCount()
    {
        // TODO
        Logger.log(">", "Storage", "Nukleotid lekerese");
        Logger.log("<", "Storage", "C");
        return nucleotidCount;
    }

    /**
     * Raktárban levő nukleotid mennyiségét beállító setter metódus.
     * Nukleotid levonása
     * @param nucleotidCount
     */
    public void setNucleotidCount(int nucleotidCount) {
        Logger.log(">", "Storage", "Nukleotid levonasa");
        this.nucleotidCount = nucleotidCount;
        Logger.log("<", "Storage", "");
    }

    /**
     * Raktárban levő aminósav mennyiségét beállító setter metódus.
     * Aminósav levonása
     * @param aminoacidCount
     */
    public void setAminoacidCount(int aminoacidCount) {
        Logger.log(">", "Storage", "Aminosav levonasa");
        this.aminoacidCount = aminoacidCount;
        Logger.log("<", "Storage", "");
    }

    /**
     * TODO
     * MEDVEVÍRUS
     * @param _virologist a virológus
     */
    @Override
    public void addVirologist( Virologist _virologist )
    {
        this.currentVirologist = _virologist;

        if ( _virologist.isBearAffected() )
        {
            this.aminoacidCount = 0;
            this.nucleotidCount = 0;
        }
    }
}
