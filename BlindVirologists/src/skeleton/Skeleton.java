package skeleton;

import blindvirologist.Virologist;
import blindvirologist.agent.ForgetAgentGeneticCode;
import blindvirologist.collectible.BagEquipment;
import blindvirologist.collectible.Equipment;
import blindvirologist.collectible.GloveEquipment;
import blindvirologist.collectible.ProtectiveCaveEquipment;
import blindvirologist.field.Bunker;
import blindvirologist.field.Field;
import blindvirologist.field.Laboratory;
import blindvirologist.field.Storage;
import customexceptions.InventoryFullException;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Szkeleton osztály
 * A szkeleton viselkedését valósítja meg
 * Sztenderd input és output használatával
 */
public class Skeleton
{
    private static String regexpCharacter = " ";
    private Scanner scanner;

    /**
     * A szkeleton belépési pontja (indítás)
     * @param args
     */
    public static void main( String[] args )
    {
        Skeleton main = new Skeleton();

        Logger.log( ">", "Skeleton", "Elindult a jatek" );

        HashMap<String, Runnable> actions = new HashMap<>() {{
            put( "create-agent", main::createAgent );
            put( "throw-agent", main::throwAgent );
            put( "pick-material", main::pickMaterial );
            put( "pick-equipment", main::pickEquipment );
            put( "virologist-step", main::virologistStep );
            put( "steal-material", main::stealMaterial );
            put( "steal-equipment", main::stealEquipment );
            put( "learn-code", main::learnGeneticCode );
            put( "next-round", main::nextRound );
            put( "exit", main::exit );
            put( "help", () -> {
                System.out.println( "\tParancs lista:" );
                this.entrySet().forEach( item -> {
                    System.out.println( "\t " + item.getKey() );
                });
            } );
        }};

        Scanner scanner = new Scanner( System.in );
        String input;
        while( true )
        {
            System.out.print( "\nAdj meg egy parancsot ( help -> a parancslista megnyitasa )\n# " );
            input = scanner.nextLine();

            if ( actions.containsKey( input ) ) actions.get( input ).run();
            else System.out.println( "Ervenytelen parancs!" );
        }
    }

    /**
     * Konstruktor
     * Inicializálja a használathoz szükséges változókat
     */
    public Skeleton()
    {
        this.scanner = new Scanner( System.in );
    }

    /**
     * Bemeneti paraméterlistát parszoló metódus
     * @param parameter a kapott paraméterlista
     * @return egy sztring tömb a paraméterekkel
     */
    private String[] parameterParser( String parameter )
    {
        if ( parameter.length() < 1 ) return null;
        return parameter.split( Skeleton.regexpCharacter );
    }

    private ForgetAgentGeneticCode forgetAgentGeneticCode;
    private Bunker bunker;
    private Laboratory laboratory;
    private Storage storage;
    private Field field;
    private Field field2;
    private Field field3;
    private Virologist player1;
    private Virologist player2;

    public void initialize()
    {
        this.forgetAgentGeneticCode = new ForgetAgentGeneticCode(2,2);

        this.bunker           = new Bunker();
        this.laboratory   = new Laboratory( forgetAgentGeneticCode );
        this.storage         = new Storage(10, 10);
        this.field             = new Field();
        this.field2            = new Field();
        this.field3            = new Field();

        field2.addNeighbor(field3);
        field2.addNeighbor(storage);
        storage.addNeighbor(laboratory);
        laboratory.addNeighbor(bunker);
        field3.addNeighbor(field);

        this.player1 = new Virologist( "Player1", field2 );
        this.player2 = new Virologist( "Player2", field3 );
    }

    public void createAgent()
    {
        initialize();

        System.out.println( "+\tA virologus keszit egy virust" );
        System.out.print( "?\tVan eleg alapanyaga? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player1.setNucleotidCount(10);
            player1.setAminoacidCount(10);
        }
        else
        {
            player1.setNucleotidCount(0);
            player1.setAminoacidCount(0);
        }

        player1.createAgent(forgetAgentGeneticCode);
    }

    public void throwAgent()
    {
        initialize();

        System.out.println( "+\tA virologus agenst szor egy masik virologusra" );
        System.out.print( "?\tVan szorhato agense? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player1.setAminoacidCount(10);
            player1.setNucleotidCount(10);
            player1.createAgent(forgetAgentGeneticCode);

            System.out.print( "?\tLejart a szorhatosagi ideje? (I/N): " );
            input = scanner.nextLine();
            if ( input.equals( "I" ) )
            {
                player1.nextTurn();
            }
            else
            {
                System.out.print( "?\tElerheto tavolsagra van a virologus? (I/N): " );
                input = scanner.nextLine();
                if ( input.equals( "I" ) )
                {
                   player1.smearAgent(player2);
                }
            }
        }

    }

    public void pickMaterial()
    {
        initialize();

        System.out.println( "+\tAlapanyag felszedese a mezorol" );
        System.out.print( "?\tRaktar mezon van? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player1.setLocation(storage);

            System.out.print( "?\tA virologusnak maximum mennyisegu alapanyaga van? (I/N): " );

            input = scanner.nextLine();
            if ( input.equals( "I" ) )
            {
                int tmpA = player1.getAminoacidCount();
                int tmpN = player1.getNucleotidCount();

                player1.setAminoacidCount(6);
                player1.setNucleotidCount(6);

                player1.setAminoacidCount(storage.getAminoacidCount());
                player1.setNucleotidCount((storage.getNucleotidCount()));

                storage.setAminoacidCount(player1.getAminoacidCount()-tmpA);
                storage.setNucleotidCount(player1.getNucleotidCount()-tmpN);
            }
            else
            {
                storage.setNucleotidCount(1);
                storage.setAminoacidCount(1);

                int tmpA = player1.getAminoacidCount();
                int tmpN = player1.getNucleotidCount();

                player1.setNucleotidCount(0);
                player1.setAminoacidCount(0);

                player1.setAminoacidCount(storage.getAminoacidCount());
                player1.setNucleotidCount((storage.getNucleotidCount()));

                storage.setAminoacidCount(player1.getAminoacidCount()-tmpA);
                storage.setNucleotidCount(player1.getNucleotidCount()-tmpN);
            }
        }
    }

