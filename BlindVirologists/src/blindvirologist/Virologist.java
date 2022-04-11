package blindvirologist;
    import blindvirologist.agent.Agent;
    import blindvirologist.agent.GeneticCode;
    import blindvirologist.collectible.Equipment;
    import blindvirologist.field.Field;
    import customexceptions.AgentBlockedException;
    import customexceptions.GloveException;
    import customexceptions.InventoryFullException;
    import skeleton.Logger;

    import java.util.ArrayList;
    import java.util.List;


/**
 * Virológus osztály
 */
public class Virologist
{
    private String              name;
    private Field               location;
    private int                 aminoacidCount;
    private int                 nucleotidCount;
    private int                 maxAminoacidCount = 6;
    private int                 maxNucleotidCount = 6;
    private List<GeneticCode>   learntCodes;
    private List<Equipment>     wornEquipments;
    private Agent               createdAgent    = null;
    private List<Agent>         affectingAgents;
    private boolean             stunned         = false;
    public static int           usableAfterCreation = 3;

    /**
     * Konstruktor
     * @param _name a virológus neve
     * @param _current a mező amin áll
     */
    public Virologist( String _name, Field _current )
    {
        this.name               = _name;
        this.location           = _current;
        this.aminoacidCount = this.maxAminoacidCount;
        this.nucleotidCount     = this.maxNucleotidCount;
        this.learntCodes        = new ArrayList<>();
        this.wornEquipments     = new ArrayList<>();
        this.affectingAgents    = new ArrayList<>();
    }

    /**
     * Virológus neve a játékban
     * @return név
     */
    public String getName()
    {
        return name;
    }

    /**
     * Aminósav getter
     * @return aminósav emnnyiség
     */
    public int getAminoacidCount()
    {
        return aminoacidCount;
    }

    /**
     * Aminósav setter
     */
    public void setAminoacidCount(int _aminoacidCount)
    {
        Logger.log( ">", "Virologist", "Aminosav novelese" );
        if ( (this.aminoacidCount + _aminoacidCount) <= this.maxAminoacidCount )
        {
            this.aminoacidCount += _aminoacidCount;
        }
        else
        {
            Logger.log( ">", "Virologist", "Aminosav a max szinten van" );
            this.aminoacidCount = this.maxAminoacidCount;
        }
        Logger.log( "<", "Virologist", "" );
    }

    /**
     * Nukleotid getter
     * @return nukleotid mennyiség
     */
    public int getNucleotidCount() {
        return nucleotidCount;
    }

    /**
     * Nukleotid setter
     * @param nucleotidCount nukleotid mennyiség
     */
    public void setNucleotidCount(int nucleotidCount)
    {
        Logger.log( ">", "Virologist", "Nukleotid novelese" );
        if ( (this.nucleotidCount + nucleotidCount) <= this.maxNucleotidCount )
        {
            this.nucleotidCount += nucleotidCount;
        }
        else
        {
            Logger.log( ">", "Virologist", "Nukleotid max szinten van" );
            this.nucleotidCount = this.maxNucleotidCount;
        }
        Logger.log("<", "Virologist", "");
    }

    /**
     * Megtanult genetikai kódok getter
     * @return
     */
    public List<GeneticCode> getLearntCodes()
    {
        return learntCodes;
    }

    /**
     * Megtanult genetikai kódok setter
     * @param learntCodes
     */
    public void setLearntCodes(List<GeneticCode> learntCodes)
    {
        this.learntCodes = learntCodes;
    }

    /**
     * Bénított setter
     * @param stunned
     */
    public void setStunned(boolean stunned)
    {
        this.stunned = stunned;
    }

    /**
     * Bénított getter
     */
    public boolean getStunned()
    {
        return this.stunned;
    }

    /**
     * Location getter
     * @return location
     */
    public Field getLocation()
    {
        return location;
    }

    /**
     * Location setter
     * @param _new new location
     */
    public void setLocation( Field _new )
    {
        this.location = _new;
    }

    /**
     * A virológus lép egyet
     * @param _field a mező amire lép
     */
    public void move( Field _field )
    {
        Logger.log(">", "Virologist", "Virologus megprobal lepni");
        if ( !_field.equals( this.location ) )
        {

            if ( this.location.isNeighbor( _field ) )
            {
                Logger.log(">", "Virologist", "A mezo szomszedos");
                this.location.removeVirologist();
                _field.addVirologist( this );
                Logger.log("<", "Virologist", "");
            }
        }
        Logger.log("<", "Virologist", "");
    }

    /**
     * A virológus letapogat egy genetikai kódot
     * @param _newCode az új genetikai kód
     */
    public void learnCode( GeneticCode _newCode )
    {
        Logger.log( ">", "Virologist", "Genetikai kod letapogatas kezdese" );

        if ( !this.learntCodes.contains( _newCode ) ) this.learntCodes.add( _newCode );

        Logger.log( "<", "Virologist", "" );
    }

    /**
     * Ágens létrehozása
     * @param _code a létrehozandó ágens genetikai kódja
     */
    public void createAgent( GeneticCode _code )
    {
        Logger.log( ">", "Virologist", "Agens letrehozasanak kezdemenyezese" );

        if ( this.aminoacidCount >= _code.getAminoacidCost() && this.nucleotidCount >= _code.getNucleotidCost() )
        {
            this.createdAgent = _code.createAgent();

            this.aminoacidCount -= _code.getAminoacidCost();
            this.nucleotidCount -= _code.getNucleotidCost();
        }

        Logger.log( ">", "Virologist", "" );
    }

