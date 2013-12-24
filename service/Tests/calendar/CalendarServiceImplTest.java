package calendar;

import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 21.12.13
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
public class CalendarServiceImplTest {
    @Test
    public void testAddEvent1() throws Exception {
        String[] emails1 = {"first@mail.com","second@mail.com"};
        String[] emails2 = {"third@mail.com","fourth@mail.com"};

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2013,00,01))
                .attenders(Arrays.asList(emails1))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .dateFrom(new GregorianCalendar(2013,10,25))
                .dateTo(new GregorianCalendar(2013, 10, 26))
                .attenders(Arrays.asList(emails2))
                .build();

        Map<String,Event> expectedMap1 = new HashMap<String,Event>();
        expectedMap1.put("New Year", firstEvent);
        expectedMap1.put("Helloween", secondEvent);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Map<String,Event> testMap1 = service.getCalendar();

        assertEquals(expectedMap1, testMap1);
    }

    @Test
    public void testAddEvent2() throws Exception {
        String[] emails1 = {"first@mail.com","second@mail.com"};
        String[] emails2 = {"fourth@mail.com","third@mail.com"};

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2013,00,01))
                .attenders(Arrays.asList(emails1))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .dateFrom(new GregorianCalendar(2013,10,25))
                .dateTo(new GregorianCalendar(2013, 10, 26))
                .attenders(Arrays.asList(emails2))
                .build();

        Map<String,LinkedList<Event>> expectedMap2 = new HashMap<String, LinkedList<Event>>();

        LinkedList<Event> firstAttendeesEvents = new LinkedList<Event>();
        firstAttendeesEvents.add(firstEvent);

        LinkedList<Event> secondAttendeesEvents = new LinkedList<Event>();
        secondAttendeesEvents.add(firstEvent);

        LinkedList<Event> thirdAttendeesEvents = new LinkedList<Event>();
        thirdAttendeesEvents.add(secondEvent);

        LinkedList<Event> fourthAttendeesEvents = new LinkedList<Event>();
        fourthAttendeesEvents.add(secondEvent);

        expectedMap2.put("first@mail.com",firstAttendeesEvents);
        expectedMap2.put("second@mail.com",secondAttendeesEvents);
        expectedMap2.put("third@mail.com",thirdAttendeesEvents);
        expectedMap2.put("fourth@mail.com",fourthAttendeesEvents);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Map<String,LinkedList<Event>> testMap2 = service.getEventsSchedule();

        assertEquals(expectedMap2,testMap2);
    }

    @Test
    public void testIsFreeAtTime() throws Exception {

    }

    @Test
    public void testConvenientStartTime() throws Exception {

    }

    @Test
    public void testConvenientEndTime() throws Exception {

    }

    @Test
    public void testCreateEvent1() throws Exception {

    }

    @Test
    public void testCreateEvent2() throws Exception {

    }

    @Test
    public void testPrintEvent1() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        String[] emails = {"fitst@mail.com","second@mail.com"};
        Event testEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,05,05))
                .dateTo(new GregorianCalendar(2013,05,06))
                .attenders(Arrays.asList(emails))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintEvent2() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        String[] emails = {"fitst@mail.com","second@mail.com"};
        Event testEvent = new Event.Builder()
                .dateFrom(new GregorianCalendar(2013,05,05))
                .dateTo(new GregorianCalendar(2013, 05, 06))
                .attenders(Arrays.asList(emails))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintEvent3() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        String[] emails = {"fitst@mail.com","second@mail.com"};
        Event testEvent = new Event.Builder()
                .description("New Year")
                .attenders(Arrays.asList(emails))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintEvent4() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        Event testEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013, 05, 05))
                .dateTo(new GregorianCalendar(2013, 05, 06))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintAllEvents1() throws Exception {
        String[] emails1 = {"first@mail.com","second@mail.com"};
        String[] emails2 = {"third@mail.com","fourth@mail.com"};

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .attenders(Arrays.asList(emails1))
                .build();
        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .attenders(Arrays.asList(emails2))
                .build();

        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        testCalendar.addEvent(firstEvent);
        testCalendar.addEvent(secondEvent);

        testCalendar.printAllEvents();
        //fail();
    }

    @Test
    public void testPrintAllEvents2() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();

        testCalendar.printAllEvents();
        //fail();
    }


    @Test
    public void testGetEventsByDate() throws Exception {

    }
}
