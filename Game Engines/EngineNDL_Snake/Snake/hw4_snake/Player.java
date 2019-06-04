package hw4_snake;

import java.awt.Rectangle;
import java.util.LinkedList;

import processing.core.PApplet;

public class Player extends AbsPlatform implements Updateable {

    private static final long serialVersionUID = 4529076579195289507L;         // ground regardless of pos
    Rectangle                 intersected      = new Rectangle();              // intersected rect
    Rectangle                 spawn;                                           // this player's spawn point
    char                      dir;                                             // direction of movement
    int                       bodyLength;
    LinkedList<AbsPlatform>   body             = new LinkedList<AbsPlatform>();
    boolean                   dead;

    // constructed
    // init player no show i think
    public Player ( final PApplet parent ) {
        super( parent );
        initMostVars();

        // can't init x/y/rectangle without spawn point

    }

    // init with papplet and spawn point
    public Player ( final PApplet parent, final Rectangle spawn ) {
        super( parent, spawn );
        this.setSpawn( spawn );
        initMostVars();
        final Event respawn = new Event( Type.SPAWN );
        respawn.addToArguments( "Player", this );
        respawn.addToArguments( "Spawn", spawn );
        EventManager.raise( respawn );
    }

    public void initMostVars () {

        // size

        // movement
        this.setUpdateable( true );
        // color
        setColorR( 0 );// default random
        setColorG( 255 );
        setColorB( 0 );

        EventManager.register( getHandler(), Type.COLLISION );
        EventManager.register( getHandler(), Type.DEATH );
        EventManager.register( getHandler(), Type.SPAWN );
        EventManager.register( getHandler(), Type.INPUT );
        // MAY NEED PAUSE, START, STOP, HAVENT GOT THERE

    }

    public void initSpawnVars () {
        this.setRectangle( this.spawn );
        // snake deets
        this.setMovesX( 0 );
        this.setMovesY( -5 );
        this.setW( 20 );
        this.setH( 20 );
        // this.setDir( 'u' );
        setBodyLength( 3 );
        System.out.println( "initSpawnVars" );
        for ( int loop = 1; loop <= 3; loop++ ) {
            body.add( new Platform( parent, new Rectangle( (int) getX(), (int) ( getY() + ( 25 * loop ) ), (int) getW(), (int) getH() ), 'b' ) );
            body.get( loop - 1 ).setMovesX( getMovesX() );
            body.get( loop - 1 ).setMovesY( getMovesY() );

            body.get( loop - 1 ).display();

            System.out.println( "in body loop" );
        }
        // dead = false;
        this.display();
    }

    // public void publicInitSpawnVars () {
    // initSpawnVars();
    // }

    private void loadPlayerScript () {
        ScriptManager.loadScript( "snake/hw4_snake/player_snake.js" );
    }

    // @Override
    @Override
    public void update () {

        loadPlayerScript();
        if ( movesX != 0 || movesY != 0 ) {
            updateBody();
        }
        ScriptManager.executeScript( "updateMoveSpeed", this );
        this.rectangle.setRect( x, y, w, h );

        this.display();
        if ( ( ( ( getX() + getW() ) > parent.width ) || ( getX() < 0 ) ) || ( ( getY() < 0 ) || ( getY() + getH() > parent.height ) ) ) {// intersects edges
            die();
        }
        // for ( int bod = 1; bod < body.size(); bod++ ) {// final AbsPlatform body :
        // getBody() ) {//intersecting body
        // if ( getRectangle().intersects( body.get( bod ).getRectangle() ) ) {
        // final Event die = new Event( Type.DEATH );
        // die.addToArguments( "Player", this );
        // die.addToArguments( "Spawn", getSpawn() );
        // EventManager.raise( die );
        // }
        // }

    }

