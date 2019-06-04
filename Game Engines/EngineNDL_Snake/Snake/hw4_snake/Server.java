package hw4_snake;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;

/*
 * @author Nathanael Lane initial server/client code from Dr. David Roberts,
 * provided Additional references for: closing sockets:
 * https://coderanch.com/t/634573/java/properly-close-socket-connection
 * flushing:
 * https://stackoverflow.com/questions/2340106/what-is-the-purpose-of-flush-in-
 * java-streams
 */
public class Server extends PApplet implements Runnable {

    private static CopyOnWriteArrayList<ObjectInputStream>  input_streams   = new CopyOnWriteArrayList<ObjectInputStream>();;
    private static CopyOnWriteArrayList<ObjectOutputStream> output_streams  = new CopyOnWriteArrayList<ObjectOutputStream>();
    private static ArrayList<Integer>                       clientNumbers   = new ArrayList<Integer>();
    private static ArrayList<AbsPlatform>                   platforms       = new ArrayList<AbsPlatform>();
    private static ArrayList<MovingPlatform>                movingPlatforms = new ArrayList<MovingPlatform>();
    private static ArrayList<Player>                        players         = new ArrayList<Player>();
    private static final EventManager                       eventManager    = EventManager.getInstance();
    private ObjectOutputStream                              dout;
    private ObjectInputStream                               din;
    private static ServerSocket                             ss;
    private static PApplet                                  pApplet;
    private static Object                                   sync            = new Object();                                  // "server"

    // l8r also used for sync
    public Server () {
    }

    public void run () {
        try {
            pApplet = this;
            // hidden.add( new HiddenPlatform( pApplet, new Rectangle( pApplet.width / 3,
            // pApplet.height / 4, 20, 20 ) ) );// spawn
            int clientNumber = 0;//
            while ( true ) {
                System.out.println( "About to accept..." );
                final Socket s = ss.accept();
                System.out.println( "New connection Established" );
                synchronized ( pApplet ) {
                    dout = new ObjectOutputStream( s.getOutputStream() );
                    din = new ObjectInputStream( s.getInputStream() );
                    players.add( new Player( pApplet, new Rectangle( 300, 300, 20, 20 ) ) );
                    clientNumbers.add( clientNumber );// matchclient&stream
                    output_streams.add( dout );// output stream @clientnum
                    input_streams.add( din );// input stream @clientnum
                    dout.writeInt( clientNumber ); // writes the client number
                    System.out.println( "sent cn" );

                    dout.writeObject( players );
                    dout.flush();
                    dout.reset();

                }
                System.out.println( clientNumber + " Streams have been successfully added." );
                clientNumber++;
            }
        }
        catch ( final IOException iox ) {
            iox.printStackTrace();
        }
    }

    public static void main ( final String[] args ) {
        try {
            ss = new ServerSocket( 5500 );
        }
        catch ( final IOException e1 ) {
            e1.printStackTrace();
        }
        PApplet.main( "hw4_snake.Server" );
        final Server server = new Server();
        ( new Thread( server ) ).start();
        while ( true ) {// loop
            int index = 0;
            synchronized ( sync ) {
                for ( final ObjectInputStream in : input_streams ) {// per player // player
                    try {

                        // System.out.println( "in server read/write" );
                        index = input_streams.indexOf( in );

                        final int playerNum = in.readInt();
                        // System.out.println( "verify client num: " + playerNum );
                        // final Player changedPlayer = (Player) in.readObject();
                        // players.set( index, changedPlayer );
                        // for ( final Integer cn : clientNumbers ) {
                        // if ( cn == playerNum ) {
                        // players.set( clientNumbers.indexOf( cn ), changedPlayer );
                        // }
                        // }
                    }
                    catch ( final Exception e ) {
                        output_streams.remove( index );
                        input_streams.remove( index );
                        clientNumbers.remove( index );
                        index--;
                    }
                    index++;
                }
            }
        }
    }

    @Override
    public void settings () {
        size( 520, 520 );
    }

    @Override
    public void setup () {

        // Initialize all "stripes"
        pApplet = this;
        background( 0 );
        textSize( 40 );
        textAlign( CENTER );
        text( "Welcome to Snake", 260, 200 );
        textSize( 30 );
        text( "To Play, Start up a Client!", 260, 400 );
        // hidden.add( new HiddenPlatform( pApplet, new Rectangle( 0, pApplet.height -
        // 15, 50, 15 ) ) );// death
        //
        // platforms.add( new Platform( pApplet, new Rectangle( 301, pApplet.height -
        // 75, 50, 75 ) ) );// small
        // platforms.add( new Platform( pApplet, new Rectangle( 200, pApplet.height -
        // 115, 100, 115 ) ) );// med
        // platforms.add( new Platform( pApplet, new Rectangle( 49, pApplet.height -
        // 155, 150, 170 ) ) );// large
        // platforms.add( new Platform( pApplet, new Rectangle( ( pApplet.width / 2 ),
        // 100, 75, 75 ) ) );// center
        // // mover
        // movingPlatforms.add( new MovingPlatform( pApplet, new Rectangle( 100, 270,
        // 130, 20 ), 'h', 90 ) );// left h
    }

    @Override
    public void draw () {

        // EventManager.alertOfNextEvent();

    }
}
