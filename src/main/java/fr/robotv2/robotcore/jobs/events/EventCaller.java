package fr.robotv2.robotcore.jobs.events;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.impl.IJob;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Collection;

public record EventCaller(JobManager jobManager) {

    public void call(Action action, Event event) {
        Collection<IJob> jobs = jobManager.getJobs();
        switch (action) {

            case HARVEST_PLANT -> {
                HarvestPlaceEvent harvestPlaceEvent = (HarvestPlaceEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleHarvestPlant(harvestPlaceEvent));
            }

            case HARVEST_BREAK -> {
                HarvestBreakEvent harvestBreakEvent = (HarvestBreakEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleHarvestBreak(harvestBreakEvent));
            }

            case PLACE -> {
                BlockPlaceEvent blockPlaceEvent = (BlockPlaceEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handlePlace(blockPlaceEvent));
            }

            case BREAK -> {
                BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleBreak(blockBreakEvent));
            }

            case KILL_PLAYERS -> {
                PlayerKillByPlayerEvent playerKillByPlayerEvent = (PlayerKillByPlayerEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handlePlayerKillByPlayer(playerKillByPlayerEvent));
            }

            case KILL_ENTITIES -> {
                EntityKillByPlayerEvent entityKillByPlayerEvent = (EntityKillByPlayerEvent) event;
                jobs.stream()
                        .filter(job -> job.getActions().contains(action))
                        .forEach(job -> job.handleEntityKillByPlayer(entityKillByPlayerEvent));
            }
        }
    }
}
