package fr.robotv2.robotcore.jobs.stock;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.impl.IJob;
import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collections;
import java.util.Set;

public class LumberJackJob extends IJob {

    private final JobManager jobManager;
    public LumberJackJob(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public JobType getType() {
        return JobType.LUMBERJACK;
    }

    @Override
    public Set<Action> getActions() {
        return Collections.singleton(Action.BREAK);
    }

    @Override
    public ChatColor getChatColor() {
        return ChatColor.GREEN;
    }

    @Override
    public void handleBreak(BlockBreakEvent event) {
    }
}
