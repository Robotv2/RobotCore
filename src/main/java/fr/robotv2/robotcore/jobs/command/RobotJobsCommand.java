package fr.robotv2.robotcore.jobs.command;

import fr.robotv2.robotcore.api.impl.AbstractCommand;
import fr.robotv2.robotcore.api.impl.AbstractSub;
import fr.robotv2.robotcore.jobs.command.subs.InfoSub;
import fr.robotv2.robotcore.jobs.command.subs.JoinSub;
import fr.robotv2.robotcore.jobs.command.subs.QuitSub;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class RobotJobsCommand extends AbstractCommand {

    private final Set<AbstractSub> subs = new HashSet<>();

    public RobotJobsCommand(JavaPlugin plugin, String name) {
        super(plugin, name);

        AbstractSub joinSub = new JoinSub();
        AbstractSub quitSub = new QuitSub();
        AbstractSub infoSub = new InfoSub();
        subs.addAll(Set.of(joinSub, quitSub, infoSub));
        this.afterLoadSubs();
    }

    @Override
    public String getNoPermissionMessage() {
        return "&cYou don't have the permission to execute this command.";
    }

    @Override
    public Set<AbstractSub> getSubs() {
        return subs;
    }

    @Override
    public boolean enableTabCompleter() {
        return true;
    }
}
