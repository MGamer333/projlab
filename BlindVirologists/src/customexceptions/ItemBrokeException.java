package customexceptions;

/**
 * Ha eltörött egy eszköz akkor dobódik a kivétel
 */
public class ItemBrokeException extends Exception
{
    /**
     * Konstruktor
     * Továbbítja a hibaüzenetet az ősosztálynak
     * @param msg a  hibaüzenet
     */
    public ItemBrokeException( String msg )
    {
        super( msg );
    }
}
