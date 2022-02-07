package fr.robotv2.robotcore.jobs.data;

import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobId;

import java.util.Set;
import java.util.UUID;

public interface JobData {

    void load();
    void initPlayer(UUID playerUUID);
    void close();

    int getLevel(UUID playerUUID, JobId id);
    double getExp(UUID playerUUID, JobId id);
    Set<Job> getJobs(UUID playerUUID);

    void setLevel(UUID playerUUID, JobId id, int value);
    void setExp(UUID playerUUID, JobId id, double value);

    void setEnabled(UUID playerUUID, JobId id, boolean value);
    void setJobs(UUID playerUUID, Set<Job> jobs);

    boolean needAsync();
}
