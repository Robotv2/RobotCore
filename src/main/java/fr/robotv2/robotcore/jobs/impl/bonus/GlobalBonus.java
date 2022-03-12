package fr.robotv2.robotcore.jobs.impl.bonus;

import fr.robotv2.robotcore.jobs.impl.Currency;
import fr.robotv2.robotcore.jobs.impl.job.Job;

public class GlobalBonus extends Bonus {
    public GlobalBonus(Job job, double pourcentage, int delay, Currency currency) {
        super(job, pourcentage, delay, currency);
    }
}
