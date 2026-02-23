public class Event extends Task {

    static final String taskType = "[E]";
    private String startTime = new String();
    private String endTime = new String();

    public Event() {
        super();
        this.name = "empty unnamed Event";
    }

    public Event(String name, String startTime, String endTime, int assignmentOrder) {
        super(name, assignmentOrder);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String name, String startTime, String endTime, boolean isComplete, int assignmentOrder) {
        super(name, isComplete, assignmentOrder);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Event markAsComplete(){
        return new Event(this.name, this.startTime, this.endTime, true, this.assignmentOrder);
    }

    @Override
    public Event markAsIncomplete() {
        return new Event(this.name, this.startTime, this.endTime, false, this.assignmentOrder);
    }

    @Override
    public String readBack() {
        return (taskType + (isComplete ? marks[1]: marks[0])) + " " + name + "(from: " + startTime +
                ", to: " + endTime + ")";
    }

    // This is a side effect free way of resetting the assignment order by returning a new instance of Event
    // with an updated order, used in Kluso to set orders after a deletion.
    @Override
    public Event reassignOrder(int newOrder) {
        return new Event(this.name, this.startTime, this.endTime, this.isComplete, newOrder);
    }

}
