package game;

import blindvirologist.field.Bunker;
import blindvirologist.field.Field;
import blindvirologist.field.Laboratory;
import blindvirologist.field.Storage;

import java.util.*;
import java.util.concurrent.Callable;


/**
 * Prototípus osztály
 * Futtatóparancsok kezelése, azok továbbítása a Game osztálynak
 */
public class Prototype
{
    /**
     * Játék osztály
     */
    public static Game      game;

    /**
     * Bemeneti sztring parszolva
     */
    public static Command   command;

    /**
     * Kimeneti sztring
     */
    public static String    response;

    /**
     * Bemeneti és kimeneti akciókat tartalmazó map
     * Statikusan inicializált, itt van az összes játékbeli parancs ami használható
     */
    public static Map<String, MapMember> actions = new HashMap<>();
    static
    {
        // Játékkal kapcsolatos parancsok
        actions.put( "start-game",  new MapMember( 0, 0, Prototype::start) );                           // Tesztelve
        actions.put( "end-game",  new MapMember( 0, 0, Prototype::stop) );                              // Tesztelve
        // Játékon belüli parancsok
        actions.put( "mapgen",  new MapMember( 2, 0, Prototype::mapgen) );                              // Tesztelve
        actions.put( "spawn",   new MapMember( 2, 0, Prototype::spawn ) );                              // Tesztelve
        actions.put( "create-field",    new MapMember( 2, 1, Prototype::createField ) );                // Tesztelve
        actions.put( "add-neighbor",    new MapMember( 2, 0, Prototype::addNeighborField) );            // Tesztelve
        actions.put( "field-add-equipment", new MapMember( 2, 0, Prototype::fieldAddEquipment));        // Tesztelve
        actions.put( "field-add-genetic-code", new MapMember( 2, 0, Prototype::fieldAddGeneticCode));   // Tesztelve
        actions.put( "field-set-affected", new MapMember( 1, 1, Prototype::laboratorySetAffected));     // Tesztelve
        actions.put( "move", new MapMember( 2, 0, Prototype::moveVirologist));                          // Tesztelve
        actions.put( "virologist-add-equipment", new MapMember(2, 0, Prototype::addEquipment));         // Tesztelve
        actions.put( "virologist-remove-equipment", new MapMember(2, 0, Prototype::removeEquipment));   // Tesztelve
        actions.put( "virologist-die", new MapMember(1, 0, Prototype::killVirologist));                 // Tesztelve
        actions.put( "learn-code", new MapMember(1, 0, Prototype::learnCode));                          // Tesztelve
        actions.put( "virologist-set-materials", new MapMember(1, 2, Prototype::setMaterials));         // Tesztelve
        actions.put( "virologist-stun", new MapMember(1, 1, Prototype::stun));                          // Tesztelve
        actions.put( "virologist-set-location", new MapMember(2, 0, Prototype::setLocation));           // Tesztelve
        actions.put( "stat", new MapMember(1, 0, Prototype::stat));                                     // Tesztelve
        actions.put( "create-agent", new MapMember(2, 0, Prototype::createAgent));                      // Tesztelve
        actions.put( "pickup-equipment", new MapMember(1, 0, Prototype::pickup));                       // Tesztelve
        actions.put( "next-turn", new MapMember(0, 1, Prototype::nextTurn));                            // Tesztelve
        actions.put( "steal-material", new MapMember(2, 0, Prototype::stealMaterial));                  // Tesztelve
        actions.put( "steal-equipment", new MapMember(3, 0, Prototype::stealEquipment));                // Tesztelve
        actions.put( "smear-agent", new MapMember(2, 0, Prototype::smear));
        actions.put( "use", new MapMember(3, 0, Prototype::use));
        // Debug parancs a könnyebb hibakereséshez
        actions.put( "debug", new MapMember( 0, 0, () -> {
            return "[game] Debug started";
        }) );
        // Kilépés a játékból
        actions.put( "exit",    new MapMember( 0, 0, Prototype::exit ) );
    }


    /**
     * Prototípus futtatása
     * @param args input paraméterek
     */
    public static void main( String[] args )
    {
        game = Game.getInstance();

        /*Scanner scanner = new Scanner( System.in );
        System.out.println( "+------------------------+\n" +
                            "|    Blind Virologist    |\n" +
                            "+------------------------+\n" );

        while( true )
        {
            System.out.print( "# " );
            System.out.println( run(scanner.nextLine()) );
        }*/

        StringBuilder sb = new StringBuilder();
        sb.append("create-field l1 laboratory\n" +
                "create-field l2 laboratory\n" +
                "add-neighbor l1 l2\n" +
                "field-add-genetic-code l1 stun\n" +
                "field-add-genetic-code l2 protect\n" +
                "spawn j1 l1 \n" +
                "spawn j2 l2\n" +
                "learn-code j1\n" +
                "learn-code j2\n" +
                "create-agent j2 1\n" +
                "smear-agent j2 j2\n" +
                "create-agent j1 1\n" +
                "smear-agent j1 j2\n" +
                "stat j1\n" +
                "stat j2");

        StringBuilder response = new StringBuilder();

        Arrays.asList((sb.toString()).split("\n")).forEach( line -> {
            response.append(run(line)).append("\n");
        });

        System.out.println(sb.toString());
        System.out.println("\n\n");
        System.out.println(response.toString());
    }

