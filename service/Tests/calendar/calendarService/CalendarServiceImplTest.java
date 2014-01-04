package calendar.calendarService;

import calendar.event.Event;
import calendar.event.SortEventsByDatesAndDesc;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;


public class CalendarServiceImplTest {
    @Test
    public void testAddEvent1() throws Exception {

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2014,00,01))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .dateFrom(new GregorianCalendar(2013,10,25))
                .dateTo(new GregorianCalendar(2013, 10, 26))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
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

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2014,00,01))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .dateFrom(new GregorianCalendar(2013,10,25))
                .dateTo(new GregorianCalendar(2013, 10, 26))
                .attendees(Arrays.asList("fourth@mail.com", "third@mail.com"))
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
    public void testRemoveEvent() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2014,00,01))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .dateFrom(new GregorianCalendar(2013,10,25))
                .dateTo(new GregorianCalendar(2013, 10, 26))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Map<String,Event> expectedMap = new HashMap<String,Event>();
        expectedMap.put("Helloween", secondEvent);

        CalendarServiceImpl service = new CalendarServiceImpl();
        service.addEvent(firstEvent);
        service.addEvent(secondEvent);
        service.removeEvent("New Year");

        Map<String,Event> testMap = service.getCalendar();

        assertEquals(expectedMap,testMap);
    }

    @Test
    public void testIsFreeAtTime1() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting")
                .dateFrom(new GregorianCalendar(2013,10,05,15,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);

        boolean testValue = service.isFreeAtTime("third@mail.com",new GregorianCalendar(2013,10,05,16,00,00));

        assertEquals(testValue, true);
    }

    @Test
    public void testIsFreeAtTime2() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting")
                .dateFrom(new GregorianCalendar(2013,10,05,15,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);

        boolean testValue = service.isFreeAtTime("first@mail.com",new GregorianCalendar(2013,10,05,16,00,00));

        assertEquals(testValue, false);
    }

    @Test
    public void testIsFreeAtTime3() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting")
                .dateFrom(new GregorianCalendar(2013,10,05,15,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);

        boolean testValue = service.isFreeAtTime("first@mail.com",new GregorianCalendar(2013,10,05,18,00,00));

        assertEquals(testValue, true);
    }

    @Test
    public void testConvenientStartTime1() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,18,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientStartTime(Arrays.asList("first@mail.com","third@mail.com"),
                new GregorianCalendar(2013,10,05,18,00,00));

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientStartTime2() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,17,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientStartTime(Arrays.asList("first@mail.com","third@mail.com"),
                new GregorianCalendar(2013,10,05,16,00,00));

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientStartTime3() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,17,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientStartTime(Arrays.asList("first@mail.com","third@mail.com"),
                new GregorianCalendar(2013,10,05,12,00,00));

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientStartTime4() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,12,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientStartTime(Arrays.asList("fifth@mail.com","sixth@mail.com"),
                new GregorianCalendar(2013,10,05,12,00,00));

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientEndTime1() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,14,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientEndTime(Arrays.asList("fifth@mail.com","sixth@mail.com"),
                new GregorianCalendar(2013,10,05,9,00,00), 5);

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientEndTime2() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,21,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientEndTime(Arrays.asList("first@mail.com","third@mail.com"),
                new GregorianCalendar(2013,10,05,18,00,00), 3);

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientEndTime3() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,11,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientEndTime(Arrays.asList("first@mail.com","third@mail.com"),
                new GregorianCalendar(2013,10,05,9,00,00), 2);

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testConvenientEndTime4() throws Exception {
        Event firstEvent = new Event.Builder()
                .description("Meeting1")
                .dateFrom(new GregorianCalendar(2013,10,05,11,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,15,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Meeting2")
                .dateFrom(new GregorianCalendar(2013,10,05,14,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Calendar expectedTime =  new GregorianCalendar(2013,10,05,11,00,00);

        CalendarServiceImpl service = new CalendarServiceImpl();

        service.addEvent(firstEvent);
        service.addEvent(secondEvent);

        Calendar testTime = service.convenientEndTime(Arrays.asList("first@mail.com","third@mail.com"),
                new GregorianCalendar(2013,10,05,9,00,00), 3);

        assertEquals(expectedTime,testTime);
    }

    @Test
    public void testCreateEvent1() throws Exception {
        Event expectedEvent = new Event.Builder()
                .description("Meeting")
                .dateFrom(new GregorianCalendar(2013,10,05,15,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,17,00,00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        CalendarServiceImpl service = new CalendarServiceImpl();

        Event testEvent = service.createEvent("Meeting",new GregorianCalendar(2013,10,05,15,00,00),
                new GregorianCalendar(2013,10,05,17,00,00),Arrays.asList("first@mail.com","second@mail.com"));

        assertEquals(expectedEvent,testEvent);
    }

    @Test
    public void testCreateEvent2() throws Exception {
        Event expectedEvent = new Event.Builder()
                .description("Meeting")
                .dateFrom(new GregorianCalendar(2013,10,05,00,00,00))
                .dateTo(new GregorianCalendar(2013,10,05,23,59,59))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        CalendarServiceImpl service = new CalendarServiceImpl();

        Event testEvent = service.createEvent("Meeting",new GregorianCalendar(2013,10,05),
                Arrays.asList("first@mail.com","second@mail.com"));

        assertEquals(expectedEvent,testEvent);

    }

    @Test
    public void testPrintEvent1() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        Event testEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2014,00,01))
                .attendees(Arrays.asList("fitst@mail.com", "second@mail.com"))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintEvent2() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        Event testEvent = new Event.Builder()
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2014,00,01))
                .attendees(Arrays.asList("fitst@mail.com", "second@mail.com"))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintEvent3() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        Event testEvent = new Event.Builder()
                .description("New Year")
                .attendees(Arrays.asList("fitst@mail.com", "second@mail.com"))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintEvent4() throws Exception {
        CalendarServiceImpl testCalendar = new CalendarServiceImpl();
        Event testEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013,11,31))
                .dateTo(new GregorianCalendar(2014,00,01))
                .build();
        testCalendar.printEvent(testEvent);
        //fail();
    }

    @Test
    public void testPrintAllEvents1() throws Exception {

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();
        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
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

        Event firstEvent = new Event.Builder()
                .description("New Year")
                .dateFrom(new GregorianCalendar(2013, 11, 31))
                .dateTo(new GregorianCalendar(2014, 00, 01))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        Event secondEvent = new Event.Builder()
                .description("Helloween")
                .dateFrom(new GregorianCalendar(2013, 11, 10))
                .dateTo(new GregorianCalendar(2013, 11, 11))
                .attendees(Arrays.asList("third@mail.com", "fourth@mail.com"))
                .build();

        Event thirdEvent = new Event.Builder()
                .description("Business meeting")
                .dateFrom(new GregorianCalendar(2013, 11, 10, 14, 00, 00))
                .dateTo(new GregorianCalendar(2013, 11, 10, 16, 00, 00))
                .attendees(Arrays.asList("first@mail.com", "second@mail.com"))
                .build();

        CalendarServiceImpl service = new CalendarServiceImpl();
        service.addEvent(firstEvent);
        service.addEvent(secondEvent);
        service.addEvent(thirdEvent);

        List<Event> expectedList = Arrays.asList(secondEvent,thirdEvent);

        List<Event> testList = service.getEventsByDate(new GregorianCalendar(2013,11,10,15,00,00));

        Collections.sort(expectedList, new SortEventsByDatesAndDesc());
        Collections.sort(testList,new SortEventsByDatesAndDesc());

        assertEquals(expectedList,testList);

    }
}
