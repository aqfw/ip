import java.time.Month;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {
    private String name;
    private boolean isComplete;
    private LocalDateTime dateTime;

    public Event() {
        this.name = "default unnamed Event";
    }

    public Event(String name) {
        this.name = name;
    }

    public Event(String name, boolean isComplete) {
        this.name = name;
        this.isComplete = isComplete;
    }

    public Event(String name, LocalDateTime startingDateTime) {
        this.name = name;
        this.dateTime = startingDateTime;
    }

    public Event(String name, boolean isComplete, LocalDateTime startingDateTime) {
        this.name = name;
        this.isComplete = isComplete;
        this.dateTime = startingDateTime;
    }

    public String readBack() {
        return name;
    }

}
