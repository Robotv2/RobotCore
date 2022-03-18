package fr.robotv2.robotcore.town.manager;

import fr.robotv2.robotcore.town.impl.Town;
import org.bukkit.OfflinePlayer;

import java.util.Optional;

public record PlayerManager(TownManager townManager) {

    public Optional<Town> getTown(OfflinePlayer offlinePlayer) {
        return townManager.getTowns().stream().filter(town -> town.isMember(offlinePlayer)).findFirst();
    }

    public boolean isInTown(OfflinePlayer offlinePlayer) {
        return getTown(offlinePlayer).isPresent();
    }

    public boolean kickPlayer(OfflinePlayer offlinePlayer) {
        Optional<Town> optionalTown = this.getTown(offlinePlayer);
        if(optionalTown.isPresent()) {
            Town town = optionalTown.get();
            town.removeMember(offlinePlayer);
            return true;
        }
        return false;
    }

    public boolean addPlayer(OfflinePlayer offlinePlayer, Town town) {
        if(!isInTown(offlinePlayer)) {
            town.addMember(offlinePlayer);
            return true;
        }
        return false;
    }
}
