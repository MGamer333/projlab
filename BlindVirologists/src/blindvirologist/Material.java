package blindvirologist;

//az alapanyagokat megvalósító absztakt osztály
public abstract class Material extends Collectible
{
	//konstruktor
	public Material(String Name)
	{
		//az alapanyag neve szigorúan 1 betû
		//így ha több betût stringet kap a konstruktorban, szimplán csak levágja az elsõ karakterét
		super(Name.substring(0, 0));
	}
}
