package fr.robotv2.robotcore.jobs.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HarvestPlaceEvent extends PlayerEvent implements Cancellable {

    private boolean cancel = false;
    private final Block farmLand;
    private final ItemStack seed;

    public HarvestPlaceEvent(@NotNull Player who, Block block, ItemStack seed) {
        super(who);
        this.farmLand = block;
        this.seed = seed;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public Block getFarmland() {
        return this.farmLand;
    }

    public ItemStack getSeed() {
        return seed;
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
