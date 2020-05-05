package rpgscene;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import gameobject.*;
import rpgmain.Tickable;
import rpgscene.battleutil.BattleAction;
import rpgscene.battleutil.BattleManager;
import rpgscene.battleutil.Battler;

/**
 * Represents what to draw on screen and how to update for a battle.
 * @Author Tony Comanzo
 * Version 1.0
 */
public class BattleScene extends RPGScene implements Tickable {

    private Battler partyLeader; // the party leader of the player
    private Battler enemyLeader; // the party leader of the enemies
    private VBox playerSprites; // container holding the players' sprites
    private VBox enemySprites; // container holding the enemies' sprites
    private VBox playerNames; // container holding the players' names
    private VBox bottomRight; // represents the container at the bottom right that holds the instructions
    private int playerNamePointer; // an index pointing to a player name
    private BattleManager battleManager;

    // represents the style for all menus
    private String menuStyle = "-fx-border-color: white;\n" +
            "-fx-border-width: 3;\n" +
            "-fx-border-style: solid;\n" +
            "-fx-background-color: linear-gradient(to bottom right, #745eff 10%, #081bff 65%, #000000 100%);";

    // represents the style for all containers holding sprites
    private String spriteStyle = "-fx-border-color: white;\n" +
            "-fx-border-width: 3;\n" +
            "-fx-border-style: solid;";

    public BattleScene(Group root, Canvas canvas, Paint fill, String state, Player partyLeader, Enemy enemyLeader) {
        super(root, canvas, fill, state);
        this.partyLeader = partyLeader;
        this.enemyLeader = enemyLeader;
        placeObjects(root);
        playerNamePointer = 0;
        battleManager = new BattleManager(partyLeader, enemyLeader);
        battleManager.handle(); // make a new timer to create attacks for the enemies
    }

