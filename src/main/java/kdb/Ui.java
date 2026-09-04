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
        System.out.println(welcomeMessage());
    }

    /** Returns the startup banner and greeting for display. */
    public static String welcomeMessage() {
        String banner =
                "mm   mm   mmmmmm    mmmmmmm\n"
              + "##  ##    ##    ##  ##    ##\n"
              + "##m##     ##    ##  ##    ##\n"
              + "#####     ##    ##  #######\n"
              + "##  ##m   ##    ##  ##    ##\n"
              + "##   ##m  ##mmm##   ##mmmm##";

        return banner + "\nHello! I'm Kdb.\nWhat can I do for you?";
    }

    /** Reads the next command from the user. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Displays the standard console divider. */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /** Displays the farewell message. */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /** Displays all tasks with their one-based list numbers. */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Displays tasks matching a search keyword. */
    public void showMatchingTasks(TaskList tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Displays confirmation after adding a task. */
    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /** Displays confirmation after marking a task done. */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /** Displays confirmation after marking a task incomplete. */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /** Displays confirmation after deleting a task. */
    public void showDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /** Displays an error message. */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Displays help for an unrecognized command. */
    public void showUnknownCommandHelp() {
        showError(unknownCommandHelp());
    }

    /** Returns help text for an unrecognized command. */
    public static String unknownCommandHelp() {
        return
                "I'm not sure what that means. Here's what I can do:\n"
              + "  todo <description>\n"
              + "  deadline <description> /by <time>\n"
              + "  event <description> /from <start> /to <end>\n"
              + "  list\n"
              + "  mark <task number>\n"
              + "  unmark <task number>\n"
              + "  delete <task number>\n"
              + "  find <keyword>\n"
              + "  bye";
    }

    /** Closes the console input scanner. */
    @Override
    public void close() {
        scanner.close();
    }
}
