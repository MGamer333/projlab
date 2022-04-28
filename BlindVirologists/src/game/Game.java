package game;


import blindvirologist.Virologist;
import blindvirologist.agent.*;
import blindvirologist.collectible.*;
import blindvirologist.field.Bunker;
import blindvirologist.field.Field;
import blindvirologist.field.Laboratory;
import blindvirologist.field.Storage;
import customexceptions.InventoryFullException;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * A játékot vezérlő osztály
 * Szingleton: egyetlen példánya létezik, ez vezérli a játékot
 */
public class Game
{
    /**
     * Szingleton insztansz
     * A game osztály egyetlen példánya
     */
    private static Game singleton_instance = null;

    /**
     * Game konstruktor
     * Privát konstruktor a szingleton osztálynak, hogy ne lehessen többször példányosítani
     */
    private Game()
    {
        this.start();
    }

    /**
     * Szingleton osztály getter
     * @return a game példány
     */
    public static Game getInstance()
    {
        if ( singleton_instance == null )
        {
            singleton_instance = new Game();
        }

        return singleton_instance;
    }


    // Nem-statikus osztályrészek

    private Map<String, Virologist> virologistMap;
    private Map<String, Field>      fieldMap;

    /**
     * Játék indítása.
     * Elindítja a játékot, létrehozza a szükséges objektumokat, inicializálja a változókat.
     */
    public Response start()
    {
        virologistMap   = new HashMap<>();
        fieldMap        = new HashMap<>();

        return new Response(1, "start-game", "New game started.");
    }

    /**
     * Játék megállítása.
     * Játék befejeztével kerül meghívásra, törli a jelenlegi játékhoz tartozó objektumokat,
     * előkészíti az új játék hívását
     */
    public Response stop()
    {
        virologistMap.clear();
        return new Response(1, "stop-game", "Game ended.");
    }

    /**
     * Kör léptetése
     * @return response
     */
    public Response nextTurn( int step )
    {
        if ( step < 1 ) return new Response(0, "next-turn", "Invalid number parameter \""+step+"\"");

        for ( int i = 0; i < step; i++ )
        {
            virologistMap.forEach( (key, value) -> {
                value.nextTurn();
            });
        }

        return new Response(1, "next-turn", "Game stepped by \""+step+"\"");
    }


    // Publikus metódusok a játékhoz

    /**
     * Létrehozza a virológust, és lehelyezi a megadott mezőre a pályán
     * @param name virológus neve
     * @param field mező neve
     * @return response
     */
    public Response virologistSpawn(String name, String field )
    {
        if ( virologistMap.containsKey( name ) ) return new Response( 0, "spawn", "Virologist still exists." );
        if ( !fieldMap.containsKey( field ) )    return new Response( 0, "spawn", "Field does not exists." );

        Virologist virologist = new Virologist( name, fieldMap.get( field ));
        virologistMap.put( name, virologist );
        fieldMap.get( field ).addVirologist( virologist );

        return new Response( 1, "spawn", "Virologist \""+name+"\" spawned, and location set to \""+field+"\".");
    }

