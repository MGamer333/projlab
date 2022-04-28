package game;

/**
 * TODO
 */
public class Response
{
    private int     status;
    private String  sender;
    private String  message;

    public Response( int s, String se, String m )
    {
        status  = s;
        sender  = se;
        message = m;
    }

    public String getMessage()
    {
        return message;
    }

    public int getStatus()
    {
        return status;
    }

    @Override
    public String toString()
    {
        return "[" + sender + "] " + message;
    }
}
