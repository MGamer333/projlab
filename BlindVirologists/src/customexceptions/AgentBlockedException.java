package customexceptions;

/**
 * Egy vírus szórása során, ha akire szórták kivédi, akkor dobódik a kivétel
 */
public class AgentBlockedException extends Exception
{
    public AgentBlockedException(String msg)
    {
        super(msg);
    }
}
