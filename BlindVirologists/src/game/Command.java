package game;
    import java.util.LinkedList;
    import java.util.List;


/**
 * Command Serializer osztály
 * Feladata egy bemeneti sztring parszolása
 */
public class Command
{
    /**
     * A parancs
     */
    private String              name;

    /**
     * A parancs paramétereit tartalmazó lista
     */
    private List<Option>        options;

    /**
     * Konstruktor
     */
    public Command() {};


    /**
     * Statikus parszoló függvény
     * Parszolja a bemenetként kapott sztringet, és visszaadja a kész parancsot
     * @param input bemeneti sztring
     * @return parszolt parancs
     */
    public static Command parse(String input )
    {
        Command cmd = new Command();

        String[] splitted = input.split(" ");
        cmd.name = null;
        cmd.options = new LinkedList<>();

        if (splitted.length < 1) return cmd;

        cmd.name = splitted[0];

        if (splitted.length < 2) return cmd;

        for ( int i = 1; i < splitted.length; i++ )
        {
            try
            {
                Integer number = Integer.parseInt( splitted[i] );
                cmd.options.add( new Option( "integer", number ));
            }
            catch ( NumberFormatException e )
            {
                try
                {
                    Double number = Double.parseDouble( splitted[i] );
                    cmd.options.add( new Option( "double", number ));
                }
                catch ( NumberFormatException ex )
                {
                    cmd.options.add( new Option( "string", splitted[i] ));
                }
            }
        }

        return cmd;
    }

    /**
     * Parancs getter
     * @return parancs
     */
    public String getCommand()
    {
        return name;
    }

    /**
     * Paraméterlista getter
     * @return paraméterek
     */
    public List<Option> getOptions()
    {
        return options;
    }

    /**
     * Paraméter getter
     * @param i ahányadik elem kell
     * @return a paraméter
     */
    public Option getParam( int i )
    {
        return options.get(i);
    }

    /**
     * Paraméterlista mérete getter
     * @return paraméterlista mérete
     */
    public int getOptionsLength()
    {
        return options.size();
    }


    /**
     * Statikus Option osztály
     * Feladata egy paraméter és annak metaadatainak tárolása
     */
    static class Option
    {
        /**
         * Paraméter típusa
         */
        private final String type;

        /**
         * Paraméter adat
         */
        private final Object data;

        /**
         * Konstruktor
         * Inicializálja az objektumot
         * @param t típus
         * @param d adat
         */
        public Option( String t, Object d )
        {
            this.type = t;
            this.data = d;
        }


        /**
         * Adat getter
         * @return paraméter
         */
        public Object getData()
        {
            return data;
        }

        /**
         * Típus getter
         * @return paraméter típusa
         */
        public String getType()
        {
            return type;
        }
    }
}
