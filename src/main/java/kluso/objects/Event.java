package kluso.objects;

import kluso.tools.KlusoDateTime;

public class Event extends Task {

    static final String taskType = "[E]";

    private KlusoDateTime startTime;
    private KlusoDateTime endTime;

    //private String startTime = new String();
    //private String endTime = new String();

    public Event() {
        super();
        this.name = "empty unnamed kluso.objects.Event";
    }

    public Event(String name, String startTime, String endTime, int assignmentOrder) {
        super(name, assignmentOrder);
        this.startTime = parseEventTime(startTime, "start");
        this.endTime = parseEventTime(endTime, "end");
    }

    public Event(String name, String startTime, String endTime, boolean isComplete, int assignmentOrder) {
        super(name, isComplete, assignmentOrder);
        this.startTime = parseEventTime(startTime, "start");
        this.endTime = parseEventTime(endTime, "end");
    }

    @Override
    public Event markAsComplete(){
        return new Event(this.name, getEventString(startTime), getEventString(endTime), true, this.assignmentOrder);
    }

    @Override
    public Event markAsIncomplete() {
        return new Event(this.name, getEventString(startTime), getEventString(endTime), false, this.assignmentOrder);
    }

    @Override
    public String readBack() {

        String startDisplay;
        String startRaw;
        if (startTime != null) {
            startDisplay = startTime.toString();
            startRaw = startTime.getInput();
        } else {
            startDisplay = "";
            startRaw = "";
        }

        String endRaw;
        String endDisplay;
        if (startTime != null) {
            endDisplay = endTime.toString();
            endRaw = endTime.getInput();
        } else {
            endDisplay = "";
            endRaw = "";
        }

        return (taskType + (isComplete ? marks[1] : marks[0])) + " " + name + " | " +
                startDisplay + " to " + endDisplay +
                " (from: " + startRaw + ", to: " + endRaw + ")";
    }

    // This is a side effect free way of resetting the assignment order by returning a new instance of kluso.objects.Event
    // with an updated order, used in kluso.ui.Kluso to set orders after a deletion.
    @Override
    public Event reassignOrder(int newOrder) {
        return new Event(this.name, getEventString(startTime), getEventString(endTime),
                this.isComplete, newOrder);
    }

    private static KlusoDateTime parseEventTime(String eventTime, String label) {
        try {
            return new KlusoDateTime(eventTime);
        } catch (IllegalArgumentException e) {
            System.out.println("You've given me a bad " + label + "date/time, I need it in" +
                    "dd-mm-yyyy/hhmm or just dd-mm-yyyy!");
            return null;
        }
    }

    private static String getEventString(KlusoDateTime klusoDateTime) {
        if (klusoDateTime != null) {
            return klusoDateTime.getInput();
        } else {
            return "";
        }
    }

}
