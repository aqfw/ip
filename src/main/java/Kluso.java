import java.util.ArrayList;
import java.util.Scanner;

public class Kluso {

    // Standard padding strings
    static final String LINE_BREAK = "____________________________________________________________\n";
    static final String NEW_NAME = "Kluso";
    static final String GREETING_TEXT = NEW_NAME + " single ship, checking in!\n" + "Tasking?\n";
    static final String QUITTING_TEXT = NEW_NAME + " is RTB!\n";
    static final String LIST_HEADER = "Standby to read back your tasking order:";

    // Commands
    static final String QUIT_STRING = "out";
    static final String LIST_STRING = "list";
    static final String MARK_STRING = "mark";
    static final String UNMARK_STRING = "unmark";
    static final String TODO_STRING = "todo";
    static final String DEADLINE_STRING = "deadline";
    static final String EVENT_STRING = "event";
    static final String DELETE_STRING = "delete";

    // Logic variables
    static ArrayList<Task> tasks = new ArrayList<>();
    static boolean isReadyForInputs = true;


    /**
     * Main method of the Kluso application.
     * Opens a scanner stream, reads user input in a loop, and dispatches commands.
     */
    public static void main(String[] args) {

        //Print greeting text
        System.out.println(LINE_BREAK + GREETING_TEXT + LINE_BREAK);

        // Open a new scanner stream
        Scanner scanner = new Scanner(System.in);

        // Execution block
        while (isReadyForInputs) {
            
            // Read input and parse into command segments
            String input = scanner.nextLine();
            String[] commandSegments = parse(input);

            // Handle each of the cases, differentiated according to the length of the input.
            if (commandSegments.length == 1) {
                switch (commandSegments[0]) {
                    case QUIT_STRING -> quitProgram();
                    case LIST_STRING -> listTasks();
                    default -> addTask(commandSegments[0]);
                }
            }

            if (commandSegments.length == 2) {
                switch (commandSegments[0]) {
                    case MARK_STRING -> markTask(commandSegments[1]);
                    case UNMARK_STRING -> unmarkTask(commandSegments[1]);
                    case TODO_STRING -> addTodo(commandSegments[1]);
                    //case DELETE_STRING -> deleteTask(commandSegments[1]);
                }
            }

            if (commandSegments.length == 3) {
                switch (commandSegments[0]) {
                    case DEADLINE_STRING -> addDeadline(commandSegments[1], commandSegments[2]);
                }
            }

            if (commandSegments.length == 4) {
                switch (commandSegments[0]) {
                    case EVENT_STRING -> addEvent(commandSegments[1], commandSegments[2], commandSegments[3]);
                }
            }

        }

        // Close the scanner stream
        scanner.close();

    }

    /**
     * Displays the quit message and stops the user's input loop.
     */
    private static void quitProgram() {
        //Display quit message
        System.out.println(LINE_BREAK + QUITTING_TEXT + LINE_BREAK);
        isReadyForInputs = false;
    }

    /**
     * Prints all tasks currently stored in the task list.
     */
    private static void listTasks() {
        // Subtract 1 from taskCount to get the last index in the array where a Task is stored
        //int lastIndex = taskCount - 1;

        // Style padding
        System.out.println(LINE_BREAK);
        System.out.println(LIST_HEADER);

        // If there are no tasks, say so in the program.
        if (tasks.isEmpty()) {
            System.out.println("No taskings!");
        }

        // Iteratively print the Tasks in the console in the order in which each Task was assigned
        for (int i = 0; i < tasks.size(); i++) {
            String taskNumber = String.valueOf(tasks.get(i).getAssignmentOrder());
            System.out.println(taskNumber + ". " + tasks.get(i).readBack());
        }

        // Style padding
        System.out.println(LINE_BREAK);
    }

    private static void markTask(String indexString) {
        // Task marking logic, checking if a valid integer has been passed; Should be consolidated
        try {
            int indexToMark = Integer.parseInt(indexString);

            // Immutably mark a task as complete if a valid index has been passed
            if (indexToMark > 0 && indexToMark <= tasks.size()) {
                System.out.println(LINE_BREAK + "Okay, I've marked task no. " + (indexToMark) +
                        " as done:\n");
                Task markedTask = tasks.get(indexToMark-1).markAsComplete();
                tasks.set(indexToMark-1, markedTask);
                System.out.println(tasks.get(indexToMark-1).readBack() + "\n" + LINE_BREAK);
            } else {
                System.out.println("There's no tasking with this index! Check back!");
            }
        } catch (NumberFormatException e) {
            System.out.println("What you said after " + UNMARK_STRING
                    + " is invalid; I need a valid index integer!");
        }
    }

