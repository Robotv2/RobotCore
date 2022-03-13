package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.TaskUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobId;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerManager {

    private final JobModule jobModule;
    private final Map<UUID, Set<JobId>> jobCache = new HashMap<>();
    public PlayerManager(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    public boolean initializePlayer(Player player) {
        try {
            JobData data = jobModule.getDataHandler().getData();
            Set<Job> jobs = data.getActiveJobs(player.getUniqueId());
            Set<JobId> jobsIds = jobs.stream().map(Job::getJobId).collect(Collectors.toSet());
            jobCache.put(player.getUniqueId(), jobsIds);
            return true;
        } catch (Exception exception) {
            StringUtil.log("&cAn error occurred while loading " + player.getName() + "'s data. (jobs)");
            StringUtil.log("Error's message: " + exception.getMessage());
            return false;
        }
    }

    public Set<JobId> getJobsId(Player player) {
        return jobCache.get(player.getUniqueId());
    }

    public Set<Job> getJobs(Player player) {
        return getJobsId(player).stream().map(id -> jobModule.getJob(id.toString())).collect(Collectors.toSet());
    }

    public boolean hasJob(Player player, Job job) {
        return getJobsId(player).contains(job.getJobId());
    }

    public void joinJob(Player player, Job job) {
        if(hasJob(player, job)) return;
        getJobsId(player).add(job.getJobId());
    }

    public void quitJob(Player player, Job job) {
        if(!hasJob(player, job)) return;
        savePlayer(player, job);
        getJobsId(player).remove(job.getJobId());
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

    public void savePlayer(Player player, Job job) {
        JobData jobData = jobModule.getDataHandler().getData();
        LevelManager levelManager = jobModule.getLevelManager();
        UUID playerUUID = player.getUniqueId();
        TaskUtil.runTask(() -> {
            jobData.setLevel(playerUUID, job.getJobId(), levelManager.getLevel(player, job));
            jobData.setExp(playerUUID, job.getJobId(), levelManager.getExp(player, job));
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
        }.runTaskTimer(jobModule.getPlugin(), 20 * 30, 20 * 30);
    }
}