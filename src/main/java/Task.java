import java.time.LocalDateTime;

public class Task {
    private String name;
    private boolean isComplete;
    private LocalDateTime dateTime;
    private int assignmentOrder;

    public Task() {
        this.name = "default unnamed Event";
    }

    public Task(String name, int assignmentOrder) {
        this.name = name;
        this.assignmentOrder = assignmentOrder;
    }

    public Task(String name, boolean isComplete, int assignmentOrder) {
        this.name = name;
        this.isComplete = isComplete;
        this.assignmentOrder = assignmentOrder;
    }

    public Task(String name, LocalDateTime startingDateTime, int assignmentOrder) {
        this.name = name;
        this.dateTime = startingDateTime;
        this.assignmentOrder = assignmentOrder;
    }

    public Task(String name, boolean isComplete, LocalDateTime startingDateTime, int assignmentOrder) {
        this.name = name;
        this.isComplete = isComplete;
        this.dateTime = startingDateTime;
        this.assignmentOrder = assignmentOrder;
    }

    public String readBack() {
        return name;
    }

    public int getAssignmentOrder() {
        return assignmentOrder;
    }

}
