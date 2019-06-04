package hw4_platformer;

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
    private static ArrayList<HiddenPlatform>                hidden          = new ArrayList<HiddenPlatform>();               // 0death1spawn
    private static final EventManager                       eventManager    = EventManager.getInstance();
    private ObjectOutputStream                              dout;
    private ObjectInputStream                               din;
    private static ServerSocket                             ss;
    private static PApplet                                  pApplet;
    private static Object                                   sync            = new Object();                                  // "server"
    private static Boolean                                  paused;

    public Server () {
    }

    public void run () {
        try {
            pApplet = this;
            int clientNumber = 0;//
            paused = false;
            while ( true ) {
                System.out.println( "About to accept..." );
                final Socket s = ss.accept();
                System.out.println( "New connection Established" );
                synchronized ( pApplet ) {
                    dout = new ObjectOutputStream( s.getOutputStream() );
                    din = new ObjectInputStream( s.getInputStream() );
                    players.add( new Player( pApplet, hidden.get( 1 ).getRectangle() ) );
                    clientNumbers.add( clientNumber );// matchclient&stream
                    output_streams.add( dout );// output stream @clientnum
                    input_streams.add( din );// input stream @clientnum
                    dout.writeBoolean( paused );
                    dout.writeInt( clientNumber ); // writes the client number
                    System.out.println( "sent cn" );

                    dout.writeObject( players );
                    dout.writeObject( platforms );
                    dout.writeObject( movingPlatforms );
                    dout.writeObject( hidden );// death
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
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        PApplet.main( "hw4_platformer.Server" );
        final Server server = new Server();
        ( new Thread( server ) ).start();
        while ( true ) {// loop
            int index = 0;

            synchronized ( sync ) {

                for ( final ObjectInputStream in : input_streams ) {// for every
                                                                    // player
                    try {
                        // System.out.println( "in server read/write" );
                        index = input_streams.indexOf( in );
                        paused = in.readBoolean();

                        final int playerNum = in.readInt();
                        final Player changedPlayer = (Player) in.readObject();
                        players.set( index, changedPlayer );
                        for ( final Integer cn : clientNumbers ) {
                            if ( cn == playerNum ) {
                                players.set( clientNumbers.indexOf( cn ), changedPlayer );
                            }
                        }
                        output_streams.get( input_streams.indexOf( in ) ).writeObject( players );
                        output_streams.get( input_streams.indexOf( in ) ).flush();
                        output_streams.get( input_streams.indexOf( in ) ).reset();

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
            // }

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

        hidden.add( new HiddenPlatform( pApplet, new Rectangle( 0, pApplet.height - 15, 50, 15 ) ) );// death
        hidden.add( new HiddenPlatform( pApplet, new Rectangle( pApplet.width - 75, pApplet.height - 75, 50, 75 ) ) );// spawn

        platforms.add( new Platform( pApplet, new Rectangle( 301, pApplet.height - 75, 50, 75 ) ) );// small
        platforms.add( new Platform( pApplet, new Rectangle( 200, pApplet.height - 115, 100, 115 ) ) );// med
        platforms.add( new Platform( pApplet, new Rectangle( 49, pApplet.height - 155, 150, 170 ) ) );// large
        platforms.add( new Platform( pApplet, new Rectangle( ( pApplet.width / 2 ), 100, 75, 75 ) ) );// center
        movingPlatforms.add( new MovingPlatform( pApplet, new Rectangle( 400, 100, 150, 20 ), 'v', 90 ) ); // right
                                                                                                           // mover
        movingPlatforms.add( new MovingPlatform( pApplet, new Rectangle( 100, 270, 130, 20 ), 'h', 90 ) );// left
                                                                                                          // h
                                                                                                          // mover

    }

    @Override
    public void draw () {
        background( 100 );

        synchronized ( ss ) {
            for ( final AbsPlatform plats : platforms ) {
                plats.display();
            }
            for ( final MovingPlatform mov : movingPlatforms ) {
                mov.display();
                mov.update();
            }
        }

        // player.movePlayer();
    }

}
