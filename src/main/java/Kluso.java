import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

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

    // Output file variables: folder ./data/ and file name kluso_task_list.txt
    static final String SAVE_DIR = "data";
    static final String SAVE_FILE = SAVE_DIR + File.separator + "kluso_task_list.txt";

    // Indices into the ArrayList returned by decomposeLine; will require enum mapping later
    static final int DECOMPOSED_TYPE = 0;
    static final int DECOMPOSED_IS_COMPLETE = 1;
    static final int DECOMPOSED_NAME = 2;
    static final int DECOMPOSED_DEADLINE = 3;
    static final int DECOMPOSED_START = 3;
    static final int DECOMPOSED_END = 4;

    // Task-type identifiers used in decomposeLine and reconstructTask
    static final String DECOMPOSED_TYPE_TASK = "TASK";
    static final String DECOMPOSED_TYPE_TODO = "TODO";
    static final String DECOMPOSED_TYPE_DEADLINE = "DEADLINE";
    static final String DECOMPOSED_TYPE_EVENT = "EVENT";

    /**
     * Main method of the Kluso; loads saved data, reads new input and calls commands as needed.
     * Relies on parse(String input) to decompose keyboard inputs for relevant methods
     */
    public static void main(String[] args) {

        // Load tasks from the previous Kluso session at the start of this session
        loadTasksFromFile();

        // Print greeting text
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
                case DELETE_STRING -> deleteTask(commandSegments[1]);
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
     * Displays the quit message and stops the main method's input loop.
     */
    private static void quitProgram() {
        //Display quit message
        System.out.println(LINE_BREAK + QUITTING_TEXT + LINE_BREAK);
        isReadyForInputs = false;
    }

    /**
     * Deletes a task by number (taking in a String but requiring it to be a number)
     */
    private static void deleteTask(String indexString) {
        try {
            int indexToDelete = Integer.parseInt(indexString);

            if (indexToDelete > 0 && indexToDelete <= tasks.size()) {
                Task removedTask = tasks.remove(indexToDelete - 1);

                // Shifts all entries after the removed entry in the list "up" by one index by inserting
                // new Task entries into the tasks list
                for (int i = indexToDelete - 1; i < tasks.size(); i++) {
                    tasks.set(i, tasks.get(i).reassignOrder(i+1));
                }

                System.out.println(LINE_BREAK + "Roger, I've removed this tasking:\n  " +
                        removedTask.readBack() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);

                // Saves the current list of tasks to the file SAVE_FILE
                saveTasksToFile();

            } else {
                System.out.println("There's no tasking with this index! Check back!");
            }

        } catch (NumberFormatException e) {
            System.out.println("What you said after " + DELETE_STRING
                    + " is invalid; I need a valid index integer!");
        }
    }

    /**
     * Prints all tasks currently stored in the task list in the terminal.
     */
    private static void listTasks() {
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

    /**
     * Marks a Task Object as complete (using object's methods), saving it, and returning it to tasks.
     */
    private static void markTask(String indexString) {

        // Task marking logic
        try {
            int indexToMark = Integer.parseInt(indexString);

            // Immutably mark a task as complete if a valid index has been passed
            if (indexToMark > 0 && indexToMark <= tasks.size()) {
                System.out.println(LINE_BREAK + "Okay, I've marked task no. " + (indexToMark) +
                        " as done:\n");
                Task markedTask = tasks.get(indexToMark-1).markAsComplete();
                tasks.set(indexToMark-1, markedTask);
                System.out.println(tasks.get(indexToMark-1).readBack() + "\n" + LINE_BREAK);

                // Saves the current list of tasks to the file SAVE_FILE
                saveTasksToFile();
            } else {
                System.out.println("There's no tasking with this index! Check back!");
            }
        } catch (NumberFormatException e) {
            System.out.println("What you said after " + MARK_STRING +
                    " is invalid; I need a valid index integer!");
        }
    }

    /**
     * Unmarks a Task Object regardless (using object's methods), saving it, and returning it to tasks.
     */
    private static void unmarkTask(String indexString) {

        // Task unmarking logic
        try {
            int indexToUnmark = Integer.parseInt(indexString);

            // Immutably mark a task as complete if a valid index has been passed
            if (indexToUnmark > 0 && indexToUnmark <= tasks.size()) {
                System.out.println(LINE_BREAK + "Okay, I've unmarked task no. " + (indexToUnmark) +
                        " to incomplete:\n");
                Task unmarkedTask = tasks.get(indexToUnmark -1).markAsIncomplete();
                tasks.set(indexToUnmark -1, unmarkedTask);
                System.out.println(tasks.get(indexToUnmark -1).readBack() + "\n" + LINE_BREAK);

                // Saves the current list of tasks to the file SAVE_FILE
                saveTasksToFile();
            } else {
                System.out.println("There's no tasking with this index! Check back!");
            }
        } catch (NumberFormatException e) {
            System.out.println("What you said after " + UNMARK_STRING +
                    " is invalid; I need a valid index integer!");
        }

    }

    /**
     * Helper method to just return in the terminal the number of tasks
     */
    private static String getTaskCountSignature() {
        int count = tasks.size();
        return "I've got " + count + (count == 1 ? " tasking" : " taskings") + " on my order.";
    }

    /**
     * For the Task class, add a new Task to tasks ArrayList
     */
    private static void addTask(String input) {
        // Create new Task object, add to array, echo the addition and increment taskCount counter
        Task newTask = new Task(input, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added task at position no.: " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();

        // Saves the current list of tasks to the file SAVE_FILE
        saveTasksToFile();
    }

    /**
     * For the Todo class, create new Todo object, add to array, echo the addition and increment taskCount counter
     */
    private static void addTodo(String todoName) {

        Task newTask = new Todo(todoName, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added to-do at position no. " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();

        // Saves the current list of tasks to the file SAVE_FILE
        saveTasksToFile();
    }

    /**
     * Create new Deadline object, add to array, echo the addition and increment taskCount counter
     * @param deadlineName
     * @param deadlineTime
     */
    private static void addDeadline(String deadlineName, String deadlineTime) {
        // Create new Deadline object, add to array, echo the addition and increment taskCount counter
        Task newTask = new Deadline(deadlineName, deadlineTime, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added deadline at position no. " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();

        // Saves the current list of tasks to the file SAVE_FILE
        saveTasksToFile();
    }

    /**
     * Create a new Event, and add it to tasks, and echo. Updates the saved text file.
     * @param eventName
     * @param startTime
     * @param endTime
     */
    private static void addEvent(String eventName, String startTime, String endTime) {
        Task newTask = new Event(eventName, startTime, endTime, tasks.size() + 1);
        tasks.add(newTask);
        System.out.println(LINE_BREAK + "Roger, I've added event at position no. " + tasks.size() + ", " +
                tasks.get(tasks.size()-1).getName() + "\n" + getTaskCountSignature() + "\n" + LINE_BREAK);
        System.out.println();

        // Saves the current list of tasks to the file SAVE_FILE
        saveTasksToFile();
    }

    /**
     * parse is used to comprehend a user's text inputs into components that commands can take in.
     * @param input any keyboard string that the user inputs into the app
     * @return a list of strings, with each string being each component of a command.
     */
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
                return (parts);
            case UNMARK_STRING:
                // Check and throw an exception if the integer after mark is empty.
                if (arguments.isEmpty()) {
                    throw new IllegalArgumentException("You haven't given me an integer associated with a task!");
                }
                return (parts);
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
            case DELETE_STRING:
                if (arguments.isEmpty()) {
                    throw new IllegalArgumentException("You haven't given me an integer associated with a task!");
                }
                return (parts);
            // The case where a basic task is to be entered will be handled using the "default" case.
            default:

                // Treat unrecognised first word as a task name as per Level-3
                String taskName = input.trim();
                return (new String[]{taskName});
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Problem! " + e.getMessage());
            return (new String[]{});
        }

    }

    /**
     * Writes the current task list to ./data/kluso_task_list.txt.
     * Creates ./data/ and the file itself if they do not exist.
     *
     */
    public static void saveTasksToFile(){

        //Create directory in which to save the list data, and check if it can be created
        File dir = new File(SAVE_DIR);
        if (!dir.exists() && !dir.mkdir()) {
            System.out.println("Warning: could not create save directory.");
            return;
        }

        File file = new File(SAVE_FILE);

        //Create the file if it does not already exist
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not create kluso_task_list.txt" + e.getMessage());
            return;
        }

        // Write the task list directly to the file, overwriting previous task list
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            if (tasks.isEmpty()) {
                writer.println("=empty tasking order=");
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    int order = tasks.get(i).getAssignmentOrder();
                    String line = order + ". " + tasks.get(i).readBack();
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: task list file or directory cannot be written to" + e.getMessage());
        }
    }

    /**
     * Loads tasks by using the java.io libraries, using parseSaveLines to parse the inputs into objects
     * to be recorded in tasks, by setting tasks to parseSaveLines's ArrayList Task output.
     */
    public static void loadTasksFromFile(){

        // Load the SAVE_FILE if it exists
        File file = new File(SAVE_FILE);

        //Catch case where either no file or no directory exists.
        if (!file.exists()) {
            System.out.println("No tasking order in my central computer's memory! I'll make a new one!");
        }

        // Read the file line by line, parse into objects, add objects into the "tasks" list
        ArrayList<String> lines = new ArrayList<String>();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("The tasking order file is unreadable! " + e.getMessage());
        }
        tasks = parseSaveLines(lines);
    }

    /**
     * Takes the list of lines from the save file, goes through each line, strips extraneous parts, gets order
     * And uses reconstructTask on each line to obtain a Task object to put into restoredTasks.
     * @param lines
     * @return
     */
    public static ArrayList<Task> parseSaveLines(ArrayList<String> lines) {
        ArrayList<Task> restoredTasks = new ArrayList<Task>();

        // Iteratively go through every line, parsing each into an object. Each run parses one line & object.
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            int dotIndex = line.indexOf(". ");
            if (dotIndex == -1) {
                continue; // This excludes any line that doesn't have a valid position number
            }

            String taskReadBack = line.substring(dotIndex + 2);

            // This excludes any task output which does not start with "[ ]" or any 3 chars
            if (taskReadBack.length() < 3) {
                continue;
            }

            // Exclude any task output that doesn't start with the appropriate checkboxes by length.
            // For parent Tasks the checkbox is at index 0-2
            // For child classes it is at index 3-5, due to the type prefix (e.g. "[E]" for event).
            String presumedCheckbox = taskReadBack.substring(0, 3);
            boolean isChildClass = taskReadBack.length() >= 6 && taskReadBack.charAt(3) == '[';
            String presumedChildCheckbox = (isChildClass ? taskReadBack.substring(3, 6) : "");
            boolean hasValidCheckbox = presumedCheckbox.equals("[ ]") || presumedCheckbox.equals("[X]")
                    || presumedChildCheckbox.equals("[ ]") || presumedChildCheckbox.equals("[X]");
            if (!hasValidCheckbox) {
                continue;
            }

            ArrayList<String> decomposedLine = decomposeLine(taskReadBack);

            // Check and skip a line that decomposeLine fails to return a pattern for (may change to corrupt!)
            if (decomposedLine == null) {
                continue;
            }

            // Counter just to be able to number the tasks with the appropriate assignmentOrder
            int assignmentOrder = restoredTasks.size() + 1;
            Task restoredTask = reconstructTask(decomposedLine, assignmentOrder, line);

            if (restoredTask != null) {
                restoredTasks.add(restoredTask);
            }
        }
        return restoredTasks;
    }

    /**
     * Reconstructs a task from a decomposed line, by calling decomposedLine, then checking the case.
     * relies on some static strings that will need to be enum'ed for ease of modification.
     * @param decomposedLine
     * @param assignmentOrder
     * @param rawLine
     * @return
     */
    // Will need to try a better default/non-readable task handling.
    public static Task reconstructTask(ArrayList<String> decomposedLine, int assignmentOrder, String rawLine) {
        boolean isComplete = Boolean.parseBoolean(decomposedLine.get(DECOMPOSED_IS_COMPLETE));
        String name = decomposedLine.get(DECOMPOSED_NAME);

        switch (decomposedLine.get(DECOMPOSED_TYPE)) {
        case DECOMPOSED_TYPE_TASK:
            return new Task(name, isComplete, assignmentOrder);
        case DECOMPOSED_TYPE_TODO:
            return new Todo(name, isComplete, assignmentOrder);
        case DECOMPOSED_TYPE_DEADLINE:
            return new Deadline(name, decomposedLine.get(DECOMPOSED_DEADLINE), isComplete, assignmentOrder);
        case DECOMPOSED_TYPE_EVENT:
            return new Event(name, decomposedLine.get(DECOMPOSED_START), decomposedLine.get(DECOMPOSED_END),
                    isComplete, assignmentOrder);
        default:
            System.out.println("There's some data corruption here, the task is unreadable" + rawLine);
            return null;
        }
    }

    /**
     * Decomposes the lines into segments that can be read by reconstructTask, by mapping characters.
     * @param taskReadBack
     * @return
     */
    public static ArrayList<String> decomposeLine(String taskReadBack) {

        // Break the line string into components depending on whether Task or child class thereof
        String taskType = new String();
        String taskMarking = new String();
        boolean isComplete = taskMarking.equals("[X]");
        String taskContent = new String();

        // Case where a line item is of the parent class Task
        if (taskReadBack.charAt(3) != '[') {
            taskType = DECOMPOSED_TYPE_TASK;
            taskMarking = taskReadBack.substring(0, 3);
            isComplete = taskMarking.equals("[X]");
            taskContent = taskReadBack.substring(4);
        }

        // Case where a line item is of any child class of Task: map the file prefix to the appropriate constant
        if (taskReadBack.charAt(3) == '[') {
            String filePrefix = taskReadBack.substring(0, 3);
            switch (filePrefix) {
            case "[T]":
                taskType = DECOMPOSED_TYPE_TODO;
                break;
            case "[D]":
                taskType = DECOMPOSED_TYPE_DEADLINE;
                break;
            case "[E]":
                taskType = DECOMPOSED_TYPE_EVENT;
                break;
            default:
                return null;
            }
            taskMarking = taskReadBack.substring(3, 6);
            isComplete = taskMarking.equals("[X]");
            taskContent = taskReadBack.substring(7);
        }

        // Build the decomposed line from components
        ArrayList<String> decomposedLine = new ArrayList<String>();

        // Parse according to type, adding each component of the decomposed line.
        switch (taskType) {
        case DECOMPOSED_TYPE_TASK:
            //Task: [X] some task
            decomposedLine.add(DECOMPOSED_TYPE_TASK);
            decomposedLine.add(Boolean.toString(isComplete));
            decomposedLine.add(taskContent);
            return decomposedLine;
        case DECOMPOSED_TYPE_TODO:
            // Todo: [T][X] some todo
            decomposedLine.add(DECOMPOSED_TYPE_TODO);
            decomposedLine.add(Boolean.toString(isComplete));
            decomposedLine.add(taskContent);
            return decomposedLine;
        case DECOMPOSED_TYPE_DEADLINE:
            // Deadline: [D][ ] some deadline(time)

            int deadlineOpenBracket = taskContent.indexOf('(');
            int deadlineCloseBracket = taskContent.lastIndexOf(')');

            if (deadlineOpenBracket == -1 || deadlineCloseBracket == -1
                    || deadlineCloseBracket <= deadlineOpenBracket) {
                return null;
            }

            String deadlineName = taskContent.substring(0, deadlineOpenBracket).trim();
            String deadlineTime = taskContent.substring(deadlineOpenBracket + 1, deadlineCloseBracket).trim();

            decomposedLine.add(DECOMPOSED_TYPE_DEADLINE);
            decomposedLine.add(Boolean.toString(isComplete));
            decomposedLine.add(deadlineName);
            decomposedLine.add(deadlineTime);
            return decomposedLine;
        case DECOMPOSED_TYPE_EVENT:
            // Event: [E][ ] some event(from: 0130, to: 0330)
            int eventOpenBracket = taskContent.indexOf('(');
            int eventCloseBracket = taskContent.lastIndexOf(')');

            if (eventOpenBracket == -1 || eventCloseBracket == -1
                    || eventCloseBracket <= eventOpenBracket) {
                return null;
            }

            String eventName = taskContent.substring(0, eventOpenBracket).trim();
            String eventTimes = taskContent.substring(eventOpenBracket + 1, eventCloseBracket).trim();

            // eventTimes is "from: startTime, to: endTime"
            int fromIndex = eventTimes.indexOf("from: ");
            int toIndex = eventTimes.indexOf(", to: ");
            if (fromIndex == -1 || toIndex == -1) {
                return null;
            }

            String startTime = eventTimes.substring(fromIndex + 6, toIndex).trim();
            String endTime = eventTimes.substring(toIndex + 6).trim();

            decomposedLine.add(DECOMPOSED_TYPE_EVENT);
            decomposedLine.add(Boolean.toString(isComplete));
            decomposedLine.add(eventName);
            decomposedLine.add(startTime);
            decomposedLine.add(endTime);
            return decomposedLine;
        default:
            return null;
        }
    }
}
