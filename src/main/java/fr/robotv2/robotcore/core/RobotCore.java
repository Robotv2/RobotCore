package fr.robotv2.robotcore.core;

import fr.robotv2.robotcore.api.config.ConfigAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class RobotCore extends JavaPlugin {

    private static RobotCore instance;
    private ModuleRegistry registry;

    @Override
    public void onEnable() {
        RobotCore.instance = this;
        ConfigAPI.init(this);
        this.registry = new ModuleRegistry(this);
        DependencyManager.loadDependencies();
    }

    @Override
    public void onDisable() {
        RobotCore.instance = null;
        getModuleRegistry().disableModules();
    }

    public static RobotCore getInstance() {
        return instance;
    }

    public ModuleRegistry getModuleRegistry() {
        return registry;
    }
}
