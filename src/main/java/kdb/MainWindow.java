package kdb;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controls the Kdb JavaFX window.
 */
public class MainWindow {
    
    private final Kdb kdb = new Kdb();
    private final Image botImage = loadImage("/images/kdb.png");
    private final Image userImage = loadImage("/images/hal.png");

    @FXML
    private TextField userInput;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    /** Adds Kdb's initial greeting after the FXML controls are created. */
    @FXML
    private void initialize() {
        dialogContainer.getChildren().add(
                new DialogBox(Ui.welcomeMessage(), botImage, false));
        dialogContainer.heightProperty().addListener(
                (observable, oldHeight, newHeight) -> scrollToBottom());
        userInput.requestFocus();
    }
    /**
     * Displays the user's message when Send or Enter is used.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (!input.isBlank()) {
            String response = kdb.executeCommand(input);

            dialogContainer.getChildren().add(new DialogBox(input, userImage, true));
            dialogContainer.getChildren().add(new DialogBox(response, botImage, false));

            userInput.clear();
            scrollToBottom();

            if (input.trim().equalsIgnoreCase("bye")) {
                PauseTransition closeDelay = new PauseTransition(Duration.seconds(3));
                closeDelay.setOnFinished(event -> Platform.exit());
                closeDelay.play();
            }
        }
    }

    /** Scrolls the conversation area to the newest message. */
    private void scrollToBottom() {
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    /** Loads an avatar from the classpath, returning null when it is absent. */
    private Image loadImage(String path) {
        var stream = MainWindow.class.getResourceAsStream(path);
        return stream == null ? null : new Image(stream);
    }

    
}
