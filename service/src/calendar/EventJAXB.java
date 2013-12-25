package calendar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@XmlRootElement(name = "event")
public class EventJAXB implements Serializable {
    String description;
    Calendar dateFrom;
    Calendar dateTo;
    List<String> attendees;

    public EventJAXB(){

    }

    public EventJAXB(Event event){
        this.description=event.getDescription();
        this.dateFrom = event.getDateFrom();
        this.dateTo = event.getDateTo();
        this.attendees = event.getAttendees();
    }



    public String getDescription() {
        return description;
    }

    public Calendar getDateFrom() {
        return dateFrom;
    }

    public Calendar getDateTo() {
        return dateTo;
    }

    public List<String> getAttendees() {
        return attendees;
    }
    @XmlAttribute
    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement
    public void setDateFrom(Calendar dateFrom) {
        this.dateFrom = dateFrom;
    }
    @XmlElement
    public void setDateTo(Calendar dateTo) {
        this.dateTo = dateTo;
    }
    @XmlElement
    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }
}
