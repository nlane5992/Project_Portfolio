package hw4_snake;

import java.io.Serializable;
import java.util.HashMap;

/*
 * originally had methods, but already have most of functionality in other
 * classes takes the events given from the manager and actually executes them as
 * needed
 */
// https://www.geeksforgeeks.org/enum-in-java/
public class EventHandler implements Serializable {
    private static final long serialVersionUID = -3666287246045496093L;

    public EventHandler () {
        // TODO Auto-generated constructor stub
    }

    // do all in the one method, ez -> moved to scripting so can change for future
    // stuff ezier
    public void onEvent ( final Event event ) {
        final int typeNum = event.getTypeNum();
        final HashMap<String, Object> arguments = event.getArguments();
        ScriptManager.loadScript( "snake/hw4_snake/eventhandler_snake.js" );
        Player player = null;
        AbsPlatform otherAbs = null;
        if ( arguments.containsKey( "Player" ) ) {
            player = ( (Player) arguments.get( "Player" ) );
        }
        if ( arguments.containsKey( "OtherAbs" ) ) {
            otherAbs = ( (AbsPlatform) arguments.get( "OtherAbs" ) );

        }
        ScriptManager.executeScript( "handle", typeNum, arguments, player, otherAbs );
    }

}
