package gameobject;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Represents a square on the in-game map.
 * @Author Tony Comanzo
 * Version 1.0
 */
public class Tile {

    private int x; // this tile's x position on the map
    private int y; // this tile's y position on the map
    private int height; // the height of this tile
    private int width; // the width of this tile
    private Rectangle tile; // a Rectangle that represents how to draw this tile

    // a boolean representing if this tile is solid or not. Being solid means the player cannot walk through it.
    private boolean solid;

    public Tile (int x, int y, int height, int width, boolean solid, Paint paint) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.solid = solid;
        tile = new Rectangle(x, y, width, height);
        tile.setFill(paint);
        if (solid) {
            tile.setStroke(Color.BLACK);
            tile.setStrokeWidth(2);
        }
    }

    /**
     * Returns the Rectangle that represents how this tile is drawn.
     * @return the Rectangle that represents how this tile is draw.
     */
    public Rectangle getTile() {
        return tile;
    }

    /**
     * Returns true if this Tile is solid, false otherwise.
     * @return true if this Tile is solid, false otherwise.
     */
    public boolean isSolid() {
        return solid;
    }

}
