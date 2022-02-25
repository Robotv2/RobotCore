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
            Set<Job> jobs = data.getJobs(player.getUniqueId());
            jobCache.put(player.getUniqueId(), jobs);
            return true;
        } catch (Exception exception) {
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
        StringUtil.sendMessage(player, "&fVous venez de rejoindre le métier de " + job.getName(), true);
    }

    public void quitJob(Player player, Job job) {
        if(!hasJob(player, job)) return;
        getJobs(player).remove(job);
    }

    public void savePlayer(Player player) {
        JobData jobData = jobModule.getDataHandler().getData();
        LevelManager levelManager = jobModule.getLevelManager();
        TaskUtil.runTask(() -> {
            for(Job job : this.getJobs(player)) {
                jobData.setLevel(player.getUniqueId(), job.getJobId(), levelManager.getLevel(player, job));
                jobData.setExp(player.getUniqueId(), job.getJobId(), levelManager.getExp(player, job));
            }
            jobData.setJobs(player.getUniqueId(), this.getJobs(player));
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