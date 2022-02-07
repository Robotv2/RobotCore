package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.JobModuleManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockManager implements Listener {

    private final String METADATA_TEXT = "has-been-broken";
    private final JobModuleManager jobModuleManager;
    public BlockManager(JobModuleManager jobModuleManager) {
        this.jobModuleManager = jobModuleManager;
        jobModuleManager.getPlugin().getServer().getPluginManager().registerEvents(this, jobModuleManager.getPlugin());
    }

    public boolean hasBeenPlaced(Block block) {
        return block.hasMetadata(METADATA_TEXT);
    }

    public void setHasBeenPlaced(Block block, boolean value) {
        if(value) {
            block.setMetadata(METADATA_TEXT, new FixedMetadataValue(jobModuleManager.getPlugin(), true));
        } else {
            block.setMetadata(METADATA_TEXT, new FixedMetadataValue(jobModuleManager.getPlugin(), null));
        }
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        this.setHasBeenPlaced(event.getBlock(), true);
    }
}
