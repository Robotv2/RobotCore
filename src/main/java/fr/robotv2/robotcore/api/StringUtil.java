package fr.robotv2.robotcore.api;

import fr.robotv2.robotcore.RobotCore;
import org.bukkit.ChatColor;

public class StringUtil {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        RobotCore.getInstance().getLogger().info(colorize(message));
    }
}
