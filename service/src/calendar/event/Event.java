package calendar.event;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Event implements Serializable {
    private String description;
    private Calendar dateFrom;
    private Calendar dateTo;
    private List<String> attendees;

    private Event(Builder builder){
        this.description = builder.description;
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
        this.attendees = builder.attendees;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public Calendar getDateFrom() {
        return dateFrom;
    }

    public Calendar getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (dateFrom != null ? !dateFrom.equals(event.dateFrom) : event.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(event.dateTo) : event.dateTo != null) return false;
        if (!description.equals(event.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }


    public static class Builder{
        private String description;
        private Calendar dateFrom;
        private Calendar dateTo;
        private List<String> attendees;

        public Builder(){
            this.build();
        }

        public Builder(Event original){
            this.description = original.description;
            this.dateFrom = original.dateFrom;
            this.dateTo = original.dateTo;
            this.attendees = original.attendees;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder dateFrom (Calendar dateFrom){
            this.dateFrom = dateFrom;
            return this;
        }

        public Builder dateTo (Calendar dateTo){
            this.dateTo = dateTo;
            return this;
        }

        public Builder attendees(List<String> attendees){
            this.attendees = attendees;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }
}
