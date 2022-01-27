package fr.robotv2.robotcore.jobs.stock;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.events.HarvestBreakEvent;
import fr.robotv2.robotcore.jobs.events.HarvestPlaceEvent;
import fr.robotv2.robotcore.jobs.impl.IJob;
import org.bukkit.ChatColor;

import java.util.Set;

public class FarmerJob extends IJob {

    private final JobManager jobManager;
    public FarmerJob(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public JobType getType() {
        return JobType.FARMER;
    }

    @Override
    public Set<Action> getActions() {
        return Set.of(Action.HARVEST_BREAK, Action.HARVEST_PLANT);
    }

    @Override
    public ChatColor getChatColor() {
        return ChatColor.DARK_AQUA;
    }

    @Override
    public void handleHarvestBreak(HarvestBreakEvent event) {
    }

    @Override
    public void handleHarvestPlant(HarvestPlaceEvent event) {
    }
}
