package rpgscene;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Represents a game over screen.
 * @Author Tony Comanzo
 * Version 1.0
 */
public class GameOverScene extends RPGScene {

    public GameOverScene(Group root, Canvas canvas, Paint fill, String state) {
        super(root, canvas, fill, state);
        Text message = new Text("You lose. Thanks for playing!");
        message.setX((canvas.getWidth() / 2) - 150);
        message.setY((canvas.getHeight() / 2) - 10);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Comic Sans MS", 30));
        root.getChildren().add(message);
    }

    /**
     * Creates an EventHandler that exits the program when the user clicks their mouse.
     */
    @Override
    void createMouseHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
    }

    /**
     * Creates an EventHandler that exits the program when the user presses a key.
     */
    @Override
    void createKeyHandler() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.exit(0);
            }
        });
    }
}
