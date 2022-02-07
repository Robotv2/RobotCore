package fr.robotv2.robotcore.jobs.impl.job;

import fr.robotv2.robotcore.api.VaultAPI;
import fr.robotv2.robotcore.jobs.JobModuleManager;
import fr.robotv2.robotcore.jobs.enums.JobAction;
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
    private final Set<JobAction> actions;
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
                    .map(actionStr -> JobAction.valueOf(actionStr.toUpperCase())).collect(Collectors.toSet());
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

    public Set<JobAction> getActions() {
        return actions;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Configuration getConfigurationFile() {
        return configuration;
    }

    //<-- EVENT BLOCK -->
    private void handleAction(Player player, String value, JobAction jobAction) {
        double moneyReward = rewardManager.getRewardMoneyFromConfig(jobAction, value);
        if(moneyReward != 0D) {
            VaultAPI.giveMoney(player, moneyReward);
        }

        double expReward = rewardManager.getRewardExpFromConfig(jobAction, value);
        if(expReward != 0D) {
            jobModuleManager.getLevelManager().giveExp(player, this, expReward);
            jobModuleManager.getBossBarJob().sendBossBar(player, this);
        }
    }

    public void handleAction(Player player, Material material, JobAction jobAction) {
        handleAction(player, material.toString(), jobAction);
    }

    public void handleAction(Player player, EntityType type, JobAction jobAction) {
        handleAction(player, type.toString(), jobAction);
    }
}
