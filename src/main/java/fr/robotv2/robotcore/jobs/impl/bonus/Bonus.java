package fr.robotv2.robotcore.jobs.impl.bonus;

import fr.robotv2.robotcore.jobs.impl.job.Job;

public abstract class Bonus {

    private final Job job;
    private final double pourcentage;
    private final long end;
    private Currency currency;

    public enum Currency {
        EXP, MONEY;
    }

    public Bonus(Job job, double pourcentage, int delay, Currency currency) {
        this.job = job;
        this.pourcentage = pourcentage;
        this.currency = currency;
        if(delay != -1) {
            this.end = System.currentTimeMillis() + (delay * 60000L);
        } else {
            this.end = -1;
        }
    }

    public Job getJob() {
        return job;
    }
    
    public double getPourcentage() {
        return pourcentage;
    }

    public Long getEnd() {
        return end;
    }

    public boolean hasEnded() {
        if(end == -1) return false;
        return getEnd() > System.currentTimeMillis();
    }

    public long getRemaining() {
        return this.end - System.currentTimeMillis();
    }

    public Currency getCurrency() {
        return currency;
    }

    public double apply(Number number) {
        return number.doubleValue() * (1 + getPourcentage() / 100);
    }
}
