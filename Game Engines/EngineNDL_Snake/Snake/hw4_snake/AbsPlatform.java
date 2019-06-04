package hw4_snake;

import java.awt.Rectangle;

import processing.core.PApplet;

public abstract class AbsPlatform extends RectGameObjects {

    private static final long serialVersionUID = -20130903375143063L;

    public AbsPlatform ( final PApplet parent ) {
        super( parent );
    }

    public AbsPlatform ( final PApplet parent, final Rectangle rectangle ) {
        super( parent, rectangle );
    }

    public void display () {
        parent.fill( colorR, colorG, colorB, colorA );
        parent.noStroke();
        parent.rect( x, y, w, h );

    }
}
