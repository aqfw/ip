package kluso.objects;

public class Todo extends Task {
    static final String taskType = "[T]";

    public Todo() {
        super();
        this.name = "empty unnamed kluso.objects.Todo";
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

    // This is a side effect free way of resetting the assignment order by returning a new instance of kluso.objects.Todo
    // with an updated order, used in kluso.ui.Kluso to set orders after a deletion.
    @Override
    public Todo reassignOrder(int newOrder) {
        return new Todo(this.name, this.isComplete, newOrder);
    }


}
