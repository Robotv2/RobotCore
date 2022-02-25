package fr.robotv2.robotcore.jobs.data.stock;

import fr.robotv2.robotcore.api.config.Config;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobId;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record YamlData(JobModule jobModule,
                       Config config) implements JobData {

    @Override
    public void load() {}

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
    public Set<Job> getJobs(UUID playerUUID) {
        ConfigurationSection section = config.get().getConfigurationSection("players." + playerUUID);
        if (section == null)
            return new HashSet<>();

        //Collect all the jobs idea to create a hash set of ids.
        List<String> jobIds = jobModule.getJobsId();

        //Check in the list if it contains the id and if it does, map it to a job and collect it.
        return section.getKeys(false)
                .stream().filter(jobIds::contains).filter(id -> this.isEnabled(playerUUID, id))
                .map(jobModule::getJob).collect(Collectors.toSet());
    }

    @Override
    public void setLevel(UUID playerUUID, JobId id, int value) {
        config.get().set("players." + playerUUID + "." + id + ".level", value);
        save();
    }

    @Override
    public void setExp(UUID playerUUID, JobId id, double value) {
        config.get().set("players." + playerUUID + "." + id + ".exp", value);
        save();
    }

    @Override
    public void setEnabled(UUID playerUUID, JobId jobId, boolean value) {
        config.get().set("players." + playerUUID + "." + jobId.getId() + ".enabled", value);
        save();
    }

    public boolean isEnabled(UUID playerUUID, String jobId) {
        return config.get().getBoolean("players." + playerUUID + "." + jobId + ".enabled");
    }

    @Override
    public void setJobs(UUID playerUUID, Set<Job> jobs) {
        ConfigurationSection section = config.get().getConfigurationSection("players." + playerUUID);
        if(section == null) {
            jobs.forEach(job -> {
                this.setEnabled(playerUUID, job.getJobId(), true);
            });
        } else {
            section.getKeys(false).forEach(jobId -> {
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
