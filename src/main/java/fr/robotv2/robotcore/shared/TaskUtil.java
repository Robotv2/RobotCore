package fr.robotv2.robotcore.shared;

import fr.robotv2.robotcore.core.RobotCore;
import org.bukkit.Bukkit;

public class TaskUtil {

    public static void runTask(Runnable runnable, boolean async) {
        if(async)
            Bukkit.getScheduler().runTaskAsynchronously(RobotCore.getInstance(), runnable);
        else
            Bukkit.getScheduler().runTask(RobotCore.getInstance(), runnable);
    }
}
