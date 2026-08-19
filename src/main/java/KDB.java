import java.util.Scanner;

public class KDB {
    public static void main(String[] args) {

        String[] storage = new String[100];
        boolean[] isDone = new boolean[100];
        int taskCount = 0;

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

                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println((i + 1) + "." + status + " " + storage[i]);
                }

            } else if (command.startsWith("mark ")) {
                String[] parts = command.split(" ");
                int taskNumber = Integer.parseInt(parts[1]);
                int index = taskNumber - 1;

                isDone[index] = true;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [X] " + storage[index]);

            } else if (command.startsWith("unmark ")) {
                String[] parts = command.split(" ");
                int taskNumber = Integer.parseInt(parts[1]);
                int index = taskNumber - 1;

                isDone[index] = false;
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [ ] " + storage[index]);

            } else {
                storage[taskCount] = command;
                isDone[taskCount] = false;
                taskCount++;

                System.out.println("added: " + command);
            }

            System.out.println("____________________________________________________________\n");
        }
    }
}