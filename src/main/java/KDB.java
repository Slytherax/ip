import java.util.Scanner;

public class KDB {
    public static void main(String[] args) {

        String[] storage = new String[100];

        String banner =
                "mm   mm   mmmmmm    mmmmmmm\n"
              + "##  ##    ##    ##  ##    ##\n"
              + "##m##     ##    ##  ##    ##\n"
              + "#####     ##    ##  #######\n"
              + "##  ##m   ##    ##  ##    ##\n"
              + "##   ##m  ##mmm##   ##mmmm##\n";

        System.out.println("____________________________________________________________");
        System.out.println(banner);
        System.out.println("Hello! I'm KDB.");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            System.out.println("____________________________________________________________\n");

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n");
                System.out.println("____________________________________________________________\n");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < storage.length; i++) {
                    if (storage[i] != null) {
                        System.out.println((i + 1) + ". " + storage[i]);
                    }
                }
            } else {
                int i = 0;
                while (storage[i] != null){
                    i++;
                }
                storage[i] = command;
                System.out.println("added: " + command);
                
            }
            System.out.println("____________________________________________________________\n");
        }
    }
}