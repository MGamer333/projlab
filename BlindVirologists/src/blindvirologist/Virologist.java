package blindvirologist;
    import blindvirologist.agent.Agent;
    import blindvirologist.agent.BearVirusAgent;
    import blindvirologist.agent.GeneticCode;
    import blindvirologist.collectible.Equipment;
    import blindvirologist.field.Field;
    import customexceptions.AgentBlockedException;
    import customexceptions.GloveException;
    import customexceptions.InventoryFullException;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Random;


/**
 * Virológus osztály.
 * A játékost, és a játék fő szereplőit megvalósító osztály. Egy karaktert reprezentál.
 * Interfészt nyújt a játékos számára, hogy a játékban tevékenykedni tudjon
 */
public class Virologist
{
    /**
     * name: A virológus neve a játékban
     */
    private String              name;

    /**
     * location: A mező amin a virológus áll
     */
    private Field               location;

    /**
     * aminoacidCount: A virológusnál levő aminósav mennyiség
     */
    private int                 aminoacidCount;

    /**
     * nucleotidCount: A virológusnál levő nukleotid mennyiség
     */
    private int                 nucleotidCount;

    /**
     * maxAminoacidCount: Statikus tagváltozó, a virológusnál ennyi aminósav lehet maximum
     */
    private int                 maxAminoacidCount = 6;

    /**
     * maxNucleotidCount: Statikus tagváltozó, a virológusnál ennyi nukleotid lehet maximum
     */
    private int                 maxNucleotidCount = 6;

    /**
     * learntCodes: A virológus által már megtanult genetikai kódok
     */
    private List<GeneticCode>   learntCodes;

    /**
     * wornEquipments: A virológusnál levő eszközök (felszerelések)
     */
    private List<Equipment>     wornEquipments;

    /**
     * createdAgent: A virológus által éppen elkészített ágens
     */
    private Agent               createdAgent    = null;

    /**
     * affectingAgents: A virológusra szórt, azon levő hatásukat jelenleg is kifejtő ágensek
     */
    private List<Agent>         affectingAgents;

    /**
     * stunned: Megadja hogy a virológus lebénult állapotban van-e
     */
    private boolean             stunned         = false;

    /**
     * usableAfterCreation: A virológus által éppen elkészített ágens szórhatósági ideje
     */
    public static int           usableAfterCreation = 3;

    /**
     * dead: A virológus halott-e
     */
    private boolean             dead            = false;

    /**
     * Medve lett-e
     */
    private boolean             bearAffected    = false;

    /**
     * Kell-e furán mozognia
     */
    private boolean             chorea          = false;


    /**
     * Konstruktor
     * Beállítja a privát tagváltozók értékeit, inicializálja azokat
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
     * Virológus nevét adja meg
     * @return név
     */
    public String getName()
    {
        return name;
    }

    /**
     * A virológusnál lévő aminósavmennyiséget adja meg
     * @return aminósav emnnyiség
     */
    public int getAminoacidCount()
    {
        return aminoacidCount;
    }

    /**
     * A virológusnál lévő aminósav mennyiségét állítja be
     */
    public boolean setAminoacidCount(int _aminoacidCount)
    {
        if ( _aminoacidCount < this.maxAminoacidCount )
        {
            this.aminoacidCount = _aminoacidCount;
            return true;
        }
        else
        {
            this.aminoacidCount = this.maxAminoacidCount;
            return false;
        }
    }

    /**
     * A virológusnál lévő nukleotidmennyiséget adja meg
     * @return nukleotid mennyiség
     */
    public int getNucleotidCount() {
        return nucleotidCount;
    }

    /**
     * A virológusnál lévő nukleotid mennyiségét állítja be
     * @param nucleotidCount nukleotid mennyiség
     */
    public boolean setNucleotidCount(int nucleotidCount)
    {
        if ( nucleotidCount < this.maxNucleotidCount )
        {
            this.nucleotidCount = nucleotidCount;
            return true;
        }
        else
        {
            this.nucleotidCount = this.maxNucleotidCount;
            return false;
        }
    }

    /**
     * Visszaadja a virológus által megtanult genetikai kódok listáját
     * @return genetikai kódok listája
     */
    public List<GeneticCode> getLearntCodes()
    {
        return learntCodes;
    }

