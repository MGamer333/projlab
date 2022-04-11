package blindvirologist.field;

import skeleton.Logger;

/**
 * Raktár mező
 * Ezen a mezőn gyűjthet alapanyagot a rajt
 * álló virológus
 */
public class Storage extends Field
{
    private int aminoacidCount;
    private int nucleotidCount;

    /**
     * Konstruktor
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
        Logger.log(">", "Storage", "Nukleotid lekerese");
        Logger.log("<", "Storage", "C");
        return nucleotidCount;
    }

    /**
     * Setter
     * @param nucleotidCount
     */
    public void setNucleotidCount(int nucleotidCount) {
        Logger.log(">", "Storage", "Nukleotid levonasa");
        this.nucleotidCount = nucleotidCount;
        Logger.log("<", "Storage", "");
    }

    /**
     * Setter
     * @param aminoacidCount
     */
    public void setAminoacidCount(int aminoacidCount) {
        Logger.log(">", "Storage", "Aminosav levonasa");
        this.aminoacidCount = aminoacidCount;
        Logger.log("<", "Storage", "");
    }
}
