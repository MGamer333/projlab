package customexceptions;

/**
 * Ha eltörött egy eszköz akkor dobódik a kivétel
 */
public class ItemBrokeException extends Exception
{
    public ItemBrokeException( String msg )
    {
        super( msg );
    }
}