    /**
     * Minket fertőző ágens hozzáadása az ágenslistánkhoz
     * @param _agent az új fertőző ágens
     */
    public void addAgent( Agent _agent ) throws GloveException
    {
        Logger.log( ">","Virologist", "Agens hozzaadasa a virlogushoz" );

        for ( Equipment equipment : this.wornEquipments )
        {
            try
            {
                equipment.handleEffect( this );
                this.affectingAgents.add( _agent );
            }
            catch ( AgentBlockedException ex )
            {
                System.err.println( "Ágens blokkolva" );
            }
            catch ( Exception ex )
            {
                System.err.println( "Ismeretlen kivétel" );
            }
        }

        Logger.log( "<","Virologist", "" );
    }

    /**
     * Ágens szórása egy virológusra
     * @param _virologist a virológus akire szórjuk az ágenst
     */
    public void smearAgent( Virologist _virologist )
    {
        Logger.log( ">","Virologist", "Agens szorasa" );

        if ( this.createdAgent != null )
        {
            try
            {
                _virologist.addAgent( this.createdAgent );
                this.createdAgent = null;
            }
            catch ( GloveException ex )
            {
                try
                {
                    this.addAgent( this.createdAgent );
                }
                catch ( GloveException gex )
                {
                    System.err.println( "A dobó is kivédte a vírust, és az megsemmisül" );
                }

                this.createdAgent = null;
            }
        }

        Logger.log( "<","Virologist", "" );
    }

    /**
     * Felszerelés hozzáadása
     * @param _new az új felszerelés
     */
    public void addEquipment( Equipment _new ) throws InventoryFullException
    {
        Logger.log(">", "Virologist", "Felszereles hozzaadasa");
        if ( this.wornEquipments.size() >= 3 ) {
            Logger.log(">", "Virologist", "Az inventory tele van");
            throw new InventoryFullException("Tele az inventory");
        }
        if ( !this.wornEquipments.contains( _new ) ) {
            this.wornEquipments.add(_new);
            Logger.log(">", "Virologist", "Felszereles sikeresen hozzaadva");
        }
        Logger.log("<", "Virologist", "");
    }

    /**
     * Felszerelés eltávolítása
     * @param _old az eltávolítandó felszerelés
     */
    public void removeEquipment( Equipment _old )
    {
        this.wornEquipments.remove( _old );
    }

    /**
     * Ágensek hatási idejének csökkentése, hatásuknak kifejtése
     */
    public void nextTurn()
    {
        Logger.log( ">","Virologist", "Virologus korenek leptetese" );

        if ( this.createdAgent != null )
        {
            if ( !this.createdAgent.decreaseUsableTime() )
            {
                Logger.log( ">","Virologist", "Elkeszitett virus ideje lejart" );
                this.createdAgent = null;
                Logger.log("<", "Virologist", "" );
            }
        }

        for ( Agent current : this.affectingAgents )
        {
            try
            {
                if ( current.decreaseDuration() ) current.affect( this );
                else this.affectingAgents.remove( current );
            }
            catch ( AgentBlockedException ex )
            {
                System.err.println( "Ágens blokkolva" );
            }
        }

        Logger.log( "<","Virologist", "" );
    }

    /**
     * Alapanyagok lopása egy lebénult virológustól
     * @param _another a másik virológus
     */
    public void stealMaterial( Virologist _another )
    {
        Logger.log(">", "Virologist", "Alapanyag elvetele");
        if ( _another.getStunned() )
        {
            Logger.log(">", "Virologist", "A virologus benult");
            int tmpAminoacid = this.aminoacidCount;
            int tmpNucleotid = this.nucleotidCount;

            this.aminoacidCount = Math.min((this.aminoacidCount + _another.aminoacidCount), this.maxAminoacidCount);
            this.nucleotidCount = Math.min((this.nucleotidCount + _another.nucleotidCount), this.maxNucleotidCount);

            _another.aminoacidCount -= (this.aminoacidCount - tmpAminoacid);
            _another.nucleotidCount -= (this.nucleotidCount - tmpNucleotid);
            Logger.log("<", "Virologist", "");
        }
        Logger.log("<", "Virologist", "");
    }

    /**
     * Felszerelés lopása egy lebénult virológustól
     * @param _another a másik virológus
     */
    public void stealEquipment( Virologist _another, Equipment _equipment )
    {
        Logger.log( ">", "Virologist", "Felszereles ellopasa" );

        if ( _another.getStunned() )
        {
            if ( this.location.isNeighbor( _another.getLocation() ) )
            {
                Logger.log( ">", "Virologist", "Szomszedos mezon van" );
                Logger.log( ">", "Virologist", "A virologus le van benulva" );
                try
                {
                    this.addEquipment( _equipment );
                    _another.removeEquipment( _equipment );
                    Logger.log( ">", "Virologist", "Felszereles Sikeresen felveve" );
                    Logger.log( "<", "Virologist", "" );
                }
                catch ( InventoryFullException ex )
                {
                    Logger.log( ">", "Virologist", "Tele van az inventory" );
                    System.err.println( "Ha tele az inventory, nem vonjuk le a másiktól a felszerelést" );
                    Logger.log( "<", "Virologist", "" );
                }
            }
            else
            {
                Logger.log( ">", "Virologist", "Nem szomszedos mezon van" );
            }
        }

        Logger.log( "<", "Virologist", "" );
    }

    public void die()
    {
        // TODO
    }
}
