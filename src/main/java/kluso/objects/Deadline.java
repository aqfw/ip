package kluso.objects;

import kluso.tools.KlusoDateTime;

public class Deadline extends Task {

    static final String taskType = "[D]";
    private KlusoDateTime deadline;
    //private String deadline = new String();

    public Deadline() {
        super();
        this.name = "empty unnamed kluso.objects.Deadline";
    }

    public Deadline(String name, String deadlineString, int assignmentOrder) {
        super(name, assignmentOrder);
        this.deadline = parseDeadlineString(deadlineString);
    }

    public Deadline(String name, String deadlineString, boolean isComplete, int assignmentOrder) {
        super(name, isComplete, assignmentOrder);
        this.deadline = parseDeadlineString(deadlineString);
    }

    @Override
    public Deadline markAsComplete(){
        return new Deadline(this.name, getDeadlineString(), true, this.assignmentOrder);
    }

    @Override
    public Deadline markAsIncomplete() {
        return new Deadline(this.name, getDeadlineString(), false, this.assignmentOrder);
    }

    @Override
    public String readBack() {
        String deadlinePrintOutput;
        if (deadline != null) {
            deadlinePrintOutput = deadline.toString();
        } else {
            deadlinePrintOutput = "";
        }

        String rawDeadline;
        if (deadline != null) {
            rawDeadline = deadline.getInput();
        } else {
            rawDeadline = "";
        }

        return (taskType + (isComplete ? marks[1] : marks[0])) + " " + name  +
                " | Due: " + deadlinePrintOutput + " (" + rawDeadline + ")";
    }

    // This is a side effect free way of resetting the assignment order by returning a new instance of kluso.objects.Deadline
    // with an updated order, used in kluso.ui.Kluso to set orders after a deletion.
    @Override
    public Deadline reassignOrder(int newOrder) {
        return new Deadline(this.name, deadline.getInput(), this.isComplete, newOrder);
    }

    private KlusoDateTime parseDeadlineString(String deadlineString) {
        try {
            return new KlusoDateTime(deadlineString);
        } catch (IllegalArgumentException e) {
            System.out.println("You've given me a bad date/time format! Give me <dd-mm-yyyy/0000> date/time," +
                    "or just <dd-mm-yyyy>!" + e.getMessage());
            return null;
        }
    }

    private String getDeadlineString() {
        if (deadline != null) {
            return deadline.getInput();
        } else {
            return "";
        }
    }

}
