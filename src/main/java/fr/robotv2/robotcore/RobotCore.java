package fr.robotv2.robotcore;

import fr.robotv2.robotcore.jobs.JobManager;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.VaultAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class RobotCore extends JavaPlugin {

    private static RobotCore instance;
    private JobManager jobManager;

    @Override
    public void onEnable() {
        instance = this;
        registerJobSystem();
        registerVault();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static RobotCore getInstance() {
        return instance;
    }

    //<-- JOB MODULE -->

    public void registerJobSystem() {
        this.jobManager = new JobManager(this);
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    //<-- VAULT -->

    public void registerVault() {
        if(VaultAPI.initialize()) {
            StringUtil.log("&aSuccessfully hooked into Vault.");
        } else {
            StringUtil.log("&cVault couldn't be found, disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}