    public void pickEquipment()
    {
        initialize();

        System.out.println( "+\tFelszereles felszedese a mezorol" );
        System.out.print( "?\tBunker mezon van? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player1.setLocation(bunker);
            bunker.addEquipment(new GloveEquipment());

            System.out.print( "?\tA virologusnak maximum felszerelese van? (I/N): " );

            input = scanner.nextLine();
            if ( input.equals( "I" ) )
            {
                try
                {
                    player1.addEquipment(new GloveEquipment());
                    player1.addEquipment(new ProtectiveCaveEquipment());
                    player1.addEquipment(new BagEquipment(4));
                    player1.addEquipment(new BagEquipment(10));
                }
                catch(InventoryFullException e)
                {
                }
            }
            else
            {
                try
                {
                    player1.addEquipment(new GloveEquipment());
                }
                catch(InventoryFullException e)
                {
                }
            }
        }
    }

    public void virologistStep()
    {
        initialize();
        player1.setLocation(field2);

        System.out.println( "+\tVirologus lep egyet" );
        System.out.print( "?\tSzomszedos mezo? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player1.move(field3);
        }
        else
        {
            player1.move(laboratory);
        }
    }

    public void stealMaterial()
    {
        initialize();

        System.out.println( "+\tAlapanyag elvetele a masik virologustol" );
        System.out.print( "?\tA masik virologus le van benulva? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player2.setStunned(true);

            System.out.print( "?\tA masik virologus elerheto tavlsagban van? (I/N): " );
            input = scanner.nextLine();
            if ( input.equals( "I" ) )
            {
                player1.setLocation(field2);
                player2.setLocation(field3);

                System.out.print( "?\tA virologusnak tele van az alapanyagraktara? (I/N): " );
                input = scanner.nextLine();
                if ( input.equals( "I" ) )
                {
                    player1.setAminoacidCount(1);
                    player1.setNucleotidCount(1);

                    player1.setAminoacidCount(player1.getAminoacidCount()+player2.getAminoacidCount());
                    player1.setNucleotidCount(player1.getNucleotidCount()+player2.getNucleotidCount());
                }
                else
                {
                    player1.setAminoacidCount(6);
                    player1.setNucleotidCount(6);

                    player1.setAminoacidCount(player1.getAminoacidCount()+player2.getAminoacidCount());
                    player1.setNucleotidCount(player1.getNucleotidCount()+player2.getNucleotidCount());
                }
            }
            else
            {
                player1.setLocation(field2);
                player2.setLocation(laboratory);

                player1.stealMaterial(player2);
            }
        }
        else
        {
            player2.setStunned(false);
            player1.stealMaterial(player2);
        }
    }

    public void stealEquipment()
    {
        initialize();
        Equipment eq = new GloveEquipment();
        try {player2.addEquipment(eq); } catch(Exception ex) {}

        System.out.println( "+\tFelszereles elvetele a masik virologustol" );
        System.out.print( "?\tA masik virologus le van benulva? (I/N): " );

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player2.setStunned(true);

            System.out.print( "?\tA masik virologus elerheto tavlsagban van? (I/N): " );
            input = scanner.nextLine();
            if ( input.equals( "I" ) )
            {
                player1.setLocation(field2);
                player2.setLocation(field3);

                System.out.print( "?\tA virologusnak tele van az inventory-ja? (I/N): " );
                input = scanner.nextLine();
                if ( input.equals( "I" ) )
                {
                    try
                    {
                        player1.addEquipment(new BagEquipment(2));
                        player1.addEquipment(new BagEquipment(3));
                        player1.addEquipment(new BagEquipment(4));
                    }
                    catch(Exception ex) {}

                    player1.stealEquipment(player2, eq);
                }
                else
                {
                    player1.stealEquipment(player2, eq);
                }
            }
            else
            {
                player1.setLocation(field2);
                player2.setLocation(laboratory);

                player1.stealEquipment(player2, eq);
            }
        }
        else
        {
            player2.setStunned(false);
            player1.stealEquipment(player2, eq);
        }
    }


    public void learnGeneticCode()
    {
        initialize();

        System.out.println("+\tA virologus megtanul egy genetikai kodot");
        System.out.print("?\tLaboratorium mezon all? (I/N): ");

        String input = scanner.nextLine();
        if ( input.equals( "I" ) )
        {
            player1.setLocation(laboratory);
            player1.learnCode( laboratory.getGeneticCode() );
        }
        else
        {
            player1.setLocation(field);
        }
    }

    public void nextRound()
    {
        initialize();
        player1.nextTurn();
        player2.nextTurn();
    }

    /**
     * Kilépés az alkalmazásból
     */
    public void exit()
    {
        Logger.log( ">", "Skeleton", "Jatek vege" );
        Logger.log("<", "Skeleton", "");
        Logger.log("<", "Skeleton", "");
        System.exit(1);
    }
}
