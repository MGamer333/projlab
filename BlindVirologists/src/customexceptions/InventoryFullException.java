package customexceptions;

/**
 * Ha a virológus fel akar venni egy új eszközt vagy alapanyagot, de már rendelkezik
 * hárommal, akkor dobódik a kivétel
 */
public class InventoryFullException extends Exception
{
    /**
     * Konstruktor
     * Továbbítja a hibaüzenetet az ősosztálynak
     * @param msg a  hibaüzenet
     */
    public InventoryFullException(String msg)
    {
        super(msg);
    }
}
