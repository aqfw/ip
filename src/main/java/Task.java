public class Task {
    protected String name;
    protected boolean isComplete = false;
    protected int assignmentOrder;
    protected String[] marks = {"[ ]", "[X]"};

    public Task() {
        this.name = "empty unnamed Task";
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

    public Task markAsComplete() {
        return new Task(this.name, true, this.assignmentOrder);
    }

    public Task markAsIncomplete() {
        return new Task(this.name, false, this.assignmentOrder);
    }

    public String readBack() {
        return (isComplete ? marks[1]: marks[0]) + " " + name;
    }

    // This is a side effect free way of resetting the assignment order by returning a new instance of Todo with an
    // updated order, used in Kluso to set orders after a deletion.
    public Task reassignOrder(int newOrder) {
        return new Task(this.name, this.isComplete, newOrder);
    }

    public String getName() {
        return name;
    }

    public int getAssignmentOrder() {
        return assignmentOrder;
    }

    public String indicateCompletion() {
        if (isComplete) {
            return "X";
        } else {
            return " ";
        }
    }

}
