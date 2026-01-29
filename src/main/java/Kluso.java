import java.util.Scanner;

public class Kluso {
    public static void main(String[] args) {
        
        String lineBreak = "_______________________"
                + "________________________________"
                + "_____\n";
        String newName = "Kluso";
        String greetingText = newName + " single ship, checking in!\n"
                + "Tasking?\n";
        String quittingText = newName + " is RTB!\n";
        
        //Print greeting text
        System.out.println(lineBreak
                + greetingText
                + lineBreak);

        // Create logic variables
        Task[] tasks = new Task[100];
        int taskCount = 0;
        boolean readyForInputs = true;

        // Open a new scanner stream
        Scanner scanner = new Scanner(System.in);

        // Execution block
        while (readyForInputs == true) {
            
            // Initialise Scanner
            String input = scanner.nextLine();

            if (input.equals("bye")) {

                //Display quit message
                System.out.println(lineBreak + quittingText + lineBreak);
                readyForInputs = false;

            } else if (input.equals("list")) {
                
                // Subtract 1 from taskCount to get the last index in the array where a Task is stored
                int lastIndex = taskCount - 1;
                
                // Style padding
                System.out.println(lineBreak);
                
                // Iteratively print the Tasks in the console in the order in which each Task was assigned
                for (int i = 0; i <= lastIndex; i++) {
                    String taskNumber = String.valueOf(tasks[i].getAssignmentOrder());
                    System.out.println(taskNumber + ". " + tasks[i].readBack());
                }

                // Style padding
                System.out.println(lineBreak);

            } else {

                // Create new Task object, add to array, echo the addition and increment taskCount counter
                Task newTask = new Task(input, taskCount+1);
                tasks[taskCount] = newTask;
                System.out.println(lineBreak + "added: " + tasks[taskCount].readBack() + "\n" + lineBreak);
                System.out.println();
                taskCount += 1;

            }
        }

        scanner.close();

    }

}
