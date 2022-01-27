package fr.robotv2.robotcore.jobs;

import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class RewardManager {

    private final FileConfiguration config;
    public RewardManager(FileConfiguration configuration) {
      this.config = configuration;
    }

    public Optional<Double> getRewardExpFromConfig(JobType type, Action action, Material material) {
        Double exp = config.getDouble(type.getFunctionalName() + "." + action.toString() + "." + material.toString() + ".exp");
        return Optional.of(exp);
    }

    public Optional<Double> getRewardMoneyFromConfig(JobType type, Action action, Material material) {
        Double exp = config.getDouble(type.getFunctionalName() + "." + action.toString() + "." + material.toString() + ".money");
        return Optional.of(exp);
    }
}
