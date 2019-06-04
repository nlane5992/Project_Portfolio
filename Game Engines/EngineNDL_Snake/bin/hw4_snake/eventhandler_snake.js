/*
 * Very similar to initial eventhandler, but easily tested
 * and changed as needed, i.e. different needed event types
 */
function handle(typeNum, arguments, player, otherAbs){
    if ( typeNum == 0 ) //collision
        player.checkIntersect( otherAbs );    
    else if ( typeNum == 1 ) //death
        player.die();    
    else if ( typeNum == 2 ) //spawn
        player.spawn();    
    else if ( typeNum == 3 ) {//input
        pressedKey = arguments.get( "Key" );
        if ( pressedKey == "s" ) {
        	player.space();
        }
        if ( pressedKey == "u" ) {
        	player.up();
        }
        if ( pressedKey == "d" ) {
        	player.down();
        }
        if ( pressedKey == "l" ) {
        	player.left();
        }
        if ( pressedKey == "r" ) {
        	player.right();
        }
    }
}