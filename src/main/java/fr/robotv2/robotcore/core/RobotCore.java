package fr.robotv2.robotcore.core;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import fr.robotv2.robotcore.api.config.ConfigAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class RobotCore extends JavaPlugin {

    public static boolean stop = false;
    private static RobotCore instance;
    private ModuleRegistry registry;

    @Override
    public void onEnable() {
        RobotCore.instance = this;

        ConfigAPI.init(this);
        DependencyManager.loadDependencies();

        if(!stop)
            this.registry = new ModuleRegistry(this);
    }

    @Override
    public void onDisable() {
        RobotCore.instance = null;
        if(getModuleRegistry() != null)
            getModuleRegistry().disableModules();
    }

    public static RobotCore getInstance() {
        return instance;
    }

    public ModuleRegistry getModuleRegistry() {
        return registry;
    }

    public PaperCommandManager getCommandManager() {
        return DependencyManager.getCommandManager();
    }

    public void disablePlugin() {
        stop = true;
        getServer().getPluginManager().disablePlugin(this);
    }
}
