package fr.robotv2.robotcore.jobs.impl.job;

public record JobId(String id) {
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