    /**
     * Beállítja a virológus megtanult genetikai kód listáját
     * @param learntCodes a genetikai kód-lista
     */
    public void setLearntCodes(List<GeneticCode> learntCodes)
    {
        this.learntCodes = learntCodes;
    }

    /**
     * Beállítja a virológus lebénítottságát
     * @param stunned bénított-e
     */
    public void setStunned(boolean stunned)
    {
        this.stunned = stunned;
    }

    /**
     * Megadja hogy a virológus le van-e bénulva
     */
    public boolean isStunned()
    {
        return this.stunned;
    }

    /**
     * Visszaadja a mezőt, amin a virológus éppen áll
     * @return Field mező
     */
    public Field getLocation()
    {
        return location;
    }

    /**
     * Beállítja a mezőt amin a virológus áll
     * @param _new new location
     */
    public void setLocation( Field _new )
    {
        this.location = _new;
    }

    /**
     * A virológus lépése.
     * Egy lépést/mezőváltást valósít meg, amikor a virológus egy új mezőre lép
     * @param _field a mező amire lép
     */
    public boolean move( Field _field )
    {
        if ( _field.equals( this.location ) ) return true;
        if ( !this.location.isNeighbor( _field ) ) return false;
        if ( this.dead ) return false;
        if ( this.chorea ) return false;

        // Véletlenszerűen közlekedik a medve is meg a chorea minorral fertőzött is
        Field nextLocation = _field;
        if ( this.isBearAffected() || this.isChoreaAffected() )
        {
            Random random = new Random();
            nextLocation = this.location.getNeighbors().get(
                    random.nextInt( this.location.getNeighbors().size() )
            );
        }

        this.location.removeVirologist();
        nextLocation.addVirologist( this );
        location = nextLocation;

        // TODO
        // MEDVEVÍRUS hozzáadása más virológusokhoz
        if ( this.isBearAffected() )
        {
            _field.getNeighbors().forEach( field -> {
                try
                {
                    field.getCurrentVirologist().addAgent(new BearVirusAgent(-1, -1));
                }
                catch(Exception e) {}
            });
        }

        return true;
    }

    /**
     * A virológus letapogat egy genetikai kódot.
     * Amikor a virológus laboratórium mezőn áll, megtanulhatja az ott levő genetikai kódot
     */
    public boolean learnCode()
    {
        if ( location.getGeneticCode() == null ) return false;
        if ( this.learntCodes.contains( location.getGeneticCode() ) ) return false;

        this.learntCodes.add( location.getGeneticCode() );
        return true;
    }

    /**
     * Ágens létrehozása.
     * A virológus a megtanult genetikai kódok alapján létrehoz egy ágenst
     * @param _code a létrehozandó ágens genetikai kódja
     */
    public boolean createAgent( GeneticCode _code )
    {
        if ( this.aminoacidCount >= _code.getAminoacidCost() && this.nucleotidCount >= _code.getNucleotidCost() )
        {
            this.createdAgent = _code.createAgent();

            this.aminoacidCount -= _code.getAminoacidCost();
            this.nucleotidCount -= _code.getNucleotidCost();
            return true;
        }

        return false;
    }

    /**
     * Minket fertőző ágens hozzáadása az ágenslistánkhoz.
     * Amikor a virológusra ágenst szórnak, akkor az bekerül az őt fetőző listába
     * @param _agent az új fertőző ágens
     */
    public void addAgent( Agent _agent ) throws GloveException, Exception
    {
        if ( this.wornEquipments.size() == 0 )
        {
            this.affectingAgents.add( _agent );
            return;
        }

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
        }

