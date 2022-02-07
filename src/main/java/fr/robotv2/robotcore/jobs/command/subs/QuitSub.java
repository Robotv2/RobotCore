package fr.robotv2.robotcore.jobs.command.subs;

import fr.robotv2.robotcore.api.impl.AbstractSub;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class QuitSub extends AbstractSub {

    @Override
    public String getUsage() {
        return "&c&lUSAGE: &c/jobs quit <job>";
    }

    @Override
    public String getName() {
        return "quit";
    }

    @Override
    public String getPermission() {
        return "robotcore.job.command.quit";
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String[] args) {
        return this.getJobModuleManager().getJobsId();
    }

    @Override
    public void execute(Player player, String[] args) {
        if(!this.checkArguments(player, args, 2)) return;

        String jobId = args[1].toLowerCase(Locale.ROOT);
        Job job = getJobModuleManager().getJob(jobId);

        //job doesn't exist.
        if(!getJobModuleManager().exist(jobId)) return;
        //Doesn't have the job.
        if(!getJobModuleManager().getPlayerManager().hasJob(player, job)) return;

        getJobModuleManager().getPlayerManager().quitJob(player, job);
    }
}
