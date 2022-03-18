package fr.robotv2.robotcore.shared.serializer;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerSerializer {

    public static List<OfflinePlayer> getOfflinePlayersFromUuid(Collection<UUID> uuids) {
        return uuids.stream().map(Bukkit::getOfflinePlayer).collect(Collectors.toList());
    }

    public static List<OfflinePlayer> getOfflinePlayersFromString(Collection<String> uuidsStr) {
        List<UUID> uuids = uuidsStr.stream().map(UUID::fromString).collect(Collectors.toList());
        return getOfflinePlayersFromUuid(uuids);
    }

    public static OfflinePlayer getOfflinePlayerFromString(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return Bukkit.getOfflinePlayer(uuid);
    }
}
