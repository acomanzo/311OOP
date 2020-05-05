package rpgmain;

/**
 * Represents an object that manages a hash table of objects and its functions. All classes that implement this
 * interface should have a LinkedHashMap that uses Strings as keys.
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public interface Handler {

    /**
     * Handles one or all objects in this object's hash table.
     */
    void handle();

    /**
     * Removes an object from the object's hash table.
     * @param key key of which value is to be removed
     */
    void removeObject(String key);
}
