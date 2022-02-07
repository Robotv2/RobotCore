package fr.robotv2.robotcore.jobs.listeners;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.TaskUtil;
import fr.robotv2.robotcore.jobs.JobModuleManager;
import fr.robotv2.robotcore.jobs.data.JobData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerEvents(JobModuleManager jobModuleManager) implements Listener {

    private void loadPlayer(Player player) {
        JobData jobData = jobModuleManager.getDataHandler().getData();
        TaskUtil.runTask(() -> {
            if(!jobModuleManager.getPlayerManager().initializePlayer(player)) {
                player.kickPlayer(StringUtil.colorize("&cAn error occurred while restoring your jobs from the database."));
                return;
            }

            if(!jobModuleManager.getLevelManager().initializePlayer(player)) {
                player.kickPlayer(StringUtil.colorize("&cAn error occurred while restoring your levels from the database."));
                return;
            }

            StringUtil.log("&fAll the data from the player &e" + player.getName() + " &fhas been restored successfully.");
        }, jobData.needAsync());
    }

    @EventHandler
    public void dataInitializer(PlayerJoinEvent event) {
        this.loadPlayer(event.getPlayer());
        jobModuleManager.getPlayerManager().initScheduledSaving(event.getPlayer());
    }

    @EventHandler
    public void dataSaver(PlayerQuitEvent event) {
        jobModuleManager.getPlayerManager().savePlayer(event.getPlayer());
    }
}
