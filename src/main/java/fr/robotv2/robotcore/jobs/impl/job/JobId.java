package fr.robotv2.robotcore.jobs.impl.job;

public record JobId(String id) {
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof JobId id))
            return false;
        return this == id || id.getId().equals(getId());
    }
}