    /**
     * A virológus lép egy másik mezőre
     * @param name virológus neve
     * @param field mező neve
     * @return response
     */
    public Response virologistMove( String name, String field )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "move", "Virologist \""+name+"\" does not exists.");
        if ( !fieldMap.containsKey(field) )     return new Response(0, "move", "Field \""+field+"\" does not exists.");
        if ( fieldMap.get(field).getCurrentVirologist() != virologistMap.get(name) && fieldMap.get( field ).getCurrentVirologist() != null ) return new Response(0, "move", "Field \""+field+"\" is not empty.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        boolean moved = virologistMap.get(name).move(fieldMap.get(field));

        return moved ? new Response(1, "moved", "Virologist \""+name+"\" moved to \""+field+"\"") :
                new Response(0, "moved", "Field \""+field+"\" is not a neighbor of \""+name+"\" current field.");
    }

    /**
     * A virológus meghal
     * @param name virológus neve
     * @return response
     */
    public Response virologistDie( String name )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "virologist-die", "Virologist \""+name+"\" does not exists.");

        virologistMap.get(name).die();
        return new Response(1, "virologist-die", "Virologist \""+name+"\" died");
    }

    /**
     * A virológus megtanulja a mezőn lévő genetikai kódot
     * @param name a virológus
     * @return response
     */
    public Response virologistLearnCode( String name )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "learn-code", "Virologist \""+name+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        Response resp = virologistMap.get( name ).learnCode() ?
            new Response(1, "learn-code", "Genetic code learnt.") :
            new Response(0, "learn-code", "Can't learn genetic code.");

        return virologistMap.get(name).getLearntCodes().size() == 4 ?
                new Response(1, "game", "Virologist \""+name+"\" win the game.") :
                resp;
    }

    /**
     * Hozzáadja a felszerelést a virológushoz
     * @param name virológus neve
     * @param equipment eszköz neve
     * @return response
     */
    public Response virologistAddEquipment( String name, String equipment )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "virologist-add-equipment", "Virologist \""+name+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        Equipment eq = createNewEquipment( equipment );
        if ( eq == null ) return new Response(0, "virologist-add-equipment", "Unknown equipment type \""+equipment+"\".");

        try
        {
            Response resp = virologistMap.get(name).addEquipment(eq) ?
                    new Response(1, "virologist-add-equipment", "Equipment \""+equipment+"\" added to \""+name+"\".") :
                    new Response(0, "virologist-add-equipment", "\""+name+"\" already has the item \""+equipment+"\"");

            eq.appendEffect( virologistMap.get(name) );

            return resp;
        }
        catch( InventoryFullException ex )
        {
            return new Response(0, "virologist-add-equipment", "\""+name+"\"'s inventory is full.");
        }
    }

    /**
     * A virológustól elveszi az egyik nála lévő felszerelést
     * @param name a virológus
     * @param equipment felszerelés száma a listában
     * @return response
     */
    public Response virologistRemoveEquipment( String name, int equipment )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "virologist-remove-equipment", "Virologist \""+name+"\" does not exists.");
        if ( (virologistMap.get(name).getWornEquipments().size() < equipment) ) return new Response(0, "virologist-remove-equipment", "Item in index \""+equipment+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        virologistMap.get(name).getWornEquipments().get(equipment-1).removeEffect(
                virologistMap.get(name)
        );

        virologistMap.get(name).removeEquipment(
                virologistMap.get(name).getWornEquipments().get(equipment-1)
        );
        return new Response(1, "virologist-remove-equipment", "Equipment removed.");
    }

    /**
     * A virológus felszed egy eszközt a mezőről
     * @param name a virológus
     * @return response
     */
    public Response viroloigstPickUpEquipment( String name )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "pickup-equipment", "Virologist \""+name+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        Equipment equipment = virologistMap.get(name).getLocation().getEquipment();
        if ( equipment == null ) return new Response(0, "pickup-equipment", "Current field is empty, or not a bunker.");

        try
        {
            virologistMap.get(name).addEquipment(equipment);
            virologistMap.get(name).getLocation().removeEquipment();

            equipment.appendEffect(virologistMap.get(name));

            return new Response( 1, "pickup-equipment", "Equipment \""+equipment.toString()+"\" picked up by \""+name+"\"" );
        }
        catch ( InventoryFullException ex )
        {
            return new Response(0, "pickup-equipment", "Virologist \""+name+"\" inventory is full.");
        }
    }

    /**
     * A virológus alapanyagokat lop egy lebénult virológustól
     * @param who aki lop
     * @param whom akitől lop
     * @return response
     */
    public Response virologistStealMaterial( String who, String whom )
    {
        if ( !virologistMap.containsKey(who) ) return new Response(0, "steal-material", "Virologist \""+who+"\" does not exists.");
        if ( !virologistMap.containsKey(whom) ) return new Response(0, "steal-material", "Virologist \""+whom+"\" does not exists.");
        if ( virologistMap.get(who).isDead() ) return new Response(0, "move", "Virologist \""+who+"\" is dead.");
        if ( virologistMap.get(whom).isDead() ) return new Response(0, "move", "Virologist \""+whom+"\" is dead.");

        if ( !virologistMap.get(who).getLocation().isNeighbor( virologistMap.get(whom).getLocation() ) ) return new Response(0, "steal-material", "Virologist \""+who+"\" and \""+whom+"\" are not neighbors.");

        return virologistMap.get(who).stealMaterial(virologistMap.get(whom)) ?
                new Response(1, "steal-material", "Virologist \""+who+"\" stole materials from \""+whom+"\".") :
                new Response(1, "steal-material", "Virologist \""+whom+"\" is not stunned");
    }

    /**
     * A virológus ellop egy felszerelést egy lebénult virológustól
     * @param who aki lopja
     * @param whom akitől lopja
     * @param equipment a felszerelés száma a lsitában
     * @return response
     */
    public Response virologistStealEquipment( String who, String whom, int equipment )
    {
        if ( !virologistMap.containsKey(who) ) return new Response(0, "steal-equipment", "Virologist \""+who+"\" does not exists.");
        if ( !virologistMap.containsKey(whom) ) return new Response(0, "steal-equipment", "Virologist \""+whom+"\" does not exists.");
        if ( virologistMap.get(who).isDead() ) return new Response(0, "move", "Virologist \""+who+"\" is dead.");
        if ( virologistMap.get(whom).isDead() ) return new Response(0, "move", "Virologist \""+whom+"\" is dead.");

        if ( !virologistMap.get(who).getLocation().isNeighbor( virologistMap.get(whom).getLocation() ) ) return new Response(0, "steal-equipment", "Virologist \""+who+"\" and \""+whom+"\" are not neighbors.");

        if ( virologistMap.get(whom).getWornEquipments().size() < equipment ) return new Response(0, "steal-equipment", "Equipment in index \""+equipment+"\" does not exists.");

        try
        {
            Equipment eq = virologistMap.get(whom).getWornEquipments().get(equipment-1);
            return virologistMap.get(who).stealEquipment(
                    virologistMap.get(whom),
                    eq ) ?
                    new Response(1, "steal-equipment", "Virologist \""+who+"\" stole a(n) \""+eq.toString()+"\" from \""+whom+"\"") :
                    new Response(0, "steal-equipment", "Virologist \""+whom+"\" is not stunned.");
        }
        catch( InventoryFullException ex )
        {
            return new Response(0, "steal-equipment", "\""+who+"\" inventory is full.");
        }
    }

    /**
     * A virológus ágenst szór egy virológusra
     * @param who aki szórja
     * @param whom akire szórja
     * @return response
     */
    public Response virologistSmearAgent( String who, String whom )
    {
        if ( !virologistMap.containsKey(who) ) return new Response(0, "smear-agent", "Virologist \""+who+"\" does not exists.");
        if ( !virologistMap.containsKey(whom) ) return new Response(0, "smear-agent", "Virologist \""+whom+"\" does not exists.");
        if ( virologistMap.get(who).isDead() ) return new Response(0, "move", "Virologist \""+who+"\" is dead.");
        if ( virologistMap.get(whom).isDead() ) return new Response(0, "move", "Virologist \""+whom+"\" is dead.");

        if ( virologistMap.get(who).getLocation() != virologistMap.get(whom).getLocation() && !virologistMap.get(who).getLocation().isNeighbor(virologistMap.get(whom).getLocation()) ) return new Response(0, "smear-agent", "Virologist \""+who+"\" and \""+whom+"\" are not neighbors.");

        return virologistMap.get(who).smearAgent( virologistMap.get(whom) ) ?
                new Response(1, "smear-agent", "Virologist \""+who+"\" smeared an agent to \""+whom+"\".") :
                new Response(0, "smear-agent", "Virologist \""+who+"\" does not have a created agent." );
    }

    /**
     * A virológus használja az egyik ezközt egy másik virológuson
     * Például a baltával leüti a medvét
     * @param who a virológus
     * @param equipment az eszköz száma
     * @param whom akin használja
     * @return response
     */
    public Response virologistUseEquipment( String who, int equipment, String whom )
    {
        if ( !virologistMap.containsKey(who) ) return new Response(0, "use", "Virologist \""+who+"\" does not exists.");
        if ( !virologistMap.containsKey(whom) ) return new Response(0, "use", "Virologist \""+whom+"\" does not exists.");
        if ( virologistMap.get(who).isDead() ) return new Response(0, "move", "Virologist \""+who+"\" is dead.");
        if ( virologistMap.get(whom).isDead() ) return new Response(0, "move", "Virologist \""+whom+"\" is dead.");

        if ( virologistMap.get(who).getLocation() != virologistMap.get(whom).getLocation() && !virologistMap.get(who).getLocation().isNeighbor(virologistMap.get(whom).getLocation()) ) return new Response(0, "use", "Virologist \""+who+"\" and \""+whom+"\" are not neighbors.");

        if ( (virologistMap.get(who).getWornEquipments().size() < equipment) ) return new Response(0, "use", "Item in index \""+equipment+"\" does not exists.");

        Equipment eq = virologistMap.get(who).getWornEquipments().get(equipment-1);
        return eq.use( virologistMap.get(whom) ) ?
                new Response(1, "use", "Virologist \""+who+"\" used a(n) \""+eq.toString()+"\" on \""+whom+"\""
                        + (virologistMap.get(whom).isDead() ? "\n[game] Virologist \""+whom+"\" dead." : "")) :
                new Response(0, "use", "Virologist \""+who+"\" can't use this equipment, or it is'nt exists.");
    }

    /**
     * A virológus készít egy ágenst
     * @param name a virológus
     * @param code az ágens száma a listában
     * @return response
     */
    public Response virologistCreateAgent( String name, int code )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "create-agent", "Virologist \""+name+"\" does not exists.");
        if ( (virologistMap.get(name).getLearntCodes().size() < code) ) return new Response(0, "create-agent", "Genetic code in index \""+code+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        GeneticCode geneticCode = virologistMap.get(name).getLearntCodes().get(code-1);

        return virologistMap.get(name).createAgent(geneticCode) ?
                new Response(1, "create-agent", "Agent \""+geneticCode.toString()+"\" created.") :
                new Response(0, "create-agent", "Virologist \""+name+"\" does not have enough materials to create a \""+geneticCode.toString()+"\".");
    }

    /**
     * Beállítja a virológus alapanyagszintjeit
     * @param name virológus neve
     * @param n nukleotid
     * @param a aminósav
     * @return response
     */
    public Response virologistSetMaterials( String name, int n, int a )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "virologist-set-materials", "Virologist \""+name+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        boolean nm, am;
        nm = virologistMap.get(name).setNucleotidCount(n);
        am = virologistMap.get(name).setAminoacidCount(a);
        return new Response(1, "virologist-set-materials", "Virologist \""+name+"\" materials set to nucleotid = "+virologistMap.get(name).getNucleotidCount()+ (!nm ? "(max)" : "") +" and aminoacid = " + virologistMap.get(name).getAminoacidCount() + (!am ? "(max)": "") + "." );
    }

    /**
     * Lebénítja a virológust
     * @param name a virológus
     * @param stun bénítsa vagy ne bénítsa?
     * @return response
     */
    public Response virologistStun( String name, boolean stun )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "virologist-stun", "Virologist \""+name+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        virologistMap.get(name).setStunned( stun );

        return stun ?
                new Response(1, "virologist-stun", "Virologist \""+name+"\" is stunned.") :
                new Response(1, "virologist-stun", "Virologist \""+name+"\" is not stunned.") ;
    }

    /**
     * Beállítja a virológus jelenlegi mezőjét, amin áll
     * @param name a virológus
     * @param field a mező
     * @return response
     */
    public Response virologistSetLocation( String name, String field )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "virologist-set-location", "Virologist \""+name+"\" does not exists.");
        if ( !fieldMap.containsKey(field) ) return new Response(0, "virologist-set-location", "Field \""+name+"\" does not exists.");
        if ( virologistMap.get(name).isDead() ) return new Response(0, "move", "Virologist \""+name+"\" is dead.");

        virologistMap.get(name).getLocation().removeVirologist();

        fieldMap.get(field).addVirologist(
                virologistMap.get(name)
        );
        virologistMap.get(name).setLocation(
                fieldMap.get(field)
        );

        return new Response(1, "virologist-set-location", "Virologist \""+name+"\" current location set to \""+field+"\"." );
    }

    /**
     * Kiírja a virológus adatait
     * @param name a virológus
     * @return response
     */
    public Response virologistGetStats( String name )
    {
        if ( !virologistMap.containsKey(name) ) return new Response(0, "stat", "Virologist \""+name+"\" does not exists.");

        StringBuilder sb = new StringBuilder();
        sb.append("Virologist ").append(name).append(" stats:\n");
        sb.append("                  ").append("Dead: ").append(virologistMap.get(name).isDead() ? "yes" : "no").append("\n");
        sb.append("                  ").append("Bear: ").append(virologistMap.get(name).isBearAffected() ? "yes" : "no").append("\n");
        sb.append("                  ").append("ChoreaMinor affected: ").append(virologistMap.get(name).isChoreaAffected() ? "yes" : "no").append("\n");
        sb.append("                  ").append("Stunned: ").append(virologistMap.get(name).isStunned() ? "yes" : "no").append("\n");
        sb.append("                  ").append("Nucleotid: ").append(virologistMap.get(name).getNucleotidCount()).append("\n");
        sb.append("                  ").append("Aminoacid: ").append(virologistMap.get(name).getAminoacidCount()).append("\n");
        sb.append("                  ").append("MAX nucleotid: ").append(virologistMap.get(name).getMaxNucleotidCount()).append("\n");
        sb.append("                  ").append("MAX aminoacid: ").append(virologistMap.get(name).getMaxAminoacidCount()).append("\n");
        sb.append("                  ").append("Location: ");
        fieldMap.forEach( (key, value) -> {
            if ( virologistMap.get(name).getLocation() == value ) sb.append(key);
        });
        sb.append("\n");

        sb.append("                  ").append("Created agent: ");
        if ( virologistMap.get(name).getCreatedAgent() == null ) sb.append("-");
        else sb.append( virologistMap.get(name).getCreatedAgent().toString() );
        sb.append("\n");

        sb.append("                  ").append("Equipments: ");
        if ( virologistMap.get(name).getWornEquipments().size() == 0 ) sb.append("-");
        else
        {
            virologistMap.get(name).getWornEquipments().forEach( equipment -> {
                sb.append(equipment.toString()).append(", ");
            });
        }

        sb.append("\n").append("                  ").append("Learnt codes: ");
        if ( virologistMap.get(name).getLearntCodes().size() == 0 ) sb.append("-");
        else
        {
            virologistMap.get(name).getLearntCodes().forEach( geneticCode -> {
                sb.append(geneticCode.toString()).append(", ");
            });
        }

        sb.append("\n").append("                  ").append("Affecting viruses: ");
        if ( virologistMap.get(name).getAffectingAgents().size() == 0 ) sb.append("-");
        else
        {
            virologistMap.get(name).getAffectingAgents().forEach( virus -> {
                sb.append(virus.toString()).append(", ");
            });
        }

        return new Response(1, "stat", sb.toString());
    }

    /**
     * Létrehoz egy új mezőt
     * @param name mező neve
     * @param type mező tíousa
     * @return response
     */
    public Response fieldCreate(String name, String type )
    {
        if ( fieldMap.containsKey( name ) ) return new Response( 0, "create-field", "Field with this name already exists." );

        switch ( type )
        {
            case "bunker":      fieldMap.put(name, new Bunker()); break;
            case "laboratory":  fieldMap.put(name, new Laboratory(null)); break;
            case "storage":     fieldMap.put(name, new Storage(0, 0)); break;
            case "field":       fieldMap.put(name, new Field()); break;
            default:            return new Response( 0, "create-field", "Unknown field type \"" + type + "\".");
        }

        return new Response( 1, "create-field", "Field \""+type+"\" created with name = \""+name+"\".");
    }

    /**
     * Hozzáadja a mezőt egy másik meződ szomszédjaként
     * @param who kit adjon hozzá
     * @param whom kihez
     * @return response
     */
    public Response fieldAddNeighbor( String who, String whom )
    {
        if ( !fieldMap.containsKey(who) || !fieldMap.containsKey(whom) ) return new Response(0, "add-neighbor", "Field \""+who+"\" or \""+whom+"\" does not exists.");
        if ( fieldMap.get( whom ).isNeighbor( fieldMap.get(who) ) ) return new Response( 0, "add-neighbor", "Field \""+who+"\" is already neighbor of \""+whom+"\".");

        fieldMap.get( whom ).addNeighbor( fieldMap.get( who ) );
        return new Response(1, "add-neighbor", "Neighbor \""+who+"\" added to \""+whom+"\".");
    }

    /**
     * Hozzáadja a felszerelést a megadott óvóhelyhez
     * @param field mező
     * @param equipment felszerelés neve
     * @return repsonse
     */
    public Response fieldAddEquipment( String field, String equipment )
    {
        if ( !fieldMap.containsKey( field ) ) return new Response( 0, "field-add-equipment", "Field \""+field+"\" does not exists.");

        Equipment eq = createNewEquipment( equipment );
        if ( eq == null ) return new Response(0, "field-add-equipment", "Unknown equipment type \""+equipment+"\".");

        boolean added;
        added = fieldMap.get( field ).addEquipment( eq );

        return added ?
                new Response( 1, "field-add-equipment", "Equipment \""+equipment+"\" added to \""+field+"\".") :
                new Response( 0, "field-add-equipment", "Field \""+field+"\" is not a bunker.");
    }

    /**
     * Hozzáadja a genetikai kódot a megadott laboratóriumhoz
     * @param field mező
     * @param code genetikai kód neve
     * @return response
     */
    public Response fieldAddGeneticCode( String field, String code )
    {
        if ( !fieldMap.containsKey(field) ) return new Response(0, "field-add-genetic-code", "Field \""+field+"\" does not exists.");

        boolean added;
        switch ( code )
        {
            case "chorea-minor":    added = fieldMap.get(field).setGeneticCode( new ChoreaMinorAgentGeneticCode(GeneticCode.DefaultNucleotidCost, GeneticCode.DefaultAminoacidCost)); break;
            case "forget":          added = fieldMap.get(field).setGeneticCode( new ForgetAgentGeneticCode(GeneticCode.DefaultNucleotidCost, GeneticCode.DefaultAminoacidCost)); break;
            case "protect":         added = fieldMap.get(field).setGeneticCode( new ProtectAgentGeneticCode(GeneticCode.DefaultNucleotidCost, GeneticCode.DefaultAminoacidCost)); break;
            case "stun":            added = fieldMap.get(field).setGeneticCode( new StunAgentGeneticCode(GeneticCode.DefaultNucleotidCost, GeneticCode.DefaultAminoacidCost)); break;
            default:    return new Response( 0, "field-add-genetic-code", "Unknown genetic code \""+code+"\"");
        }

        return added ?
                new Response( 1, "field-add-genetic-code", "Genetic code \""+code+"\" added to \""+field+"\".") :
                new Response( 0, "field-add-genetic-code", "Field \""+field+"\" is not a laboratory.");
    }

    /**
     * Beállítja a laboratórium mezőnek hogy fertőzött-e
     * @param field a mező
     * @param affected fertőzött-e
     * @return response
     */
    public Response laboratorySetAffected( String field, boolean affected )
    {
        if ( !fieldMap.containsKey( field ) ) return new Response(0, "laboratory-set-affected", "Field \""+field+"\" does not exists.");

        return fieldMap.get(field).setAffected( affected ) ?
                new Response(1, "laboratory-set-affected", affected ? "Laboratory \""+field+"\" set to affected." : "Laboratory \""+field+"\" set to not affected.") :
                new Response(0, "laboratory-set-affected", "Field \""+field+"\" is not a laboratory.");
    }


    // Privát metódusok a publikos működések támogatásáshoz

    /**
     * Létrehoz egy felszerelést a típusa alapján
     * @param equipmentName a felszerelés neve
     * @return a felszerelés
     */
    private Equipment createNewEquipment( String equipmentName )
    {
        switch (equipmentName)
        {
            case "axe"              : return new AxeEquipment();
            case "bag"              : return new BagEquipment(BagEquipment.DefaultBagSize);
            case "glove"            : return new GloveEquipment();
            case "protective-cave"  : return new ProtectiveCaveEquipment();
        };

        return null;
    }
}
