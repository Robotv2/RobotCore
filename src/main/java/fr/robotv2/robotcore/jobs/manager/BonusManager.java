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

    public double applyAllBonus(Player player, Job job, Double value, Bonus.Currency currency) {
        value = applyBonus(value, job, globalBonus, currency);
        value = applyBonus(value, job, personalBonus.get(player.getUniqueId()), currency);
        return value;
    }

    public void createPersonalBonus(Player player, Job job, double pourcentage, int minute, Bonus.Currency currency) {
        PersonalBonus bonus = new PersonalBonus(player, job, pourcentage, minute, currency);
        if(personalBonus.containsKey(player.getUniqueId())) {
            personalBonus.get(player.getUniqueId()).add(bonus);
        } else {
            personalBonus.put(player.getUniqueId(), Collections.singletonList(bonus));
        }
    }

    public void createGlobalBonus(Job job, double pourcentage, int minute, Bonus.Currency currency) {
        GlobalBonus bonus = new GlobalBonus(job, pourcentage, minute, currency);
        globalBonus.add(bonus);
    }

    private Double applyBonus(Double value, Job job, List<Bonus> bonusList, Bonus.Currency currency) {
        if(bonusList == null || bonusList.isEmpty())
            return value;
        for(Bonus bonus : bonusList) {
            if(bonus.getJob().equals(job) && !bonus.hasEnded() && bonus.getCurrency() == currency) {
                value = bonus.apply(value);
            }
        }
        return value;
    }
}
