package gameobject;

import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an in-game character.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public abstract class RPGCharacter extends DynamicGameObject {

    // a unique identifier
    private String id;

    // this character's actual name in-game
    private String name;

    public RPGCharacter(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name) {
        super(x, y, width, height, changeInX, changeInY, color);
        id = String.valueOf(System.currentTimeMillis() * ThreadLocalRandom.current().nextInt(1, 1000 + 1));
        this.name = name;
    }

    /**
     * Returns this character's unique identifier
     * @return this character's unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns this character's actual name
     * @return this character's actual name
     */
    public String getName() {
        return name;
    }
}
