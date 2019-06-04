package hw4_platformer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import processing.core.PApplet;

/*
 * @author Nathanael Lane initial server/client code from Dr. David Roberts,
 * provided Additional references for: proper closing:
 * https://coderanch.com/t/634573/java/properly-close-socket-connection
 * flushing:
 * https://stackoverflow.com/questions/2340106/what-is-the-purpose-of-flush-in-
 * java-streams
 */
public class Client extends PApplet implements Runnable {

    private static ObjectInputStream         input_stream;
    private static ObjectOutputStream        output_stream;
    private static Socket                    s;
    private static PApplet                   pApplet;
    private static ArrayList<MovingPlatform> mov          = new ArrayList<MovingPlatform>();
    private static ArrayList<Platform>       plats        = new ArrayList<Platform>();
    private static ArrayList<Player>         players      = new ArrayList<Player>();
    private static int                       id;
    private static ArrayList<HiddenPlatform> hidden       = new ArrayList<HiddenPlatform>( 2 );
    private static Player                    player;
    private static EventManager              eventManager = EventManager.getInstance();
    private static Object                    syncer       = new Object();
    private static HiddenPlatform            death;
    private static Boolean                   paused;
    private static final EventHandler        handler      = new EventHandler();                // one
                                                                                               // across
                                                                                               // all
                                                                                               // instances?
                                                                                               // oof
    // stupid stupid main wont read
    // ^solved by making this real main a dummy main to call the one that can
    // take
    // exceptions

    public static void main ( final String[] args ) {
        PApplet.main( "hw4_platformer.Client" );
        try {
            exceptionMain();
        }
        catch ( final Exception e ) {
        }

    }

    // throws errors otherwise
    public static void exceptionMain () throws IOException, InterruptedException, ClassNotFoundException {
        final Socket s = new Socket( "127.0.0.1", 5500 );
        // System.out.println( "connected..." );// debugging
        output_stream = new ObjectOutputStream( s.getOutputStream() );
        input_stream = new ObjectInputStream( s.getInputStream() );
        // System.out.println( "getting id..." ); // debugging
        EventManager.registerAll( handler );
        synchronized ( syncer ) {
            // System.out.print( "inside client sync" );
            paused = input_stream.readBoolean();
            id = input_stream.readInt();
            players = (ArrayList<Player>) input_stream.readObject();
            for ( final Player play : players ) {
                play.setParent( pApplet );
            }
            player = players.get( id );
            plats = (ArrayList<Platform>) input_stream.readObject();
            for ( final Platform plat : plats ) {
                plat.setParent( pApplet );
            }
            mov = (ArrayList<MovingPlatform>) input_stream.readObject();
            for ( final MovingPlatform mover : mov ) {
                mover.setParent( pApplet );
            }
            hidden = (ArrayList<HiddenPlatform>) input_stream.readObject();
            for ( final HiddenPlatform hide : hidden ) {
                hide.setParent( pApplet );
            }
            death = hidden.get( 0 );
        }

        // player = Player stuff
        try {
            while ( true ) {
                // System.out.println( "in client read/write" );
                output_stream.writeBoolean( paused );
                output_stream.writeInt( id );

                output_stream.writeObject( player );
                output_stream.flush();
                output_stream.reset();
                synchronized ( syncer ) {
                    players.clear();
                    players = (ArrayList<Player>) input_stream.readObject();
                    for ( final Player play : players ) {
                        play.setParent( pApplet );
                    }
                }
            }
        }
        finally {
            s.close();// closes when done
        }
    }

    @Override
    public void settings () {
        size( 520, 520 );
    }

    @Override
    public void setup () {
        pApplet = this;

    }

    @Override
    public void keyPressed () {
        final Event userInput = new Event( Type.INPUT );
        userInput.addToArguments( "Player", player );
        if ( key == CODED ) {
            if ( keyCode == LEFT ) {
                userInput.addToArguments( "Key", "l" );
            }
            if ( keyCode == RIGHT ) {
                userInput.addToArguments( "Key", "r" );
            }
        }
        else if ( key == ' ' ) {
            userInput.addToArguments( "Key", "s" );
            // System.out.print( "space" );
        }
        EventManager.raise( userInput );
    }

    @Override
    public void draw () {
        synchronized ( syncer ) {
            EventManager.alertOfNextEvent();

            background( 100 );

            if ( player != null ) {

                player.update();

                if ( death != null && player.getRectangle().intersects( death.getRectangle() ) ) {
                    final Event playerDied = new Event( Type.DEATH );
                    playerDied.addToArguments( "Player", player );
                    playerDied.addToArguments( "Spawn", player.getSpawn() );
                    EventManager.raise( playerDied );

                }
            }
            for ( final AbsPlatform plat : plats ) {
                if ( plat != null ) {
                    // System.out.print( plat.getColorR() + " " +
                    // plat.getColorG() + " " +
                    // plat.getColorB() + " " + "ITER" );
                    plat.display();
                    if ( plat.getRectangle().intersects( player.rectangle ) ) {
                        final Event playerCollision = new Event( Type.COLLISION );
                        playerCollision.addToArguments( "Player", player );
                        playerCollision.addToArguments( "OtherAbs", plat );
                        EventManager.raise( playerCollision );
                    }
                    player.checkIntersect( plat );
                }

            }
            for ( final MovingPlatform m : mov ) {
                if ( m != null ) {
                    m.display();
                    if ( m.getRectangle().intersects( player.rectangle ) ) {
                        final Event playerCollision = new Event( Type.COLLISION );
                        playerCollision.addToArguments( "Player", player );
                        playerCollision.addToArguments( "OtherAbs", m );
                        EventManager.raise( playerCollision );
                    }
                    m.update();
                }
            }
            for ( final Player play : players ) {
                if ( play != null ) {
                    play.display();
                }
            }
            // player.checkIntersect( platforms );
        }

    }

    // player.movePlayer();

    // @Override
    public void run () {

    }
}
