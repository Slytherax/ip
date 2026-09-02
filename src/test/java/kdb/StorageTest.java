package kdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests saving and loading tasks from the task file. */
class StorageTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void saveAndLoad_roundTripsAllTaskTypesAndStatus() throws IOException {
        Storage storage = new Storage(temporaryDirectory.resolve("tasks.txt").toString());
        TaskList original = new TaskList();
        Todo todo = new Todo("read book");
        todo.markAsDone();
        original.add(todo);
        original.add(new Deadline("return book", LocalDateTime.of(2019, 12, 2, 18, 0)));
        original.add(new Event("meeting", "Monday 2pm", "3pm"));

        storage.save(original);
        TaskList loaded = storage.load();

        assertEquals(3, loaded.size());
        assertEquals("[T][X] read book", loaded.get(0).toString());
        assertTrue(loaded.get(0).isDone());
        assertEquals("[D][ ] return book (by: Dec 02 2019, 6:00 pm)", loaded.get(1).toString());
        assertEquals("[E][ ] meeting (from: Monday 2pm to: 3pm)", loaded.get(2).toString());
    }

    @Test
    void loadMissingFile_returnsEmptyTaskList() throws IOException {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        assertEquals(0, storage.load().size());
    }

    @Test
    void save_createsParentDirectoryAndFile() throws IOException {
        Path file = temporaryDirectory.resolve("nested/tasks.txt");
        Storage storage = new Storage(file.toString());

        storage.save(new TaskList());

        assertTrue(Files.exists(file));
        assertEquals("", Files.readString(file));
    }

    @Test
    void loadMalformedTask_throwsIoException() throws IOException {
        Path file = temporaryDirectory.resolve("invalid.txt");
        Files.writeString(file, "X | 0 | unknown task\n");
        Storage storage = new Storage(file.toString());

        assertThrows(IOException.class, storage::load);
    }

    @Test
    void loadInvalidDeadlineDate_throwsIoException() throws IOException {
        Path file = temporaryDirectory.resolve("invalid-date.txt");
        Files.writeString(file, "D | 0 | return book | not-a-date\n");
        Storage storage = new Storage(file.toString());

        assertThrows(IOException.class, storage::load);
    }
}
