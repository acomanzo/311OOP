package gameobject;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import rpgscene.battleutil.Battler;

/**
 * Represents an in-game enemy.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class Enemy extends Battler {

    public Enemy(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, int health, int attackPoints) {
        this(x, y, width, height, changeInX, changeInY, color, name, health, attackPoints, null);
    }

    public Enemy(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, int health, int attackPoints, PartyMember next) {
        super(x, y, width, height, changeInX, changeInY, color, name, health, attackPoints, next);
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
        // get this objects battle sprite's bounds relative to the scene
        Node attackerNode = getBattleSprite();
        Bounds attackerBounds = attackerNode.localToScene(attackerNode.getBoundsInLocal());

        // Duration = 2.5 seconds
        Duration duration = Duration.millis(1000);
        // Create new translate transition
        TranslateTransition transition = new TranslateTransition(duration, getBattleSprite());
        // Move in X axis to defender
        transition.setByX(defenderBounds.getMinX() - attackerBounds.getMinX());
        // Move in Y axis to defender
        transition.setByY(defenderBounds.getMinY() - attackerBounds.getMinY());
        // Go back to previous position once translation is over
        transition.setAutoReverse(true);
        transition.setCycleCount(2);

        // play the animation
        transition.play();
    }
}
