package rpgmain;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import rpgscene.*;

/**
 * Represents an application that runs the actual game and initializes necessary resources.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class Game extends Application {

    private SceneManager sceneManager; // manages the game's scenes
    private Stage stage; // represents the window the user plays in

    // handlers that make sure objects get updated and drawn in the game loop
    private TickHandler tickHandler;
    private RenderHandler renderHandler;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        sceneManager = SceneManager.getInstance();
        makeTickHandler();
        makeRenderHandler();
        this.stage = stage;
        stage.show(); // show the game window

        // create a game loop that will call Game's tick and render methods until the user exits
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                tick();
                render();
            }
        };

        timer.start(); // start the game loop

    }

    /**
     * Gets the current scene from the scene manager and the current state of all scenes from the current state.
     * Checks for certain states and updates the tick handler accordingly. Then updates the stage with the current
     * scene and invokes the TickHandler to update every item in its collection.
     */
    private void tick() {
        RPGScene currentScene = sceneManager.getCurrentScene();
        String state = currentScene.getState();

        // if the state is a battle, make a new BattleScene (if there isn't one) and add it to the TickHandler
        if (state.equals("battle")) {
            BattleScene temp = sceneManager.getBattleScene();
            if (temp == null) {
                temp = sceneManager.makeBattleScene();
                tickHandler.addObject(temp.getId(), temp);
            }
        }
        // if the battle is over, remove the BattleScene from the TickHandler
        else if (state.equals("battle over")) {
            BattleScene temp = sceneManager.getBattleScene();
            tickHandler.removeObject(temp.getId());
        }
        // if the user won, remove the BattleScene from the TickHandler
        else if (state.equals("battle won")) {
            BattleScene temp = sceneManager.getBattleScene();
            tickHandler.removeObject(temp.getId());
        }
        // if the user lost, remove the BattleScene from the TickHandler
        else if (state.equals("player dead")) {
            BattleScene temp = sceneManager.getBattleScene();
            tickHandler.removeObject(temp.getId());
        }

        stage.setScene(currentScene); // render the current scene in the window

        tickHandler.handle(); // tick every object in the TickHandler's collection
    }

    /**
     * Invokes the RenderHandler, rendering every object in its collection.
     */
    private void render() {
        renderHandler.handle();
    }

    /**
     * Gets a singleton TickHandler and adds the SceneManager to its collection.
     */
    private void makeTickHandler() {
        tickHandler = TickHandler.getInstance();
        tickHandler.addObject(sceneManager.getId(), sceneManager);
    }

    /**
     * Gets a singleton RenderHandler and adds the SceneManager to its collection.
     */
    private void makeRenderHandler() {
        renderHandler = RenderHandler.getInstance();
        renderHandler.addObject(sceneManager.getId(), sceneManager);
    }
}