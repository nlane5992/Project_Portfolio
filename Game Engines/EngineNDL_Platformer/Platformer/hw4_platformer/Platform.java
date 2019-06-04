package hw4_platformer;

import java.awt.Rectangle;

import processing.core.PApplet;

public class Platform extends AbsPlatform {

    private static final long serialVersionUID = 357197074942487942L;

    public Platform ( final PApplet parent ) {
        super( parent );
    }

    public Platform ( final PApplet parent, final Rectangle rectangle ) {
        super( parent, rectangle );
        // color
        this.setColorR( 0 );// default random
        this.setColorG( 200 );
        this.setColorB( 200 );
        this.display();
    }

}
