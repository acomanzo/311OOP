package rpgscene.battleutil;

import gameobject.PartyMember;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents an object that can battle.
 * @Author Tony Comanzo
 * Version 1.0
 */
public abstract class Battler extends PartyMember {
    // represents amount of health remaining
    private int health;
    // represents how much damage this battler deals on each attack
    private int attackPoints;
    // represents the state of this battler
    private String state;
    // represents how this battler should be drawn in battle
    private Rectangle battleSprite;

    public Battler(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, int health, int attackPoints) {
        this(x, y, width, height, changeInX, changeInY, color, name, health, attackPoints, null);
    }

    public Battler(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, int health, int attackPoints, PartyMember next) {
        super(x, y, width, height, changeInX, changeInY, color, name, next);
        this.health = health;
        this.attackPoints = attackPoints;
        state = "alive";
        battleSprite = new Rectangle(x, y, width, height);
        battleSprite.setFill(color);
    }

    /**
     * Invokes this Battler's animation method and passes the argument defender.
     * @param defender the object this Battler is attacking
     */
    public void attack(Battler defender) {
        animation(defender);
    }

    /**
     * Creates an animation for this Battler's attack. Is called every time the Battler attacks.
     * @param defender the object this Battler is attacking. Is useful for getting the coordinates of the defender
     *                 in order to make an appropriate animation.
     */
    public abstract void animation(Battler defender);

    /**
     * Reduces this Battler's health by the number of attack points.
     * @param attackPoints the damage this Battler received.
     */
    public void defend(int attackPoints) {
        health -= attackPoints;
        if (health <= 0) {
            setDead();
        }
    }

    /**
     * Returns this Battler's attack points.
     * @return this Battler's attack points.
     */
    public int getAttackPoints() {
        return attackPoints;
    }

    /**
     * Returns this Battler's remaining health.
     * @return this Battler's remaining health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the state of this Battler to "dead".
     */
    public void setDead() {
        state = "dead";
    }

    /**
     * Sets the state of this Battler to "alive".
     */
    public void resurrect() {
        state = "alive";
    }

    /**
     * Returns true if this Battler is dead, false otherwise.
     * @return true if this Battler is dead, false otherwise.
     */
    public boolean isDead() {
        if (state.equals("dead")) {
            return true;
        }
        return false;
    }

    /**
     * Returns this Battler's battle sprite.
     * @return this Battler's battle sprite.
     */
    public Rectangle getBattleSprite() {
        return battleSprite;
    }


}
