package hw4_snake;

import java.awt.Rectangle;

import processing.core.PApplet;

public class Platform extends AbsPlatform {

    private static final long serialVersionUID = 357197074942487942L;

    public Platform ( final PApplet parent ) {
        super( parent );
    }

    public Platform ( final PApplet parent, final Rectangle rectangle ) {
        super( parent, rectangle );
        this.display();
    }

    public Platform ( final PApplet parent, final Rectangle rectangle, final char col ) {
        super( parent, rectangle );
        // color
        if ( col == 'b' ) {
            this.setColorR( 0 );// default random
            this.setColorG( 0 );
            this.setColorB( 255 );
        }
        this.display();
    }

}
