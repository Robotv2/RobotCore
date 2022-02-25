package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.JobModule;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockManager implements Listener {

    private final JobModule jobModule;
    private final String METADATA_TEXT = "has-been-broken";
    private final FixedMetadataValue METADATA_FILLED;

    public BlockManager(JobModule jobModule) {
        this.jobModule = jobModule;
        this.METADATA_FILLED = new FixedMetadataValue(jobModule.getPlugin(), true);
        jobModule.getPlugin().getServer().getPluginManager().registerEvents(this, jobModule.getPlugin());
    }

    public boolean hasBeenPlaced(Block block) {
        return block.hasMetadata(METADATA_TEXT);
    }

    public void setHasBeenPlaced(Block block, boolean value) {
        if(value) {
            block.setMetadata(METADATA_TEXT, METADATA_FILLED);
        } else {
            block.removeMetadata(METADATA_TEXT, jobModule.getPlugin());
        }
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        this.setHasBeenPlaced(event.getBlock(), true);
    }
}
