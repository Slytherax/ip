package kdb;

import java.util.Scanner;

/**
 * Handles interaction with the user through the console.
 */
public class Ui implements AutoCloseable {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    /** Creates a console user interface. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Displays the startup banner and greeting. */
    public void showWelcome() {
        String banner =
                "mm   mm   mmmmmm    mmmmmmm\n"
              + "##  ##    ##    ##  ##    ##\n"
              + "##m##     ##    ##  ##    ##\n"
              + "#####     ##    ##  #######\n"
              + "##  ##m   ##    ##  ##    ##\n"
              + "##   ##m  ##mmm##   ##mmmm##";

        showDivider();
        System.out.println(banner);
        System.out.println("Hello! I'm KDB.");
        System.out.println("What can I do for you?");
        showDivider();
    }

    /** Reads the next command from the user. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Displays the standard console divider. */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showUnknownCommandHelp() {
        showError(
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

    @Override
    public void close() {
        scanner.close();
    }
}
