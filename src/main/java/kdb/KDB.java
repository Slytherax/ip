package kdb;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Coordinates the chatbot's user interaction, commands, tasks, and storage. */
public class KDB {
    /** Starts KDB and runs its command loop. */
    public static void main(String[] args) {
        Storage storage = new Storage("data/tasks.txt");
        Parser parser = new Parser();
        Ui ui = new Ui();
        TaskList tasks;
        try {
            tasks = storage.load();
        } catch (IOException e) {
            ui.showError("An error occurred while loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }

        ui.showWelcome();

        try (ui) {
            boolean isExit = false;

            while (!isExit) {
                String input = ui.readCommand();
                Parser.ParsedCommand parsedCommand = parser.parse(input);
                CommandType command = parsedCommand.getCommand();
                String arguments = parsedCommand.getArguments();

                ui.showDivider();

                try {
                    switch (command) {
                    case BYE:
                        ui.showBye();
                        isExit = true;
                        break;

                    case LIST:
                        ui.showTaskList(tasks);
                        break;

                    case MARK: {
                        int index = parseTaskIndex(arguments, "mark", tasks.size());
                        tasks.get(index).markAsDone();
                        saveTasksSafely(storage, tasks);
                        ui.showMarked(tasks.get(index));
                        break;
                    }

                    case UNMARK: {
                        int index = parseTaskIndex(arguments, "unmark", tasks.size());
                        tasks.get(index).markAsNotDone();
                        saveTasksSafely(storage, tasks);
                        ui.showUnmarked(tasks.get(index));
                        break;
                    }

                    case DELETE: {
                        int index = parseTaskIndex(arguments, "delete", tasks.size());
                        Task removed = tasks.remove(index);
                        saveTasksSafely(storage, tasks);
                        ui.showDeleted(removed, tasks.size());
                        break;
                    }

                    case TODO:
                        if (arguments.isEmpty()) {
                            throw new KDBException("The description of a todo cannot be empty.");
                        }
                        tasks.add(new Todo(arguments));
                        saveTasksSafely(storage, tasks);
                        ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;

                    case DEADLINE: {
                        if (arguments.isEmpty()) {
                            throw new KDBException("The description of a deadline cannot be empty.");
                        }

                        String[] parts = arguments.split(" /by ", 2);

                        if (parts.length < 2
                                || parts[0].trim().isEmpty()
                                || parts[1].trim().isEmpty()) {
                            throw new KDBException(
                                    "A deadline needs a description and date/time, "
                                    + "e.g. deadline return book /by 2/12/2019 1800.");
                        }

                        String description = parts[0].trim();
                        LocalDateTime deadlineDateTime = parseDate(parts[1].trim());

                        if (deadlineDateTime == null) {
                            throw new KDBException(
                                    "Invalid date/time. Please use d/M/yyyy HHmm, "
                                    + "e.g. 2/12/2019 1800.");
                        }

                        tasks.add(new Deadline(description, deadlineDateTime));
                        saveTasksSafely(storage, tasks);

                        ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;
                    }

                    case EVENT: {
                        if (arguments.isEmpty()) {
                            throw new KDBException("The description of an event cannot be empty.");
                        }
                        String[] fromParts = arguments.split(" /from ", 2);
                        if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
                            throw new KDBException(
                                    "An event needs a /from time, "
                                    + "e.g. event meeting /from Mon 2pm /to 4pm.");
                        }
                        String[] toParts = fromParts[1].split(" /to ", 2);
                        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
                            throw new KDBException(
                                    "An event needs a /to time, "
                                    + "e.g. event meeting /from Mon 2pm /to 4pm.");
                        }
                        tasks.add(new Event(fromParts[0].trim(), toParts[0].trim(), toParts[1].trim()));
                        saveTasksSafely(storage, tasks);
                        ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;
                    }

                    case UNKNOWN:
                    default:
                        ui.showUnknownCommandHelp();
                        break;
                    }
                } catch (KDBException e) {
                    ui.showError(e.getMessage());
                }

                ui.showDivider();
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
            throw new KDBException(
                    "Please tell me which task number to " + commandWord
                    + ", e.g. " + commandWord + " 2.");
        }

        int index;
        try {
            index = Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new KDBException(
                    "Task number needs to be a whole number, e.g. " + commandWord + " 2.");
        }

        if (index < 0 || index >= taskCount) {
            throw new KDBException(
                    "That task number doesn't exist. You currently have "
                    + taskCount + " task(s).");
        }

        return index;
    }

    private static void saveTasksSafely(Storage storage, TaskList tasks) {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks: " + e.getMessage());
        }
    }

    private static LocalDateTime parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("d/M/uuuu HHmm");

            return LocalDateTime.parse(dateStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
