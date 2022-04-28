package customexceptions;

/**
 * Ha a virológuson védőkesztyű van, és rászórtak egy ágenst, akkor dobódik
 * a kivétel
 */
public class GloveException extends Exception
{
    /**
     * Konstruktor
     * Továbbítja a hibaüzenetet az ősosztálynak
     * @param msg a  hibaüzenet
     */
    public GloveException(String msg)
    {
        super(msg);
    }
}
