/*
 * Used to be seperated between "respawn" "update" "move" and "collision
 * but decided to just make a player.js to accompany the player.java with 
 * all of the associated features. This is made purposely for replication in 
 * future games to easily test this as I am going and change. Purposely left 
 * instantiation stuff in the java file only, to make distinction between projects
 */
//move set
function updateMoveSpeed(player){
	if(player.getMovesX() != 0)
		player.setX(player.getX()+player.getMovesX());
	else
		player.setY(player.getY()+player.getMovesY());	

	
}
function up (player) {
    if ( player.getMovesY() == 0 ) {
    	player.setMovesX(0);
    	player.setMovesY(-5);
    }
}
function down (player) {
	if ( player.getMovesY() == 0 ) {
    	player.setMovesX(0);
    	player.setMovesY(5);
    }
}
function left (player) {
	if ( player.getMovesX() == 0 ) {
    	player.setMovesY(0);
    	player.setMovesX(-5);
    }
}
function right (player) {
	if ( player.getMovesX() == 0 ) {
    	player.setMovesY(0);
    	player.setMovesX(5);
    }
}
function die(player ,blank){

	
}
function spawn(player){
	//player.initSpawnVars();
}
function collision(play,pellet, plat ){
	r = pellet.rectangle;
//    if ( play.rectangle.intersects( r ) ) { // only care bout pellet
//    	play.setBodyLength(play.getBodyLength()+1);
//    	play.body.addLast(plat));
//    	play.setRectangle(r);
//    }
}