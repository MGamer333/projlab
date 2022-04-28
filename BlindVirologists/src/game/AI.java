package game;
    import blindvirologist.Virologist;

    import java.util.List;


/**
 * A többi virológust irányító osztály.
 * A játék alapvetően egyjátékos módú, a játékos a gép "ellen" játszik. Ez az osztály
 * felelős a virológus bot-ok irányításáért
 */
public class AI
{
    /**
     * bots: A számítógép által vezérelt virológusok listája
     */
    private List<Virologist> bots;


    /**
     * Hozzád egy létrehozott virológust a botok listájához.
     * Innentől ez a virológus is egy szereplő lesz
     * @param _virologist a hozzáadandó virológus
     */
    public void addBod( Virologist _virologist )
    {
        // TODO
    }

    /**
     * A virológus léptetését futtató metódus.
     * Az AI osztály lépteti a megadott virológust a körében
     */
    public void step( Virologist _virologist )
    {
        // TODO
    }
}
