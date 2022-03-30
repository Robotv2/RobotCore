package fr.robotv2.robotcore.town.manager;

import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.TaskUtil;
import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.data.TownData;
import fr.robotv2.robotcore.town.impl.Town;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TownManager {

    private final Map<UUID, Town> towns = new ConcurrentHashMap<>();
    private final TownModule townModule;

    public TownManager(TownModule townModule) {
        this.townModule = townModule;
    }

    public boolean isLoaded(UUID townUUID) {
        return towns.containsKey(townUUID);
    }

    public void unloadTown(UUID townUUID, boolean save) {
        if(save) this.saveTown(getTown(townUUID));
        towns.remove(townUUID);
    }

    public void loadTown(Town town) {
        towns.put(town.getTownUUID(), town);
    }

    public void loadTown(UUID townUUID) {
        TownData data = townModule.getDataHandler().getData();
        TaskUtil.runTask(() -> {

            String name = data.getName(townUUID);
            double bank = data.getBank(townUUID);
            OfflinePlayer chef = data.getChef(townUUID);
            List<OfflinePlayer> members = data.getMembers(townUUID);
            Set<Chunk> territories = data.getTerritories(townUUID);

            Town town = new Town(townUUID, name, bank, chef, members, territories);
            towns.put(townUUID, town);

            StringUtil.log("&fData for the town &e" + name + "&f successfully loaded.");
            StringUtil.log(town.toString());

        }, data.needAsync());
    }

    public void saveTown(Town town) {
        TownData data = townModule.getDataHandler().getData();
        TaskUtil.runTask(() -> {

            UUID townUUID = town.getTownUUID();
            data.setName(townUUID, town.getName());
            data.setChef(townUUID, town.getChef());
            data.setMembers(town.getMembers(), townUUID);
            data.setBank(town.getBank(), townUUID);
            data.setTerritories(town.getTerritories(), townUUID);

        }, data.needAsync());
    }

    public void createTown(UUID townUUID, UUID chefUUID, String name) {
        TownData data = townModule.getDataHandler().getData();
        TaskUtil.runTask(() -> {

            Town town = data.createTown(townUUID, chefUUID, name);
            this.loadTown(town);

        }, data.needAsync());
    }

    //<-- GETTERS -->

    public Town getTown(UUID townUUID) {
        return towns.get(townUUID);
    }

    public Optional<Town> getTown(String townName) {
        return getTowns().stream()
                .filter(town -> town.getName().equalsIgnoreCase(townName))
                .findFirst();
    }

    public Collection<Town> getTowns() {
        return towns.values();
    }
}
