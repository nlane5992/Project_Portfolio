package hw4_snake;

import java.awt.Rectangle;

import processing.core.PApplet;

// used to be moving platform, now is the snake tail
public class MovingPlatform extends AbsPlatform implements Updateable {
    /**
     *
     */
    private static final long serialVersionUID = -8121717702928733997L;
    public char               dir;
    // public String pattern; //used for multiple patterns, imp l8r

    public MovingPlatform ( final PApplet parent ) {
        super( parent );
        initMostVars();
        // TODO Auto-generated constructor stub
    }

    // was gonna just be a platform, but if it is moving, it has to move. so added
    // vars. also, string will be used
    // later with more parameters in the constructors to facilitate multiple
    // patterns so as to make a square i.e. "hvhv"
    public MovingPlatform ( final PApplet parent, final Rectangle rectangle, final char dir ) {
        super( parent, rectangle );
        initMostVars();
        setDir( dir );
        display();

    }

    private void initMostVars () {

        // color
        setColorR( 0 );// default random
        setColorG( 255 );
        setColorB( 0 );

        // movement
        setMovesX( 0 );
        setMovesY( 0 );
        setUpdateable( true );
        // internal
        dir = ' ';
        // dirtern = " ";
    }

    public void update () {
        stepDirection();
        rectangle.setRect( x, y, w, h );
        display();
    }

    public void stepDirection () {
        if ( dir == 'u' || dir == 'd' ) {
            setMovesX( 0 );
            if ( dir == 'u' ) {
                setMovesY( -2 );// negative to go up
            }
            else if ( dir == 'd' ) {
                setMovesY( 2 );// positive to go down
            }
            setY( getY() + getMovesY() );
        }
        else if ( dir == 'l' || dir == 'r' ) {

            setMovesY( 0 );

            if ( dir == 'l' ) {
                setMovesX( -2 );
            }
            else if ( dir == 'r' ) {
                setMovesY( 2 );
            }
            setX( getX() + getMovesX() );
        }
    }

    /**
     * @return the dir
     */
    public char getDir () {
        return dir;
    }

    /**
     * @param dir
     *            the dir to set
     */
    public void setDir ( final char dir ) {
        this.dir = dir;
    }

}
