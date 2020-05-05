package rpgscene.battleutil;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import rpgmain.*;

/**
 * Represents an object that manages what happens during a battle and receives inputs related to the user's interaction
 * with the GUI in the BattleScene. Maintains a map of players and a map of enemies and a queue of BattleActions. Also
 * creates attacks for any enemies.
 * @Author Tony Comanzo
 * Version 1.0
 */
public class BattleManager implements Handler, Tickable {

    // a hash table holding the players
    private LinkedHashMap<String, Battler> playerMap;
    // a list holding all players' ids
    private ArrayList<String> playerIds;
    // a hash table holding the enemies
    private LinkedHashMap<String, Battler> enemyMap;
    // a list holding all enemies' ids
    private ArrayList<String> enemyIds;
    // a queue of BattleActions
    private LinkedList<BattleAction> actions;
    // the state of this BattleManager
    private String state;
    // an index representing an enemy's id in enemyIds
    private int enemyPointer;

    public BattleManager(Battler playerLeader, Battler enemyLeader) {
        actions = new LinkedList<>();
        state = "waiting";
        playerMap = new LinkedHashMap<>();
        playerIds = new ArrayList<>();
        // populate the player map and id list
        while (playerLeader != null) {
            String id = playerLeader.getId();
            playerMap.put(id, playerLeader);
            playerIds.add(id);
            playerLeader = (Battler) playerLeader.getNext();
        }
        enemyMap = new LinkedHashMap<>();
        enemyIds = new ArrayList<>();
        // populate the enemy map and id list
        while (enemyLeader != null) {
            String id = enemyLeader.getId();
            enemyMap.put(id, enemyLeader);
            enemyIds.add(id);
            enemyLeader = (Battler) enemyLeader.getNext();
        }
        enemyPointer = 0;

        // if any players have 0 or less remaining health, remove them
        for (int i = 0; i < playerIds.size(); i++) {
            String id = playerIds.get(i);
            if (playerMap.get(id).getHealth() <= 0) {
                removeObject(id);
                playerIds.remove(i);
            }
        }
    }

    /**
     * Sets a timer to create a new enemy attack every 5 seconds.
     */
    @Override
    public void handle() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                makeEnemyAttack();
            }
        }, 5000, 5000);
    }

    /**
     * Removes an object from the object's hash table.
     * @param key key of which value is to be removed
     */
    @Override
    public void removeObject(String key) {
        playerMap.remove(key);
    }

    /**
     * Gets the head of the queue of BattleActions, actions, and executes that BattleAction if not null. If the
     * defender's health is 0 and it's an instance of class Enemy, then changes the state to "battle won". If the
     * defender is a an instance of class Player, then changes the state to "player dead".
     */
    @Override
    public void tick() {
        BattleAction battleAction = actions.poll();
        if (battleAction != null) {
            // execute the BattleAction, invoking the attacker's attack method and animation method, and the defender's
            // defend method
            battleAction.execute();
        }

        // check if any players are dead
        for (int i = 0; i < playerIds.size(); i++) {
            String id = playerIds.get(i);
            if (playerMap.get(id).isDead()) {
                setState("player dead");
                playerMap.remove(id);
                playerIds.remove(i);
            }
        }

        // check if any enemies are dead
        for (int i = 0; i < enemyIds.size(); i++) {
            String id = enemyIds.get(i);
            if (enemyMap.get(id).isDead()) {
                setState("battle won");
                enemyMap.remove(id);
                enemyIds.remove(i);
            }
        }
    }

    /**
     * Adds a BattleAction to the queue.
     * @param battleAction the BattleAction to add to the queue.
     */
    public void addBattleAction(BattleAction battleAction) {
        actions.offerLast(battleAction);
    }

    /**
     * Sets the state of this BattleManager.
     * @param state the state of this BattleManager.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the state of this BattleManager.
     * @return the state of this BattleManager.
     */
    public String getState() {
        return state;
    }

    /**
     * Picks a number between 0 and the size of the player hash table and get's that player's id from the list of ids.
     * Then, gets the enemy that enemyPointer represents and makes a new BattleAction where the defender is the player
     * and the attacker is the enemy. Then, adds that BattleAction to the queue and updates enemyPointer to the next
     * enemy.
     */
    private void makeEnemyAttack() {
        // pick a random number
        int index = ThreadLocalRandom.current().nextInt(0, playerMap.size());
        String id = playerIds.get(index);
        Battler defender = playerMap.get(id);

        id = enemyIds.get(enemyPointer);
        Battler attacker = enemyMap.get(id);
        addBattleAction(new BattleAction(attacker, defender));

        // if enemyPointer is greater than or equal to the number of enemies, set to 0, otherwise increment
        enemyPointer = (enemyPointer >= enemyMap.size()) ? enemyPointer = 0 : enemyPointer++;
    }
}
