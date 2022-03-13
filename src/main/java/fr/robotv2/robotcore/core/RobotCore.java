package fr.robotv2.robotcore.core;

import co.aikar.commands.PaperCommandManager;
import fr.robotv2.robotcore.api.config.Config;
import fr.robotv2.robotcore.api.config.ConfigAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public final class RobotCore extends JavaPlugin {

    public static boolean stop = false;
    public static RobotCore instance;

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

    /**
     * @return an instance of the core.
     */
    public static RobotCore getInstance() {
        return instance;
    }

    public void setInstance(@Nullable RobotCore core) {
        RobotCore.instance = core;
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
