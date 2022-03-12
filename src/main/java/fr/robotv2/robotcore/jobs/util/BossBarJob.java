package fr.robotv2.robotcore.jobs.util;

import fr.robotv2.robotcore.api.BossBarUtil;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBarJob {

    private final Map<UUID, BukkitTask> tasks = new HashMap<>();
    private final DecimalFormat format = new DecimalFormat("####.##");

    private final JobModule jobModule;
    public BossBarJob(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    public void sendBossBar(Player player, Job job) {
        double currentExp = jobModule.getLevelManager().getExp(player, job);
        double neededExp = jobModule.getLevelManager().getExpNeeded(player, job);

        BossBar bar = BossBarUtil.createOrGetBar(player);

        bar.setColor(job.getChatColor());
        bar.setTitle(StringUtil.colorize(job.getName() + " &8| &7" + format.format(currentExp) + "&8/&7" + format.format(neededExp)));
        bar.setProgress(currentExp / neededExp);
        bar.setVisible(true);
        if(!bar.getPlayers().contains(player))
            bar.addPlayer(player);


        if(tasks.containsKey(player.getUniqueId())) {
            tasks.get(player.getUniqueId()).cancel();
        }

        @NotNull BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                BossBarUtil.clearPlayer(player);
                bar.removePlayer(player);
            }
        }.runTaskLater(jobModule.getPlugin(), 20L * 5);
        tasks.put(player.getUniqueId(), runnable);
    }
}
