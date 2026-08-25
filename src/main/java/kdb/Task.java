public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an unfinished task with the given description.
     *
     * @param description text that describes the task
     */
    
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns whether this task has been marked as done.
     *
     * @return true if the task is done
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Converts this task to the format used in the task data file.
     *
     * @return a pipe-separated representation of this task
     */
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
