package calendar.event;

import java.util.Calendar;
import java.util.Comparator;

public class SortEventsByDatesAndDesc implements Comparator<Event> {
    public int compare(Event event1, Event event2) {

        String desc1 = event1.getDescription();
        String desc2 = event2.getDescription();

        Calendar dateFrom1 = event1.getDateFrom();
        Calendar dateFrom2 = event2.getDateFrom();

        Calendar dateTo1 = event1.getDateTo();
        Calendar dateTo2 = event2.getDateTo();

        if (dateFrom1.compareTo(dateFrom2)!= 0)
            return dateFrom1.compareTo(dateFrom2);
        else if (dateTo1.compareTo(dateTo2)!= 0)
            return dateTo1.compareTo(dateTo2);
        else return desc1.compareTo(desc2);

    }
}
