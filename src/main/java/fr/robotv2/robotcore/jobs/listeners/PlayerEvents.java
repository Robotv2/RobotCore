package fr.robotv2.robotcore.jobs.listeners;

import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.TaskUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerEvents(JobModule jobModule) implements Listener {

    private void loadPlayer(Player player) {
        JobData jobData = jobModule.getDataHandler().getData();
        TaskUtil.runTask(() -> {
            if(!jobModule.getPlayerManager().initializePlayer(player)) {
                player.kickPlayer(StringUtil.colorize("&cAn error occurred while restoring your jobs from the database."));
                return;
            }

            if(!jobModule.getLevelManager().initializePlayer(player)) {
                player.kickPlayer(StringUtil.colorize("&cAn error occurred while restoring your levels from the database."));
                return;
            }

            StringUtil.log("&fAll the data from the player &e" + player.getName() + " &fhas been restored successfully.");
        }, jobData.needAsync());
    }

    @EventHandler
    public void dataInitializer(PlayerJoinEvent event) {
        this.loadPlayer(event.getPlayer());
        jobModule.getPlayerManager().initScheduledSaving(event.getPlayer());
    }

    @EventHandler
    public void dataSaver(PlayerQuitEvent event) {
        jobModule.getPlayerManager().savePlayer(event.getPlayer());
    }
}
