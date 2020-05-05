package rpgscene;

import rpgmain.*;
import gameobject.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Represents the world map.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class PlayScene extends RPGScene implements Tickable, Renderable {

    private Player player;
    private Enemy enemy1;

    private Map map; // represents the tile map
    private Group mapNode; // represents a node holding all Tiles in the map

    public PlayScene(Group root, Canvas canvas, Paint fill, String state, Player player) {
        super(root, canvas, fill, state);
        this.player = player;
        enemy1 = new Enemy(600, 450, 20, 20, 0, 0, Color.RED, "enemy1", 300, 40);

        // add the player and enemy's sprites to the map
        root.getChildren().addAll(player.getMapSprite(), enemy1.getMapSprite());

        mapNode = new Group();
        // add the node containing the map to the scene
        root.getChildren().add(mapNode);
        map = new Map(1500, 1500, mapNode);

    }

    /**
     * For every element in the map, gets the Tile at that element and compares it to the player's position.
     */
    public void tick() {
        Tile[][] tileMap = map.getTileMap();
        for (int row = 0; row < tileMap.length; row++) {
            for (int col = 0; col < tileMap[0].length; col++) {
                Tile tile = tileMap[row][col];
                if (tile.isSolid()) {
                    Rectangle tileObject = tile.getTile();
                    // if the player would move into the Tile after the map is updated, then shift the player's position
                    // back and set their translation vector to 0
                    if (player.getMapSprite().intersects(tileObject.getX() - player.getChangeInX(), tileObject.getY() - player.getChangeInY(), tileObject.getWidth(), tileObject.getHeight())) {
                        if (player.getChangeInX() > 0) {
                            player.decrementPseudoX();
                            player.setChangeInX(0);
                        }
                        if (player.getChangeInX() < 0) {
                            player.incrementPseudoX();
                            player.setChangeInX(0);
                        }
                        if (player.getChangeInY() > 0) {
                            player.decrementPseudoY();
                            player.setChangeInY(0);
                        }
                        if (player.getChangeInY() < 0) {
                            player.incrementPseudoY();
                            player.setChangeInY(0);
                        }
                    }
                }
            }
        }
        // if the player would move into the enemy after the map is updated, then shift the player's position back,
        // change its translation vector to 0, and set the state to "battle" to display a BattleScene
        if (player.getMapSprite().intersects(enemy1.getX() - player.getChangeInX(), enemy1.getY() - player.getChangeInY(), enemy1.getMapSprite().getWidth(), enemy1.getMapSprite().getHeight())) {
            setState("battle");
            player.setCurrentOpponent(enemy1);
            if (player.getChangeInX() > 0) {
                player.decrementPseudoX();
                player.setChangeInX(0);
            }
            if (player.getChangeInX() < 0) {
                player.incrementPseudoX();
                player.setChangeInX(0);
            }
            if (player.getChangeInY() > 0) {
                player.decrementPseudoY();
                player.setChangeInY(0);
            }
            if (player.getChangeInY() < 0) {
                player.incrementPseudoY();
                player.setChangeInY(0);
            }
        }

        // translate the enemy's sprite in the opposite direction of the player's translation vector
        enemy1.setX(enemy1.getX() - player.getChangeInX());
        enemy1.setY(enemy1.getY() - player.getChangeInY());

        // update the map with the player's position and translation vectors
        map.tick(player.getPseudoX(), player.getPseudoY(), (int) player.getChangeInX(), (int) player.getChangeInY());

        // set the player's translation vectors to 0
        player.setChangeInX(0);
        player.setChangeInY(0);
    }

    /**
     * Invokes the map's render method.
     */
    public void render() {
        map.render();
    }

    /**
     * Creates an EventHandler that updates the player's position if the user presses a certain key.
     */
    public void createKeyHandler() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Move every tile right if the key pressed is Left or A
                if (event.getCode().getName().equals("Left") || event.getCode().getName().equals("A")) {
                    player.decrementPseudoX();
                    player.setChangeInX(-20); // set translation in x direction to -20
                }
                // Move every tile left if the key pressed is Right or D
                else if (event.getCode().getName().equals("Right") || event.getCode().getName().equals("D")) {
                    player.incrementPseudoX();
                    player.setChangeInX(20); // set translation in x direction to 20
                }
                // Move every tile Up if the key pressed is Down or S
                else if (event.getCode().getName().equals("Down") || event.getCode().getName().equals("S")) {
                    player.incrementPseudoY();
                    player.setChangeInY(20); // set translation in y direction to 20
                }
                // Move every tile down if the key pressed is Up or W
                else if (event.getCode().getName().equals("Up") || event.getCode().getName().equals("W")) {
                    player.decrementPseudoY();
                    player.setChangeInY(-20); // set translation in y direction to -20
                }
            }
        });
    }

    /**
     * Creates an EventHandler that sets the state to "menu" whenever the player clicks their mouse.
     */
    public void createMouseHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setState("menu");
            }
        });
    }
}
