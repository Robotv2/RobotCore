package fr.robotv2.robotcore.jobs.stock;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.events.PlayerKillByPlayerEvent;
import fr.robotv2.robotcore.jobs.impl.IJob;
import org.bukkit.ChatColor;

import java.util.Set;

public class HunterJob extends IJob {

    private final JobManager jobManager;
    public HunterJob(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public JobType getType() {
        return JobType.HUNTER;
    }

    @Override
    public Set<Action> getActions() {
        return Set.of(Action.KILL_ENTITIES, Action.KILL_PLAYERS);
    }

    @Override
    public ChatColor getChatColor() {
        return ChatColor.RED;
    }

    @Override
    public void handlePlayerKillByPlayer(PlayerKillByPlayerEvent event) {
    }
}
