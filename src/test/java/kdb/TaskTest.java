package kdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/** Tests task status changes and task-specific display/file formats. */
class TaskTest {
    @Test
    void taskStartsIncomplete_andCanBeMarkedDoneAndNotDone() {
        Task task = new Task("read book");

        assertFalse(task.isDone());
        assertEquals("[ ] read book", task.toString());

        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("[X] read book", task.toString());

        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    void todoToFileFormat_containsTypeStatusAndDescription() {
        Todo todo = new Todo("join sports club");
        todo.markAsDone();

        assertEquals("T | 1 | join sports club", todo.toFileFormat());
        assertEquals("[T][X] join sports club", todo.toString());
    }

    @Test
    void deadlineFormatsDateForDisplayAndStorage() {
        Deadline deadline = new Deadline(
                "return book", LocalDateTime.of(2019, 12, 2, 18, 0));

        assertEquals("[D][ ] return book (by: Dec 02 2019, 6:00 pm)", deadline.toString());
        assertEquals("D | 0 | return book | 2/12/2019 1800", deadline.toFileFormat());
    }

    @Test
    void eventFormatsStartAndEndTimes() {
        Event event = new Event("project meeting", "Aug 6th 2pm", "4pm");

        assertEquals("[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)", event.toString());
        assertEquals("E | 0 | project meeting | Aug 6th 2pm | 4pm", event.toFileFormat());
    }
}
