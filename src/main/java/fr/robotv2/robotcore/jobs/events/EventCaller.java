package fr.robotv2.robotcore.jobs.events;

import fr.robotv2.robotcore.jobs.JobModuleManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.impl.Job;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Collection;

public record EventCaller(JobModuleManager jobModuleManager) {

    public void call(Action action, Event event) {
        Collection<Job> jobs = jobModuleManager.getJobs();
        switch (action) {

            case HARVEST_PLANT -> {
                HarvestPlaceEvent harvestPlaceEvent = (HarvestPlaceEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleAction(harvestPlaceEvent.getPlayer(), harvestPlaceEvent.getSeed().getType(), action));
            }

            case HARVEST_BREAK -> {
                HarvestBreakEvent harvestBreakEvent = (HarvestBreakEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleAction(harvestBreakEvent.getPlayer(), harvestBreakEvent.getBlock().getType(), action));
            }

            case PLACE -> {
                BlockPlaceEvent blockPlaceEvent = (BlockPlaceEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleAction(blockPlaceEvent.getPlayer(), blockPlaceEvent.getBlock().getType(), action));
            }

            case BREAK -> {
                BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleAction(blockBreakEvent.getPlayer(), blockBreakEvent.getBlock().getType(), action));
            }

            case KILL_PLAYERS -> {
                PlayerKillByPlayerEvent playerKillByPlayerEvent = (PlayerKillByPlayerEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleAction(playerKillByPlayerEvent.getDamager(), EntityType.PLAYER, action));
            }

            case KILL_ENTITIES -> {
                EntityKillByPlayerEvent entityKillByPlayerEvent = (EntityKillByPlayerEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleAction(entityKillByPlayerEvent.getPlayer(), entityKillByPlayerEvent.getEntity().getType(), action));
            }

            case FISHING -> {
                PlayerFishEvent playerFishEvent = (PlayerFishEvent) event;
                if(playerFishEvent.getCaught() == null) return;
                if(playerFishEvent.getCaught() instanceof Item) {
                    jobs.stream()
                            .filter(job -> job.getActions().contains(action))
                            .forEach(job -> job.handleAction(playerFishEvent.getPlayer(), ((Item) playerFishEvent.getCaught()).getItemStack().getType(), action));
                } else {
                    jobs.stream()
                            .filter(job -> job.getActions().contains(action))
                            .forEach(job -> job.handleAction(playerFishEvent.getPlayer(), playerFishEvent.getCaught().getType(), action));
                }
            }
        }
    }
}