    /**
     * Futtatja a megadott parancsot
     * @param input a bementi sztring
     * @return válasz
     */
    public static String run( String input )
    {
        command = Command.parse( input );

        if ( !actions.containsKey( command.getCommand() ) )
            return "[game] Invalid command!";

        if ( command.getOptionsLength() < actions.get( command.getCommand() ).RequiredParams )
            return "[game] Invalid parameters for command \"" + command.getCommand() + "\". Required: " + actions.get( command.getCommand() ).RequiredParams + ", provided: " + command.getOptionsLength() + ".";

        try
        {
            return actions.get( command.getCommand() ).Method.call();
        }
        catch ( Exception ex )
        {
            return "[exception] " + ex.getMessage();
        }
    }

    /**
     * MapMember osztály
     * A futtatatndó elemeket tartalmazó map kiegészítő osztálya
     * Feladata a paraméterek tárolása, futtatáshoz
     */
    static class MapMember
    {
        public int      RequiredParams;
        public int      OptionalParams;
        public Callable<String> Method;

        public MapMember( int r, int o, Callable<String> run )
        {
            RequiredParams = r;
            OptionalParams = o;
            Method = run;
        }
    }

    // Delegátor metódusok, amik közvetítik a paraméterezést és a hívást a Game osztálynak
    public static String spawn()
    {
        Response resp = game.virologistSpawn(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return resp.toString();
    }

    public static String createField()
    {
        Response response = game.fieldCreate(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String addNeighborField()
    {
        Response response = game.fieldAddNeighbor(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String fieldAddEquipment()
    {
        Response response = game.fieldAddEquipment(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String fieldAddGeneticCode()
    {
        Response response = game.fieldAddGeneticCode(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String laboratorySetAffected()
    {
        boolean affected = command.getOptionsLength() <= 1 || !command.getOptions().get(1).getType().equals("string") || !command.getOptions().get(1).getData().equals("N");

        Response response = game.laboratorySetAffected(
                command.getOptions().get(0).getData().toString(),
                affected
        );

        return response.toString();
    }

    public static String moveVirologist()
    {
        Response response = game.virologistMove(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String addEquipment()
    {
        Response response = game.virologistAddEquipment(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String killVirologist()
    {
        Response response = game.virologistDie(
                command.getOptions().get(0).getData().toString()
        );

        return response.toString();
    }

    public static String mapgen()
    {
        if ( !command.getOptions().get(0).getType().equals("integer") || !command.getOptions().get(1).getType().equals("integer")  )
            return "[mapgen] Invalid parameter type(s).";

        int x = (Integer)command.getOptions().get(0).getData();
        int y = (Integer)command.getOptions().get(1).getData();

        if ( x < 1 || y < 1 )
            return "[mapgen] Invalid map size 0 0";

        String[][]   map = new String[x][y];
        Random       random = new Random();

        // Mezők létrehozása
        for ( int column = 0; column < x; column++ )
        {
            for ( int row = 0; row < y; row++ )
            {
                int rand = random.nextInt(100);

                if ( rand < 60 )
                {
                    // Field
                    run( "create-field F" + column + row + " field" );
                    map[column][row] = "F" + column + row;
                }
                else if ( rand < 73 )
                {
                    // Laboratory
                    run( "create-field L" + column + row + " laboratory" );
                    map[column][row] = "L" + column + row;
                }
                else if ( rand < 86 )
                {
                    // Storage
                    run( "create-field S" + column + row + " storage" );
                    map[column][row] = "S" + column + row;
                }
                else
                {
                    // Bunker
                    run( "create-field B" + column + row + " bunker" );
                    map[column][row] = "B" + column + row;
                }
            }
        }

        // Szomszédság beállítása
        for ( int column = 0; column < x; column++ )
        {
            for ( int row = 0; row < y; row++ )
            {
                if ( column > 0 )                 run( "add-neighbor " + map[column][row] + " " + map[column-1][row] );
                if ( column+1 < map.length )      run( "add-neighbor " + map[column][row] + " " + map[column+1][row] );

                if ( row > 0 )                    run( "add-neighbor " + map[column][row] + " " + map[column][row-1] );
                if ( row+1 < map[column].length ) run( "add-neighbor " + map[column][row] + " " + map[column][row+1] );

                if ( column > 0 && row > 0 )      run( "add-neighbor " + map[column][row] + " " + map[column-1][row-1] );
                if ( column+1 < map.length && row > 0 ) run( "add-neighbor " + map[column][row] + " " + map[column+1][row-1] );

                if ( column > 0 && row+1 < map[column].length ) run( "add-neighbor " + map[column][row] + " " + map[column-1][row+1] );
                if ( column+1 < map.length && row+1 < map[column].length  ) run( "add-neighbor " + map[column][row] + " " + map[column+1][row+1] );
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[mapgen] Map created ").append(x).append(" ").append(y).append("\n");

        // Map kiiratása
        Arrays.asList(map).forEach( column -> {
            sb.append("         ");

            Arrays.asList( column ).forEach( row -> {
                sb.append(row).append(" ");
            });

            sb.append("\n");
        });

        return (sb.toString()).substring( 0, (sb.toString()).length()-2 );
    }

    public static String learnCode()
    {
        Response response = game.virologistLearnCode(
                command.getOptions().get(0).getData().toString()
        );

        return response.toString();
    }

    public static String setMaterials()
    {
        int n = 0, a = 0;

        if ( command.getOptionsLength() > 1 && command.getOptions().get(1).getType().equals("integer") )
            n = (Integer)command.getOptions().get(1).getData();

        if ( command.getOptionsLength() > 2 && command.getOptions().get(2).getType().equals("integer") )
            a = (Integer)command.getOptions().get(2).getData();

        Response response = game.virologistSetMaterials(
                command.getOptions().get(0).getData().toString(),
                n,
                a
        );

        return response.toString();
    }

    public static String stun()
    {
        boolean stun = true;

        if ( command.getOptionsLength() > 1 && command.getOptions().get(1).getData().equals("I") )      stun = true;
        else if ( command.getOptionsLength() > 1 && command.getOptions().get(1).getData().equals("N") ) stun = false;

        Response response = game.virologistStun(
                command.getOptions().get(0).getData().toString(),
                stun
        );

        return response.toString();
    }

    public static String setLocation()
    {
        Response response = game.virologistSetLocation(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String stat()
    {
        Response response = game.virologistGetStats(
                command.getOptions().get(0).getData().toString()
        );

        return response.toString();
    }

    public static String removeEquipment()
    {
        if ( !command.getOptions().get(1).getType().equals("integer") )
            return "[virologist-remove-equipment] Invalid parameter type(s).";

        Response response = game.virologistRemoveEquipment(
                command.getOptions().get(0).getData().toString(),
                (Integer)command.getOptions().get(1).getData()
        );

        return response.toString();
    }

    public static String createAgent()
    {
        if ( !command.getOptions().get(1).getType().equals("integer") )
            return "[create-agent] Invalid parameter type(s).";

        Response response = game.virologistCreateAgent(
                command.getOptions().get(0).getData().toString(),
                (Integer)command.getOptions().get(1).getData()
        );

        return response.toString();
    }

    public static String pickup()
    {
        Response response = game.viroloigstPickUpEquipment(
                command.getOptions().get(0).getData().toString()
        );

        return response.toString();
    }

    public static String nextTurn()
    {
        int turn = 1;

        if ( command.getOptionsLength() > 0 && command.getOptions().get(0).getType().equals("integer"))
            turn = (Integer)command.getOptions().get(0).getData();

        Response response = game.nextTurn(turn);

        return response.toString();
    }

    public static String stealMaterial()
    {
        Response response = game.virologistStealMaterial(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String stealEquipment()
    {
        if ( !command.getOptions().get(2).getType().equals("integer") )
            return "[steal-equipment] Invalid parameter type(s).";

        Response response = game.virologistStealEquipment(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString(),
                (Integer)command.getOptions().get(2).getData()
        );

        return response.toString();
    }

    public static String smear()
    {
        Response response = game.virologistSmearAgent(
                command.getOptions().get(0).getData().toString(),
                command.getOptions().get(1).getData().toString()
        );

        return response.toString();
    }

    public static String use()
    {
        if ( !command.getOptions().get(1).getType().equals("integer") )
            return "[steal-equipment] Invalid parameter type(s).";

        Response response = game.virologistUseEquipment(
                command.getOptions().get(0).getData().toString(),
                (Integer)command.getOptions().get(1).getData(),
                command.getOptions().get(2).getData().toString()
        );

        return response.toString();
    }

    public static String start()
    {
        Response response = game.start();
        return response.toString();
    }

    public static String stop()
    {
        Response response = game.stop();
        return response.toString();
    }

    public static String exit()
    {
        System.exit(1);
        return "[game] Exit. Good Bye";
    }
}
