package calendar.calendarService;

import calendar.event.Event;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public interface CalendarService {
    //getter for map with all events
    Map<String, Event> getCalendar();

    //getter for map with all emails
    Map<String, LinkedList<Event>> getEventsSchedule();

    //adding event to collection with all events and to collection with events to each participant
    void addEvent(Event event) throws CalendarException;

    void removeEvent(String description);


    //checking if the person free in particular time
    boolean isFreeAtTime(String email, Calendar date);

    //searching for the nearest convenient start time for event starting from the date startDate
    Calendar convenientStartTime(List<String> attendees, Calendar startDate);

    /*
        searching for the maximum end time for event, which start time was found using previous method.
        parameter "durationInHours" points expected duration of the event. If any of attendees busy on expected end time method
        returns maximum possible end time of event.
        */
    Calendar convenientEndTime(List<String> attendees, Calendar startDate, int durationInHours);

    //creation of event
    Event createEvent(String description, Calendar dateFrom, Calendar dateTo, List<String> emails) throws CalendarException;

    //creation of Event with all-day duration
    Event createEvent(String description, Calendar date, List<String> emails);

    //printing the details of event
    String printEvent(Event event);

    //printing events from map with all events
    void printAllEvents();

    List<Event> getEventsByDate(Calendar date);

    void writeEvent(Event event);
}
