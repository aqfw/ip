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

    public String getName() {
        return name;
    }

    public String readBack() {
        return (isComplete ? marks[1]: marks[0]) + " " + name;
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