    private static void unmarkTask(String indexString) {

        // Task marking logic, checking if a valid integer has been passed; Should be consolidated
        try {
            int indexToUnmark = Integer.parseInt(indexString);

            // Immutably mark a task as complete if a valid index has been passed
            if (indexToUnmark > 0 && indexToUnmark <= tasks.size()) {
                System.out.println(LINE_BREAK + "Okay, I've unmarked task no. " + (indexToUnmark) +
                        " to incomplete:\n");
                Task unmarkedTask = tasks.get(indexToUnmark -1).markAsIncomplete();
                tasks.set(indexToUnmark -1, unmarkedTask);
                System.out.println(tasks.get(indexToUnmark -1).readBack() + "\n" + LINE_BREAK);
            } else {
                System.out.println("There's no tasking with this index! Check back!");
            }
        } catch (NumberFormatException e) {
            System.out.println("What you said after " + MARK_STRING
                    + " is invalid; I need a valid index integer!");
        }

    }

    private static String getTaskCountSignature() {
        int count = tasks.size();
        return "I've got " + count + (count == 1 ? " tasking" : " taskings") + " on my order.";
    }

    private static void addTask(String input) {
        // Create new Task object, add to array, echo the addition and increment taskCount counter
        Task newTask = new Task(input, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added task at position no.: " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();
    }

    private static void addTodo(String todoName) {
        // Create new Todo object, add to array, echo the addition and increment taskCount counter
        Task newTask = new Todo(todoName, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added to-do at position no. " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();
    }

    private static void addDeadline(String deadlineName, String deadlineTime) {
        // Create new Deadline object, add to array, echo the addition and increment taskCount counter
        Task newTask = new Deadline(deadlineName, deadlineTime, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added deadline at position no. " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();
    }

    private static void addEvent(String eventName, String startTime, String endTime) {
        Task newTask = new Event(eventName, startTime, endTime, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added event at position no. " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();
    }

    private static String[] parse(String input) {

        try {

            // Check if input is null, if not try/catch exception
            if (input == null || input.isBlank()) {

                throw new IllegalArgumentException("You gave me an empty message, type what you want instead!");
            }

            // Split using regex into two parts for the commands, and store in a two-element parts array
            String[] parts = input.trim().split("\\s+", 2);

            // Set the first element to be the command (if it is a command).
            String command = parts[0].toLowerCase();

            // Check if more than one word was input, if so store it as args, else just store an empty string;
            // The empty string is for exception checking later.
            String arguments = parts.length > 1 ? parts[1].trim() : "";

            // Handle the first word using a switch case:
            switch (command) {
            case QUIT_STRING:
                return (new String[]{"out"});
            case LIST_STRING:
                return (new String[]{"list"});
            case MARK_STRING:
                // Check and throw an exception if the integer after mark is empty.
                if (arguments.isEmpty()) {
                    throw new IllegalArgumentException("You haven't given me an integer associated with a task!");
                }
            case UNMARK_STRING:
                // Check and throw an exception if the integer after mark is empty.
                if (arguments.isEmpty()) {
                    throw new IllegalArgumentException("You haven't given me an integer associated with a task!");
                }
                return parts;
            case TODO_STRING:
                if (arguments.isEmpty()) {
                    throw new IllegalArgumentException("I'm missing a name for the todo!");
                } else {
                    return (parts);
                }
            case DEADLINE_STRING:
                int slashIndex = arguments.indexOf("/");

                // Check if the user has used the "slash" to indicate "from" time.
                if (slashIndex == -1) {
                    throw new IllegalArgumentException("Missing slash for deadline! Use a slash before typing " +
                            "in the deadline!");
                }

                // storing the deadline
                String deadlineName = arguments.substring(0, slashIndex).trim();
                String deadlineTime = arguments.substring(slashIndex + 1).trim();

                if (deadlineName.isEmpty()) {
                    throw new IllegalArgumentException("You're missing the name of the deadline event!");
                }

                if (deadlineTime.isEmpty()) {
                    throw new IllegalArgumentException("You didn't give a time for the deadline event!");
                }

                return (new String[]{command, deadlineName, deadlineTime});
            case EVENT_STRING:
                int startTimeIndex = arguments.indexOf("/from");
                int endTimeIndex = arguments.indexOf("/to");

                // Check if the user has correctly input the start and end of the event.
                if (startTimeIndex == -1) {
                    throw new IllegalArgumentException("You're missing the /from command for the start of the event!");
                }
                if (endTimeIndex == -1) {
                    throw new IllegalArgumentException("You're missing the /to command for the end of the event!");
                }

                String eventName = arguments.substring(0, startTimeIndex).trim();
                String startTime = arguments.substring(startTimeIndex + 5, endTimeIndex).trim();
                String endTime = arguments.substring(endTimeIndex + 3).trim();

                if (eventName.isEmpty()) {
                    throw new IllegalArgumentException("You're missing the name of the event!");
                } else if (startTime.isEmpty()) {
                    throw new IllegalArgumentException("You're missing the start time of the event, to be input " +
                            "after '/from' !");
                } else if (endTime.isEmpty()) {
                    throw new IllegalArgumentException("You're missing the end time of the event, to be input" +
                            "after '/to' !");
                }

                return (new String[]{command, eventName, startTime, endTime});

            // The case where a basic task is to be entered will be handled using the "default" case.
            default:

                // Treat unrecognised first word as a task name as per Level-3
                String taskName = input.trim();
                return (new String[]{taskName});
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Problem! " + e.getMessage());
            return new String[]{};
        }

    }

}

