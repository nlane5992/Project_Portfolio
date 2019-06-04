package hw4_platformer;

import java.io.Serializable;
import java.util.HashMap;

/*
 * Help from: https://www.geeksforgeeks.org/enum-in-java/,
 * https://www.baeldung.com/a-guide-to-java-enums
 * https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html ,esp from the
 * Planet was considering doing the whole javax.script migration, but not enough
 * exp and staying enum .values() gets the types, .ordinal() gets the index,
 * .valueOf(String) gets enum for des string lecture dates 10/2, 10/9, 10/18 for
 * events /enum inspir
 */
enum Type {
    // Required original player specific
    // Player
    COLLISION ( 0 ),
    DEATH ( 1 ),
    SPAWN ( 2 ),
    INPUT ( 3 ),
    // Required additional
    // Replay
    RSTART ( 4 ), // pressed record, take every object/pos as an argument
    RSTOP ( 5 ), // redraw (restore) the screen based on started variables
    RSPEED ( 6 ),
    // Potential additional,
    // Time
    PAUSE ( 7 ),
    UNPAUSE ( 8 ),
    // generic event
    GENERIC ( 9 );//
    // Replay stuff
    // RESTORE, LOCATION;

    private int typeNum;

    private Type ( final int num ) {
        setTypeNum( num );
    }

    public int getTypeNum () {
        return typeNum;
    }

    private void setTypeNum ( final int typeNum ) {
        this.typeNum = typeNum;
    }
}

public class Event implements Serializable {
    /**
     *
     */
    private static final long       serialVersionUID = 1310878594239298958L;
    private HashMap<String, Object> arguments;                              // key, generic
    private long                    initTime;                               // millis as suggested,
    private int                     age;                                    // type of age ++ per event created
    private int                     priority;                               // priority of type, may become time
    private int                     typeNum;
    private Type                    type;
    private static final Type[]     types            = Type.values();

    /**
     * initiate event with just type and an empty arg list to be added in later
     * steps
     *
     * @return the types
     */

    public Event ( final Type type ) {
        setArguments( new HashMap<String, Object>() );
        setInitTime( System.currentTimeMillis() );
        setAge( 0 );
        setPriority( 0 );
        setTypeNum( type );
        setType( type );

    }

    /**
     * initiate an event with the set of arguments if applicable
     *
     * @return the types
     */

    public Event ( final Type type, final HashMap<String, Object> arguments ) {
        setArguments( arguments );
        setInitTime( System.currentTimeMillis() );
        setAge( 0 );
        setPriority( 0 );
        setTypeNum( type );
        setType( type );

    }

    public void setArguments ( final HashMap<String, Object> args ) {
        this.arguments = args;
        // TODO Auto-generated method stub

    }

    public void addToArguments ( final String key, final Object obj ) {
        this.arguments.put( key, obj );
        // TODO Auto-generated method stub

    }

    /**
     * @return the arguments
     */
    public HashMap<String, Object> getArguments () {
        return arguments;
    }

    /**
     * @return the initTime
     */
    public long getInitTime () {
        return initTime;
    }

    /**
     * @param initTime
     *            the initTime to set
     */
    public void setInitTime ( final long initTime ) {
        this.initTime = initTime;
    }

    /**
     * @return the age
     */
    public int getAge () {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge ( final int age ) {
        this.age = age;
    }

    public void incAge () {
        this.age++;
    }

    /**
     * @return the priority
     */
    public int getPriority () {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority ( final int priority ) {
        this.priority = priority;
    }

    /**
     * @return the typeNum
     */
    public int getTypeNum () {
        return typeNum;
    }

    /**
     * @param typeNum
     *            the typeNum to set
     */
    public void setTypeNum ( final Type type ) {
        this.typeNum = type.getTypeNum();
    }

    public static final Type[] getTypes () {
        return types;
    }

    /**
     * @return the type
     */
    public Type getType () {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType ( final Type type ) {
        this.type = type;
    }

}
