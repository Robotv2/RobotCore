package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.enums.Action;
import org.bukkit.configuration.file.FileConfiguration;

public class RewardManager {

    private final FileConfiguration config;
    public RewardManager(FileConfiguration configuration) {
      this.config = configuration;
    }

    public double getRewardExpFromConfig(Action action, String value) {
        return config.getDouble("actions." + action.toString() + "." + value + ".exp");
    }

    public double getRewardMoneyFromConfig(Action action, String value) {
        return config.getDouble("actions." + action.toString() + "." + value + ".money");
    }
}
