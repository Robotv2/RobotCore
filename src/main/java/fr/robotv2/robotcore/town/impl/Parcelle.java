package fr.robotv2.robotcore.town.impl;

import org.bukkit.Location;

import java.util.UUID;

public class Parcelle {

    private final Location firstBound;
    private final Location secondBound;
    private final UUID owner;

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final int minZ;
    private final int maxZ;

    public Parcelle(Location firstBound, Location secondBound, UUID owner) {
        this.firstBound = firstBound;
        this.secondBound = secondBound;
        this.owner = owner;

        minX = Math.min(firstBound.getBlockX(), secondBound.getBlockX());
        minY = Math.min(firstBound.getBlockY(), secondBound.getBlockY());
        minZ = Math.min(firstBound.getBlockZ(), secondBound.getBlockZ());
        maxX = Math.max(firstBound.getBlockX(), secondBound.getBlockX());
        maxY = Math.max(firstBound.getBlockY(), secondBound.getBlockY());
        maxZ = Math.max(firstBound.getBlockZ(), secondBound.getBlockZ());
    }

    public UUID getOwner() {
        return owner;
    }

    public Location getFirstBound() {
        return firstBound;
    }

    public Location getSecondBound() {
        return secondBound;
    }

    public boolean contains(int x, int y, int z) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

}
