import java.util.ArrayList;
import java.util.Iterator;

/**
 * Owns the collection of tasks and provides operations for changing it.
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /** Adds a task to the end of the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Returns the task at a zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Removes and returns the task at a zero-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Returns the number of tasks currently stored. */
    public int size() {
        return tasks.size();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
