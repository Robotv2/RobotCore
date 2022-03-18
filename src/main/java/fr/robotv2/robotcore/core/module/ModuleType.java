package fr.robotv2.robotcore.core.module;

import fr.robotv2.robotcore.core.RobotCore;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.town.TownModule;

public enum ModuleType {
    JOB(JobModule.class),
    TOWN(TownModule.class);

    private final Class<? extends Module> module;
    ModuleType(Class<? extends Module> module) {
        this.module = module;
    }

    public Class<? extends Module> getModuleClass() {
        return module;
    }

    public boolean isEnabled() {
        return RobotCore.getInstance().getModuleRegistry()
                .getModuleConfiguration().getBoolean("modules." + this.toString().toLowerCase());
    }
}