    public void updateBody () {
        for ( int bod = body.size() - 1; bod >= 0; bod-- ) {
            if ( bod == 0 ) {
                if ( body.get( bod ).getMovesY() != 0 && ( body.get( bod ).getY() == getY() ) ) {
                    body.get( bod ).setMovesX( getMovesX() );
                    body.get( bod ).setMovesY( 0 );
                }
                else if ( body.get( bod ).getMovesX() != 0 && ( body.get( bod ).getX() == getX() ) ) {
                    body.get( bod ).setMovesX( 0 );
                    body.get( bod ).setMovesY( getMovesY() );
                }
                // body.get( bod ).setMovesX( getMovesX() );
                // body.get( bod ).setMovesY( getMovesY() );
            }
            else {
                if ( body.get( bod ).getMovesY() != 0 && ( body.get( bod ).getY() == getY() ) ) {
                    body.get( bod ).setMovesX( body.get( bod - 1 ).getMovesX() );
                    body.get( bod ).setMovesY( 0 );
                }
                else if ( body.get( bod ).getMovesX() != 0 && ( body.get( bod ).getX() == getX() ) ) {
                    body.get( bod ).setMovesX( 0 );
                    body.get( bod ).setMovesY( body.get( bod - 1 ).getMovesY() );
                }
                // body.get( bod ).setMovesX( body.get( bod - 1 ).getMovesX() );
                // body.get( bod ).setMovesY( body.get( bod - 1 ).getMovesY() );
            }
            if ( body.get( bod ).getMovesX() != 0 ) {
                body.get( bod ).setX( body.get( bod ).getX() + body.get( bod ).getMovesX() );
            }
            else {
                body.get( bod ).setY( body.get( bod ).getY() + body.get( bod ).getMovesY() );
            }
            body.get( bod ).display();

        }

    }

    public void up () {
        loadPlayerScript();
        ScriptManager.executeScript( "up", this );
    }

    public void down () {
        loadPlayerScript();
        ScriptManager.executeScript( "down", this );
    }

    public void left () {
        loadPlayerScript();
        ScriptManager.executeScript( "left", this );
    }

    public void right () {
        loadPlayerScript();
        ScriptManager.executeScript( "right", this );
    }

    public void space () {// respawn trigger
        spawn();

    }

    // changed from HW4 to having just an event/script call, good for different
    // games
    public void die () {
        body.clear();
        setRectangle( getBlankRectangle() );
        display();
        // loadPlayerScript();
        // ScriptManager.executeScript( "die", this, this.getBlankRectangle() );
    }

    public void spawn () {
        initSpawnVars();
    }

    // public void eatPellet ( final AbsPlatform pellet ) {
    // loadPlayerScript();
    // ScriptManager.executeScript( "eat", pellet, this );
    //
    // }

    public void checkIntersect ( final AbsPlatform pellet ) {
        loadPlayerScript();
        final Platform plat = new Platform( getParent(), getRectangle(), 'b' );
        ScriptManager.executeScript( "collision", this, pellet, plat );

    }

    /**
     * @return the spawn
     */
    public Rectangle getSpawn () {
        return spawn;
    }

    /**
     * @param spawn
     *            the spawn to set
     */
    public void setSpawn ( final Rectangle spawn ) {
        this.spawn = spawn;
    }

    /**
     * @return the intersected
     */
    public Rectangle getIntersected () {
        return intersected;
    }

    /**
     * @param intersected
     *            the intersected to set
     */
    public void setIntersected ( final Rectangle intersected ) {
        this.intersected = intersected;
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

    /**
     * @return the bodyLength
     */
    public int getBodyLength () {
        return bodyLength;
    }

    /**
     * @param bodyLength
     *            the bodyLength to set
     */
    public void setBodyLength ( final int bodyLength ) {
        this.bodyLength = bodyLength;
    }

    /**
     * @return the body
     */
    public LinkedList<AbsPlatform> getBody () {
        return body;
    }

    /**
     * @param body
     *            the body to set
     */
    public void setBody ( final LinkedList<AbsPlatform> body ) {
        this.body = body;
    }

    /**
     * @return the dead
     */
    public boolean isDead () {
        return dead;
    }

    /**
     * @param dead
     *            the dead to set
     */
    public void setDead ( final boolean dead ) {
        this.dead = dead;
    }
}
