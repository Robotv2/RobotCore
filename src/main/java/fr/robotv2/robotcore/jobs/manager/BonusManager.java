package fr.robotv2.robotcore.jobs.manager;

import fr.robotv2.robotcore.jobs.impl.bonus.Bonus;
import fr.robotv2.robotcore.jobs.impl.bonus.GlobalBonus;
import fr.robotv2.robotcore.jobs.impl.bonus.PersonalBonus;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import org.bukkit.entity.Player;

import java.util.*;

public class BonusManager {
    private final List<Bonus> globalBonus = new ArrayList<>();
    private final Map<UUID, List<Bonus>> personalBonus = new HashMap<>();

    public double applyAllBonus(Player player, Job job, Double value) {
        value = applyBonus(value, job, globalBonus);
        value = applyBonus(value, job, personalBonus.get(player.getUniqueId()));
        return value;
    }

    public void createPersonalBonus(Player player, Job job, double pourcentage, int minute) {
        PersonalBonus bonus = new PersonalBonus(player, job, pourcentage, minute);
        if(personalBonus.containsKey(player.getUniqueId())) {
            personalBonus.get(player.getUniqueId()).add(bonus);
        } else {
            personalBonus.put(player.getUniqueId(), Collections.singletonList(bonus));
        }
    }

    public void createGlobalBonus(Job job, double pourcentage, int minute) {
        GlobalBonus bonus = new GlobalBonus(job, pourcentage, minute);
        globalBonus.add(bonus);
    }

    private Double applyBonus(Double value, Job job, List<Bonus> bonnuses) {
        for(Bonus bonus : bonnuses) {
            if(bonus.getJob().equals(job) && !bonus.hasEnded()) {
                value = bonus.apply(value);
            }
        }
        return value;
    }
}
