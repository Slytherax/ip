import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

        return "[D]" + super.toString()
                + " (by: " + by.format(formatter) + ")";
    }

    @Override
    public String toFileFormat() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("d/M/uuuu HHmm");

        return "D | " + (isDone() ? "1" : "0")
                + " | " + getDescription()
                + " | " + by.format(formatter);
    }
}