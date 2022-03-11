package fr.robotv2.robotcore.jobs.listeners;

import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.events.EntityKillByPlayerEvent;
import fr.robotv2.robotcore.jobs.events.HarvestBreakEvent;
import fr.robotv2.robotcore.jobs.events.HarvestPlaceEvent;
import fr.robotv2.robotcore.jobs.impl.job.JobAction;
import fr.robotv2.robotcore.jobs.util.MatUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public record SystemEvents(JobModule jobModule) implements Listener {

    /**
     * Handle event related to planting seeds.
     */
    @EventHandler
    public void plantSeed(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        //Check if the clicked block is the face up of a farmland.
        if (block == null
                || block.getType() != Material.FARMLAND
                || block.getFace(block) != BlockFace.UP
                || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        //Check if he has a seed in his hand.
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInSecondHand = player.getInventory().getItemInOffHand();

        if (!MatUtil.isSeed(itemInMainHand.getType())
                && !MatUtil.isSeed(itemInSecondHand.getType()))
            return;

        ItemStack seed = MatUtil.isSeed(itemInMainHand.getType()) ? itemInMainHand : itemInSecondHand;
        HarvestPlaceEvent harvestPlaceEvent = new HarvestPlaceEvent(player, block, seed);

        jobModule.getCaller()
                .call(JobAction.HARVEST_PLANT, harvestPlaceEvent);

        if(harvestPlaceEvent.isCancelled())
            event.setCancelled(true);
    }

    /**
     * Handle event related to breaking crops.
     */
    @EventHandler
    public void onBreakOfCrops(BlockBreakEvent event) {
        if(jobModule.getBlockManager().hasBeenPlaced(event.getBlock()))
            return;

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(MatUtil.isFullyGrown(block)) {
            HarvestBreakEvent harvestBreakEvent = new HarvestBreakEvent(player, block);
            jobModule.getCaller().call(JobAction.HARVEST_BREAK, harvestBreakEvent);

            if(harvestBreakEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Handle event related to breaking block.
     */
    @EventHandler
    public void onBreakOfBlocks(BlockBreakEvent event) {
        if(jobModule.getBlockManager().hasBeenPlaced(event.getBlock()))
            return;
        jobModule.getCaller().call(JobAction.BREAK, event);
    }

    /**
     * Handle event related to place player.
     */
    @EventHandler
    public void onPlaceOfBlocks(BlockPlaceEvent event) {
        jobModule.getCaller().call(JobAction.PLACE, event);
    }

    /**
     * Handle events related to killing players / entities.
     */
    @EventHandler
    public void killEntity(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player player))
            return;

        if(!(event.getEntity() instanceof LivingEntity livingEntity))
            return;

        if(event.getFinalDamage() >= livingEntity.getHealth()) {
            EntityKillByPlayerEvent entityKillByPlayerEvent = new EntityKillByPlayerEvent(player, livingEntity);
            jobModule.getCaller().call(JobAction.KILL, entityKillByPlayerEvent);
            if(entityKillByPlayerEvent.isCancelled()) event.setCancelled(true);
        }
    }

    /**
     * Handle event related to fishing.
     */
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if(event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        jobModule.getCaller().call(JobAction.FISHING, event);
    }
}
