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
    private final Map<UUID, JobLevelPlayerData> levelDatas = new HashMap<>();

    public LevelManager(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    public static class JobLevelPlayerData {
        private final Map<JobId, Integer> levels = new HashMap<>();
        private final Map<JobId, Double> experiences = new HashMap<>();

        public int getLevel(JobId id) {
            return levels.get(id);
        }

        public double getExperiences(JobId id) {
            return experiences.get(id);
        }

        public void setLevel(JobId id, Integer value) {
            this.levels.put(id, value);
        }

        public void setExperiences(JobId id, Double value) {
            this.experiences.put(id, value);
        }
    }

    public boolean initializePlayer(Player player) {
        try {
            JobData data = jobModule.getDataHandler().getData();

            UUID playerUUID = player.getUniqueId();
            JobLevelPlayerData levelPlayerData = new JobLevelPlayerData();

            for(Job job : data.getActiveJobs(playerUUID)) {
                levelPlayerData.setLevel(job.getJobId(), data.getLevel(playerUUID, job.getJobId()));
                levelPlayerData.setExperiences(job.getJobId(), data.getExp(playerUUID, job.getJobId()));
            }

            levelDatas.put(playerUUID, levelPlayerData);
            return true;
        } catch (Exception exception) {
            StringUtil.log("&cAn error occurred while loading " + player.getName() + "'s data. (levels)");
            StringUtil.log("Error's message: " + exception.getMessage());
            return false;
        }
    }

    public JobLevelPlayerData getLevelPlayerData(UUID playerUUID) {
        return levelDatas.get(playerUUID);
    }

    public JobLevelPlayerData getLevelPlayerData(Player player) {
        return getLevelPlayerData(player.getUniqueId());
    }

    public int getLevel(Player player, Job type) {
        return getLevelPlayerData(player).getLevel(type.getJobId());
    }

    public void setLevel(Player player, Job job, Integer level) {
        getLevelPlayerData(player).setLevel(job.getJobId(), level);
    }

    public Double getExp(Player player, Job type) {
        return getLevelPlayerData(player).getExperiences(type.getJobId());
    }

    public void setExp(Player player, Job job, Double exp) {
        getLevelPlayerData(player).setExperiences(job.getJobId(), exp);
    }

    public void giveExp(Player player, Job job, Double value) {
        this.setExp(player, job, getExp(player, job) + value);
        this.levelUp(player, job);
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
