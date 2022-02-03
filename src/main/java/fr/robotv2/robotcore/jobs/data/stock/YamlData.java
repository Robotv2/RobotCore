package fr.robotv2.robotcore.jobs.data.stock;

import fr.robotv2.robotcore.jobs.JobModuleManager;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.Job;
import fr.robotv2.robotcore.jobs.impl.JobId;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public class YamlData implements JobData {

    private final JobModuleManager jobModuleManager;
    private final Configuration configuration;
    public YamlData(JobModuleManager jobModuleManager, Configuration configuration) {
        this.jobModuleManager = jobModuleManager;
        this.configuration = configuration;
    }

    @Override
    public void load() {}

    @Override
    public void initPlayer(UUID playerUUID) {}

    @Override
    public void close() {}

    @Override
    public int getLevel(UUID playerUUID, JobId id) {
        return configuration.getInt("players." + playerUUID + "." + id + ".level");
    }

    @Override
    public double getExp(UUID playerUUID, JobId id) {
        return configuration.getDouble("players." + playerUUID + "." + id + ".exp");
    }

    @Override
    public Set<Job> getJobs(UUID playerUUID) {
        ConfigurationSection section = configuration.getConfigurationSection("players." + playerUUID);
        if(section == null) return Collections.emptySet();

        //Collect all the jobs idea to create a hash set of ids.
        Set<String> jobIds = jobModuleManager.getJobs()
                .stream().map(job -> job.getJobId().getId())
                .collect(Collectors.toSet());

        //Check in the list if it contains the id and if it does, map it to a job and collect it.
        return section.getKeys(false)
                .stream().filter(jobIds::contains)
                .map(jobModuleManager::getJob).collect(Collectors.toSet());
    }

    @Override
    public void setLevel(UUID playerUUID, JobId id, int value) {
        configuration.set("players." + playerUUID + "." + id + ".level", value);
    }

    @Override
    public void setExp(UUID playerUUID, JobId id, double value) {
        configuration.set("players." + playerUUID + "." + id + ".exp", value);
    }

    public void setEnabled(UUID playerUUID, String id, boolean value) {
        configuration.set("players." + playerUUID + "." + id+ ".enabled", value);
    }

    @Override
    public void setJobs(UUID playerUUID, JobId id, Set<Job> jobs) {
        ConfigurationSection section = configuration.getConfigurationSection("players." + playerUUID);
        if(section == null) {
            jobs.forEach(job -> {
                this.setLevel(playerUUID, job.getJobId(), 0);
                this.setExp(playerUUID, job.getJobId(), 0);
                this.setEnabled(playerUUID, job.getJobId().toString(), true);
            });
        } else {
            section.getKeys(false).forEach(jobId -> {
                Job job = jobModuleManager.getJob(jobId);
                if(job == null)
                    this.setEnabled(playerUUID, jobId, false);
                else
                    this.setEnabled(playerUUID, job.getJobId().getId(), jobs.contains(job));
            });
        }
    }
}
