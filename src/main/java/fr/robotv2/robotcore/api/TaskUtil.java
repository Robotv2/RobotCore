package fr.robotv2.robotcore.api;

import fr.robotv2.robotcore.RobotCore;
import org.bukkit.Bukkit;

public class TaskUtil {

    public static void runTask(Runnable runnable, boolean async) {
        if(async)
            Bukkit.getScheduler().runTaskAsynchronously(RobotCore.getInstance(), runnable);
        else
            Bukkit.getScheduler().runTask(RobotCore.getInstance(), runnable);
    }
}
