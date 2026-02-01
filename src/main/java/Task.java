import java.time.LocalDateTime;

public class Task {
    private final String NAME;
    private boolean isComplete = false;
    private LocalDateTime DATE_TIME;
    private int ASSIGNMENT_ORDER;
    private final String[] MARKS = {"[ ]", "[X]"};

    public Task() {
        this.NAME = "empty unnamed Event";
    }

    public Task(String NAME, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
    }

    public Task(String NAME, boolean isComplete, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.isComplete = isComplete;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
    }

    public Task(String NAME, LocalDateTime startingDateTime, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.DATE_TIME = startingDateTime;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
    }

    public Task(String NAME, boolean isComplete, LocalDateTime startingDateTime, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.isComplete = isComplete;
        this.DATE_TIME = startingDateTime;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
    }

    public String getName() {
        return NAME;
    }

    public String readBack() {
        return (isComplete ? MARKS[1]: MARKS[0]) + " " + NAME;
    }

    public int getAssignmentOrder() {
        return ASSIGNMENT_ORDER;
    }

    public String indicateCompletion() {
        if (isComplete) {
            return "X";
        } else {
            return " ";
        }
    }

}
