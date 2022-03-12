package fr.robotv2.robotcore.core;

import co.aikar.commands.PaperCommandManager;
import fr.robotv2.robotcore.api.config.Config;
import fr.robotv2.robotcore.api.config.ConfigAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RobotCore extends JavaPlugin {

    public static boolean stop = false;
    private static RobotCore instance;

    private ModuleRegistry registry;
    private Config moduleConfig;

    @Override
    public void onEnable() {
        RobotCore.instance = this;

        ConfigAPI.init(this);
        moduleConfig = ConfigAPI.getConfig("modules");

        DependencyManager.loadDependencies();
        if(!stop) this.registry = new ModuleRegistry(this);
    }

    @Override
    public void onDisable() {
        if(getModuleRegistry() != null)
            getModuleRegistry().disableModules();
        RobotCore.instance = null;
    }

    /**
     * @return an instance of the core.
     */
    public static RobotCore getInstance() {
        return instance;
    }

    /**
     * @return the file configuration of the file 'modules.yml'.
     */
    public FileConfiguration getModuleConfiguration() {
        return moduleConfig.get();
    }

    /**
     * save the file 'modules.yml'.
     */
    public void saveModuleConfiguration() {
        moduleConfig.save();
    }

    /**
     * @return the instance of the module registry.
     */
    public ModuleRegistry getModuleRegistry() {
        return registry;
    }

    /**
     * @return the instance of the ACF command manager.
     */
    public PaperCommandManager getCommandManager() {
        return DependencyManager.getCommandManager();
    }

    public void disablePlugin() {
        stop = true;
        getServer().getPluginManager().disablePlugin(this);
    }
}
