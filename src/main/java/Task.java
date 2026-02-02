import java.time.LocalDateTime;

public class Task {
    protected String NAME;
    protected boolean isComplete = false;
    protected int ASSIGNMENT_ORDER;
    protected String[] MARKS = {"[ ]", "[X]"};

    public Task() {
        this.NAME = "empty unnamed Task";
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


    public Task markAsComplete() {
        return new Task(this.NAME, true, this.ASSIGNMENT_ORDER);
    }

    public Task markAsIncomplete() {
        return new Task(this.NAME, false, this.ASSIGNMENT_ORDER);
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
