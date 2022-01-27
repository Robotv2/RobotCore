package fr.robotv2.robotcore.jobs.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class EntityKillByPlayerEvent extends PlayerEvent implements Cancellable {

    private final LivingEntity entity;
    private boolean cancel;
    public EntityKillByPlayerEvent(@NotNull Player who, LivingEntity livingEntity) {
        super(who);
        this.entity = livingEntity;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
