package fr.robotv2.robotcore.jobs.events;

import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJobLevelUpEvent extends PlayerEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Job job;

    public PlayerJobLevelUpEvent(@NotNull Player who, Job job) {
        super(who);
        this.job = job;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Job getJob() {
        return job;
    }
}
