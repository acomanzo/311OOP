package gameobject;

import javafx.scene.paint.Color;

/**
 * Represents a character that can be a part of a group of characters
 * @Author Tony Comanzo ICSI 311
 * Version 1.0
 */
public abstract class PartyMember extends RPGCharacter {

    // the next PartyMember in this PartyMember's group
    private PartyMember next;

    public PartyMember(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name) {
        this(x, y, width, height, changeInX, changeInY, color, name, null);
    }

    public PartyMember(double x, double y, double width, double height, double changeInX, double changeInY, Color color, String name, PartyMember next) {
        super(x, y, width, height, changeInX, changeInY, color, name);
        this.next = next;
    }

    /**
     * Returns the next PartyMember in this PartyMember's group.
     * @return the next PartyMember in this PartyMember's group.
     */
    public PartyMember getNext() {
        return next;
    }

    /**
     * Returns the size of this PartyMember's group. Starts at this member and continues until a member has no member
     * after it. Instead of returning the size of the whole party, returns how many members there are between this
     * member and the end of the party.
     * @return the number of members there are between this member and the end of the party.
     */
    public int getPartySize() {
        PartyMember member = this;
        int counter = 0;
        while(member != null) {
            counter++;
            member = member.next;
        }
        return counter;
    }

}
