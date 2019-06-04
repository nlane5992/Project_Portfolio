/*
 * Used to be seperated between "respawn" "update" "move" and "collision
 * but decided to just make a player.js to accompany the player.java with 
 * all of the associated features. This is made purposely for replication in 
 * future games to easily test this as I am going and change. Purposely left 
 * instantiation stuff in the java file only, to make distinction between projects
 */
function update(player){
	player.setX((player.getX()+player.getSpeedX()));
	player.setY((player.getY()+player.getSpeedY()));
	player.setB((player.getY()+player.getH()));
	if(player.isOnGround()){
	    if (player.getSpeedX() > 0 ) {
	    	player.setSpeedX((player.getSpeedX() + player.getSlow()));
	        if ( player.getSpeedX() < 0 ) 
	        	player.setSpeedX(0);        
	    }
	    else if (player.getSpeedX() < 0 ) {
	    	player.setSpeedX((player.getSpeedX() - player.getSlow()));
	        if ( player.getSpeedX() > 0 ) 
	        	player.setSpeedX(0);	        
	    }
	}
	else player.fall();
}
function left (player) {
    if ( !(player.isIntersectLeft()) ) {
        if ( player.getX() > 0 ) {
        	player.setSpeedX(-(player.getMovesX())); // decrease that many x coords
        }
        else {// if passes left side can't go left. bastardized my
              // movePlayer
        	player.setX(0);
        	player.setSpeedX( player.getX());
        }
        player.setIntersectRight(false);
    }

}

function right (player) {
    if ( (player.getX() + player.getW()) < player.parent.width ) 
    	player.setSpeedX(player.getMovesX());
    
    else {// if passes right side can't go right. bastardized my movePlayer
    	player.setX((player.parent.width-player.getW()));
    	player.setSpeedX(0);
    }
    player.setIntersectLeft(false);
}

function jump (player) {
    if ( player.isOnGround() ) {
    	player.setIntersectLeft(false);
    	player.setOnGround(false);
        player.setIntersectBot(false);
        player.setY(player.getY()+player.getMovesY());
        player.setSpeedY(player.getMovesY());
    }
}
function fall(player){
	ground = player.getGround();
	if ( (player.getB() < ground.getY()) && ( (player.getX() < ground.getWidth()) || (player.getX() < ground.getWidth()) ) ) {
        player.setSpeedY(player.getGrav()+player.getSpeedY());
        player.setY(player.getY()+player.getSpeedY());
    }
    else if ( player.getB() > player.parent.height ) {
    	player.setGround(player.getBase());
    	player.setSpeedY(0);
    	player.setY(player.parent.height - player.getH());
    	player.setOnGround(true);
    }
    else {
    	player.setSpeedY(0);
    	player.setGround(player.getBase());
    	player.setOnGround(true);

    }
}
function die(player ){
	player.setRectangle( player.getEmptyTri());
    player.display();
}
function spawn(player){
	player.publicInitSpawnVars();
}
function collision(ap, play ){

	r = ap.rectangle;
    if ( !play.rectangle.intersects( play.intersected ) ) {
    	play.ground = play.base;
        if ( play.rectangle.intersects( play.base ) ) {
        	play.onGround = true;
        }
    }

    if ( play.rectangle.intersects( r ) ) { // only care left bot and now right
    	play.intersected = r;
        if ( ( play.b <= r.getY() + play.buff && play.b >= r.getY() - play.buff ) && ( play.x < r.getX() + r.getWidth() && play.x > r.getX() ) ) {// base
            // ==
            // rectangle's
            // y
        	play.ground = r;
        	play.onGround = true;
            if ( play.speedY != 0 ) {
            	play.speedY = 0;
            }
            if ( ap.getMovesX() > 0 ) {
            	play.x += ap.getMovesX();
            }
            if ( ap.getMovesY() != 0 ) {
            	play.y += ap.getMovesY();
            }

            play.update();
        }
         
        if ( ( play.x <= r.getX() + r.getWidth() + play.buff && play.x >= r.getX() + r.getWidth() - play.buff ) ) {
        	play.intersectLeft = true;
        	play.x = ( r.getX() + r.getWidth() );
            if ( play.speedX < 0 ) {
            	play.speedX = 0;

            }

        }
        if ( ( ( play.x + play.w ) >= r.getX() - play.buff && play.x + play.w <= r.getX() + play.buff ) ) {
        	play.intersectRight = true;
        	play.x =  ( r.getX() - r.getWidth() );
            if ( play.speedX > 0 ) {
            	play.speedX = 0;

            }

        }
    }
}