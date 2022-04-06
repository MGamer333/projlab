package customexceptions;

/**
 * Ha a virológus fel akar venni egy új eszközt vagy alapanyagot, de már rendelkezik
 * hárommal, akkor dobódik a kivétel
 */
public class InventoryFullException extends Exception
{
    public InventoryFullException(String msg)
    {
        super(msg);
    }
}
