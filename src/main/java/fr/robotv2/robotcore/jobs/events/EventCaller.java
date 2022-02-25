package fr.robotv2.robotcore.jobs.events;

import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.enums.JobAction;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Collection;

public record EventCaller(JobModule jobModule) {

    public void call(JobAction jobAction, Event event) {
        Collection<Job> jobs = jobModule.getJobs();
        switch (jobAction) {

            case HARVEST_PLANT -> {
                HarvestPlaceEvent harvestPlaceEvent = (HarvestPlaceEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(jobAction))
                        .forEach(job -> job.handleAction(harvestPlaceEvent.getPlayer(), harvestPlaceEvent.getSeed().getType(), jobAction));
            }

            case HARVEST_BREAK -> {
                HarvestBreakEvent harvestBreakEvent = (HarvestBreakEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(jobAction))
                        .forEach(job -> job.handleAction(harvestBreakEvent.getPlayer(), harvestBreakEvent.getBlock().getType(), jobAction));
            }

            case PLACE -> {
                BlockPlaceEvent blockPlaceEvent = (BlockPlaceEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(jobAction))
                        .forEach(job -> job.handleAction(blockPlaceEvent.getPlayer(), blockPlaceEvent.getBlock().getType(), jobAction));
            }

            case BREAK -> {
                BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(jobAction))
                        .forEach(job -> job.handleAction(blockBreakEvent.getPlayer(), blockBreakEvent.getBlock().getType(), jobAction));
            }

            case KILL_PLAYERS -> {
                PlayerKillByPlayerEvent playerKillByPlayerEvent = (PlayerKillByPlayerEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(jobAction))
                        .forEach(job -> job.handleAction(playerKillByPlayerEvent.getDamager(), EntityType.PLAYER, jobAction));
            }

            case KILL_ENTITIES -> {
                EntityKillByPlayerEvent entityKillByPlayerEvent = (EntityKillByPlayerEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(jobAction))
                        .forEach(job -> job.handleAction(entityKillByPlayerEvent.getPlayer(), entityKillByPlayerEvent.getEntity().getType(), jobAction));
            }

            case FISHING -> {
                PlayerFishEvent playerFishEvent = (PlayerFishEvent) event;
                if(playerFishEvent.getCaught() == null) return;
                if(playerFishEvent.getCaught() instanceof Item) {
                    jobs.stream()
                            .filter(job -> job.getActions().contains(jobAction))
                            .forEach(job -> job.handleAction(playerFishEvent.getPlayer(), ((Item) playerFishEvent.getCaught()).getItemStack().getType(), jobAction));
                } else {
                    jobs.stream()
                            .filter(job -> job.getActions().contains(jobAction))
                            .forEach(job -> job.handleAction(playerFishEvent.getPlayer(), playerFishEvent.getCaught().getType(), jobAction));
                }
            }
        }
    }
}
