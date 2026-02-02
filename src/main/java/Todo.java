public class Todo extends Task {
    private final String TASK_TYPE = "[T]";

    public Todo() {
        this.NAME = "empty unnamed Todo";
    }

    public Todo(String NAME, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
    }

    public Todo(String NAME, boolean isComplete, int ASSIGNMENT_ORDER) {
        this.NAME = NAME;
        this.isComplete = isComplete;
        this.ASSIGNMENT_ORDER = ASSIGNMENT_ORDER;
    }

    @Override
    public Todo markAsComplete(){
        return new Todo(this.NAME, true, this.ASSIGNMENT_ORDER);
    }

    @Override
    public Todo markAsIncomplete() {
        return new Todo(this.NAME, false, this.ASSIGNMENT_ORDER);
    }

    @Override
    public String readBack() {
        return (TASK_TYPE + (isComplete ? MARKS[1]: MARKS[0])) + " " + NAME;
    }


}
