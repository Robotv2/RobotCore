package fr.robotv2.robotcore.town.impl;

import fr.robotv2.robotcore.shared.StringUtil;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Town {
    private final UUID townUUID;
    private String name;
    private double bank;

    private OfflinePlayer chef;
    private final List<OfflinePlayer> members;
    private final Map<Flag, Boolean> flags = new HashMap<>();

    private final Set<Chunk> territories;
    private final Set<Parcelle> parcelles;

    public Town(UUID townUUID, String name, double bank, OfflinePlayer chef, List<OfflinePlayer> members, Set<Chunk> territories) {
        this.townUUID = townUUID;
        this.name = name;
        this.bank = bank;
        this.chef = chef;
        this.members = members;
        this.territories = territories;
        this.parcelles = Collections.emptySet();
    }

    //<-- GETTERS -->

    public UUID getTownUUID() {
        return townUUID;
    }

    public String getName() {
        return name;
    }

    public OfflinePlayer getChef() {
        return chef;
    }

    public Set<Chunk> getTerritories() {
        return territories;
    }

    public Set<Parcelle> getParcelles() {
        return parcelles;
    }

    public List<OfflinePlayer> getMembers() {
        return members;
    }

    public Map<Flag, Boolean> getFlags() {
        return flags;
    }

    //<-- SETTERS -->

    public void setName(String name) {
        this.name = name;
    }

    public void setChef(OfflinePlayer chef) {
        this.chef = chef;
    }

    public void setFlag(Flag flag, boolean value) {
        flags.put(flag, value);
    }

    //<-- TERRITORIES -->

    public boolean isTerritory(Chunk chunk) {
        return this.territories.contains(chunk);
    }

    public void addChunk(Chunk chunk) {
        this.territories.add(chunk);
    }

    public void removeChunk(Chunk chunk) {
        this.territories.remove(chunk);
    }

    //<-- MEMBERS -->

    public boolean isChef(OfflinePlayer offlinePlayer) {
        return Objects.equals(this.chef.getUniqueId(), offlinePlayer.getUniqueId());
    }

    public boolean isMember(OfflinePlayer offlinePlayer) {
        return this.members.contains(offlinePlayer) || this.isChef(offlinePlayer);
    }

    public void addMember(OfflinePlayer offlinePlayer) {
        if(isMember(offlinePlayer)) return;
        this.members.add(offlinePlayer);
    }

    public void removeMember(OfflinePlayer offlinePlayer) {
        this.members.remove(offlinePlayer);
    }

    //<-- BANK -->

    public double getBank() {
        return bank;
    }

    public void addToBank(double value) {
        this.bank = bank + value;
    }

    public void removeFromBank(double value) {
        this.bank = bank - value;
    }

    public void setBank(double value) {
        this.bank = value;
    }

    //ACTION

    public void sendMessage(String message) {
        for(OfflinePlayer offlinePlayer : getMembers()) {
            Player player = offlinePlayer.getPlayer();
            if(player != null && player.isOnline()) {
                StringUtil.sendMessage(player, message, false);
            }
        }
    }

    @Override
    public String toString() {
        return "{town:" +
                "{UUID:" + townUUID + "};" +
                "{NAME:" + name + "};" +
                "{BANK:" + bank + "};" +
                "{CHEF:" + chef.getUniqueId() + "};" +
                "{MEMBERS:" + members.toString() + "};}";
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Town town))
            return false;
        return Objects.equals(this.getTownUUID(), town.getTownUUID());
    }
}
