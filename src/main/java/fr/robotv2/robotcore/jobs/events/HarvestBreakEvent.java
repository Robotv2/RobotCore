package fr.robotv2.robotcore.jobs.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class HarvestBreakEvent extends PlayerEvent implements Cancellable {

    private final Block block;
    private boolean cancel;

    public HarvestBreakEvent(@NotNull Player who, Block block) {
        super(who);
        this.block = block;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public Block getBlock() {
        return block;
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
