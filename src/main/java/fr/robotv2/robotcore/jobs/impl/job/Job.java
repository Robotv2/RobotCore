package fr.robotv2.robotcore.jobs.impl.job;

import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.dependencies.VaultAPI;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.Currency;
import fr.robotv2.robotcore.jobs.manager.RewardManager;
import fr.robotv2.robotcore.shared.item.ItemAPI;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
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
    private final Expression expression;

    private final int slot;
    private final ItemStack item;

    public Job(FileConfiguration configuration, JobModule jobModule) {
        //Config values.
        this.id = new JobId(configuration.getString("id"));
        this.name = Objects.requireNonNull(configuration.getString("display"));
        this.barColor = BarColor.valueOf(configuration.getString("bar-color", "white").toUpperCase());

        //Actions.
        ConfigurationSection section = configuration.getConfigurationSection("actions");
        if(section != null) {
            this.actions = section.getKeys(false)
                    .stream().map(actionStr -> JobAction.valueOf(actionStr.toUpperCase()))
                    .collect(Collectors.toSet());
        } else {
            this.actions = Collections.emptySet();
        }

        //Leveling system
        String expressionStr = configuration.getString("leveling-system.exp-needed", "(level * 30.5) * (level * level)").replace(',', '.');
        this.expression = new ExpressionBuilder(expressionStr).variables("level").build();

        //Gui
        this.slot = configuration.getInt("gui.slot");
        Material material = Material.valueOf(configuration.getString("gui.material", "STONE").toUpperCase());
        this.item = new ItemAPI.ItemBuilder().setType(material).setName(configuration.getString("gui.name", this.name)).build();

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

    public Expression  getExpression() {
        return expression;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem(Player player) {
        ItemAPI.ItemBuilder builder = ItemAPI.toBuilder(item);
        List<String> lore = configuration.getStringList("gui.lore");

        int level = jobModule.getLevelManager().getLevel(player, this);
        double exp = jobModule.getLevelManager().getExp(player, this);

        lore = lore.stream()
                .map(StringUtil::colorize)
                .map(line -> line.replace("%level%", String.valueOf(level)))
                .map(line -> line.replace("%exp%", String.valueOf(exp)))
                .collect(Collectors.toList());
        return builder.setLore(lore).setKey("job", id.getId()).build();
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
