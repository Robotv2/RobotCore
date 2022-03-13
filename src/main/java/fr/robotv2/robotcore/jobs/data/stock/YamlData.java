package fr.robotv2.robotcore.jobs.data.stock;

import fr.robotv2.robotcore.shared.config.Config;
import fr.robotv2.robotcore.shared.config.ConfigAPI;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobId;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class YamlData implements JobData {

    private final JobModule jobModule;
    private final Config config;

    public YamlData(JobModule module) {
        this.jobModule = module;
        this.config = ConfigAPI.getConfig("job-module" + File.separator + "data");
    }

    @Override
    public void load() {
        this.config.setup();
    }

    @Override
    public void initPlayer(UUID playerUUID) {}

    @Override
    public void close() {}

    @Override
    public int getLevel(UUID playerUUID, JobId id) {
        return config.get().getInt("players." + playerUUID + "." + id + ".level");
    }

    @Override
    public double getExp(UUID playerUUID, JobId id) {
        return config.get().getDouble("players." + playerUUID + "." + id + ".exp");
    }

    @Override
    public Set<Job> getActiveJobs(UUID playerUUID) {
        return getJobs(playerUUID).stream()
                .filter(job -> isEnabled(playerUUID, job.getJobId().toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Job> getJobs(UUID playerUUID) {
        ConfigurationSection section = config.get().getConfigurationSection("players." + playerUUID);
        if (section == null)
            return new HashSet<>();
        return section.getKeys(false).stream()
                .filter(jobModule::exist).map(jobModule::getJob).collect(Collectors.toSet());
    }

    @Override
    public void setLevel(UUID playerUUID, JobId id, int value) {
        config.get().set("players." + playerUUID + "." + id + ".level", value);
    }

    @Override
    public void setExp(UUID playerUUID, JobId id, double value) {
        config.get().set("players." + playerUUID + "." + id + ".exp", value);
    }

    @Override
    public void setEnabled(UUID playerUUID, JobId jobId, boolean value) {
        config.get().set("players." + playerUUID + "." + jobId.getId() + ".enabled", value);
    }

    public boolean isEnabled(UUID playerUUID, String jobId) {
        return config.get().getBoolean("players." + playerUUID + "." + jobId + ".enabled");
    }

    @Override
    public void setActiveJobs(UUID playerUUID, Set<Job> jobs) {
        ConfigurationSection section = config.get().getConfigurationSection("players." + playerUUID);
        if(section == null) {
            jobs.forEach(job -> this.setEnabled(playerUUID, job.getJobId(), true));
        } else {
            Set<String> keys = section.getKeys(false);

            //Enable all jobs.
            jobs.forEach(job -> this.setEnabled(playerUUID, job.getJobId(), true));

            //Checking in already registered keys to enable them or not.
            keys.forEach(jobId -> {
                Job job = jobModule.getJob(jobId);
                if (job != null)
                    this.setEnabled(playerUUID, job.getJobId(), jobs.contains(job));
            });
        }
        this.save();
    }

    @Override
    public boolean needAsync() {
        return false;
    }

    public void save() {
        config.save();
    }
}
