package hw4_platformer;

import java.awt.Rectangle;

import processing.core.PApplet;

// TODO, HAVENT IMPLEMENTED. need patterns and stock movenment given a char
public class MovingPlatform extends AbsPlatform implements Updateable {
    /**
     *
     */
    private static final long serialVersionUID = -8121717702928733997L;
    private boolean           reverse;
    public char               pat;
    private int               iter;
    public int                desiredIter;
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
    public MovingPlatform ( final PApplet parent, final Rectangle rectangle, final char pat, final int desiredIter ) {
        super( parent, rectangle );
        display();
        initMostVars();
        setPat( pat );
        setDesiredIter( desiredIter );
        if ( pat == 'h' || pat == 'H' ) {
            setMovesX( 2 );
        }
        else if ( pat == 'v' || pat == 'V' ) {
            setMovesY( 2 );
        }
    }

    private void initMostVars () {

        // color
        setColorR( 0 );// default random
        setColorG( 0 );
        setColorB( 255 );

        // movement
        setMovesX( 0 );
        setMovesY( 0 );
        setUpdateable( true );
        // internal
        reverse = false;
        pat = ' ';
        iter = 0;
        desiredIter = 0;
        // pattern = " ";
    }

    public void update () {
        stepPattern();
        rectangle.setRect( x, y, w, h );
        display();
    }

    public void stepPattern () {
        // for ( int step = 0; step < pattern.length(); step++ ) { // currently only
        // does v and h`
        if ( iter == 0 ) {
            reverse = false;
        }
        else if ( iter == desiredIter ) {
            reverse = true;
            setMovesX( -getMovesX() );

            setMovesY( -getMovesY() );

        }
        if ( !reverse ) {
            if ( pat == 'h' || pat == 'H' ) {
                setX( getX() + getMovesX() );
            }
            else if ( pat == 'v' || pat == 'V' ) {
                setY( getY() + getMovesY() );
            }
            iter++;
        }
        else {
            if ( pat == 'h' || pat == 'H' ) {
                this.setX( getX() + getMovesX() );
            }
            else if ( pat == 'v' || pat == 'V' ) {
                this.setY( getY() + getMovesY() );
            }
            iter--;
        }
        // }
    }

    /**
     * @return the iter
     */
    public int getIter () {
        return iter;
    }

    /**
     * @param iter
     *            the iter to set
     */
    public void setIter ( final int iter ) {
        this.iter = iter;
    }

    /**
     * @return the desiredIter
     */
    public int getDesiredIter () {
        return desiredIter;
    }

    /**
     * @param desiredIter
     *            the desiredIter to set
     */
    public void setDesiredIter ( final int desiredIter ) {
        this.desiredIter = desiredIter;
    }

    /**
     * @return the reverse
     */
    public boolean isReverse () {
        return reverse;
    }

    /**
     * @param reverse
     *            the reverse to set
     */
    public void setReverse ( final boolean reverse ) {
        this.reverse = reverse;
    }

    /**
     * @return the pat
     */
    public char getPat () {
        return pat;
    }

    /**
     * @param pat
     *            the pat to set
     */
    public void setPat ( final char pat ) {
        this.pat = pat;
    }

    /**
     * @return the pattern
     *
     *         public String getPattern () { return pattern; }
     **
     *
     * @param pattern
     *            the pattern to set
     *
     *            public void setPattern ( final String pattern ) { this.pattern =
     *            pattern; }
     */
}
