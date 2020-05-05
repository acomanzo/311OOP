package rpgscene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a scene for this application and all of its functions.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public abstract class RPGScene extends Scene {

    // represents the state of all scenes in this application
    private static String state;

    // represents the root node that holds all visual objects in this Scene but not the Scene itself
    private Group root;

    // represents this scene's canvas
    private Canvas canvas;

    // a unique identifier for this scene
    private String id;

    public RPGScene(Group root, Canvas canvas, Paint fill, String state) {
        super(root, fill);
        createKeyHandler();
        createMouseHandler();
        this.state = state;

        this.root = root;
        this.canvas = canvas;
        this.id = String.valueOf(System.currentTimeMillis() * ThreadLocalRandom.current().nextInt(1, 1000 + 1));
    }

    /**
     * Creates an EventHandler for when the user presses a key.
     */
    abstract void createKeyHandler();

    /**
     * Creates an EventHandler for when the user clicks the mouse.
     */
    abstract void createMouseHandler();

    /**
     * Returns the state of all RPGScenes.
     * @return the state of all RPGScenes.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of all RPGScenes.
     * @param state the state of all RPGScenes.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns this Scene's canvas
     * @return this Scene's canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Returns this Scene's root node
     * @return this Scene's root ndoe
     */
    public Group getParentRoot() {
        return root;
    }

    /**
     * Returns this RPGScene's unique identifier.
     * @return this RPGScene's unique identifier.
     */
    public String getId() {
        return id;
    }
}
