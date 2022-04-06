package customexceptions;

/**
 * Ha a virológuson védőkesztyű van, és rászórtak egy ágenst, akkor dobódik
 * a kivétel
 */
public class GloveException extends Exception
{
    public GloveException(String msg)
    {
        super(msg);
    }

}
