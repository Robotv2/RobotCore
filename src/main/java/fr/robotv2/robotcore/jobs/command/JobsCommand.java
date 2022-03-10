package fr.robotv2.robotcore.jobs.command;

import co.aikar.commands.annotation.*;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;
import co.aikar.commands.BaseCommand;

@CommandAlias("job|jobs")
public class JobsCommand extends BaseCommand {

    private final JobModule module;

    public JobsCommand(JobModule module) {
        this.module = module;
    }

    @Subcommand("join")
    @CommandCompletion("@jobs")
    @CommandPermission("robotcore.job.command.join")
    @Syntax("&c&lUSAGE: &c/jobs join <job>")
    public void onJobJoin(Player player, Job job) {
        //job doesn't exist.
        if(job == null)
            return;
        //Has already the job.
        if(module.getPlayerManager().hasJob(player, job))
            return;

        module.getPlayerManager().joinJob(player, job);
    }

    @Subcommand("leave")
    @CommandCompletion("@jobs")
    @CommandPermission("robotcore.job.command.quit")
    @Syntax("&c&lUSAGE: &c/jobs quit <job>")
    public void onJoinLeave(Player player, Job job) {
        //job doesn't exist.
        if(job == null) return;
        //Doesn't have the job.
        if(!module.getPlayerManager().hasJob(player, job))
            return;

        module.getPlayerManager().quitJob(player, job);
    }

    @Subcommand("info")
    @CommandCompletion("@jobs")
    @CommandPermission("robotcore.job.command.info")
    @Syntax("&c&lUSAGE: &c/jobs info <job>")
    public void onJobInfo(Player player, Job job) {
        if(job == null) return;
        //Doesn't have the job.
        if(!module.getPlayerManager().hasJob(player, job)) return;

        StringUtil.sendMessage(player, job.getName(), false);
        StringUtil.sendMessage(player, "Level: " + this.module.getLevelManager().getLevel(player, job), false);
        StringUtil.sendMessage(player, "Exp:" + this.module.getLevelManager().getExp(player, job), false);
    }

    @Subcommand("reload")
    @CommandPermission("robotcore.job.command.reload")
    @Syntax("&c&lUSAGE: &c/jobs reload")
    public void onReload(Player player) {
        module.onReload();
        StringUtil.sendMessage(player, "&aThe job module has been reloaded succesufully.", true);
    }
}
