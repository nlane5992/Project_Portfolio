package hw4_snake;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

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

    private static ObjectInputStream  input_stream;
    private static ObjectOutputStream output_stream;
    private static Socket             s;
    private static PApplet            pApplet;
    private static ArrayList<Player>  players      = new ArrayList<Player>();
    private AbsPlatform               pellet;
    private boolean                   isPelletGone;
    private static int                id;
    private static Player             player;
    private static EventManager       eventManager = EventManager.getInstance();
    private static Object             syncer       = new Object();
    private static final EventHandler handler      = new EventHandler();
    private final Random              random       = new Random();
    // one across all instances
    // stupid stupid main wont read
    // ^solved by making this real main a dummy main to call the one that can
    // take
    // exceptions

    public static void main ( final String[] args ) {
        PApplet.main( "hw4_snake.Client" );
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
            id = input_stream.readInt();
            players = (ArrayList<Player>) input_stream.readObject();
            for ( final Player play : players ) {
                play.setParent( pApplet );
                // play.initMostVars();
                play.initSpawnVars();
            }
            player = players.get( id );
        }

        // player = Player stuff
        try {
            while ( true ) {
                // System.out.println( "in client read/write" );
                output_stream.writeInt( id );
                // output_stream.writeObject( player );
                output_stream.flush();
                output_stream.reset();
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
        background( 0 );
        isPelletGone = true;

    }

    @Override
    public void keyPressed () {
        final Event userInput = new Event( Type.INPUT );
        userInput.addToArguments( "Player", player );
        if ( key == CODED ) {
            if ( keyCode == UP ) {
                userInput.addToArguments( "Key", "u" );
                System.out.println( "up" );
            }
            if ( keyCode == DOWN ) {
                userInput.addToArguments( "Key", "d" );
                System.out.println( "down" );

            }
            if ( keyCode == LEFT ) {
                userInput.addToArguments( "Key", "l" );
                System.out.println( "left" );

            }
            if ( keyCode == RIGHT ) {
                userInput.addToArguments( "Key", "r" );
                System.out.println( "right " );

            }
        }
        // else if ( key == 'w' || key == 'W' ) {
        // userInput.addToArguments( "Key", "u" );
        // }
        // else if ( key == 's' || key == 'S' ) {
        // userInput.addToArguments( "Key", "d" );
        // }
        // else if ( key == 'a' || key == 'A' ) {
        // userInput.addToArguments( "Key", "l" );
        // }
        // else if ( key == 'D' || key == 'D' ) {
        // userInput.addToArguments( "Key", "r" );
        // }
        else if ( key == ' ' ) {// space to respawn
            userInput.addToArguments( "Key", "s" );
            System.out.print( "space" );
        }
        EventManager.raise( userInput );
    }

    @Override
    public void draw () {
        synchronized ( syncer ) {

            background( 100 );
            if ( isPelletGone ) {
                pellet = new Platform( pApplet, new Rectangle( ( 10 * random.nextInt( 22 ) ), ( 10 * random.nextInt( 22 ) ), 20, 20 ) );
                pellet.display();
                isPelletGone = false;
            }
            if ( !isPelletGone ) {
                pellet.display();
            }

            player.update();
            if ( player.getRectangle().intersects( pellet.rectangle ) ) {
                isPelletGone = true;
                final Event playerEat = new Event( Type.COLLISION );
                playerEat.addToArguments( "Player", player );
                playerEat.addToArguments( "OtherAbs", pellet );
                EventManager.raise( playerEat );
            }
            EventManager.alertOfNextEvent();

            // player.checkIntersect( platforms );
        }

    }

    // player.movePlayer();

    // @Override

    public void run () {

    }
}
