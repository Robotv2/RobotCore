package fr.robotv2.robotcore.jobs;

import fr.robotv2.robotcore.jobs.impl.job.JobId;
import fr.robotv2.robotcore.jobs.ui.JobBrowseUI;
import fr.robotv2.robotcore.jobs.ui.JobInfoUI;
import fr.robotv2.robotcore.shared.MessageAPI;
import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.config.ConfigAPI;
import fr.robotv2.robotcore.shared.module.Module;
import fr.robotv2.robotcore.core.RobotCore;
import fr.robotv2.robotcore.jobs.command.JobsCommand;
import fr.robotv2.robotcore.jobs.data.DataHandler;
import fr.robotv2.robotcore.jobs.events.EventCaller;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.listeners.PlayerEvents;
import fr.robotv2.robotcore.jobs.listeners.SystemEvents;
import fr.robotv2.robotcore.jobs.manager.BlockManager;
import fr.robotv2.robotcore.jobs.manager.BonusManager;
import fr.robotv2.robotcore.jobs.manager.LevelManager;
import fr.robotv2.robotcore.jobs.manager.PlayerManager;
import fr.robotv2.robotcore.jobs.util.ActionBarJob;
import fr.robotv2.robotcore.jobs.util.BossBarJob;
import fr.robotv2.robotcore.shared.ui.GUI;
import fr.robotv2.robotcore.shared.ui.GuiAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptEngineManager;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    private BonusManager bonusManager;
    private DataHandler dataHandler;

    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    private final String PATH_TO_CONFIG = "job-module" + File.separator + "config";
    public String PATH_TO_JOBS_DIRECT;

    @Override
    public void onEnable(JavaPlugin plugin) {
        this.plugin = plugin;
        this.PATH_TO_JOBS_DIRECT = plugin.getDataFolder() + File.separator + "job-module" + File.separator + "jobs";

        this.dataHandler = new DataHandler();
        this.dataHandler.initializeStorage(this, this.getConfig());

        this.levelManager = new LevelManager(this);
        this.playerManager = new PlayerManager(this);

        this.blockManager = new BlockManager(this);
        this.caller = new EventCaller(this);
        this.bonusManager = new BonusManager();

        this.bossBarJob = new BossBarJob(this);
        this.actionBarJob = new ActionBarJob();
        this.jobMessage = new MessageAPI(ConfigAPI.getConfig("job-module" + File.separator + "messages"));

        loadJobsFromDir();
        registerListener();
        registerCommand();
        registerUI();
    }

    @Override
    public void onDisable() {
        getDataHandler().getData().close();
    }

    public void onReload() {
        this.jobMessage.clearPaths();
        this.jobMessage.getFile().reload();

        //Reload jobs.
        this.jobs.clear();
        this.loadJobsFromDir();

        //Reset needed exp.
        for(JobId id : getJobsId()) {
            for(LevelManager.JobLevelPlayerData data : getLevelManager().getDatas()) {
                data.resetNeededExp(id);
            }
        }
    }

    private void loadJobsFromDir() {
        if(getJobsFiles() == null) return;
        Arrays.stream(getJobsFiles())
                .filter(File::isFile)
                .forEach(this::registerJob);
    }

    private File[] getJobsFiles() {
        File directory = new File(PATH_TO_JOBS_DIRECT);
        if(!directory.exists()) {
            this.registerDefaultJobs();
            return getJobsFiles();
        } else if(directory.listFiles() != null){
            return directory.listFiles();
        } else {
            return null;
        }
    }

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

    public Set<JobId> getJobsId() {
        return getJobs().stream().map(Job::getJobId)
                .collect(Collectors.toSet());
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

    public BonusManager getBonusManager() {
        return bonusManager;
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
            StringUtil.log("&7The job " + job.getName() + "&r &7has been successfully loaded.");
        } catch (Exception exception) {
            StringUtil.log("&cAn error occurred while trying to load the job for the file: " + file.getName());
            StringUtil.log("&cError message: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void registerDefaultJobs() {
        ConfigAPI.getConfig("job-module" + File.separator + "jobs" + File.separator + "miner").setup();
    }

    private void registerListener() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new SystemEvents(this), plugin);
        pm.registerEvents(new PlayerEvents(this), plugin);
    }

    private void registerCommand() {
        RobotCore.getInstance().getCommandManager().getCommandContexts().registerContext(Job.class, c -> {
            return getJob(c.getFirstArg());
        });
        RobotCore.getInstance().getCommandManager().getCommandCompletions().registerCompletion("jobs", c -> {
            return getJobsId().stream().map(JobId::getId).collect(Collectors.toList());
        });
        RobotCore.getInstance().getCommandManager().registerCommand(new JobsCommand(this));
    }

    private void registerUI() {
        GuiAPI.addMenu(new JobBrowseUI(this));
        GuiAPI.addMenu(new JobInfoUI());
    }
}
