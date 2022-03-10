package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.impl.job.JobAction;
import org.bukkit.configuration.file.FileConfiguration;

public class RewardManager {

    private final FileConfiguration config;
    public RewardManager(FileConfiguration configuration) {
      this.config = configuration;
    }

    public double getRewardExpFromConfig(JobAction jobAction, String value) {
        return config.getDouble("actions." + jobAction.toString() + "." + value + ".exp");
    }

    public double getRewardMoneyFromConfig(JobAction jobAction, String value) {
        return config.getDouble("actions." + jobAction.toString() + "." + value + ".money");
    }
}
