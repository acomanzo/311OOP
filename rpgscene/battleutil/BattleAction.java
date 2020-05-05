package rpgscene.battleutil;

/**
 * Represents an attack action made by a character whilst in battle.
 * @Author Tony Comanzo
 * Version 1.0
 */
public class BattleAction {

    private Battler attacker; // the character attacking
    private Battler defender; // the character defending

    public BattleAction(Battler attacker, Battler defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    /**
     * Invokes the attacker's attack method and the defender's defend method.
     */
    public void execute() {
        attacker.attack(defender);
        defender.defend(attacker.getAttackPoints());
    }

    /**
     * Returns this BattleAction's defender.
     * @return this BattleAction's defender.
     */
    public Battler getDefender() {
        return defender;
    }

    /**
     * Returns this BattleAction's attacker.
     * @return this BattleAction's attacker.
     */
    public Battler getAttacker() {
        return attacker;
    }
}
