package hw4_platformer;

import java.awt.Rectangle;

import processing.core.PApplet;

public class Player extends AbsPlatform implements Updateable {

    private static final long serialVersionUID = 4529076579195289507L;
    float                     b;                                                                     // bottom of player
    float                     speedX;                                                                // speed of player in x
    float                     speedY;                                                                // speed of player in y
    float                     grav;                                                                  // how fast a player falls
    float                     buff;                                                                  // buffer for intersections
    boolean                   onGround;                                                              // if bottom is on ground or an obj
    boolean                   intersectLeft;                                                         // if intersect on left side
    // boolean intersectTop; // if intersect on Top side
    boolean                   intersectRight;                                                        // if intersect on Right side
    boolean                   intersectBot;                                                          // stop falling if true
    Rectangle                 ground           = new Rectangle();                                    // ground regardless of pos
    Rectangle                 intersected      = new Rectangle();                                    // intersected rect
    Rectangle                 base             = new Rectangle( 0, parent.height, parent.width, 0 ); // init floor
    float                     slow;                                                                  // ground slower for xmov
    Rectangle                 spawn;                                                                 // this player's spawn point
    public final Rectangle    emptyTri         = new Rectangle( 0, 0, 0, 0 );

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
        respawn.addToArguments( "Spawn", getSpawn() );
        EventManager.raise( respawn );
    }

    private void initMostVars () {

        // size
        this.setW( 50 );
        this.setH( 75 );
        // movement
        this.setMovesX( 5 );
        this.setMovesY( -20 );
        this.setUpdateable( true );

        // internal vars
        speedX = 0;
        speedY = 0;
        grav = 3;
        buff = 6;

        slow = (float) -.75;
        EventManager.register( getHandler(), Type.COLLISION );
        EventManager.register( getHandler(), Type.DEATH );
        EventManager.register( getHandler(), Type.SPAWN );
        EventManager.register( getHandler(), Type.INPUT );
        // MAY NEED PAUSE, START, STOP, HAVENT GOT THERE

    }

    private void initSpawnVars () {
        this.setRectangle( this.spawn );
        b = y + h; // init y location of bottom
        onGround = true;
        intersectLeft = false;
        // intersectTop = false;
        intersectRight = false;
        intersectBot = true;
        ground = base;
        speedX = 0;
        speedY = 0;
        this.display();
    }

    public void publicInitSpawnVars () {
        initSpawnVars();
    }

    private void loadPlayerScript () {
        ScriptManager.loadScript( "Platformer/hw4_platformer/player_platformer.js" );
    }

    // @Override
    @Override
    public void update () {
        loadPlayerScript();
        ScriptManager.executeScript( "update", this );
        this.rectangle.setRect( x, y, w, h );
        this.display();
    }

    public void left () {
        loadPlayerScript();
        ScriptManager.executeScript( "left", this );
    }

    public void right () {
        loadPlayerScript();
        ScriptManager.executeScript( "right", this );
    }

    public void jump () {
        loadPlayerScript();
        ScriptManager.executeScript( "jump", this );
    }

    public void fall () {
        loadPlayerScript();
        ScriptManager.executeScript( "fall", this );
    }

    // changed from HW4 to having just an event/script call, good for different
    // games
    public void die () {
        loadPlayerScript();
        ScriptManager.executeScript( "die", this );
        final Event die = new Event( Type.SPAWN );
        die.addToArguments( "Player", this );
        die.addToArguments( "Spawn", getSpawn() );
        EventManager.raise( die );
    }

    public void spawn () {
        loadPlayerScript();
        ScriptManager.executeScript( "spawn", this );
    }

    public void checkIntersect ( final AbsPlatform ap ) {
        loadPlayerScript();
        ScriptManager.executeScript( "collision", ap, this );

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
     * @return the speedX
     */
    public float getSpeedX () {
        return speedX;
    }

    /**
     * @param speedX
     *            the speedX to set
     */
    public void setSpeedX ( final float speedX ) {
        this.speedX = speedX;
    }

    /**
     * @return the speedY
     */
    public float getSpeedY () {
        return speedY;
    }

    /**
     * @param speedY
     *            the speedY to set
     */
    public void setSpeedY ( final float speedY ) {
        this.speedY = speedY;
    }

    /**
     * @return the b
     */
    public float getB () {
        return b;
    }

    /**
     * @param b
     *            the b to set
     */
    public void setB ( final float b ) {
        this.b = b;
    }

    /**
     * @return the grav
     */
    public float getGrav () {
        return grav;
    }

    /**
     * @param grav
     *            the grav to set
     */
    public void setGrav ( final float grav ) {
        this.grav = grav;
    }

    /**
     * @return the buff
     */
    public float getBuff () {
        return buff;
    }

    /**
     * @param buff
     *            the buff to set
     */
    public void setBuff ( final float buff ) {
        this.buff = buff;
    }

    /**
     * @return the onGround
     */
    public boolean isOnGround () {
        return onGround;
    }

    /**
     * @param onGround
     *            the onGround to set
     */
    public void setOnGround ( final boolean onGround ) {
        this.onGround = onGround;
    }

    /**
     * @return the intersectLeft
     */
    public boolean isIntersectLeft () {
        return intersectLeft;
    }

    /**
     * @param intersectLeft
     *            the intersectLeft to set
     */
    public void setIntersectLeft ( final boolean intersectLeft ) {
        this.intersectLeft = intersectLeft;
    }

    /**
     * @return the intersectRight
     */
    public boolean isIntersectRight () {
        return intersectRight;
    }

    /**
     * @param intersectRight
     *            the intersectRight to set
     */
    public void setIntersectRight ( final boolean intersectRight ) {
        this.intersectRight = intersectRight;
    }

    /**
     * @return the intersectBot
     */
    public boolean isIntersectBot () {
        return intersectBot;
    }

    /**
     * @param intersectBot
     *            the intersectBot to set
     */
    public void setIntersectBot ( final boolean intersectBot ) {
        this.intersectBot = intersectBot;
    }

    /**
     * @return the ground
     */
    public Rectangle getGround () {
        return ground;
    }

    /**
     * @param ground
     *            the ground to set
     */
    public void setGround ( final Rectangle ground ) {
        this.ground = ground;
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
     * @return the base
     */
    public Rectangle getBase () {
        return base;
    }

    /**
     * @param base
     *            the base to set
     */
    public void setBase ( final Rectangle base ) {
        this.base = base;
    }

    /**
     * @return the slow
     */
    public float getSlow () {
        return slow;
    }

    /**
     * @param slow
     *            the slow to set
     */
    public void setSlow ( final float slow ) {
        this.slow = slow;
    }

    /**
     * @return the emptyTri
     */
    public Rectangle getEmptyTri () {
        return emptyTri;
    }
}
