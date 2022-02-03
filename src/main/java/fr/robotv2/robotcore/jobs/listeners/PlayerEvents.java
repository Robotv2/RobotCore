package fr.robotv2.robotcore.jobs.listeners;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.JobModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerEvents(JobModuleManager jobModuleManager) implements Listener {

    @EventHandler
    public void dataInitializer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(jobModuleManager.getPlugin(), () -> {
            if(!jobModuleManager.getPlayerManager().initializePlayer(player) || !jobModuleManager.getLevelManager().initializePlayer(player)) {
                event.getPlayer().kickPlayer(
                        StringUtil.colorize("&cAn error occurred while trying to restore your data. Please try to reconnect."));
            }
        });
    }
}
