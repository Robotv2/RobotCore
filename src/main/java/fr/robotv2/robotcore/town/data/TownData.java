package fr.robotv2.robotcore.town.data;

import fr.robotv2.robotcore.town.impl.Parcelle;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TownData {

    void init();
    void stop();

    UUID getTownUUID(UUID playerUUID);
    void setTown(UUID playerUUID, UUID townUUID);

    String getName(UUID townUUID);
    void setName(UUID townUUID, String name);

    OfflinePlayer getChef(UUID townUUID);
    void setChef(UUID townUUID, OfflinePlayer chef);

    List<OfflinePlayer> getMembers(UUID townUUID);
    void setMembers(List<OfflinePlayer> members, UUID townUUID);

    double getBank(UUID townUUID);
    void setBank(double value, UUID townUUID);

    Set<Chunk> getTerritories(UUID townUUID);
    void setTerritories(Set<Chunk> chunks, UUID townUUID);

    Set<Parcelle> getParcelles(UUID townUUID);
    void setParcelle(Set<Parcelle> parcelles, UUID townUUID);

    void createTown(UUID townUUID, UUID playerUUID, String name);
    void deleteTown(UUID townUUID);

    boolean needAsync();
}
