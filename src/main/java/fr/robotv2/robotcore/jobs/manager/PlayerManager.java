package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.TaskUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerManager {

    private final JobModule jobModule;
    private final Map<UUID, Set<Job>> jobCache = new HashMap<>();
    public PlayerManager(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    public boolean initializePlayer(Player player) {
        try {
            JobData data = jobModule.getDataHandler().getData();
            Set<Job> jobs = data.getActiveJobs(player.getUniqueId());
            jobCache.put(player.getUniqueId(), jobs);
            return true;
        } catch (Exception exception) {
            StringUtil.log("&cAn error occurred while loading " + player.getName() + "'s data. (jobs)");
            StringUtil.log("Error's message: " + exception.getMessage());
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

    public void savePlayer(Player player) {

        JobData jobData = jobModule.getDataHandler().getData();
        LevelManager levelManager = jobModule.getLevelManager();
        UUID playerUUID = player.getUniqueId();

        TaskUtil.runTask(() -> {
            jobData.setActiveJobs(playerUUID, this.getJobs(player));
            for(Job job : this.getJobs(player)) {
                jobData.setLevel(playerUUID, job.getJobId(), levelManager.getLevel(player, job));
                jobData.setExp(playerUUID, job.getJobId(), levelManager.getExp(player, job));
            }
        }, jobData.needAsync());
    }

    public void initScheduledSaving(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(player == null || !player.isOnline()) {
                    cancel();
                    return;
                }
                savePlayer(player);
            }
        }.runTaskTimer(jobModule.getPlugin(), 20 * 60 * 5, 20 * 60 * 5);
    }
}