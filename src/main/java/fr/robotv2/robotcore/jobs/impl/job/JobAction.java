package fr.robotv2.robotcore.jobs.impl.job;

import fr.robotv2.robotcore.core.RobotCore;
import fr.robotv2.robotcore.core.module.ModuleType;
import fr.robotv2.robotcore.jobs.JobModule;

public enum JobAction {
    BREAK,
    PLACE,
    KILL,
    HARVEST_PLANT,
    HARVEST_BREAK,
    FISHING;

    public String getTranslation() {
        return RobotCore.getInstance().getModuleRegistry()
                .getModule(JobModule.class, ModuleType.JOB).getJobMessage().getPath(this.toString());
    }
}
