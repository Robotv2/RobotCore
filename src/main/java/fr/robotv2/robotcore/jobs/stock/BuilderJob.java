package fr.robotv2.robotcore.jobs.stock;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.impl.IJob;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Collections;
import java.util.Set;

public class BuilderJob extends IJob {
    private final JobManager jobManager;
    public BuilderJob(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public JobType getType() {
        return JobType.BUILDER;
    }

    @Override
    public Set<Action> getActions() {
        return Collections.singleton(Action.PLACE);
    }

    @Override
    public ChatColor getChatColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public void handlePlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
    }
}
