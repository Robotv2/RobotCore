package fr.robotv2.robotcore.jobs.impl;

import fr.robotv2.robotcore.api.VaultAPI;
import fr.robotv2.robotcore.jobs.JobModuleManager;
import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.manager.RewardManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Job {

    private final JobModuleManager jobModuleManager;
    private final JobId id;
    private final String name;
    private final Set<Action> actions;
    private final ChatColor chatColor;
    private final Configuration configuration;
    private final RewardManager rewardManager;

    public Job(FileConfiguration configuration, JobModuleManager jobModuleManager) {

        this.jobModuleManager = jobModuleManager;
        this.id = new JobId(configuration.getString("id"));
        this.name = configuration.getString("display");
        this.chatColor = ChatColor.valueOf(Objects.requireNonNull(configuration.getString("color")).toUpperCase());
        this.configuration = configuration;
        this.rewardManager = new RewardManager(configuration);

        ConfigurationSection section = configuration.getConfigurationSection("actions");
        if(section != null) {
            this.actions = section.getKeys(false).stream()
                    .map(actionStr -> Action.valueOf(actionStr.toUpperCase())).collect(Collectors.toSet());
        } else {
            this.actions = new HashSet<>();
        }
    }

    public JobId getJobId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Configuration getConfigurationFile() {
        return configuration;
    }

    //<-- EVENT BLOCK -->
    private void handleAction(Player player, String value, Action action) {
        double moneyReward = rewardManager.getRewardMoneyFromConfig(action, value);
        if(moneyReward != 0D) {
            VaultAPI.giveMoney(player, moneyReward);
        }

        double expReward = rewardManager.getRewardExpFromConfig(action, value);
        if(expReward != 0D) {
            jobModuleManager.getLevelManager().giveExp(player, this, expReward);
            jobModuleManager.getBossBarJob().sendBossBar(player, this);
        }
    }

    public void handleAction(Player player, Material material, Action action) {
        handleAction(player, material.toString(), action);
    }

    public void handleAction(Player player, EntityType type, Action action) {
        handleAction(player, type.toString(), action);
    }
}
