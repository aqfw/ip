public class Todo extends Task {
    static final String taskType = "[T]";

    public Todo() {
        super();
        this.name = "empty unnamed Todo";
    }

    public Todo(String name, int assignmentOrder) {
        super(name, assignmentOrder);
    }

    public Todo(String name, boolean isComplete, int assignmentOrder) {
        super(name, isComplete, assignmentOrder);
    }

    @Override
    public Todo markAsComplete() {
        return new Todo(this.name, true, this.assignmentOrder);
    }

    @Override
    public Todo markAsIncomplete() {
        return new Todo(this.name, false, this.assignmentOrder);
    }

    @Override
    public String readBack() {
        return (taskType + (isComplete ? marks[1]: marks[0])) + " " + name;
    }


}
