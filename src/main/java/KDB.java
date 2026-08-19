import java.util.ArrayList;
import java.util.Scanner;

public class KDB {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();

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

        try (Scanner scanner = new Scanner(System.in)) {
            boolean isExit = false;

            while (!isExit) {
                String input = scanner.nextLine();
                String[] split = input.split(" ", 2);
                String commandWord = split[0];
                String arguments = split.length > 1 ? split[1].trim() : "";

                CommandType command = CommandType.fromWord(commandWord);

                System.out.println("____________________________________________________________");

                try {
                    switch (command) {
                    case BYE:
                        System.out.println("Bye. Hope to see you again soon!");
                        isExit = true;
                        break;

                    case LIST:
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + "." + tasks.get(i));
                        }
                        break;

                    case MARK: {
                        int index = parseTaskIndex(arguments, "mark", tasks.size());
                        tasks.get(index).markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(index));
                        break;
                    }

                    case UNMARK: {
                        int index = parseTaskIndex(arguments, "unmark", tasks.size());
                        tasks.get(index).markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(index));
                        break;
                    }

                    case DELETE: {
                        int index = parseTaskIndex(arguments, "delete", tasks.size());
                        Task removed = tasks.remove(index);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removed);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        break;
                    }

                    case TODO:
                        if (arguments.isEmpty()) {
                            throw new KDBException("The description of a todo cannot be empty.");
                        }
                        tasks.add(new Todo(arguments));
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        break;

                    case DEADLINE: {
                        if (arguments.isEmpty()) {
                            throw new KDBException("The description of a deadline cannot be empty.");
                        }
                        String[] parts = arguments.split(" /by ", 2);
                        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                            throw new KDBException("A deadline needs both a description and a /by time, e.g. deadline return book /by Sunday.");
                        }
                        tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        break;
                    }

                    case EVENT: {
                        if (arguments.isEmpty()) {
                            throw new KDBException("The description of an event cannot be empty.");
                        }
                        String[] fromParts = arguments.split(" /from ", 2);
                        if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
                            throw new KDBException("An event needs a /from time, e.g. event meeting /from Mon 2pm /to 4pm.");
                        }
                        String[] toParts = fromParts[1].split(" /to ", 2);
                        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
                            throw new KDBException("An event needs a /to time, e.g. event meeting /from Mon 2pm /to 4pm.");
                        }
                        tasks.add(new Event(fromParts[0].trim(), toParts[0].trim(), toParts[1].trim()));
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        break;
                    }

                    case UNKNOWN:
                    default:
                        throw new KDBException(
                                "I'm not sure what that means. Here's what I can do:\n"
                              + "  todo <description>\n"
                              + "  deadline <description> /by <time>\n"
                              + "  event <description> /from <start> /to <end>\n"
                              + "  list\n"
                              + "  mark <task number>\n"
                              + "  unmark <task number>\n"
                              + "  delete <task number>\n"
                              + "  bye");
                    }
                } catch (KDBException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("____________________________________________________________");
            }
        }
    }

    /**
     * Parses and validates the task index for mark/unmark/delete commands.
     *
     * @param arguments  the text after the command word (e.g. "3")
     * @param commandWord "mark", "unmark", or "delete", used in error messages
     * @param taskCount  current number of tasks, used for bounds checking
     * @return zero-based task index
     * @throws KDBException if the index is missing, not a number, or out of range
     */
    private static int parseTaskIndex(String arguments, String commandWord, int taskCount) throws KDBException {
        if (arguments.isEmpty()) {
            throw new KDBException("Please tell me which task number to " + commandWord + ", e.g. " + commandWord + " 2.");
        }

        int index;
        try {
            index = Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new KDBException("Task number needs to be a whole number, e.g. " + commandWord + " 2.");
        }

        if (index < 0 || index >= taskCount) {
            throw new KDBException("That task number doesn't exist. You currently have " + taskCount + " task(s).");
        }

        return index;
    }
}