        // TODO protectek
    }

    /**
     * Ágens szórása egy virológusra.
     * A virológus egy már elkészített ágenst szór egy másik, általa kiválasztott virológusra
     * @param _virologist a virológus akire szórjuk az ágenst
     */
    public boolean smearAgent( Virologist _virologist )
    {
        if ( this.createdAgent == null ) return false;

        try
        {
            _virologist.addAgent( this.createdAgent );
            this.createdAgent = null;
            return true;
        }
        catch ( GloveException ex )
        {
            try
            {
                this.addAgent(this.createdAgent);
                this.createdAgent = null;
                return true;
            }
            catch ( GloveException gex )
            {
                System.err.println( "A dobó is kivédte a vírust, és az megsemmisül" );
                this.createdAgent = null;
                return false;
            }
            catch ( Exception eex )
            {
                System.err.println("[exception] "+eex.getMessage());
            }
        }
        catch ( Exception eeex )
        {
            System.err.println("[exception] "+eeex.getMessage());
        }

        return true;
    }

    /**
     * Felszerelés hozzáadása.
     * A virológus kap egy eszközt, ami bekerül a zsákjába, ha van benne hely
     * @param _new az új felszerelés
     */
    public boolean addEquipment( Equipment _new ) throws InventoryFullException
    {
        if ( this.wornEquipments.size() >= 3 ) throw new InventoryFullException( "Inventory is full" );
        if ( this.wornEquipments.contains( _new ) ) return false;

        this.wornEquipments.add(_new);
        return true;
    }

    /**
     * Felszerelés eltávolítása.
     * A virológustól elvételre kerül agy eszköz
     * @param _old az eltávolítandó felszerelés
     */
    public void removeEquipment( Equipment _old )
    {
        this.wornEquipments.remove( _old );
    }

    /**
     * TODO
     * @return
     */
    public List<Equipment> getWornEquipments()
    {
        return this.wornEquipments;
    }

    /**
     * Ágensek hatási idejének csökkentése, hatásuknak kifejtése.
     * Minden egyes játékkörben a virológuson levő ágens kifejti a hatását rá, és a hatási
     * ideje csökken, vagy ha ez elfogyott akkor megsemmisül
     */
    public void nextTurn()
    {
        if ( this.createdAgent != null )
        {
            if ( !this.createdAgent.decreaseUsableTime() )
            {
                this.createdAgent = null;
            }
        }

        for ( Agent current : this.affectingAgents )
        {
            affectMe(current);

            if ( !current.decreaseDuration() )
            {
                current.remove( this );
                this.affectingAgents.remove( current );
            }
        }
    }

    private void affectMe( Agent current )
    {
        try
        {
            current.affect(this);
        }
        catch ( AgentBlockedException ex )
        {
            System.out.println("[game] Agent "+current.toString()+" blocked by \""+name+"\"");
        }
    }

    /**
     * Alapanyagok lopása egy lebénult virológustól.
     * A virológus elveheti egy lebénult virológus alapanyagait
     * @param _another a másik virológus
     */
    public boolean stealMaterial( Virologist _another )
    {
        if ( !_another.isStunned() ) return false;

        int tmpAminoacid = this.aminoacidCount;
        int tmpNucleotid = this.nucleotidCount;

        this.aminoacidCount = Math.min((this.aminoacidCount + _another.aminoacidCount), this.maxAminoacidCount);
        this.nucleotidCount = Math.min((this.nucleotidCount + _another.nucleotidCount), this.maxNucleotidCount);

        _another.aminoacidCount -= (this.aminoacidCount - tmpAminoacid);
        _another.nucleotidCount -= (this.nucleotidCount - tmpNucleotid);

        return true;
    }

    /**
     * Felszerelés lopása egy lebénult virológustól.
     * A virológus elveheto egy lebénult virológus eszközét.
     * @param _another a másik virológus
     */
    public boolean stealEquipment( Virologist _another, Equipment _equipment ) throws InventoryFullException
    {
        if ( !_another.isStunned() ) return false;

        this.addEquipment( _equipment );
        _equipment.appendEffect(this);

        _another.removeEquipment( _equipment );
        _equipment.removeEffect(_another);

        return true;
    }

    /**
     * Virológus halálát megvalósító metódus.
     * Amikor a virológust megcsapják a baltával, azonnal meghal, számára a játék véget ér.
     */
    public void die()
    {
        this.location.removeVirologist();
        this.location = null;
        this.dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isBearAffected() {
        return bearAffected;
    }

    public void setBearAffected(boolean bearAffected) {
        this.bearAffected = bearAffected;
    }

    public boolean isChoreaAffected() {
        return chorea;
    }

    public void setChoreaAffected(boolean chorea) {
        this.chorea = chorea;
    }

    public List<Agent> getAffectingAgents() {
        return affectingAgents;
    }

    public int getMaxAminoacidCount() {
        return maxAminoacidCount;
    }

    public int getMaxNucleotidCount() {
        return maxNucleotidCount;
    }

    public Agent getCreatedAgent() {
        return createdAgent;
    }
}
