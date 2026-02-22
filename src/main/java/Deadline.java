public class Deadline extends Task {

    private final String TASK_TYPE = "[D]";
    private String DATE_TIME = new String();

    public Deadline() {
        super();
        this.NAME = "empty unnamed Deadline";
    }

    public Deadline(String NAME, String DATE_TIME, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
        this.DATE_TIME = DATE_TIME;

    }

    public Deadline(String NAME, String DATE_TIME, boolean isComplete, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.isComplete = isComplete;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
        this.DATE_TIME = DATE_TIME;

    }

    @Override
    public Deadline markAsComplete(){
        return new Deadline(this.NAME, this.DATE_TIME, true, this.ASSIGNMENT_ORDER);
    }

    @Override
    public Deadline markAsIncomplete() {
        return new Deadline(this.NAME, this.DATE_TIME, false, this.ASSIGNMENT_ORDER);
    }

    @Override
    public String readBack() {
        return (TASK_TYPE + (isComplete ? MARKS[1]: MARKS[0])) + " " + NAME + "(" + DATE_TIME;
    }

}
