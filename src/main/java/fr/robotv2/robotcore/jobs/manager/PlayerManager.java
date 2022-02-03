package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.JobModuleManager;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.Job;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private final JobModuleManager jobModuleManager;
    private final Map<UUID, Set<Job>> jobCache = new HashMap<>();
    public PlayerManager(JobModuleManager jobModuleManager) {
        this.jobModuleManager = jobModuleManager;
    }

    public boolean initializePlayer(Player player) {
        try {
            JobData data = jobModuleManager.getDataHandler().getData();
            Set<Job> jobs = data.getJobs(player.getUniqueId());
            jobCache.put(player.getUniqueId(), jobs);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Set<Job> getJobs(Player player) {
        return jobCache.get(player.getUniqueId());
    }

    public boolean hasJob(Player player, Job job) {
        return getJobs(player).contains(job);
    }

    public void joinJob(Player player, Job job) {
        if(hasJob(player, job)) return;
        getJobs(player).add(job);
    }

    public void quitJob(Player player, Job job) {
        if(!hasJob(player, job)) return;
        getJobs(player).remove(job);
    }
}
