package hw4_platformer;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

import processing.core.PApplet;

public abstract class RectGameObjects implements Serializable {
    /**
     *
     */
    private static final long        serialVersionUID = -90766458719177866L;
    // VARIABLES
    transient public PApplet         parent;
    public Rectangle                 rectangle        = new Rectangle();
    ArrayList<Line2D>                lines            = new ArrayList<Line2D>();
    // pos
    public float                     x;
    public float                     y;
    public float                     w;
    public float                     h;
    // col
    public float                     colorR;
    public float                     colorG;
    public float                     colorB;
    public float                     colorA;
    // move speed
    public float                     movesX;
    public float                     movesY;
    public boolean                   updateable;
    public EventHandler              handler          = new EventHandler();        // NOT SURE, HALP?
    public static final EventManager eventManager     = EventManager.getInstance();
    // CONSTRUCTORS
    // default

    public RectGameObjects ( final PApplet parent ) {
        this.parent = parent;
        initVars();

    };

    // change p, rectangle ->primarily spawning players
    public RectGameObjects ( final PApplet parent, final Rectangle rectangle ) {
        this.parent = parent;

        initVars();
        this.setRectangle( rectangle );

    }

    public void initVars () {
        setRectangle( new Rectangle( 0, 0, 0, 0 ) );

        this.setColorR( parent.random( 255 ) );// default random
        this.setColorG( parent.random( 255 ) );
        this.setColorB( parent.random( 255 ) );
        this.setColorA( 255 );// default not seethrough at all
        this.setMovesX( 0 );// default 0
        this.setMovesY( 0 );
        this.setUpdateable( false );
        lines.add( new Line2D.Float( x, y, x, y + h ) );
        lines.add( new Line2D.Float( x, y, x + w, y ) );
        lines.add( new Line2D.Float( x + w, y, x + w, y + h ) );
        lines.add( new Line2D.Float( x, y + h, x + w, y + h ) );

    }

    // GETTERS/SETTERS
    /**
     * @return the parent
     */
    public PApplet getParent () {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent ( final PApplet parent ) {
        this.parent = parent;
    }

    /**
     * @return the rectangle
     */
    public Rectangle getRectangle () {
        return rectangle;
    }

    /**
     * @param rectangle
     *            the rectangle to set
     */
    public void setRectangle ( final Rectangle rectangle ) {
        this.rectangle.setRect( rectangle );
        this.x = (float) rectangle.getX();
        this.y = (float) rectangle.getY();
        this.w = (float) rectangle.getWidth();
        this.h = (float) rectangle.getHeight();
        if ( x != 0 && y != 0 && w != 0 && h != 0 ) {
            setLines( rectangle );
        }
    }

    /**
     * @return the lines
     */
    public ArrayList<Line2D> getLines () {
        return lines;
    }

    /**
     * @param lines
     *            the lines to set
     */
    public void setLines ( final ArrayList<Line2D> lines ) {
        this.lines = lines;
    }

    /**
     * @return the lines
     */
    /**
     * @param lines
     *            the lines to set
     */
    public void setLines ( final Rectangle rectangle ) {
        // lines[0].setLine( x, y, x, (double) y + (double) h );// 0left top
        // left x,y,
        // bot left x,y+h

        lines.set( 0, new Line2D.Float( x, y, x, y + h ) );
        lines.set( 1, new Line2D.Float( x, y, x + w, y ) );
        lines.set( 2, new Line2D.Float( x + w, y, x + w, y + h ) );
        lines.set( 3, new Line2D.Float( x, y + h, x + w, y + h ) );

    }

    /**
     * @return the x
     */
    public float getX () {
        return x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX ( final float x ) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY () {
        return y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY ( final float y ) {
        this.y = y;
    }

    /**
     * @return the w
     */
    public float getW () {
        return w;
    }

    /**
     * @param w
     *            the w to set
     */
    public void setW ( final float w ) {
        this.w = w;
    }

    /**
     * @return the h
     */
    public float getH () {
        return h;
    }

    /**
     * @param h
     *            the h to set
     */
    public void setH ( final float h ) {
        this.h = h;
    }

    /**
     * @return the colorR
     */
    public float getColorR () {
        return colorR;
    }

    /**
     * @param colorR
     *            the colorR to set
     */
    public void setColorR ( final float colorR ) {
        this.colorR = colorR;
    }

    /**
     * @return the colorG
     */
    public float getColorG () {
        return colorG;
    }

    /**
     * @param colorG
     *            the colorG to set
     */
    public void setColorG ( final float colorG ) {
        this.colorG = colorG;
    }

    /**
     * @return the colorB
     */
    public float getColorB () {
        return colorB;
    }

    /**
     * @param colorB
     *            the colorB to set
     */
    public void setColorB ( final float colorB ) {
        this.colorB = colorB;
    }

    /**
     * @return the colorA
     */
    public float getColorA () {
        return colorA;
    }

    /**
     * @param colorA
     *            the colorA to set
     */
    public void setColorA ( final float colorA ) {
        this.colorA = colorA;
    }

    /**
     * @return the movesX
     */
    public float getMovesX () {
        return movesX;
    }

    /**
     * @param movesX
     *            the movesX to set
     */
    public void setMovesX ( final float movesX ) {
        this.movesX = movesX;
    }

    /**
     * @return the movesY
     */
    public float getMovesY () {
        return movesY;
    }

    /**
     * @param movesY
     *            the movesY to set
     */
    public void setMovesY ( final float movesY ) {
        this.movesY = movesY;
    }

    /**
     * @return the updateable
     */
    public boolean isUpdateable () {
        return updateable;
    }

    /**
     * @param updateable
     *            the updateable to set
     */
    public void setUpdateable ( final boolean updateable ) {
        this.updateable = updateable;
    }

    /**
     * @return the handler NOT SURE, HALP?
     */
    public EventHandler getHandler () {
        return handler;
    }
}