    /**
     * Creates an EventHandler that sets the state to "battle over" whenever the player presses the escape key.
     * In other words, switches back to the world map.
     */
    @Override
    void createKeyHandler() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().getName().equals("Esc")) {
                    setState("battle over");
                }
            }
        });
    }

    /**
     * Creates an EventHandler that sets the state to "battle over" whenever the player clicks the mouse.
     * In other words, switches back to the world map.
     */
    @Override
    void createMouseHandler() {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setState("battle over");
            }
        });
    }

    /**
     * Invokes this BattleScene's BattlerManager's tick method, updates the RPGScene's state and BattleManager's state,
     * and loops through all of the names in playerNames to update the player's health.
     */
    @Override
    public void tick() {
        battleManager.tick();

        String battleManagerState = battleManager.getState();
        // if the player won, display the win screen and update the BattleManager's state
        if (battleManagerState.equals("battle won")) {
            setState("battle won");
            battleManager.setState("waiting");
        }
        // if the player dies, display the game over screen and update the BattleManager's state
        else if (battleManagerState.equals("player dead")) {
            setState("game over");
            battleManager.setState("waiting");
        }

        // update the menu to display each player's current health
        Battler temp = partyLeader;
        int counter = 0;
        while (temp != null) {
            ((Text) ((Group) playerNames.getChildren().get(counter)).getChildren().get(0)).setText(temp.getName() + " Health: " + temp.getHealth());
            counter = (counter >= 3) ? 0 : counter + 1;
            temp = (Battler) temp.getNext();
        }
    }

    /**
     * Calls helper functions to initialize all containers for this BattleScene, then adds them to this Scene and
     * focuses the playerNames container.
     * @param root the root for all objects in this scene.
     */
    private void placeObjects(Group root) {

        makePlayers();
        makeEnemySprites();
        makeBottomRight();

        root.getChildren().addAll(playerNames, bottomRight, playerSprites, enemySprites);

        // request focus on the container holding the players' names so that it will accept user input
        playerNames.requestFocus();
    }

    /**
     * Makes all containers related to the players.
     */
    private void makePlayers() {
        // design the container to hold the players' sprites
        playerSprites = new VBox();
        playerSprites.setPrefWidth(getCanvas().getWidth() * .5);
        playerSprites.setPrefHeight(getCanvas().getHeight() * .75);
        playerSprites.setLayoutX(getCanvas().getWidth() * .5);
        playerSprites.setLayoutY(0);
        playerSprites.setStyle(spriteStyle);
        playerSprites.setAlignment(Pos.CENTER);
        playerSprites.setSpacing(20);

        // design the container to hold the players' names
        playerNames = new VBox();
        playerNames.setPrefWidth(getCanvas().getWidth() * .5 + 50);
        playerNames.setPrefHeight(getCanvas().getHeight() * .25);
        playerNames.setLayoutX(0);
        playerNames.setLayoutY(getCanvas().getHeight() * .75);
        playerNames.setStyle(menuStyle);
        playerNames.setPadding(new Insets(20));
        playerNames.setSpacing(20);
        // make a new EventHandler for this container.
        playerNames.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // create a shadow effect to show the user which name they're pointing at in the menu
                DropShadow ds = new DropShadow();
                ds.setOffsetY(3.0f);
                ds.setColor(Color.color(0.9f, 0.9f, 0.4f));

                // When the user presses "Up" on the arrow keys, decrement playerNamePointer, remove the effect from
                // the old name and add the effect to the new name
                if (event.getCode().getName().equals("Up")) {
                    Group node = (Group) playerNames.getChildren().get(playerNamePointer);
                    Text name = (Text) node.getChildren().get(0);
                    name.setFill(Color.WHITE);
                    name.setEffect(null);
                    playerNamePointer--;
                    if (playerNamePointer < 0) {
                        playerNamePointer = 0;
                    }
                    node = (Group) playerNames.getChildren().get(playerNamePointer);
                    name = (Text) node.getChildren().get(0);
                    name.setFill(Color.YELLOW);
                    name.setEffect(ds);
                }
                // When the user presses "Down" on the arrow keys, increment playerNamePointer, remove the effect from
                // the old name and add the effect to the new name
                else if (event.getCode().getName().equals("Down")) {
                    Group node = (Group) playerNames.getChildren().get(playerNamePointer);
                    Text name = (Text) node.getChildren().get(0);
                    name.setFill(Color.WHITE);
                    name.setEffect(null);
                    playerNamePointer++;
                    if (playerNamePointer >= partyLeader.getPartySize()) {
                        playerNamePointer = partyLeader.getPartySize() - 1;
                    }
                    node = (Group) playerNames.getChildren().get(playerNamePointer);
                    name = (Text) node.getChildren().get(0);
                    name.setFill(Color.YELLOW);
                    name.setEffect(ds);
                }
                // When the user presses "Enter" on the keyboard, make visible the attack menu for that player
                else if (event.getCode().getName().equals("Enter")) {
                    Node node = ((Group) playerNames.getChildren().get(playerNamePointer)).getChildren().get(1);
                    node.setVisible(!node.isVisible());
                }
                // When the user presses "A" on the keyboard, if the attack menu is open, get the player represented by
                // playerNamePointer and make a new BattleAction where that player is the attacker and the enemy is
                // the defender, and also close the attack menu
                else if (event.getCode().getName().equals("A")) {
                    Node node = ((Group) playerNames.getChildren().get(playerNamePointer)).getChildren().get(1);
                    if (node.isVisible()) {
                        node.setVisible(!node.isVisible());
                        Battler attacker = partyLeader;
                        for (int i = 0; i < playerNamePointer; i++) {
                            attacker = (Battler) attacker.getNext();
                        }
                        Battler defender = enemyLeader;
                        battleManager.addBattleAction(new BattleAction(attacker, defender));
                    }
                }
            }
        });

        // starting with the player leader, make their name in the playerNames container and their attack menu, then
        // add the name and attack menu to playerNames and add that player's sprite to the playerSprites container
        Battler temp = partyLeader;
        while(temp != null) {
            // make a new root node for the text and attack menu
            Group node =  new Group();

            // design the aesthetic of the text and attack menu
            Text text = new Text(temp.getName() + " Health: " + temp.getHealth());
            text.setFill(Color.WHITE);
            VBox attackMenu = new VBox();
            attackMenu.setStyle(menuStyle);
            attackMenu.setPrefHeight(50);
            attackMenu.setPrefWidth(100);
            attackMenu.setVisible(false);
            attackMenu.setPadding(new Insets(5));
            attackMenu.setAlignment(Pos.CENTER);
            Text attack = new Text("A: Attack");
            attack.setFill(Color.WHITE);
            attackMenu.getChildren().add(attack);

            // add the text and menu to the root's child set
            node.getChildren().add(0, text);
            node.getChildren().add(1, attackMenu);

            // add the root to the container holding the player names
            playerNames.getChildren().add(node);
            // add the player's sprite to the container holding the player sprites
            playerSprites.getChildren().add(temp.getBattleSprite());

            // get the next player
            temp = (Battler) temp.getNext();
        }
    }

    /**
     * Make the container that holds the enemies' sprites.
     */
    private void makeEnemySprites() {
        // design the container
        enemySprites = new VBox();
        enemySprites.setPrefWidth(getCanvas().getWidth() * .5);
        enemySprites.setPrefHeight(getCanvas().getHeight() * .75);
        enemySprites.setLayoutX(0);
        enemySprites.setLayoutY(0);
        enemySprites.setStyle(spriteStyle);
        enemySprites.setAlignment(Pos.CENTER);

        // starting with the enemy leader, add all enemies' sprites to the container
        Battler temp = enemyLeader;
        while(temp != null) {
            enemySprites.getChildren().add(temp.getBattleSprite());
            temp = (Battler) temp.getNext();
        }
    }

    /**
     * Makes the container at the bottom right of the screen that holds the instructions.
     */
    private void makeBottomRight() {
        // design the container
        bottomRight = new VBox();
        bottomRight.setPrefWidth(getCanvas().getWidth() - (getCanvas().getWidth() * .5 + 50));
        bottomRight.setPrefHeight(getCanvas().getHeight() * .25);
        bottomRight.setLayoutX(getCanvas().getWidth() * .5 + 50);
        bottomRight.setLayoutY(getCanvas().getHeight() * .75);
        bottomRight.setStyle(menuStyle);
        bottomRight.setAlignment(Pos.CENTER);
        bottomRight.setSpacing(20);

        Text line1 = new Text("Use the arrow keys to select a name.");
        line1.setFill(Color.WHITE);
        Text line2 = new Text("Then, press enter to open the attack menu.");
        line2.setFill(Color.WHITE);
        Text line3 = new Text("Then, press A on the keyboard to attack!");
        line3.setFill(Color.WHITE);
        Text line4 = new Text("The enemy will attack every 5 seconds. Good luck!");
        line4.setFill(Color.WHITE);

        // add all the instructions to the container
        bottomRight.getChildren().addAll(line1, line2, line3, line4);
    }
}
