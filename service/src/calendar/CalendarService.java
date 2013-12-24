package calendar;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.12.13
 * Time: 9:50
 * To change this template use File | Settings | File Templates.
 */
public interface CalendarService extends Remote{
    //adding event to collection with all events and to collection with events to each participant
    void addEvent(Event event) throws /*CalendarException,*/ RemoteException;

    //checking if the person free in particular time
    boolean isFreeAtTime(String email, Calendar date) throws RemoteException;

    //searching for the nearest convenient start time for event starting from the date startDate
    Calendar convenientStartTime(List<String> attendees, Calendar startDate) throws RemoteException;

    /*
        searching for the maximum end time for event, which start time was found using previous method.
        parameter "durationInHours" points expected duration of the event. If any of attendees busy on expected end time method
        returns maximum possible end time of event.
        */
    Calendar convenientEndTime(List<String> attendees, Calendar startDate, int durationInHours) throws RemoteException;

    //creation of event
    Event createEvent(String description, Calendar dateFrom, Calendar dateTo, List<String> emails) throws RemoteException;

    //creation of Event with all-day duration
    Event createEvent(String description, Calendar date, List<String> emails) throws RemoteException;

    //printing the details of event
    void printEvent(Event event) throws RemoteException;

    //printing events from map with all events
    void printAllEvents() throws RemoteException;

    List<Event> getEventsByDate(Calendar date) throws RemoteException;


}
