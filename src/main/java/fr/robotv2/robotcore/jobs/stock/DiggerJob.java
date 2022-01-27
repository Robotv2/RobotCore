package fr.robotv2.robotcore.jobs.stock;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.impl.IJob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class DiggerJob extends IJob {

    private final JobManager jobManager;
    public DiggerJob(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public JobType getType() {
        return JobType.DIGGER;
    }

    @Override
    public Set<Action> getActions() {
        return Collections.singleton(Action.BREAK);
    }

    @Override
    public ChatColor getChatColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public void handleBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        Optional<Double> exp = jobManager.getRewardManager().getRewardExpFromConfig(getType(), Action.BREAK, material);
        if(exp.isPresent()) {
            jobManager.getLevelManager().giveExp(player, getType(), exp.get());
            jobManager.getBossBarJob().sendBossBar(player, getType());
        }
    }
}
