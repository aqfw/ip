public class Event extends Task {

    private final String TASK_TYPE = "[E]";
    private String START_TIME = new String();
    private String END_TIME = new String();

    public Event() {
        super();
        this.NAME = "empty unnamed Event";
    }

    public Event(String NAME, String START_TIME, String END_TIME, int ASSIGNMENT_ORDER) {
        super(NAME, ASSIGNMENT_ORDER);
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
    }

    public Event(String NAME, String START_TIME, String END_TIME, boolean isComplete, int ASSIGNMENT_ORDER) {
        super(NAME, isComplete, ASSIGNMENT_ORDER);
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
    }

    @Override
    public Event markAsComplete(){
        return new Event(this.NAME, this.START_TIME, this.END_TIME, true, this.ASSIGNMENT_ORDER);
    }

    @Override
    public Event markAsIncomplete() {
        return new Event(this.NAME, this.START_TIME, this.END_TIME, false, this.ASSIGNMENT_ORDER);
    }

    @Override
    public String readBack() {
        return (TASK_TYPE + (isComplete ? MARKS[1]: MARKS[0])) + " " + NAME + "(from: " + START_TIME +
                ", to: " + END_TIME + ")";
    }

}
