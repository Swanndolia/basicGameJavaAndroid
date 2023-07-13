package dev.swanndolia.idlemmorpg.tools.animations;

import java.io.Serializable;

public class Bodypart implements Serializable {

    private String name = "none";
    private SLOTS slot;

    public Bodypart() {
    }

    public Bodypart(SLOTS slot, String name) {
        this.name = name;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SLOTS getSlot() {
        return slot;
    }

    public void setSlot(SLOTS slot) {
        this.slot = slot;
    }

    public enum SLOTS {
        body,
        head,
        arms,
        torso,
        legs,
        boots,
        cape
    }
}
