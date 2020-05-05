package gameobject;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import rpgscene.battleutil.Battler;

/**
 * Represents an in-game player.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class Player extends Battler {

    // Represents the coordinates of this player but not the coordinates of its sprite on the map.
    // These coordinates are updated to track where this Player is since when the player moves the player is not
    // actually moving: the map is getting translated to make an illusion.
    private double pseudoX;
    private double pseudoY;

    // represents this Player's current opponent in battle.
    private Enemy currentOpponent;

    public Player(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, int health, int attackPoints) {
        this(x, y, width, height, changeInX, changeInY, color, name, health, attackPoints, null);
    }

    public Player(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, int health, int attackPoints, PartyMember next) {
        super(x, y, width, height, changeInX, changeInY, color, name, health, attackPoints, next);
        pseudoX = x;
        pseudoY = y;
    }

    /**
     * Returns the x position of this player.
     * @return the x position of this player.
     */
    public double getPseudoX() {
        return pseudoX;
    }

    /**
     * Returns the y position of this player.
     * @return the y position of this player.
     */
    public double getPseudoY() {
        return pseudoY;
    }

    /**
     * Increments this Player's x position by 20 pixels.
     */
    public void incrementPseudoX() {
        pseudoX += 20;
    }

    /**
     * Decrements this Player's x position by 20 pixels.
     */
    public void decrementPseudoX() {
        pseudoX -= 20;
    }

    /**
     * Increments this Player's y position by 20 pixels.
     */
    public void incrementPseudoY() {
        pseudoY += 20;
    }

    /**
     * Decrements this Player's y position by 20 pixels.
     */
    public void decrementPseudoY() {
        pseudoY -= 20;
    }

    /**
     * Returns a reference to this Player's current opponent.
     * @return a reference to this Player's current opponent.
     */
    public Enemy getCurrentOpponent() {
        return currentOpponent;
    }

    /**
     * Changes the reference to this Player's current opponent.
     * @param opponent this Player's current opponent.
     */
    public void setCurrentOpponent(Enemy opponent) {
        currentOpponent = opponent;
    }

    /**
     * Finds a path to the defender and then translates this object to the defender and back to its origin.
     * Finds the defender's coordinates relative to the scene and this object's coordinates relative to the scene,
     * then translates the minimum distance to the defender in both the x and y directions.
     * @param defender the object this Battler is attacking. Is useful for getting the coordinates of the defender.
     */
    @Override
    public void animation(Battler defender) {
        // get the defender's battle sprite's bounds relative to the scene
        Node defenderNode = defender.getBattleSprite();
        Bounds defenderBounds = defenderNode.localToScene(defenderNode.getBoundsInLocal());
        // get this object's battle sprite's bounds relative to the scene
        Node attackerNode = getBattleSprite();
        Bounds attackerBounds = attackerNode.localToScene(attackerNode.getBoundsInLocal());

        // Duration = 2.5 seconds
        Duration duration = Duration.millis(1500);
        // Create new translate transition
        TranslateTransition transition = new TranslateTransition(duration, getBattleSprite());
        // Move in X axis to defender
        transition.setByX(defenderBounds.getMinX() - attackerBounds.getMinX());
        // Move in Y axis to defender
        transition.setByY(defenderBounds.getMinY() - attackerBounds.getMinY());
        // Go back to previous position once translation is over
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.play();
    }
}
