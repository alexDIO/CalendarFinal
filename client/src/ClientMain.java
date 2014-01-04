import calendar.calendarService.CalendarException;
import calendar.calendarService.CalendarService;
import calendar.event.Event;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Map;

public class ClientMain {


    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");
        CalendarService service = (CalendarService) context.getBean("calendarService");

        Event event1 =  service.createEvent("testEvent1",new GregorianCalendar(2013,12,19), Arrays.asList("Alex","John"));

        try{
            service.addEvent(event1);
        } catch (CalendarException ce){
            System.out.println(ce.getMessage());
        }

        Map<String,Event> calendar = service.getCalendar();

        for (Map.Entry<String, Event> pair : calendar.entrySet()) {
            System.out.println(pair.getValue().getDescription());
        }

        //service.removeEvent("testEvent1");

    }
}
