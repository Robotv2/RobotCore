package fr.robotv2.robotcore.jobs.impl.bonus;

import fr.robotv2.robotcore.jobs.impl.job.Job;

public abstract class Bonus {

    private final Job job;
    private final double pourcentage;
    private final long end;
    public Bonus(Job job, double pourcentage, long end) {
        this.job = job;
        this.pourcentage = pourcentage;
        if(end != -1) {
            //TODO calculation
            this.end = end * 1000;
        } else {
            this.end = end;
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

    public double apply(Number number) {
        return number.doubleValue() * (1 + pourcentage);
    }
}
