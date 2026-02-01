import java.util.Scanner;

public class Kluso {
    public static void main(String[] args) {
        
        String LINE_BREAK = "____________________________________________________________\n";
        String NEW_NAME = "Kluso";
        String GREETING_TEXT = NEW_NAME + " single ship, checking in!\n" + "Tasking?\n";
        String QUITTING_TEXT = NEW_NAME + " is RTB!\n";
        String LIST_HEADER = "Standby to read back your tasking order:";
        
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
                
                // Iteratively print the Tasks in the console in the order in which each Task was assigned
                if (taskCount < 1) {
                    
                }
                for (int i = 0; i <= lastIndex; i++) {
                    String taskNumber = String.valueOf(tasks[i].getAssignmentOrder());
                    System.out.println(taskNumber + ". " + tasks[i].readBack());
                }

                // Style padding
                System.out.println(LINE_BREAK);

            } else {

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
