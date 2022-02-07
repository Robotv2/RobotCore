package fr.robotv2.robotcore.jobs.impl.bonus;

import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;

public class PersonalBonus extends Bonus {

    private final Player player;

    public PersonalBonus(Player player, Job job, double pourcentage, long end) {
        super(job, pourcentage, end);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
