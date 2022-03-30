package fr.robotv2.robotcore.town.listeners;

import fr.robotv2.robotcore.shared.TaskUtil;
import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.data.TownData;
import fr.robotv2.robotcore.town.manager.TownManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerEvents extends TownListener {

    public PlayerEvents(TownModule townModule) {
        super(townModule);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        TownData data = getTownModule().getDataHandler().getData();
        TownManager manager = getTownModule().getTownManager();

        TaskUtil.runTask(() -> {

            UUID townUUID = data.getTownUUID(player.getUniqueId());
            System.out.println("Player " + player.getName() + " detected with the town: " + townUUID);

            if(townUUID != null && !manager.isLoaded(townUUID)) {
                manager.loadTown(townUUID);
            }

        }, data.needAsync());
    }
}
