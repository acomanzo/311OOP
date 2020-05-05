package gameobject;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import rpgmain.Renderable;

/**
 * Represents a tile map. A tile map represents where the user can move the player.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class Map implements Renderable {

    private Tile[][] tileMap; // a 2D array holding all the Tiles in this map. Represents the actual map.
    private int height; // the height of this map in pixels
    private int width; // the width of this map in pixels
    private int rowStart; // an index representing which row to start rendering tiles
    private int colStart; // an index representing which column to start rendering tiles
    private int rowEnd; // an index representing which row to stop rendering tiles
    private int colEnd; // an index representing which column to stop rendering tiles
    private Group parent; // the root node of all the tiles on the map

    public Map(int height, int width, Group parent) {
        this.height = height;
        this.width = width;
        rowStart = 0;
        colStart = 0;
        rowEnd = 8;
        colEnd = 10;
        this.parent = parent;
        init();
    }

    /**
     * Initializes this Map by initializing all the tiles. The tiles are defined by a predefined layout.
     */
    private void init() {
        // 2D array of chars representing the map. 1's equal a solid tile, 0's equal an open tile.
        char[][] textMap = {
                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1'},
                {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'}
        };

        // get the height and width of the tiles by dividing the height and width of this Map by the number of
        // rows and columns in the predefined layout.
        int tileWidth = this.width / textMap[0].length;
        int tileHeight = this.height / textMap.length;

        // initialize tileMap
        tileMap = new Tile[textMap.length][textMap[0].length];
        for (int row = 0; row < textMap.length; row++) {
            for (int col = 0; col < textMap[0].length; col++) {
                // make a solid tile and color it silver if this element in tileMap is a '1'
                if (textMap[row][col] == '1') {
                    tileMap[row][col] = new Tile(col * tileWidth, row * tileHeight, tileHeight, tileWidth, true, Color.SILVER);
                }
                // make an empty tile and color it clear if this element in tileMap is a '0'
                else if (textMap[row][col] == '0') {
                    tileMap[row][col] = new Tile(col * tileWidth, row * tileHeight, tileHeight, tileWidth, false, Color.color(1, 1, 1, 0));
                }
            }
        }
    }

    /**
     * Updates the position of each Tile in tileMap according to the player's position and translation vector.
     * @param playerX the player's x position
     * @param playerY the player's y position
     * @param changeInX how much the player moved in the x direction
     * @param changeInY how much the player moved in the y direction
     */
    public void tick(final double playerX, final double playerY, final int changeInX, final int changeInY) {

        // calculate where to start rendering the tiles in tileMap. Finds the player's position in each dimension
        // relative the actual size of the window, then uses that fraction to which column and which row the player
        // exists in in the tile map.
        // Subtracts 5 so that 5 rows and 5 columns are always rendered behind the player.
        colStart = (int) ((playerX / width) * tileMap[0].length - 5);
        rowStart = (int) ((playerY / height) * tileMap.length - 5);

        // calculates with the same logic as above, but makes sure to render 6 columns ahead of the player and
        // 4 rows ahead of the player.
        colEnd = (int) ((playerX / width) * tileMap[0].length + 6);
        rowEnd = (int) ((playerY / height) * tileMap.length + 4);

        // check if colStart, rowStart, colEnd, rowEnd go out of bounds and update them to be the end of the bounds if
        // they do.
        if (colStart < 0) {
            colStart = 0;
        }
        if (rowStart < 0) {
            rowStart = 0;
        }
        if (colEnd > tileMap[0].length) {
            colEnd = tileMap[0].length;
        }
        if (rowEnd > tileMap.length) {
            rowEnd = tileMap.length;
        }

        // Actually translate each tile in tileMap in the opposite direction of the player's translation vector.
        // Creates an illusion that the player is actually moving but in reality the map is being translated in the
        // opposite direction.
        for (int row = 0; row < tileMap.length; row++) {
            for (int col = 0; col < tileMap[0].length; col++) {
                Tile tile = tileMap[row][col];
                tile.getTile().setX(tile.getTile().getX() - changeInX);
                tile.getTile().setY(tile.getTile().getY() - changeInY);
            }
        }
    }

    /**
     * Removes all Tiles from the root node and then updates the root the correct tiles to draw and their updated
     * positions. Adds all tiles to the root within the range (colStart, rowStart) to (colEnd, rowEnd).
     */
    public void render() {
        // remove all children from index 0 to the size of the child set
        parent.getChildren().remove(0, parent.getChildren().size());

        // add all tiles within the bounds defined
        for (int row = rowStart; row < rowEnd; row++) {
            for(int col = colStart; col < colEnd; col++) {
                parent.getChildren().add(tileMap[row][col].getTile());
            }
        }
    }

    /**
     * Returns the parent root of the tiles in tileMap.
     * @return the parent root of the tiles in tileMap.
     */
    public Group getParent() {
        return parent;
    }

    /**
     * Returns a 2D array holding all tiles in this map.
     * @return a 2D array holding all tiles in this map.
     */
    public Tile[][] getTileMap() {
        return tileMap;
    }
}
