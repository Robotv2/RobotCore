package fr.robotv2.robotcore.town.impl;

public enum Flag {
    BLOCK_BREAK,
    BLOCK_PLACE,
    BLOCK_FROM_OUTSIDE,
    DROP,
    PICKUP,
    PVP,
    PVE,
    TNT,
    PISTON_EXTEND,
    PISTON_EXTRACT,
    INTERACT;

    public boolean isEnabledFor(Town town) {
        return town.getFlags().getOrDefault(this, false);
    }
}
