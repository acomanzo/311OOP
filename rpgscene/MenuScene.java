package rpgscene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 * Represents a main menu screen.
 * @Author Tony Comanzo
 * Version 1.0
 */
public class MenuScene extends RPGScene {

    private Button button; // a button to switch states

    public MenuScene(Group root, Canvas canvas, Paint fill, String state) {
        super(root, canvas, fill, state);
        button = new Button("Click here to play!");
        button.setLayoutX(400);
        button.setLayoutY(300);

        // Creates an event handler for the play button that switches the state to "play"
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setState("play");
            }
        });

        // add the button to the scene
        root.getChildren().add(button);
    }

    /**
     * Creates an event handler that closes the program when the user presses a key.
     */
    public void createKeyHandler() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.exit(0);
            }
        });
    }

    /**
     * Creates an event handler that prints the user's mouse's x and y coordinates when clicked.
     */
    public void createMouseHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                System.out.println("(" + x + ", " + y + ")");
            }
        });
    }
}
