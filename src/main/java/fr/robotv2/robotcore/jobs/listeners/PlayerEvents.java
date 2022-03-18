package fr.robotv2.robotcore.jobs.listeners;

import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.TaskUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerEvents(JobModule jobModule) implements Listener {

    private void loadPlayer(Player player) {
        if(!jobModule.getPlayerManager().initializePlayer(player)) {
            player.kickPlayer(StringUtil.colorize("&cAn error occurred while restoring your jobs from the database."));
            return;
        }

        if(!jobModule.getLevelManager().initializePlayer(player)) {
            player.kickPlayer(StringUtil.colorize("&cAn error occurred while restoring your levels from the database."));
            return;
        }

        StringUtil.log("&fAll the data from the player &e" + player.getName() + " &fhas been restored successfully.");
    }

    @EventHandler
    public void dataInitializer(PlayerJoinEvent event) {
        TaskUtil.runTask(() -> {
            this.loadPlayer(event.getPlayer());
        }, jobModule.getDataHandler().getData().needAsync());
        jobModule.getPlayerManager().initScheduledSaving(event.getPlayer());
    }

    @EventHandler
    public void dataSaver(PlayerQuitEvent event) {
        jobModule.getPlayerManager().savePlayer(event.getPlayer());
    }
}
