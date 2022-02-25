package fr.robotv2.robotcore.jobs;

import fr.robotv2.robotcore.api.MessageAPI;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.config.ConfigAPI;
import fr.robotv2.robotcore.api.module.Module;
import fr.robotv2.robotcore.jobs.command.RobotJobsCommand;
import fr.robotv2.robotcore.jobs.data.DataHandler;
import fr.robotv2.robotcore.jobs.events.EventCaller;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.listeners.PlayerEvents;
import fr.robotv2.robotcore.jobs.listeners.SystemEvents;
import fr.robotv2.robotcore.jobs.manager.BlockManager;
import fr.robotv2.robotcore.jobs.manager.LevelManager;
import fr.robotv2.robotcore.jobs.manager.PlayerManager;
import fr.robotv2.robotcore.jobs.util.ActionBarJob;
import fr.robotv2.robotcore.jobs.util.BossBarJob;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class JobModule implements Module {

    private JavaPlugin plugin;
    private EventCaller caller;
    private BossBarJob bossBarJob;
    private ActionBarJob actionBarJob;
    private MessageAPI jobMessage;

    private BlockManager blockManager;
    private LevelManager levelManager;
    private PlayerManager playerManager;
    private DataHandler dataHandler;

    private final Map<String, Job> jobs = new HashMap<>();

    private final String PATH_TO_CONFIG = "job-module" + File.separator + "config";

    @Override
    public void onEnable(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataHandler = new DataHandler();
        this.dataHandler.initializeStorage(this, this.getConfig());
        this.levelManager = new LevelManager(this);
        this.playerManager = new PlayerManager(this);
        this.blockManager = new BlockManager(this);
        this.caller = new EventCaller(this);
        this.bossBarJob = new BossBarJob(this);
        this.actionBarJob = new ActionBarJob();

        this.jobMessage = new MessageAPI(ConfigAPI.getConfig("job-module" + File.separator + "messages"));
        this.jobMessage.setPrefix(getJobMessage().getPath("job-prefix"));

        loadJobsFromDataFolder();
        registerListener();
        registerCommand();
    }

    @Override
    public void onDisable() {}

    private void loadJobsFromDataFolder() {
        jobs.clear();
        File dataFolder = new File(plugin.getDataFolder() + File.separator + "job-module" + File.separator + "jobs");
        this.registerDefaultJobs(dataFolder);
        File[] files = dataFolder.listFiles();
        if(files != null) {
            Arrays.stream(files).filter(File::isFile).forEach(this::registerJob);
        }
    }

    //<-- GETTERS -->//

        //<-- JOBS ->>

    public boolean exist(String id) {
        return getJob(id) != null || getJobsId().contains(id);
    }

    public Job getJob(String id) {
        return jobs.get(id);
    }

    public Collection<Job> getJobs() {
        return jobs.values();
    }

    public List<String> getJobsId() {
        return getJobs().stream().map(job -> job.getJobId().getId())
                .collect(Collectors.toList());
    }

        //<-- CLASSES ->>

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public EventCaller getCaller() {
        return caller;
    }

    public BossBarJob getBossBarJob() {
        return bossBarJob;
    }

    public ActionBarJob getActionBarJob() {
        return actionBarJob;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public MessageAPI getJobMessage() {
        return jobMessage;
    }

        //<-- PLUGIN -->

    public JavaPlugin getPlugin() {
        return plugin;
    }

        //<-- CONFIGURATION -->

    public FileConfiguration getConfig() {
        return ConfigAPI.getConfig(PATH_TO_CONFIG).get();
    }

    public void reloadConfig() {
        ConfigAPI.getConfig(PATH_TO_CONFIG).reload();
    }

    public void saveConfig() {
        ConfigAPI.getConfig(PATH_TO_CONFIG).save();
    }

    //<-- REGISTRATION -->

    private void registerJob(File file) {
        try {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Job job = new Job(configuration, this);
            jobs.put(job.getJobId().getId(), job);
            StringUtil.log("&7The job " + job.getName() + " &7has been successfully loaded.");
        } catch (Exception exception) {
            StringUtil.log("&cAn error occurred while trying to load the job for the file: " + file.getName());
            StringUtil.log("&cError message: " + exception.getMessage());
        }
    }

    private void registerDefaultJobs(File directory) {
        if(!directory.exists()) {
            directory.mkdir();
            ConfigAPI.getConfig(directory + File.separator + "miner").setup();
            ConfigAPI.getConfig(directory + File.separator + "lumberjack").setup();
        }
    }

    private void registerListener() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new SystemEvents(this), plugin);
        pm.registerEvents(new PlayerEvents(this), plugin);
    }

    private void registerCommand() {
        new RobotJobsCommand(getPlugin(), "jobs");
    }
}
