package fr.robotv2.robotcore.jobs.util;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class ActionBarJob {

    private final DecimalFormat format = new DecimalFormat("####.##");

    public void sendActionBar(Player player, Job job, double money, double exp) {
        String moneyStr = format.format(money);
        String expStr = format.format(exp);
        player.sendActionBar(StringUtil.colorize(job.getName() + " &8| &a+" + moneyStr + "$ &7- &e+" + expStr + " exp"));
    }
}
