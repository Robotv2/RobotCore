package fr.robotv2.robotcore.jobs.command.subs;

import fr.robotv2.robotcore.api.impl.AbstractSub;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinSub extends AbstractSub {

    @Override
    public String getUsage() {
        return "&c&lUSAGE: &c/jobs join <job>";
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getPermission() {
        return "robotcore.job.command.join";
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String[] args) {
        return this.getJobModuleManager().getJobsId();
    }

    @Override
    public void execute(Player player, String[] args) {
        if(!this.checkArguments(player, args, 2)) return;

        String jobId = args[1].toLowerCase();
        Job job = getJobModuleManager().getJob(jobId);

        //job doesn't exist.
        if(!getJobModuleManager().exist(jobId)) return;
        //Has already the job.
        if(getJobModuleManager().getPlayerManager().hasJob(player, job)) return;

        getJobModuleManager().getPlayerManager().joinJob(player, job);
    }
}
