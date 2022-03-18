package fr.robotv2.robotcore.town.listeners;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.impl.Town;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Optional;

public abstract class TownListener implements Listener {

    private TownModule townModule;
    public TownListener(TownModule townModule) {
        townModule.getPlugin().getServer().getPluginManager().registerEvents(this, townModule.getPlugin());
    }

    public TownModule getTownModule() {
        return townModule;
    }


    public boolean needCancel(Chunk chunk, Player player, Flag flag) {
        Optional<Town> optionalTown = getTownModule().getChunkManager().getTown(chunk);

        if (optionalTown.isEmpty())
            return false;

        Town town = optionalTown.get();
        town.isMember(player);

        if(town.isMember(player))
            return false;

        return !flag.isEnabledFor(town);
    }
}
