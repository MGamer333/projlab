package customexceptions;

public class InventoryFullException extends Exception
{
	public InventoryFullException(String msg)
	{
		super(msg);
	}

}
