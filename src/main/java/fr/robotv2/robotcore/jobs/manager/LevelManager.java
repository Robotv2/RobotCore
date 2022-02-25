package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobId;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelManager {

    private final JobModule jobModule;
    private final Map<UUID, Map<JobId, Integer>> levels = new HashMap<>();
    private final Map<UUID, Map<JobId, Double>> experiences = new HashMap<>();

    public LevelManager(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    public boolean initializePlayer(Player player) {
        try {
            JobData data = jobModule.getDataHandler().getData();

            UUID playerUUID = player.getUniqueId();
            Map<JobId, Integer> levels = new HashMap<>();
            Map<JobId, Double> experiences = new HashMap<>();

            for(Job job : data.getJobs(playerUUID)) {
                levels.put(job.getJobId(), data.getLevel(playerUUID, job.getJobId()));
                experiences.put(job.getJobId(), data.getExp(playerUUID, job.getJobId()));
            }

            this.levels.put(playerUUID, levels);
            this.experiences.put(playerUUID, experiences);

            return true;
        } catch (Exception exception) {
            StringUtil.log("Error's message: " + exception.getMessage());
            return false;
        }
    }

    public int getLevel(Player player, Job type) {
        if(levels.get(player.getUniqueId()) == null)
            return 0;
        return levels.get(player.getUniqueId()).get(type.getJobId());
    }

    public void setLevel(Player player, Job job, Integer level) {
        Map<JobId, Integer> result = levels.get(player.getUniqueId());
        result.put(job.getJobId(), level);
        levels.put(player.getUniqueId(), result);
    }

    public Double getExp(Player player, Job type) {
        if(experiences.get(player.getUniqueId()) == null)
            return 0D;
        return experiences.get(player.getUniqueId()).get(type.getJobId());
    }

    public void setExp(Player player, Job job, Double exp) {
        Map<JobId, Double> result = experiences.get(player.getUniqueId());
        result.put(job.getJobId(), exp);
        experiences.put(player.getUniqueId(), result);
    }

    public void giveExp(Player player, Job job, Double value) {
        this.setExp(player, job, getExp(player, job) + value);
        levelUp(player, job);
    }

    public Double getExpNeeded(Player player, Job job) {
        int level = this.getLevel(player, job);
        return (level * 30.5D) * (level * level);
    }

    public boolean canLevelUp(Player player, Job job) {
        return this.getExp(player, job) >= getExpNeeded(player, job);
    }

    public void levelUp(Player player, Job job) {
        if(!canLevelUp(player, job)) return;

        this.setExp(player, job, 0D);
        this.setLevel(player, job, getLevel(player, job) + 1);

        StringUtil.sendMessage(
                player,
                "&7You have just passed level &f" + getLevel(player, job) + " &7for the job " + job.getChatColor() + job.getName() + " &7!",
                true);
    }
}