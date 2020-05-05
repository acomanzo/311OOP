package rpgscene;

import gameobject.Player;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import rpgmain.Renderable;
import rpgmain.Tickable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A singleton class that manages all of the RPGScenes.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class SceneManager implements Tickable, Renderable {

    private MenuScene menuScene;
    private PlayScene playScene;
    private BattleScene battleScene;
    private WinScene winScene;
    private GameOverScene gameOverScene;

    private RPGScene currentScene; // represents the current scene displayed

    private Player player;

    private String state; // represents the state of all scenes

    private String id; // a unique identifier for this SceneManager

    private SceneManager() {
        makeMenuScene();
        makePlayScene();
        battleScene = null;
        makeWinScene();
        makeGameOverScene();

        currentScene = menuScene;

        id = String.valueOf(System.currentTimeMillis() * ThreadLocalRandom.current().nextInt(1, 1000 + 1));
    }

    /**
     * An inner class holding a single instance of SceneManager. Used for getting an instance of SceneManager.
     */
    private static class SceneManagerHolder {
        private final static SceneManager INSTANCE = new SceneManager();
    }

    /**
     * Returns an instance of a SceneManager
     * @return an instance of a SceneManager
     */
    public static SceneManager getInstance() {
        return SceneManagerHolder.INSTANCE;
    }

    /**
     * Invokes a helper function and then updates the playScene.
     */
    @Override
    public void tick() {
        updateCurrentScene();
        playScene.tick();
    }

    /**
     * Invokes the playScene's render method.
     */
    @Override
    public void render() {
        playScene.render();
    }

    /**
     * Gets the current state of the scenes from the current scene and then updates the current scene accordingly.
     */
    private void updateCurrentScene() {
        state = currentScene.getState();
        // if the state is menu, switch to menuScene
        if (state.equals("menu")) {
            currentScene = menuScene;
        }
        // if the state is play, switch to playScene
        else if (state.equals("play")) {
            currentScene = playScene;
        }
        // if the state is battle, if battleScene is null, make a new BattleScene and switch to it
        else if (state.equals("battle")) {
            if (battleScene == null) {
                battleScene = makeBattleScene();
            }
            currentScene = battleScene;
        }
        // if the state if battle over, set battleScene to null and switch the state to play
        else if (state.equals("battle over")) {
            currentScene = playScene;
            battleScene = null;
            currentScene.setState("play");
        }
        // if the state is battle won, set the battleScene to null and switch the state to win
        else if (state.equals("battle won")) {
            currentScene = playScene;
            battleScene = null;
            currentScene.setState("win");
        }
        // if the state is win, switch the scene to winScene
        else if (state.equals("win")) {
            currentScene = winScene;
        }
        // if the state is player dead, set the battleScene to null and switch the state to game over
        else if (state.equals("player dead")) {
            currentScene = playScene;
            battleScene = null;
            currentScene.setState("game over");
        }
        // if the state is game over, switch the scene to gameOverScene
        else if (state.equals("game over")) {
            currentScene = gameOverScene;
        }
    }

    /**
     * Returns the current scene
     * @return the current scene
     */
    public RPGScene getCurrentScene() {
        return currentScene;
    }

    /**
     * Returns the menu scene
     * @return the menu scene
     */
    public MenuScene getMenuScene() {
        return menuScene;
    }

    /**
     * Returns the play scene
     * @return the play scene
     */
    public PlayScene getPlayScene() {
        return playScene;
    }

    /**
     * Returns the battle scene
     * @return the battle scene
     */
    public BattleScene getBattleScene() {
        return battleScene;
    }

    /**
     * Returns the win scene
     * @return the win scene
     */
    public WinScene getWinScene() {
        return winScene;
    }

    /**
     * Returns the gameOverScene
     * @return the gameOverScene
     */
    public GameOverScene getGameOverScene() {
        return gameOverScene;
    }

    /**
     * Creates menuScene
     */
    private void makeMenuScene() {
        Canvas canvas = new Canvas(1000, 750);
        Group root = new Group(canvas);
        // make a new scene 1000 x 750 that that has a red background and set the state to menu
        menuScene = new MenuScene(root, canvas, Color.RED, "menu");
    }

    /**
     * Creates playScene
     */
    private void makePlayScene() {
        Canvas canvas = new Canvas(1000, 750);
        Group root = new Group(canvas);

        // make the players
        Player joey = new Player(0, 0, 20, 20, 0, 0, Color.GREENYELLOW, "Joey", 200, 20);
        Player chewie = new Player(0, 0, 20, 20, 0, 0, Color.BROWN, "Chewie", 200, 20, joey);
        Player wedge = new Player(0, 0, 20, 20, 0, 0, Color.BLUE, "Wedge", 200, 20, chewie);
        // make the main player that the user controls on the map
        player = new Player(500, 750/2, 20, 20, 20, 20, Color.BLACK, "Buster", 200, 20, wedge);

        // make a new scene 1000 x 750 that has a green background with a reference to the player leader and set the
        // state to menu
        playScene = new PlayScene(root, canvas, Color.GREEN, "menu", player);
    }

    /**
     * Creates a new BattleScene
     * @return a new BattleScene
     */
    public BattleScene makeBattleScene() {
        Canvas canvas = new Canvas(1000, 750);
        Group root = new Group(canvas);
        // make a new scene 1000 x 750 that has a grey background, a reference to the player leader and the enemy
        // leader, and set the state to battle
        battleScene = new BattleScene(root, canvas, Color.GREY, "battle", player, player.getCurrentOpponent());
        return battleScene;
    }

    /**
     * Creates winScene
     */
    private void makeWinScene() {
        Canvas canvas = new Canvas(1000, 750);
        Group root = new Group(canvas);
        // make a new scene 1000 x 750 that has a gold background and set the state to menu
        winScene = new WinScene(root, canvas, Color.GOLD, "menu");
    }

    /**
     * Creates gameOverScene
     */
    private void makeGameOverScene() {
        Canvas canvas = new Canvas(1000, 750);
        Group root = new Group(canvas);
        // make a new scene 1000 x 750 that has a red background and set the state to menu
        gameOverScene = new GameOverScene(root, canvas, Color.RED, "menu");
    }

    /**
     * Returns this SceneManager's unique identifier
     * @return this SceneManager's unique identifier
     */
    public String getId() {
        return id;
    }
}
