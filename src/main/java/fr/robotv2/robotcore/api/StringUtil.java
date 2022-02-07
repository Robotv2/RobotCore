package fr.robotv2.robotcore.api;

import fr.robotv2.robotcore.RobotCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StringUtil {

    private static String PREFIX = "&c&lROBOTCORE &8&l- ";

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        RobotCore.getInstance().getLogger().info(colorize(message));
    }

    public static String getPrefix() {
        return StringUtil.colorize(PREFIX);
    }

    public static void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public static void sendMessage(CommandSender sender, String message, boolean prefix) {
        if(prefix)
            sender.sendMessage(getPrefix() + colorize(message));
        else
            sender.sendMessage(colorize(message));
    }
}
