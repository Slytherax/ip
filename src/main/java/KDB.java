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

            try {
                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");

                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    int index = parseTaskIndex(command, "mark", taskCount);
                    tasks[index].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index]);

                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    int index = parseTaskIndex(command, "unmark", taskCount);
                    tasks[index].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index]);

                } else if (command.equals("todo") || command.startsWith("todo ")) {
                    String description = command.length() > 4 ? command.substring(5).trim() : "";
                    if (description.isEmpty()) {
                        throw new KDBException("The description of a todo cannot be empty.");
                    }

                    tasks[taskCount] = new Todo(description);
                    taskCount++;

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                    String details = command.length() > 8 ? command.substring(9).trim() : "";
                    if (details.isEmpty()) {
                        throw new KDBException("The description of a deadline cannot be empty.");
                    }

                    String[] parts = details.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new KDBException("A deadline needs both a description and a /by time, e.g. deadline return book /by Sunday.");
                    }

                    tasks[taskCount] = new Deadline(parts[0].trim(), parts[1].trim());
                    taskCount++;

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else if (command.equals("event") || command.startsWith("event ")) {
                    String details = command.length() > 5 ? command.substring(6).trim() : "";
                    if (details.isEmpty()) {
                        throw new KDBException("The description of an event cannot be empty.");
                    }

                    String[] fromParts = details.split(" /from ", 2);
                    if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
                        throw new KDBException("An event needs a /from time, e.g. event meeting /from Mon 2pm /to 4pm.");
                    }

                    String[] toParts = fromParts[1].split(" /to ", 2);
                    if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
                        throw new KDBException("An event needs a /to time, e.g. event meeting /from Mon 2pm /to 4pm.");
                    }

                    tasks[taskCount] = new Event(fromParts[0].trim(), toParts[0].trim(), toParts[1].trim());
                    taskCount++;

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else {
                    throw new KDBException(
                            "I'm not sure what that means. Here's what I can do:\n"
                        + "  - todo <description>\n"
                        + "  - deadline <description> /by <time>\n"
                        + "  - event <description> /from <start> /to <end>\n"
                        + "  - list\n"
                        + "  - mark <task number>\n"
                        + "  - unmark <task number>\n"
                        + "  bye");
                }
            } catch (KDBException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("____________________________________________________________");
        }
    }

    private static int parseTaskIndex(String command, String commandWord, int taskCount) throws KDBException {
        String[] parts = command.split(" ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new KDBException("Please tell me which task number to " + commandWord + ", e.g. " + commandWord + " 2.");
        }

        int index;
        try {
            index = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new KDBException("Task number needs to be a whole number, e.g. " + commandWord + " 2.");
        }

        if (index < 0 || index >= taskCount) {
            throw new KDBException("That task number doesn't exist. You currently have " + taskCount + " task(s).");
        }

        return index;
    }
}