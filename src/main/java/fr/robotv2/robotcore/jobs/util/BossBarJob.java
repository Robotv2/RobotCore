package fr.robotv2.robotcore.jobs.util;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.impl.IJob;
import fr.robotv2.robotcore.api.BossBarUtil;
import fr.robotv2.robotcore.api.StringUtil;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBarJob {

    private final Map<UUID, BukkitTask> tasks = new HashMap<>();

    private final JobManager jobManager;
    public BossBarJob(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    public void sendBossBar(Player player, JobType type) {
        double currentExp = jobManager.getLevelManager().getExp(player, type);
        double neededExp = jobManager.getLevelManager().getExpNeeded(player, type);

        IJob job = jobManager.getJob(type);
        BossBar bar = BossBarUtil.createOrGetBar(player);

        bar.setColor(BossBarUtil.toBarColor(job.getChatColor()));
        bar.setTitle(job.getChatColor() + job.getType().getTranslatedName() + StringUtil.colorize(" &8| &7" + currentExp + "&8/&7" + neededExp));
        bar.setProgress(neededExp / currentExp);
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
        }.runTaskLater(jobManager.getPlugin(), 20L * 5);
        tasks.put(player.getUniqueId(), runnable);
    }
}
