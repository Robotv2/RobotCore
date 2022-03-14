package fr.robotv2.robotcore.core;

import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.config.Config;
import fr.robotv2.robotcore.shared.config.ConfigAPI;
import fr.robotv2.robotcore.shared.module.Module;
import fr.robotv2.robotcore.shared.module.ModuleType;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModuleRegistry {

    private final RobotCore core;
    private final Map<ModuleType, Module> modules = new HashMap<>();
    private final Config moduleConfig;

    public ModuleRegistry(RobotCore core) {
        this.core = core;
        this.moduleConfig = ConfigAPI.getConfig("modules");
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

    public void enableModules() {
        Arrays.stream(ModuleType.values()).filter(ModuleType::isEnabled).forEach(this::registerModule);
    }

    public void disableModules() {
        modules.values().forEach(Module::onDisable);
    }

    private void checkModule(ModuleType type) {
        if(!modules.containsKey(type))
            throw new IllegalArgumentException("The type " + type + " isn't registered.");
    }

    public <T extends Module> T getModule(Class<T> clazz, ModuleType type) {
        this.checkModule(type);
        return clazz.cast(modules.get(type));
    }

    /**
     * @return the file configuration of the file 'modules.yml'.
     */
    public FileConfiguration getModuleConfiguration() {
        return moduleConfig.get();
    }
}
