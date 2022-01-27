package fr.robotv2.robotcore.jobs;

import fr.robotv2.robotcore.RobotCore;
import fr.robotv2.robotcore.jobs.util.BossBarJob;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.events.EventCaller;
import fr.robotv2.robotcore.jobs.impl.IJob;
import fr.robotv2.robotcore.jobs.listeners.SystemEvents;
import fr.robotv2.robotcore.jobs.stock.*;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class JobManager {

    private final RobotCore plugin;
    private final EventCaller caller;
    private final BossBarJob bossBarJob;

    private final LevelManager levelManager;
    private final RewardManager rewardManager;

    private final Map<JobType, IJob> jobs = new HashMap<>();

    public JobManager(RobotCore plugin) {
        this.plugin = plugin;
        this.levelManager = new LevelManager();
        this.rewardManager = new RewardManager(plugin.getConfig());
        this.caller = new EventCaller(this);
        this.bossBarJob = new BossBarJob(this);
        Arrays.stream(JobType.values()).forEach(this::registerJob);
        registerListener();
    }

    //<-- GETTERS -->//

    public IJob getJob(JobType type) {
        return jobs.get(type);
    }

    public Collection<IJob> getJobs() {
        return jobs.values();
    }

    public EventCaller getCaller() {
        return caller;
    }

    public BossBarJob getBossBarJob() {
        return bossBarJob;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public RobotCore getPlugin() {
        return plugin;
    }

    //<-- REGISTRATION -->

    private void registerJob(JobType type) {
        IJob job = null;
        switch (type) {
            case MINER -> job = new MinerJob(this);
            case LUMBERJACK -> job = new LumberJackJob(this);
            case FARMER -> job = new FarmerJob(this);
            case HUNTER -> job = new HunterJob(this);
            case DIGGER -> job = new DiggerJob(this);
            case BUILDER -> job = new BuilderJob(this);
        }
        if(!Objects.isNull(job)) {
            jobs.put(type, job);
            StringUtil.log("The job " + job.getType().getTranslatedName() + " has been successfully loaded.");
        } else {
            StringUtil.log("&cAn error occurred while trying to load the job " + type);
        }
    }

    private void registerListener() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new SystemEvents(this), plugin);
    }
}
