import calendar.CalendarException;
import calendar.CalendarService;
import calendar.Event;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

public class ClientMain {

    //public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");
        CalendarService service = (CalendarService) context.getBean("calendarService");

        String[] reservedCalendarNames = {"Alex","John"};
        Event event1 =  service.createEvent("testEvent1",new GregorianCalendar(2013,12,19), Arrays.asList(reservedCalendarNames));

        try{
            service.addEvent(event1);
        } catch (CalendarException ce){
            System.out.println(ce.getMessage());
        }

        Map<String,Event> calendar = service.getCalendar();

        Iterator<Map.Entry<String,Event>> iterator = calendar.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Event> pair = iterator.next();
            System.out.println(pair.getValue().getDescription());
        }
    }
}
