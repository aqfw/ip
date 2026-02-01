import java.util.Scanner;

public class Kluso {
    public static void main(String[] args) {
        
        String LINE_BREAK = "____________________________________________________________\n";
        String NEW_NAME = "Kluso";
        String GREETING_TEXT = NEW_NAME + " single ship, checking in!\n" + "Tasking?\n";
        String QUITTING_TEXT = NEW_NAME + " is RTB!\n";
        String LIST_HEADER = "Standby to read back your tasking order:";
        String MARK_STRING = "mark ";
        String UNMARK_STRING = "unmark ";
        
        //Print greeting text
        System.out.println(LINE_BREAK
                + GREETING_TEXT
                + LINE_BREAK);

        // Create logic variables
        Task[] tasks = new Task[100];
        int taskCount = 0;
        boolean readyForInputs = true;

        // Open a new scanner stream
        Scanner scanner = new Scanner(System.in);

        // Execution block
        while (readyForInputs) {
            
            // Initialise Scanner
            String input = scanner.nextLine();
            String markSlice = new String();
            String unmarkSlice = new String();

            if (input.length() >= MARK_STRING.length()) {
                markSlice = input.substring(0, MARK_STRING.length());
            }

            if (input.length() >= UNMARK_STRING.length()) {
                unmarkSlice = input.substring(0, UNMARK_STRING.length());
            }

            // Chatbot logic tree
            if (input.equals("out")) {
                //Display quit message
                System.out.println(LINE_BREAK + QUITTING_TEXT + LINE_BREAK);
                readyForInputs = false;

            } else if (input.equals("list")) {
                // Subtract 1 from taskCount to get the last index in the array where a Task is stored
                int lastIndex = taskCount - 1;
                
                // Style padding
                System.out.println(LINE_BREAK);
                System.out.println(LIST_HEADER);
                
                // If there are no tasks, say so in the program.
                if (taskCount < 1) {
                    System.out.println("No taskings!");
                }

                // Iteratively print the Tasks in the console in the order in which each Task was assigned
                for (int i = 0; i <= lastIndex; i++) {
                    String taskNumber = String.valueOf(tasks[i].getAssignmentOrder());
                    System.out.println(taskNumber + ". " + tasks[i].readBack());
                }

                // Style padding
                System.out.println(LINE_BREAK);

            } else if (!markSlice.isEmpty() && markSlice.equals(MARK_STRING)) {

                // Task marking logic, checking if a valid integer has been passed; Should be consolidated
                try {
                    int indexToMark = Integer.parseInt(input.substring(MARK_STRING.length()));

                    // Immutably mark a task as complete if a valid index has been passed
                    if (indexToMark > 0 && indexToMark <= taskCount) {
                        System.out.println(LINE_BREAK + "Okay, I've marked this task as done:\n");
                        Task markedTask = tasks[indexToMark-1].markAsComplete();
                        tasks[indexToMark-1] = markedTask;
                        System.out.println(tasks[indexToMark-1].readBack() + "\n" + LINE_BREAK);
                    } else {
                        System.out.println("There's no tasking with this index! Check back!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("What you said after" + MARK_STRING
                            + "is invalid; I need a valid index integer!");
                }
            } else if (!unmarkSlice.isEmpty() && unmarkSlice.equals(UNMARK_STRING)) {

                // Task marking logic, checking if a valid integer has been passed; Should be consolidated
                try {
                    int indexToUnmark = Integer.parseInt(input.substring(UNMARK_STRING.length()));

                    // Immutably mark a task as complete if a valid index has been passed
                    if (indexToUnmark > 0 && indexToUnmark <= taskCount) {
                        System.out.println(LINE_BREAK + "Okay, I've marked this task as incomplete:\n");
                        Task unmarkedTask = tasks[indexToUnmark -1].markAsIncomplete();
                        tasks[indexToUnmark -1] = unmarkedTask;
                        System.out.println(tasks[indexToUnmark -1].readBack() + "\n" + LINE_BREAK);
                    } else {
                        System.out.println("There's no tasking with this index! Check back!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("What you said after" + MARK_STRING
                            + "is invalid; I need a valid index integer!");
                }

            } else {
                //System.out.println(markSlice.isEmpty());
                //System.out.println(unmarkSlice.isEmpty());

                // Create new Task object, add to array, echo the addition and increment taskCount counter
                Task newTask = new Task(input, taskCount+1);
                tasks[taskCount] = newTask;
                System.out.println(LINE_BREAK + "added: " + tasks[taskCount].getName() + "\n" + LINE_BREAK);
                System.out.println();
                taskCount += 1;

            }
        }

        scanner.close();

    }

}
