import java.util.Scanner;

public class KDB {
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

        String banner =
                "mm   mm   mmmmmm    mmmmmmm\n"
              + "##  ##    ##    ##  ##    ##\n"
              + "##m##     ##    ##  ##    ##\n"
              + "#####     ##    ##  #######\n"
              + "##  ##m   ##    ##  ##    ##\n"
              + "##   ##m  ##mmm##   ##mmmm##";

        System.out.println("____________________________________________________________");
        System.out.println(banner);
        System.out.println("Hello! I'm KDB.");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");

                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] "
                            + tasks[i].getDescription());
                }
            } else if (command.startsWith("mark ")) {
                String[] parts = command.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;

                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [" + tasks[index].getStatusIcon() + "] "
                        + tasks[index].getDescription());
            } else if (command.startsWith("unmark ")) {
                String[] parts = command.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;

                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [" + tasks[index].getStatusIcon() + "] "
                        + tasks[index].getDescription());
            } else {
                tasks[taskCount] = new Task(command);
                taskCount++;

                System.out.println("added: " + command);
            }

            System.out.println("____________________________________________________________");
        }
    }
}