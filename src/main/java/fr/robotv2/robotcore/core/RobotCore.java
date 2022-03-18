package fr.robotv2.robotcore.core;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public final class RobotCore extends JavaPlugin {

    private static boolean stop = false;
    private static RobotCore instance;
    private static Logger logger;

    private ModuleRegistry registry;

    @Override
    public void onEnable() {
        this.setInstance(this);


        DependencyManager.loadDependencies();
        if(stop) return;

        this.registry = new ModuleRegistry(this);
        this.registry.enableModules();
    }

    @Override
    public void onDisable() {
        if(getModuleRegistry() != null)
            getModuleRegistry().disableModules();
        this.setInstance(null);
    }

    public void disablePlugin() {
        stop = true;
        getServer().getPluginManager().disablePlugin(this);
    }

    public static RobotCore getInstance() {
        return instance;
    }

    public static Logger getLog() {
        return logger;
    }

    public void setInstance(@Nullable RobotCore core) {
        RobotCore.instance = core;
    }


    public ModuleRegistry getModuleRegistry() {
        return registry;
    }

    public PaperCommandManager getCommandManager() {
        return DependencyManager.getCommandManager();
    }
}
