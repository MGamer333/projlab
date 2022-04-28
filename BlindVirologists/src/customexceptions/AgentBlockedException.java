package customexceptions;

/**
 * Egy vírus szórása során, ha akire szórták kivédi, akkor dobódik a kivétel
 */
public class AgentBlockedException extends Exception
{
    /**
     * Konstruktor
     * Továbbítja a hibaüzenetet az ősosztálynak
     * @param msg a  hibaüzenet
     */
    public AgentBlockedException(String msg)
    {
        super(msg);
    }
}
