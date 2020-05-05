package rpgmain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A singleton class that manages a hash table of objects that update every iteration of the game loop.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class TickHandler implements Handler {

    private LinkedHashMap<String, Tickable> objectList; // the hash table

    private TickHandler() {
        objectList = new LinkedHashMap<>();
    }

    /**
     * An inner class holding a single instance of TickHandler. Used for getting an instance of TickHandler.
     */
    private static class TickHandlerHolder {
        private static final TickHandler INSTANCE = new TickHandler();
    }

    /**
     * Calls the tick method on each object in the hash table.
     */
    @Override
    public void handle() {
        for (Map.Entry<String, Tickable> entry : objectList.entrySet()) {
            entry.getValue().tick();
        }
    }

    /**
     * Adds an object to the hash table given the associated key.
     * @param key the object's key value
     * @param value the object
     */
    public void addObject(String key, Tickable value) {
        objectList.put(key, value);
    }

    /**
     * Removes an object from the object's hash table.
     * @param key key of which value is to be removed
     */
    @Override
    public void removeObject(String key) {
        objectList.remove(key);
    }

    /**
     * Returns an instance of a TickHandler
     * @return an instance of a TickHandler
     */
    public static TickHandler getInstance() {
        return TickHandlerHolder.INSTANCE;
    }
}
