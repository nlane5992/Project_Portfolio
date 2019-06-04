package hw4_snake;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    public int compare ( final Event o1, final Event o2 ) {
        if ( o1.getInitTime() < o2.getInitTime() ) {
            return -1;
        }
        if ( o1.getInitTime() > o2.getInitTime() ) {
            return 1;
        }
        return 0;
    }
}
