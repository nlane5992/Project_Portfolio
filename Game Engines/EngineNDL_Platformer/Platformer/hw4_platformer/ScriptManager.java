package hw4_platformer;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Class to create and manage a JavaScript engine.
 */
public class ScriptManager {

    /* The javax.script JavaScript engine used by this class. */
    private static ScriptEngine js_engine    = new ScriptEngineManager().getEngineByName( "JavaScript" );
    /* The Invocable reference to the engine. */
    private static Invocable    js_invocable = (Invocable) js_engine;

    /**
     * Used to bind the provided object to the name in the scope of the scripts
     * being executed by this engine.
     */
    public static void bindArgument ( final String name, final Object obj ) {
        js_engine.put( name, obj );
    }

    /**
     * Will load the script source from the provided filename.
     */
    public static void loadScript ( final String script_name ) {
        try {
            js_engine.eval( new java.io.FileReader( script_name ) );
        }
        catch ( final ScriptException se ) {
            se.printStackTrace();
        }
        catch ( final java.io.IOException iox ) {
            iox.printStackTrace();
        }
    }

    /**
     * Will invoke the "update" function of the script loaded by this engine without
     * any parameters.
     */
    public static void executeScript ( final String name ) {
        try {
            js_invocable.invokeFunction( name );
        }
        catch ( final ScriptException se ) {
            se.printStackTrace();
        }
        catch ( final NoSuchMethodException nsme ) {
            nsme.printStackTrace();
        }
    }

    /**
     * Will invoke the "update" function of the script loaded by this engine with
     * the provided list of parameters.
     */
    public static void executeScript ( final String name, final Object... args ) {
        try {
            js_invocable.invokeFunction( name, args );
        }
        catch ( final ScriptException se ) {
            se.printStackTrace();
        }
        catch ( final NoSuchMethodException nsme ) {
            nsme.printStackTrace();
        }
    }

}
