package gameobject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a game object that has an x and y coordinates and can move on a map.
 * @Author Tony Comanzo
 * Version 1.0
 */
public abstract class DynamicGameObject {

    private double changeInX; // represents how much to translate in the x direction on update
    private double changeInY; // represents how much to translate in the y direction on update
    private Rectangle mapSprite; // represents how this game object looks on an in-game map

    public DynamicGameObject(double x, double y, double width, double height, double changeInX, double changeInY, Color color) {
        mapSprite = new Rectangle(x, y, width, height);
        mapSprite.setFill(color);
        this.changeInX = changeInX;
        this.changeInY = changeInY;
    }

    /**
     * Returns how much to translate this object in the x direction
     * @return how much to translate this object in the x direction
     */
    public double getChangeInX() {
        return changeInX;
    }

    /**
     * Returns how much to translate this object in the y direction
     * @return how much to translate this object in the y direction
     */
    public double getChangeInY() {
        return changeInY;
    }

    /**
     * Sets the change in the x direction
     * @param changeInX the change in the x direction
     */
    public void setChangeInX(int changeInX) {
        this.changeInX = changeInX;
    }

    /**
     * Sets the change in the y direction
     * @param changeInY the change in the y direction
     */
    public void setChangeInY(int changeInY) {
        this.changeInY = changeInY;
    }

    /**
     * Returns the top left x coordinate
     * @return the top left x coordinate
     */
    public double getX() {
        return mapSprite.getX();
    }

    /**
     * Returns the top left y coordinate
     * @return the top left y coordinate
     */
    public double getY() {
        return mapSprite.getY();
    }

    /**
     * Mutates the top left x coordinate
     * @param value the top left x coordinate
     */
    public void setX(double value) {
        mapSprite.setX(value);
    }

    /**
     * Mutates the top left y coordinate
     * @param value the top left y coordinate
     */
    public void setY(double value) {
        mapSprite.setY(value);
    }

    /**
     * Returns a Rectangle representing how this object appears drawn
     * @return a Rectangle representing how this object appears drawn
     */
    public Rectangle getMapSprite() {
        return mapSprite;
    }
}
