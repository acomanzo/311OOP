package rpgmain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A singleton class that manages a hash table of objects that must be redrawn whenever the game updates.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public class RenderHandler implements Handler {

    private LinkedHashMap<String, Renderable> objectList; // the hash table

    private RenderHandler() {
        objectList = new LinkedHashMap<>();
    }

    /**
     * An inner class holding a single instance of RenderHandler. Used for getting an instance of RenderHandler.
     */
    private static class RenderHandlerHolder {
        private static final RenderHandler INSTANCE = new RenderHandler();
    }

    /**
     * Calls the render method on each object in the hash table.
     */
    @Override
    public void handle() {
        for (Map.Entry<String, Renderable> entry : objectList.entrySet()) {
            entry.getValue().render();
        }
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
     * Adds an object to the hash table given the associated key.
     * @param key the object's key value
     * @param value the object
     */
    public void addObject(String key, Renderable value) {
        objectList.put(key, value);
    }

    /**
     * Returns an instance of a RenderHandler
     * @return an instance of a RenderHandler
     */
    public static RenderHandler getInstance() {
        return RenderHandlerHolder.INSTANCE;
    }

}
