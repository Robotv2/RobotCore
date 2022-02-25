package fr.robotv2.robotcore.api.module;

import fr.robotv2.robotcore.jobs.JobModule;

public enum ModuleType {
    JOB(JobModule.class);

    private final Class<? extends Module> module;
    ModuleType(Class<? extends Module> module) {
        this.module = module;
    }

    public Class<? extends Module> getModule() {
        return module;
    }

    public boolean isEnabled() {
        return true;
    }
}