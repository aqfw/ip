public class Deadline extends Task {

    static final String taskType = "[D]";
    private String deadline = new String();

    public Deadline() {
        super();
        this.name = "empty unnamed Deadline";
    }

    public Deadline(String name, String deadline, int assignmentOrder) {
        super(name, assignmentOrder);
        this.deadline = deadline;
    }

    public Deadline(String name, String deadline, boolean isComplete, int assignmentOrder) {
        super(name, isComplete, assignmentOrder);
        this.deadline = deadline;
    }

    @Override
    public Deadline markAsComplete(){
        return new Deadline(this.name, this.deadline, true, this.assignmentOrder);
    }

    @Override
    public Deadline markAsIncomplete() {
        return new Deadline(this.name, this.deadline, false, this.assignmentOrder);
    }

    @Override
    public String readBack() {
        return (taskType + (isComplete ? marks[1]: marks[0])) + " " + name + "(" + deadline + ")";
    }

}
