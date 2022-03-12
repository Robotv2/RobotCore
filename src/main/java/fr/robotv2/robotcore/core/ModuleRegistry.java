package fr.robotv2.robotcore.core;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.module.Module;
import fr.robotv2.robotcore.api.module.ModuleType;
import fr.robotv2.robotcore.jobs.JobModule;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.PluginClassLoader;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModuleRegistry {

    private final RobotCore core;
    private final Map<ModuleType, Module> modules = new HashMap<>();
    JobModule jobModule;

    public ModuleRegistry(RobotCore core) {
        this.core = core;
        //Query all available modules and registered them.
        Arrays.stream(ModuleType.values())
                .filter(ModuleType::isEnabled)
                .forEach(this::registerModule);
    }

    public void registerModule(ModuleType type) {
        try {
            Module module = type.getModuleClass().getDeclaredConstructor().newInstance();
            module.onEnable(core);
            modules.put(type, module);
            StringUtil.log("&aThe module " + type + " has been successfully registered and enabled.");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            StringUtil.log("&cAn error occurred while loading the module: " + type.name());
        }
    }

    public void disableModules() {
        modules.values().forEach(Module::onDisable);
    }

    private void checkModule(ModuleType type) {
        if(!modules.containsKey(type))
            throw new IllegalArgumentException("The type " + type + " isn't registered.");
    }

    public JobModule getJobModule() {
        this.checkModule(ModuleType.JOB);
        return (JobModule) modules.get(ModuleType.JOB);
    }

    private <T extends Module> T getModule(Class<T> clazz, ModuleType type) {
        this.checkModule(type);
        return clazz.cast(type.getModuleClass());
    }
}
