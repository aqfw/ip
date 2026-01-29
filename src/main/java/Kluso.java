import java.util.Scanner;

public class Kluso {
    public static void main(String[] args) {
        String oldLogo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String lineBreak = "_______________________"
                + "________________________________"
                + "_____\n";
        String newName = "Kluso";
        String greetingText = newName + " single ship, checking in!\n"
                + "Tasking?\n";
        String quittingText = newName + " is RTB!\n";
        
        System.out.println(lineBreak
                + greetingText
                + lineBreak);

        Scanner scanner = new Scanner(System.in);
        boolean windowOpen = true;
        while (windowOpen == true) {

            String input = scanner.nextLine();

            if (input.equals("exit")) {
                System.out.println(lineBreak + quittingText + lineBreak);
                windowOpen = false;
            } else {
                System.out.println(lineBreak + input + "\n" + lineBreak);
                System.out.println();
            }
        }

        scanner.close();

    }

}
