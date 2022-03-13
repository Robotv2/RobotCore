package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.impl.Currency;
import fr.robotv2.robotcore.jobs.impl.job.JobAction;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RewardManager {

    private final FileConfiguration config;
    private final Map<JobAction, RewardContainer> containers = new ConcurrentHashMap<>();

    public RewardManager(FileConfiguration configuration) {
      this.config = configuration;
    }

    public static class RewardContainer {

        private final FileConfiguration config;
        private final JobAction action;
        private final Map<String, Double> exps = new HashMap<>();
        private final Map<String, Double> moneys = new HashMap<>();

        public RewardContainer(JobAction action, FileConfiguration configuration) {
            this.action = action;
            this.config = configuration;
        }

        public void registerRewardFromConfig(String value, Currency currency) {
            switch (currency) {
                case MONEY -> this.moneys.put(value.toLowerCase(), config.getDouble("actions." + action.toString() + "." + value + ".money", 0D));
                case EXP -> this.exps.put(value.toLowerCase(), config.getDouble("actions." + action.toString() + "." + value + ".exp", 0D));
            }
        }

        public double getReward(String value, Currency currency) {
            switch (currency) {
                case MONEY -> {
                    return moneys.getOrDefault(value.toLowerCase(), 0D);
                }
                case EXP -> {
                    return exps.getOrDefault(value.toLowerCase(), 0D);
                }
                default -> throw new IllegalArgumentException("Unknown argument " + currency);
            }
        }

        public boolean hasReward(String value, Currency currency) {
            switch (currency) {
                case EXP -> {
                    return exps.containsKey(value.toLowerCase());
                }
                case MONEY -> {
                    return moneys.containsKey(value.toLowerCase());
                }
                default -> throw new IllegalArgumentException("Unknown arguments " + currency);
            }
        }
    }

    public double getRewardExpFromConfig(JobAction jobAction, String value) {
        this.checkExist(jobAction, value, Currency.EXP);
        return containers.get(jobAction).getReward(value, Currency.EXP);
    }

    public double getRewardMoneyFromConfig(JobAction jobAction, String value) {
        this.checkExist(jobAction, value, Currency.MONEY);
        return containers.get(jobAction).getReward(value, Currency.MONEY);
    }

    private void checkExist(JobAction action, String value, Currency currency) {
        if(!containers.containsKey(action)) {
            containers.put(action, new RewardContainer(action, config));
        }

        RewardContainer container = containers.get(action);
        if(!container.hasReward(value, currency)) {
            container.registerRewardFromConfig(value, currency);
        }
    }
}
