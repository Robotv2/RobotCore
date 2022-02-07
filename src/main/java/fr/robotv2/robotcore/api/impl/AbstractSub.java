package fr.robotv2.robotcore.api.impl;

import fr.robotv2.robotcore.RobotCore;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.JobModuleManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AbstractSub {
    public abstract String getUsage();
    public abstract String getName();
    public abstract String getPermission();
    public abstract List<String> getTabComplete(CommandSender sender, String[] args);

    public void execute(CommandSender sender, String[] args) {}
    public void execute(Player player, String[] args) {}

    public boolean checkArguments(CommandSender sender, String[] args, int min) {
        if(args.length < min) {
            sender.sendMessage(StringUtil.colorize(getUsage()));
            return false;
        }
        return true;
    }

    public JobModuleManager getJobModuleManager() {
        return RobotCore.getInstance().getJobModule();
    }
}
