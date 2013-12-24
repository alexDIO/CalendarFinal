package calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.*;


public class CalendarServiceImpl implements CalendarService {
    private Map<String, Event> calendar = new HashMap<String, Event>();
    private Map<String, LinkedList<Event>> eventsSchedule = new HashMap<String, LinkedList<Event>>();

    //getter for map with all events
    public Map<String, Event> getCalendar() {
        return calendar;
    }

    //getter for map with all emails
    public Map<String, LinkedList<Event>> getEventsSchedule() {
        return eventsSchedule;
    }


    //adding event to collection with all events and to collection with events to each participant
    @Override
    public void addEvent(Event event) /*throws CalendarException*/{

        List<String> eventsAttendees = event.getAttendees();
        for (String email : eventsAttendees){
            if (!eventsSchedule.containsKey(email)){
                LinkedList<Event> events = new LinkedList<Event>();
                events.add(event);
                eventsSchedule.put(email,events);
                calendar.put(event.getDescription(), event);
            } else{
                LinkedList<Event> eventsOfAttendee = eventsSchedule.get(email);
                for (Event eventOfAttendee : eventsOfAttendee){
                    if ((event.getDateFrom().after(eventOfAttendee.getDateFrom()) && event.getDateFrom().before(eventOfAttendee.getDateTo())) ||
                            (event.getDateTo().after(eventOfAttendee.getDateFrom()) && event.getDateTo().before(eventOfAttendee.getDateTo())) ||
                            (event.getDateFrom().before(eventOfAttendee.getDateFrom()) && event.getDateTo().after(eventOfAttendee.getDateTo())))
                    {} //throw new CalendarException("Attendee " + email + " is busy and can't take part in event " + event.getDescription());
                }

                eventsSchedule.get(email).add(event);
                calendar.put(event.getDescription(), event);
            }
        }

    }

    //checking if the person free in particular time
    @Override
    public boolean isFreeAtTime(String email, Calendar date){
        if (eventsSchedule.containsKey(email)){
            LinkedList<Event> events = eventsSchedule.get(email);
            for (Event event : events){
                if (date.after(event.getDateFrom()) && date.before(event.getDateTo()))
                    return false;
            }
        }
        return true;
    }

    //searching for the nearest convenient start time for event starting from the date startDate
    @Override
    public Calendar convenientStartTime(List<String> attendees, Calendar startDate){
        for (int i=0; i<attendees.size(); i++){
            if (eventsSchedule.containsKey(attendees.get(i))){
                LinkedList<Event> events = eventsSchedule.get(attendees.get(i));
                for (Event event : events){
                    if (startDate.after(event.getDateFrom()) && startDate.before(event.getDateTo())){
                        startDate = event.getDateTo();
                        i = 0;
                        break;
                    }
                }
            }
        }
        return startDate;
    }
    /*
    searching for the maximum end time for event, which start time was found using previous method.
    parameter "durationInHours" points expected duration of the event. If any of attendees busy on expected end time method
    returns maximum possible end time of event.
    */
    @Override
    public Calendar convenientEndTime(List<String> attendees, Calendar startDate, int durationInHours){
        Calendar endDate = new GregorianCalendar();
        endDate.setTime(startDate.getTime());

        endDate.add(Calendar.HOUR,durationInHours);
        for (int i=0; i<attendees.size(); i++){
            if (eventsSchedule.containsKey(attendees.get(i))){
                LinkedList<Event> events= eventsSchedule.get(attendees.get(i));
                for (Event event : events){
                    if (startDate.before(event.getDateFrom()) && endDate.after(event.getDateFrom())){
                        endDate = event.getDateFrom();
                        i = 0;
                        break;
                    }
                }
            }
        }
        return endDate;
    }

    //creation of event
    @Override
    public Event createEvent(String description, Calendar dateFrom, Calendar dateTo, List<String> emails){
        Event event =  new Event.Builder()
                .description(description)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .attenders(emails)
                .build();

        writeEvent(event);
        return event;
    }
    //creation of Event with all-day duration
    @Override
    public Event createEvent(String description, Calendar date, List<String> emails){
        Calendar dateFrom = new GregorianCalendar(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DATE),00,00,00);
        Calendar dateTo = new GregorianCalendar(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DATE),23,59,59);

        Event event =  new Event.Builder()
                .description(description)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .attenders(emails)
                .build();

        writeEvent(event);
        return event;
    }
    //printing the details of event
    @Override
    public void printEvent(Event event){
        if (event.getDescription() != null)
            System.out.println("Event's description - " + event.getDescription());
        if (event.getDateFrom() != null && event.getDateTo() != null)
            System.out.println("Event's duration - from " + event.getDateFrom().getTime() + " to " + event.getDateTo().getTime());
        if (event.getAttendees() != null){
            System.out.println("Event's attendees: ");
            for (String email : event.getAttendees()){
                System.out.println(email);
            }
        }
    }
    //printing events from map with all events
    @Override
    public void printAllEvents(){
        if (calendar.size()==0)
            System.out.println("There is no events in calendar.");
        else {
            Iterator<Map.Entry<String,Event>> iterator = calendar.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,Event> pair = iterator.next();
                printEvent(pair.getValue());
            }
        }
    }

    @Override
    public List<Event> getEventsByDate(Calendar date){
        List<Event> list = new LinkedList<Event>();
        Iterator<Map.Entry<String,Event>> iterator = calendar.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Event> pair = iterator.next();
            if (date.after(pair.getValue().getDateFrom()) && date.before(pair.getValue().getDateTo()))
                list.add(pair.getValue());
        }
        return list;
    }


    public void writeEvent(Event event){
        EventJAXB eventJAXB = new EventJAXB(event);

        try {
            File file = new File ("D:\\file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(EventJAXB.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //output printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(eventJAXB, file);
            jaxbMarshaller.marshal(eventJAXB, System.out);
        } catch (JAXBException e){
            e.printStackTrace();
        }
    }

}
