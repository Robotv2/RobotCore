package fr.robotv2.robotcore.jobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerKillByPlayerEvent extends Event implements Cancellable {

    private final Player victim;
    private final Player damager;
    private boolean cancel = false;

    public PlayerKillByPlayerEvent(@NotNull Player victim, Player damager) {
        this.damager = damager;
        this.victim = victim;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public Player getDamager() {
        return damager;
    }

    public Player getVictim() {
        return victim;
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
