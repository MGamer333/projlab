package blindvirologist;

//az alapanyagokat megval�s�t� absztakt oszt�ly
public abstract class Material extends Collectible
{
	//konstruktor
	public Material(String Name)
	{
		//az alapanyag neve szigor�an 1 bet�
		//�gy ha t�bb bet�t stringet kap a konstruktorban, szimpl�n csak lev�gja az els� karakter�t
		super(Name.substring(0, 0));
	}
}
