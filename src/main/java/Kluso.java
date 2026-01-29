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
        String greetingText = "Hello! I'm " + newName
                + "\nWhat can I do for you?\n";
        String quittingText = "Bye. Hope to see you again "
                + "soon!\n";
        
        
        System.out.println(lineBreak
                + greetingText
                + lineBreak
                + quittingText
                + lineBreak);
    }

}
