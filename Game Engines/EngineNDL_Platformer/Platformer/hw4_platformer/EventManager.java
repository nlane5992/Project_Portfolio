package hw4_platformer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 * Used to be named EventRegRaise until 11/14 with additional class/note help
 * *TODO something with registerAll, was explicitely talked about in class not
 * sure here References: Notes 10/18/18
 * https://codereview.stackexchange.com/questions/44235/event-handling-system-in
 * https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html
 * https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html -java ADD
 */
public class EventManager implements Serializable {
    private static final long                              serialVersionUID = -2659124522284921737L;
    private final static Type                              types[]          = Type.values();        // enum
                                                                                                    // types
                                                                                                    // for
                                                                                                    // ease
                                                                                                    // of
                                                                                                    // access
    private static EventManager                            INSTANCE;                                // singleton,
                                                                                                    // pulled
                                                                                                    // from
                                                                                                    // link
    private static PriorityQueue<Event>                    pQueue;
    private static EventComparator                         eventComparator  = new EventComparator();
    private static HashMap<Type, LinkedList<EventHandler>> registrations;

    private EventManager () {
    }

    public static EventManager getInstance () {
        if ( INSTANCE == null ) {
            pQueue = new PriorityQueue<Event>( types.length, eventComparator );
            registrations = new HashMap<Type, LinkedList<EventHandler>>( types.length );
            INSTANCE = new EventManager();
            for ( final Type type : types ) {
                registrations.put( type, new LinkedList<EventHandler>() );
            }
        }
        return INSTANCE;
    }// how to get the singleton instance, pulled from link

    public synchronized static void register ( final EventHandler handler, final Type type ) {
        registrations.get( type ).add( handler );
    }

    public synchronized static void registerAll ( final EventHandler handler ) {
        for ( final Type type : types ) {
            registrations.get( type ).add( handler );
        }
    }

    public static void remove ( final EventHandler handler ) {
        for ( final Type type : types ) {
            registrations.get( type ).remove( handler );
        }
    }

    public static void raise ( final Event event ) {
        pQueue.add( event );
    } // raises the event to queue to be exectued

    public synchronized static void alertOfNextEvent () { // do event
                                                          // (manage/dispatch
                                                          // events)

        if ( !pQueue.isEmpty() ) {
            final Event event = pQueue.remove();
            for ( final EventHandler handler : registrations.get( event.getType() ) ) {
                handler.onEvent( event );
            }
        }
    }
}
