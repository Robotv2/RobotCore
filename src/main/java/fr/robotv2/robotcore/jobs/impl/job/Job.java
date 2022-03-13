package fr.robotv2.robotcore.jobs.impl.job;

import fr.robotv2.robotcore.api.dependencies.VaultAPI;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.Currency;
import fr.robotv2.robotcore.jobs.manager.RewardManager;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Job {

    private final JobId id;
    private final String name;
    private final BarColor barColor;

    private final Set<JobAction> actions;
    private final FileConfiguration configuration;

    private final JobModule jobModule;
    private final RewardManager rewardManager;

    public Job(FileConfiguration configuration, JobModule jobModule) {
        //Config values.
        this.id = new JobId(configuration.getString("id"));
        this.name = configuration.getString("display");
        this.barColor = BarColor.valueOf(Objects.requireNonNull(configuration.getString("color")).toUpperCase());

        //Actions.
        ConfigurationSection section = configuration.getConfigurationSection("actions");
        if(section != null) {
            this.actions = section.getKeys(false)
                    .stream().map(actionStr -> JobAction.valueOf(actionStr.toUpperCase()))
                    .collect(Collectors.toSet());
        } else {
            this.actions = Collections.emptySet();
        }

        this.rewardManager = new RewardManager(configuration);
        this.jobModule = jobModule;
        this.configuration = configuration;
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

    public BarColor getChatColor() {
        return barColor;
    }

    public Configuration getConfigurationFile() {
        return configuration;
    }

    //<-- EVENT BLOCK -->
    private void handleAction(Player player, String value, JobAction jobAction) {

        if(!jobModule.getPlayerManager().hasJob(player, this))
            return;

        double moneyReward = rewardManager.getRewardMoneyFromConfig(jobAction, value);
        if(moneyReward != 0D) {
            moneyReward = jobModule.getBonusManager().applyAllBonus(player, this, moneyReward, Currency.MONEY);
            VaultAPI.giveMoney(player, moneyReward);
        }

        double expReward = rewardManager.getRewardExpFromConfig(jobAction, value);
        if(expReward != 0D) {
            expReward = jobModule.getBonusManager().applyAllBonus(player, this, expReward, Currency.EXP);
            jobModule.getLevelManager().giveExp(player, this, expReward);
            jobModule.getBossBarJob().sendBossBar(player, this);
        }

        if(expReward != 0D && moneyReward != 0D) {
            jobModule.getActionBarJob().sendActionBar(player, this, moneyReward, expReward);
        }
    }

    public void handleAction(Player player, Material material, JobAction jobAction) {
        handleAction(player, material.toString().toUpperCase(), jobAction);
    }

    public void handleAction(Player player, EntityType type, JobAction jobAction) {
        handleAction(player, type.toString().toUpperCase(), jobAction);
    }
}
