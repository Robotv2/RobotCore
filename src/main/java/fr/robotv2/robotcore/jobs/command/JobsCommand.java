package fr.robotv2.robotcore.jobs.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;

@CommandAlias("job|jobs")
public class JobsCommand extends BaseCommand {

    private final JobModule module;

    public JobsCommand(JobModule module) {
        this.module = module;
    }

    @Subcommand("join")
    @CommandCompletion("@jobs")
    @CommandPermission("robotcore.job.command.join")
    @Syntax("<job>")
    public void onJobJoin(Player player, Job job) {
        //job doesn't exist.
        if(job == null) {
            module.getJobMessage().sendPath(player, "job-dont-exist");
            return;
        }

        //Has already the job.
        if(module.getPlayerManager().hasJob(player, job)) {
            module.getJobMessage().sendPath(player, "already-has-job");
            return;
        }

        module.getPlayerManager().joinJob(player, job);

        String path = module.getJobMessage().getPath("job-join");
        module.getJobMessage().sendMessage(player, path.replace("%job%", job.getName()));
    }

    @Subcommand("leave")
    @CommandCompletion("@jobs")
    @CommandPermission("robotcore.job.command.quit")
    @Syntax("<job>")
    public void onJoinLeave(Player player, Job job) {
        //job doesn't exist.
        if(job == null) {
            module.getJobMessage().sendPath(player, "job-dont-exist");
            return;
        }

        //Doesn't have the job.
        if(!module.getPlayerManager().hasJob(player, job)) {
            module.getJobMessage().sendPath(player, "dont-has-job");
            return;
        }

        module.getPlayerManager().quitJob(player, job);

        String path = module.getJobMessage().getPath("job-leave");
        module.getJobMessage().sendMessage(player, path.replace("%job%", job.getName()));
    }

    @Subcommand("info")
    @CommandCompletion("@jobs")
    @CommandPermission("robotcore.job.command.info")
    @Syntax("<job>")
    public void onJobInfo(Player player, Job job) {
        //job doesn't exist.
        if(job == null) {
            module.getJobMessage().sendPath(player, "job-dont-exist");
            return;
        }

        //Doesn't have the job.
        if(!module.getPlayerManager().hasJob(player, job)) {
            module.getJobMessage().sendPath(player, "dont-has-job");
            return;
        }

        StringUtil.sendMessage(player, job.getName(), false);
        StringUtil.sendMessage(player, "Level: " + this.module.getLevelManager().getLevel(player, job), false);
        StringUtil.sendMessage(player, "Exp:" + this.module.getLevelManager().getExp(player, job), false);
    }

    @Subcommand("reload")
    @CommandPermission("robotcore.job.command.reload")
    public void onReload(Player player) {
        module.onReload();
        StringUtil.sendMessage(player, "&aThe job module has been reloaded successfully.", true);
    }
}
