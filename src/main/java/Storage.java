import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles saving the task list to the hard drive.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates storage using the given task-file path.
     *
     * @param filePath path of the file used to store tasks
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Replaces the task file with the current contents of the task list.
     *
     * @param tasks tasks to save
     * @throws IOException if the directory or file cannot be written
     */
    public void save(TaskList tasks) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("could not create data directory");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        }
    }
}
